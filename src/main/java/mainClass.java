import appLogic.AppState;
import appLogic.MessageInterpreter;
import appLogic.RACriticalSection;
import networking.InputConnectionManager;
import networking.OutputConnectionManager;

import java.io.IOException;

public class mainClass {
    public static void main(String[] args) throws InterruptedException {

        RACriticalSection criticalSection = new RACriticalSection();
        RACriticalSection.setApplicationState(AppState.idle);

        InputConnectionManager inputConnectionManager = new InputConnectionManager(2003, 4, new MessageInterpreter());
        new Thread(inputConnectionManager).start();

//        OutputConnectionManager ocm = new OutputConnectionManager(4);
//        ocm.connectToServer("127.0.0.1", 2001);
//
//        while (true) {
//            Thread.sleep(1500);
//            try {
//                ocm.getMessageSender("127.0.0.1").writeMessageToClient("asdasdasd");
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            }
//        }
    }
}
