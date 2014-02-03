package networking;

import appLogic.Application;
import json.Message;
import org.json.simple.JSONObject;

import java.net.InetAddress;
import java.util.ArrayList;
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

    public void startMultipleOutputConnections(ArrayList addressesAndPorts) {
        JSONObject addressAndPort;
        for (int i = 0; i < addressesAndPorts.size(); i++) {
            try {
                addressAndPort = (JSONObject) addressesAndPorts.get(i);
                connectToServer(addressAndPort.get("address").toString(), Application.getIntOrNull((Long) addressAndPort.get("port")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void connectToServer(String ipAddress, int port) {
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
