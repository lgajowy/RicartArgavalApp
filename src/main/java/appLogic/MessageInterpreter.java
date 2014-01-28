package appLogic;

import appLogic.interfaces.IMessageArrivedListener;
import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.strategies.EnteringStrategy;
import appLogic.strategies.IdleStrategy;
import appLogic.strategies.OccupiedSectionStrategy;
import appLogic.utils.Order;
import json.Message;
import json.MessageParser;
import networking.events.MessageArrived;

import java.net.InetAddress;

public class MessageInterpreter implements IMessageArrivedListener {

    private DeferredMessagesManager defferedMsgManager;

    public MessageInterpreter(DeferredMessagesManager msgManager) {
        this.defferedMsgManager = msgManager;
    }

    @Override
    public void onMessageArrived(MessageArrived event) {
        System.out.println(event.getMessage() + "<= it was printed from Message interpreter");
        Message receivedMsg = parseJsonMessageOrNull(event);
        try {
            handleMessage(receivedMsg, event.getINetAddress());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private Message parseJsonMessageOrNull(MessageArrived event) {
        MessageParser parser = new MessageParser(event.getMessage());
        return parser.parse();
    }

    private void handleMessage(Message obtainedMessage, InetAddress iNetAddress) {
       // LogicalClock.synchronize(obtainedMessage.getClockValue());  //TODO: Fixme! w przypadku, gdy przyjdzie order a ja bede chciał wejść - nie mogę porównać dobrze czasu!

        if (obtainedMessage != null) {
            switch (obtainedMessage.getMessageType()) {
                case ok:
                    getStrategy().handleOkMessage(iNetAddress);   ///TODO: Don't i have to pass logical clock value here?? (as below new eg. Ok())
                    break;
                case order:
                    getStrategy().handleOrderMessage(new Order(iNetAddress, obtainedMessage.getClockValue()));
                    break;
                case unknown:
                    System.out.println("Ignoring unknown message");
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            System.err.println("Wrong message format!");
        }
    }

    public IMessageHandlingStrategy getStrategy() {
        switch (RACriticalSection.getRaSectionState()) {
            case idle:
                return new IdleStrategy();
            case occupiedSection:
                return new OccupiedSectionStrategy(defferedMsgManager);
            case enteringSection:
                return new EnteringStrategy(defferedMsgManager);
        }
        return null;
    }
}
