package appLogic;

import java.util.Timer;
import java.util.TimerTask;

public class SectionResidence {

    public SectionResidence(int maxResidenceTime, final RACriticalSection section) {
        int occupationTime = (int) (maxResidenceTime * Math.random());
        System.out.println("Entered section.");
        new Timer().schedule(new ExitSectionTask(section), occupationTime);
    }

    class ExitSectionTask extends TimerTask {
        private RACriticalSection section;

        ExitSectionTask(RACriticalSection section) {
            this.section = section;
        }

        @Override
        public void run() {
            System.out.println("Leaving section.");
            section.leave();
        }
    }
}
