package appLogic.strategies;

import appLogic.DeferredMessagesManager;
import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.utils.Order;

import java.net.InetAddress;

public class OccupiedSectionStrategy implements IMessageHandlingStrategy {

    private final DeferredMessagesManager deferedMsgManager;

    public OccupiedSectionStrategy(DeferredMessagesManager defferedMsgManager) {
        this.deferedMsgManager = defferedMsgManager;
    }

    @Override
    public void handleOrderMessage(Order incomingOrder) {
        deferOrder(incomingOrder);
    }

    private void deferOrder(Order incomingOrder) {
        deferedMsgManager.putOrderInDeferredQueue(incomingOrder);
    }

    @Override
    public void handleOkMessage(InetAddress incommingMsgAddress) {
        //Ignore message
    }

}
