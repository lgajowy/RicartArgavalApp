package appLogic;

import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.strategies.EnteringStrategy;
import appLogic.strategies.IdleStrategy;
import appLogic.strategies.OccupiedSectionStrategy;

import appLogic.strategies.states.ApplicationStates;
import appLogic.utils.Order;
import json.Message;
import json.utils.MessageType;
import networking.InputConnectionManager;
import networking.OutputConnectionManager;

import java.util.LinkedList;
import java.util.Queue;


public class RACriticalSection {

    private static ApplicationStates applicationState = ApplicationStates.idle;
    private static Queue<Order> deferredOrders = new LinkedList<Order>();
    private static MessageInterpreter messageInterpreter;

    public void enter() {

    }

    public void leave() {

        allowDeferredNodesEnterSection();
        setApplicationState(ApplicationStates.idle);
    }

    private void allowDeferredNodesEnterSection() {
        for (Order deferredOrder : deferredOrders) {
            OutputConnectionManager.sendMessageToOneNode(new Message(deferredOrder.getClockValue(), MessageType.ok), deferredOrder.getAddress());
        }
    }

    public static void putOrderInDeferredQueue(Order order) {
        deferredOrders.add(order);
    }

    private static void setApplicationState(ApplicationStates applicationState) {
        RACriticalSection.applicationState = applicationState;
    }

    public static ApplicationStates getApplicationState() {
        return applicationState;
    }

    public static IMessageHandlingStrategy getStrategy() {
        switch (applicationState) {
            case idle:
                return new IdleStrategy();
            case occupiedSection:
                return new OccupiedSectionStrategy();
            case enteringSection:
                return new EnteringStrategy();
        }
        return null;
    }

    public static void main(String[] args) {

        OutputConnectionManager outputConnectionManager = new OutputConnectionManager(4);   //todo: read from config
        outputConnectionManager.connectToServer("127.0.0.1", 2001);
        messageInterpreter = new MessageInterpreter();
        InputConnectionManager inputConnectionManager = new InputConnectionManager(2003, 4, messageInterpreter); //todo: read from config
        new Thread(inputConnectionManager).start();

        new RACriticalSection();
        setApplicationState(ApplicationStates.occupiedSection);
    }
}
