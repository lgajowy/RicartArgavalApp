package appLogic.strategies;

import appLogic.LogicalClock;
import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.utils.Order;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

import java.net.InetAddress;

public class IdleStrategy implements IMessageHandlingStrategy {

    @Override
    public void handleOrderMessage(Order incomingOrder) {
        LogicalClock.increment();
        System.out.println("sending order idle state");
        OutputConnectionManager.sendMessageToNode(new Message(LogicalClock.getValue(), MessageType.ok), incomingOrder.getAddress());
    }

    @Override
    public void handleOkMessage(InetAddress incommingMsgAddress) {
    }
}
