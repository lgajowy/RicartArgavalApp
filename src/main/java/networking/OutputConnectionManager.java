package networking;

import json.Message;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OutputConnectionManager {

    private static ExecutorService messageThreads;
    private static HashMap<String, MessageSender> messageSenders;

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

    public static void sendMessageToAllConnectedNodes(Message message) {
        for (Map.Entry<String, MessageSender> msgSender : messageSenders.entrySet()) {
            msgSender.getValue().writeMessageToClient(message.toString());
        }
    }

    public static void sendMessageToNode(Message message, InetAddress address) {
        MessageSender ms = messageSenders.get(address.getHostAddress());
        ms.writeMessageToClient(message.toString());
    }
}
