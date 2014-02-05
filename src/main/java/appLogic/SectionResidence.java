package appLogic;

import java.util.Timer;
import java.util.TimerTask;

public class SectionResidence {

    public SectionResidence(int maxResidenceTime, final RACriticalSection section) {
        System.out.println("ENTERED SECTION.");
        new Timer().schedule(new ExitSectionTask(section), maxResidenceTime);
    }

    class ExitSectionTask extends TimerTask {
        private RACriticalSection section;

        ExitSectionTask(RACriticalSection section) {
            this.section = section;
        }

        @Override
        public void run() {
            System.out.println("LEAVING SECTION.");
            section.leave();
        }
    }
}
