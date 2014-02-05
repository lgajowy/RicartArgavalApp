package appLogic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class OkMessageManagerForEnteringState {

    private Timer okAwaitingTimer;
    private ArrayList<String> addresses;
    private Object okAnswerSync;
    private boolean areOksCollected;
    private HashSet<String> otherNodesAddresses = new HashSet<String>();

    public OkMessageManagerForEnteringState() {
        addresses = new ArrayList<String>();
        okAnswerSync = new Object();
        okAwaitingTimer = new Timer();
        areOksCollected = false;
    }

    public void recordOkAnswerFromNode(String address) {
        synchronized (okAnswerSync) {
            if (!addresses.contains(address)) {
                addresses.add(address);
                otherNodesAddresses.remove(address);
                System.out.println("STILL WAITING FOR: " + otherNodesAddresses);

                if (addresses.size() == Application.getTotalNumberOfNeighborNodes()) {
                    System.out.println("Got all ok's! Proceeding...");
                    okAnswerSync.notify();
                    areOksCollected = true;
                    addresses = new ArrayList<String>();
                }
            }
        }
    }

    public void waitForAllOkAnswersOrForTimeout() {
        otherNodesAddresses = Application.getOtherNodesAddresses();
        okAwaitingTimer.schedule(new WaitingForOkEnded(), Application.getOccupationTime() * Application.getTotalNumberOfNeighborNodes() + 1);
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
                if (!areOksCollected) {
                    System.out.println("Waiting for all ok's from user ended. ");
                }
                areOksCollected = false;
                okAnswerSync.notify();
                addresses = new ArrayList<String>();
            }
        }
    }
}
