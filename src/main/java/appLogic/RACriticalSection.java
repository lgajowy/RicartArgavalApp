package appLogic;

import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.strategies.EnteringStrategy;
import appLogic.strategies.IdleStrategy;
import appLogic.strategies.OccupiedSectionStrategy;
import com.sun.org.apache.xpath.internal.SourceTree;
import networking.InputConnectionManager;
import networking.OutputConnectionManager;

public class RACriticalSection {

    private static AppState applicationState = AppState.idle;

    private LogicalClock applicationLogicalClock;

    private MessageInterpreter incommingMsgInterpreter;



    public RACriticalSection() {
        this.applicationLogicalClock = new LogicalClock();
        this.incommingMsgInterpreter = new MessageInterpreter();

    }

    public void enter() {

    }

    public void leave() {

    }

    private static void setApplicationState(AppState applicationState) {
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

    public static void main(String[] args) {

        LogicalClock clock = new LogicalClock();
        OutputConnectionManager outputConnectionManager = new OutputConnectionManager(4);   //todo: read from config
        outputConnectionManager.connectToServer("127.0.0.1", 2001);
        InputConnectionManager inputConnectionManager = new InputConnectionManager(2003, 4, new MessageInterpreter()); //todo: read from config
        new Thread(inputConnectionManager).start();

        new RACriticalSection();

    }
}
