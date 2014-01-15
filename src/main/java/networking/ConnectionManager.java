package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager {

    private ExecutorService clientThreadExecutorService;
    private HashMap<String, ClientThread> currentlyRunningClients;

    private ServerSocket socket;
    private boolean isWorking;


    public ConnectionManager(int portToListenOn, int expectedClientsAmount) {
        clientThreadExecutorService = Executors.newFixedThreadPool(expectedClientsAmount);
        currentlyRunningClients = new HashMap<String, ClientThread>();
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

            ClientThread clientThread = new ClientThread(clientSocket);
            currentlyRunningClients.put(clientSocket.getInetAddress().getHostAddress(), clientThread);
            clientThreadExecutorService.submit(clientThread);
            //TODO: RISK! Jeżeli wywolam w tym miejscu clientThread.writeMessage("a maszz brzydalu!") to czy sie może spieprzyc?!
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
