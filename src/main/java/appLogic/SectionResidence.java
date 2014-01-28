package appLogic;

import java.util.Timer;
import java.util.TimerTask;

public class SectionResidence {
    private Timer residingTimeCounter;
    private  RACriticalSection section;

    public SectionResidence(int maxResidenceTime, final RACriticalSection section) {
        this.section = section;
        residingTimeCounter = new Timer();

        int time = (int) (maxResidenceTime * Math.random());
        System.out.println("Entered section.");
        residingTimeCounter.schedule(new ExitSectionTask(), time);
    }

    class ExitSectionTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Leaving section.");
            section.leave();
        }
    }

}
