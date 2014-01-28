package networking;

import appLogic.interfaces.IMessageArrivedListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputConnectionManager implements Runnable {

    private ExecutorService messageThreads;
    private HashMap<String, MessageReceiver> messageReceivers;
    private ServerSocket socket;
    private boolean isWorking;
    private int portToListenOn;
    private IMessageArrivedListener jsonMessageHandler;
    private String hostAddress;

    public InputConnectionManager(int portToListenOn, int expectedUsersAmount, IMessageArrivedListener messageInterpreter) {
        this.portToListenOn = portToListenOn;
        this.messageThreads = Executors.newFixedThreadPool(expectedUsersAmount);
        this.messageReceivers = new HashMap<String, MessageReceiver>();
        this.jsonMessageHandler = messageInterpreter;
    }

    private void listenForClientsWillingToConnectWithMe() throws IOException {
        while (isWorking) {
            Socket clientSocket = socket.accept();
            MessageReceiver reader = new MessageReceiver(clientSocket, this.jsonMessageHandler);
            messageThreads.submit(reader);
            hostAddress = clientSocket.getInetAddress().getHostAddress();
            messageReceivers.put(hostAddress, reader);
            System.err.println("Accepted connection: " + hostAddress);
        }
    }

    public MessageReceiver getMessageReceiver(String ipAddress) {
        return messageReceivers.get(ipAddress);
    }

    @Override
    public void run() {
        try {
            socket = new ServerSocket(portToListenOn);
            isWorking = true;
            listenForClientsWillingToConnectWithMe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
