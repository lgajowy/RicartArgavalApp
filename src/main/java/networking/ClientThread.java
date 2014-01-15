package networking;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private PrintWriter writerToClient;
    private BufferedReader readerOfDataFromClient;
    private boolean isWorking;

    public ClientThread(Socket clientSocket) {
        try {
            writerToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            readerOfDataFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        isWorking = true;
        while (isWorking) {
            listenToClientMessages();
        }
    }

    private void listenToClientMessages() {
        try {
            String line = readerOfDataFromClient.readLine();
            System.out.println(line);   //TODO: Trigger event after receiving json!

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(String message) {
        writerToClient.println(message);
    }

}
