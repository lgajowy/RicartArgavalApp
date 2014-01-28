package appLogic;

import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.states.SectionState;
import appLogic.strategies.EnteringStrategy;
import appLogic.strategies.IdleStrategy;
import appLogic.strategies.OccupiedSectionStrategy;
import appLogic.utils.Order;
import json.Message;
import json.utils.MessageType;
import networking.OutputConnectionManager;

import java.util.LinkedList;
import java.util.Queue;

public class RACriticalSection {
    private static SectionState raSectionState = SectionState.idle;
    private static Queue<Order> deferredOrders = new LinkedList<Order>();

    public void preEnter() {
        setRaSectionState(SectionState.enteringSection);
        OutputConnectionManager.sendMessageToAllConnectedNodes(new Message(LogicalClock.getValue(), MessageType.order));

        //TODO: timer?

    }

    public void enter() {
        new SectionResidence(Application.getOccuptaionTime(), this);
    }

    public void leave() {
        setRaSectionState(SectionState.idle);
        allowDeferredNodesEnterSection();
    }

    private void allowDeferredNodesEnterSection() {
        for (Order deferredOrder : deferredOrders) {
            OutputConnectionManager.sendMessageToNode(new Message(deferredOrder.getClockValue(), MessageType.ok), deferredOrder.getAddress());
        }
    }

    public static void putOrderInDeferredQueue(Order order) {
        deferredOrders.add(order);
    }

    private static void setRaSectionState(SectionState raSectionState) {
        RACriticalSection.raSectionState = raSectionState;
    }

    private static SectionState getRaSectionState() {
        return raSectionState;
    }

    public static IMessageHandlingStrategy getStrategy() {
        switch (getRaSectionState()) {
            case idle:
                return new IdleStrategy();
            case occupiedSection:
                return new OccupiedSectionStrategy();
            case enteringSection:
                return new EnteringStrategy();
        }
        return null;
    }
}
