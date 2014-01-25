package appLogic;

import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.strategies.EnteringStrategy;
import appLogic.strategies.IdleStrategy;
import appLogic.strategies.OccupiedSectionStrategy;

public class RACriticalSection {

    private LogicalClock applicationLogicalClock;
    private static AppState applicationState = AppState.idle;

    public RACriticalSection() {
        this.applicationLogicalClock = new LogicalClock();
    }

    public void enter() {


    }

    public void leave() {

    }

    public static void setApplicationState(AppState applicationState) {
        RACriticalSection.applicationState = applicationState;
    }

    public static AppState getApplicationState() {
        return applicationState;
    }

    public static IMessageHandlingStrategy getStrategy() {
        switch (applicationState) {
            case idle:
                return new IdleStrategy();
            case occupiedSection:
                return new OccupiedSectionStrategy();
            case enteringSection:
                return new EnteringStrategy();
        }
        return null;
    }
}
