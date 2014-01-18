package appLogic.interfaces;

import networking.events.MessageArrived;

import java.util.EventListener;

public interface IMessageArrivedListener extends EventListener {
    void onMessageArrived(MessageArrived event);
}
