package appLogic.strategies;

import appLogic.DeferredMessagesManager;
import appLogic.RACriticalSection;
import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.utils.Order;

import java.net.InetAddress;

public class OccupiedSectionStrategy implements IMessageHandlingStrategy {

    private final DeferredMessagesManager defferedMsgManager;

    public OccupiedSectionStrategy(DeferredMessagesManager defferedMsgManager) {
        this.defferedMsgManager = defferedMsgManager;
    }

    @Override
    public void handleOrderMessage(Order incomingOrder) {
        deferOrder(incomingOrder);
    }

    private void deferOrder(Order incomingOrder) {
        System.out.println("deferring order");
        defferedMsgManager.putOrderInDeferredQueue(incomingOrder);
    }

    @Override
    public void handleOkMessage(InetAddress incommingMsgAddress) {
        // If this app is in critical section, ok messages should be ignored
    }

}
