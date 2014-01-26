package appLogic;

import appLogic.interfaces.IMessageArrivedListener;
import json.Message;
import json.MessageParser;
import networking.events.MessageArrived;

public class MessageInterpreter implements IMessageArrivedListener {

    @Override
    public void onMessageArrived(MessageArrived event) {
        System.out.println(event.getMessage() + "<= it was printed from Message interpreter");
        Message receivedMsg = parseJsonMessageOrNull(event);
        try {
            handleMessage(receivedMsg);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private Message parseJsonMessageOrNull(MessageArrived event) {
        MessageParser parser = new MessageParser(event.getMessage());
        return parser.parse();
    }

    private void handleMessage(Message obtainedMessage) {
        if (obtainedMessage != null) {
            switch (obtainedMessage.getMessageType()) {
                case ok:
                    RACriticalSection.getStrategy().handleOkMessage();
                    break;
                case order:
                    RACriticalSection.getStrategy().handleOrderMessage();
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
}
