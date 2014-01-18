package networking;

/**
 * Created by lukasz on 1/18/14.
 */
public class CommunicationChannel {
    private MessageReceiver reader;
    private MessageSender sender;

    public CommunicationChannel(MessageReceiver reader, MessageSender sender) {
        this.reader = reader;
        this.sender = sender;
    }

    public void sendMessage(String message) {
        sender.writeMessageToClient(message);
    }
}
