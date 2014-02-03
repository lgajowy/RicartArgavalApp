package appLogic.strategies;

import appLogic.Application;
import appLogic.DeferredMessagesManager;
import appLogic.LogicalClock;
import appLogic.OkMessageManagerWhileEntering;
import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.utils.Order;
import com.google.common.net.InetAddresses;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

import java.net.InetAddress;

public class EnteringStrategy implements IMessageHandlingStrategy {

    private final DeferredMessagesManager defferedMsgManager;
    private final OkMessageManagerWhileEntering okRecorder;

    public EnteringStrategy(DeferredMessagesManager defferedMsgManager, OkMessageManagerWhileEntering okRecorder) {
        this.defferedMsgManager = defferedMsgManager;
        this.okRecorder = okRecorder;
    }

    @Override
    public void handleOrderMessage(Order incomingOrder) {
        if (LogicalClock.getValue() > incomingOrder.getClockValue()) {
            OutputConnectionManager.sendMessageToNode(new Message(LogicalClock.getValue(), MessageType.ok), incomingOrder.getAddress());
        } else if (LogicalClock.getValue() < incomingOrder.getClockValue()) {
            deferOrder(incomingOrder);
        } else {
            if (hasSmallerAddressThanThisApp(incomingOrder.getAddress())) {
                OutputConnectionManager.sendMessageToNode(new Message(LogicalClock.getValue(), MessageType.ok), incomingOrder.getAddress());
                //TODO: What about my order..
            } else {
                deferOrder(incomingOrder);
            }
        }
    }

    private boolean hasSmallerAddressThanThisApp(InetAddress address) {
        if (InetAddresses.coerceToInteger(address) < InetAddresses.coerceToInteger(Application.getThisNodeIPAddress())) {
            return true;
        }
        return false;
    }

    private void deferOrder(Order incomingOrder) {
        System.out.println("deferring order");
        defferedMsgManager.putOrderInDeferredQueue(incomingOrder);
    }

    @Override
    public void handleOkMessage(InetAddress incommingMsgAddress) {
        okRecorder.recordOkAnswerFromNode(incommingMsgAddress.getHostName());
    }
}
