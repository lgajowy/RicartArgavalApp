package appLogic;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OkMessageManagerForEnteringState {

    private Timer okAwaitingTimer;
    private ArrayList<String> addresses;
    private Object okAnswerSync;

    public OkMessageManagerForEnteringState() {
        addresses = new ArrayList<String>();
        okAnswerSync = new Object();
        okAwaitingTimer = new Timer();
    }

    public void recordOkAnswerFromNode(String address) {
        synchronized (okAnswerSync) {
            if (!addresses.contains(address)) {
                addresses.add(address);
                if (addresses.size() == Application.getTotalNumberOfNeighborNodes()) {
                    System.out.println("Got all ok's! Proceeding...");
                    okAnswerSync.notify();
                    addresses = new ArrayList<String>();
                }
            }
        }
    }

    public void waitForAllOkAnswersOrForTimeout() {
        okAwaitingTimer.schedule(new WaitingForOkEnded(), Application.getOccupationTime() * Application.getTotalNumberOfNeighborNodes());
        synchronized (okAnswerSync) {
            System.out.println("Waiting for all nodes approval...");
            try {
                okAnswerSync.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class WaitingForOkEnded extends TimerTask {
        @Override
        public void run() {
            synchronized (okAnswerSync) {
                System.out.println("Waiting for all ok's from user ended. Entering section (if not yet there...)");
                okAnswerSync.notify();
                addresses = new ArrayList<String>();
            }
        }
    }
}
