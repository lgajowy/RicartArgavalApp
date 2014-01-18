package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager {

    private ExecutorService messageReaderThreadExecutorService;
    private HashMap<String, CommunicationChannel> activeCommunicationChannels;

    private ServerSocket socket;
    private boolean isWorking;

    public ConnectionManager(int portToListenOn, int expectedClientsAmount) {
        messageReaderThreadExecutorService = Executors.newFixedThreadPool(expectedClientsAmount);
        activeCommunicationChannels = new HashMap<String, CommunicationChannel>();

        try {
            socket = new ServerSocket(portToListenOn);
            isWorking = true;
            listenForClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForClients() throws IOException {
        while (isWorking) {
            Socket clientSocket = socket.accept();
            MessageSender sender = new MessageSender(clientSocket);
            MessageReceiver reader = new MessageReceiver(clientSocket);
            CommunicationChannel channel = new CommunicationChannel(reader, sender);
            messageReaderThreadExecutorService.submit(reader);
            activeCommunicationChannels.put(clientSocket.getInetAddress().getHostAddress(), channel);
        }
    }
}
