package appLogic;

import appLogic.interfaces.IMessageArrivedListener;
import networking.events.MessageArrived;

public class MessageInterpreter implements IMessageArrivedListener {

    @Override
    public void onMessageArrived(MessageArrived event) {
        System.out.println(event.getMessage() + "<= it was printed from Message interpreter");

    }
}
