package appLogic;

import appLogic.interfaces.IMessageArrivedListener;
import networking.events.MessageArrived;

/**
 * Created by lukasz on 1/18/14.
 */
public class MessageInterpreter implements IMessageArrivedListener{

    @Override
    public void onMessageArrived(MessageArrived event) {
        System.out.println(event.getMessage() + "<= it was printed from Message interpreter");

    }
}
