package appLogic;

import appLogic.states.SectionState;
import appLogic.utils.Order;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

import java.util.LinkedList;
import java.util.Queue;

public class DeferredMessagesManager {
    private  Queue<Order> deferredOrders = new LinkedList<Order>();

    public void allowDeferredNodesEnterSection() {
        for (Order deferredOrder : deferredOrders) {
            OutputConnectionManager.sendMessageToNode(new Message(deferredOrder.getClockValue(), MessageType.ok), deferredOrder.getAddress());

        }
        deferredOrders = new LinkedList<Order>();
    }

    public synchronized void putOrderInDeferredQueue(Order order) {
        deferredOrders.add(order);
    }
}
