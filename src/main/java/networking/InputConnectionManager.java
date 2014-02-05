package networking;

import appLogic.interfaces.IMessageArrivedListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputConnectionManager implements Runnable {

    private ExecutorService inputConnectionThreadPool;
    private HashMap<InetAddress, MessageReceiver> messageReceivers;
    private ServerSocket socket;
    private boolean isWorking;
    private int portToListenOn;
    private IMessageArrivedListener jsonMessageHandler;
    private InetAddress hostAddress;

    public InputConnectionManager(int portToListenOn, int expectedUsersAmount, IMessageArrivedListener messageInterpreter) {
        this.portToListenOn = portToListenOn;
        this.inputConnectionThreadPool = Executors.newFixedThreadPool(30); //TODO Threads dont close properly. FIXME!
        this.messageReceivers = new HashMap<InetAddress, MessageReceiver>();
        this.jsonMessageHandler = messageInterpreter;
    }

    private void listenForClientsWillingToConnectWithMe() throws IOException {
        while (isWorking) {
            Socket clientSocket = socket.accept();
            MessageReceiver reader = new MessageReceiver(clientSocket, this.jsonMessageHandler);
            inputConnectionThreadPool.submit(reader);
            hostAddress = clientSocket.getInetAddress();
            messageReceivers.put(hostAddress, reader);
            System.err.println("Accepted connection: " + hostAddress);
        }
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
