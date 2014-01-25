package networking;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OutputConnectionManager {

    private ExecutorService messageThreads;
    private HashMap<String, MessageSender> messageSenders;

    public OutputConnectionManager(int expectedUsersAmount) {
        this.messageThreads = Executors.newFixedThreadPool(expectedUsersAmount);
        this.messageSenders = new HashMap<String, MessageSender>();
    }

    public void connectToServer(String ipAddress, int port) {
        try {
            MessageSender sender = new MessageSender(ipAddress, port);
            messageThreads.submit(sender);
            messageSenders.put(ipAddress, sender);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MessageSender getMessageSender(String ipAddress) {
        return messageSenders.get(ipAddress);
    }
}
