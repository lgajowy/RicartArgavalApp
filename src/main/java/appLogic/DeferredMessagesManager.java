package appLogic;

import appLogic.utils.Order;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

import java.util.LinkedList;
import java.util.Queue;

public class DeferredMessagesManager {
    private Queue<Order> deferredOrders = new LinkedList<Order>();

    public void allowDeferredNodesEnterSection() {
        for (Order deferredOrder : deferredOrders) {
            OutputConnectionManager.sendMessageToNode(new Message(deferredOrder.getClockValue(), MessageType.ok), deferredOrder.getAddress());

        }
        deferredOrders = new LinkedList<Order>();
    }

    public synchronized void putOrderInDeferredQueue(Order order) {
        if (!deferredOrders.contains(order)) {
            System.out.println("DEFERING ORDER FROM: " + order.getAddress().getHostAddress().toString());
            deferredOrders.add(order);
        } else {
            System.out.println("ORDER FROM : " + order.getAddress().getHostAddress().toString() + " IS ALREADY IN QUEUE.");
        }
    }
}
