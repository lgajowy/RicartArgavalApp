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

    private static ExecutorService outputConnectionsThreadPool;
    private static HashMap<InetAddress, MessageSender> messageSenders;

    public OutputConnectionManager(int connectionThreadsAmount) {
        this.outputConnectionsThreadPool = Executors.newFixedThreadPool(connectionThreadsAmount * 2);
        this.messageSenders = new HashMap<InetAddress, MessageSender>();
    }

    public static void sendMessageToNode(final Message message, final InetAddress address) {
        outputConnectionsThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                messageSenders.get(address).writeMessageToClient(message.toString());
            }
        });
    }

    public static void sendMessageToAllConnectedNodes(final Message message) {
        for (Map.Entry<InetAddress, MessageSender> msgSender : messageSenders.entrySet()) {
            sendMessageToNode(message, msgSender.getKey());
        }
    }

    private void connectToServer(InetAddress address, int port) {
        try {
            MessageSender sender = new MessageSender(address.getHostAddress(), port);
            outputConnectionsThreadPool.submit(sender);
            messageSenders.put(address, sender);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startMultipleOutputConnections(ArrayList addressesAndPorts) {
        JSONObject addressAndPort;
        for (int i = 0; i < addressesAndPorts.size(); i++) {
            try {
                addressAndPort = (JSONObject) addressesAndPorts.get(i);
                connectToServer(InetAddress.getByName(addressAndPort.get("address").toString()), Application.getIntOrNull((Long) addressAndPort.get("port")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
