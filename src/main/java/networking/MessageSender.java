package networking;

import javax.net.ssl.SSLSocket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class MessageSender implements Runnable {

    private String ipAddress;
    private int port;
    private Socket outputSocket;
    private DataOutputStream outToServer;

    public MessageSender(Socket outputSocket) {
        this.outputSocket = outputSocket;
        try {
            outToServer = new DataOutputStream(outputSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageSender(String ipAddress, int port) throws InterruptedException {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            establishConnection(ipAddress, port);
            System.out.println("connection established!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void establishConnection(String ipAddress, int port) throws InterruptedException {
        try {
            outputSocket = new Socket(ipAddress, port);
            outToServer = new DataOutputStream(outputSocket.getOutputStream());
        } catch (ConnectException e) {
            Thread.sleep(1000);
            System.err.println("Trying to connect to address: " + ipAddress + " on port: " + port);
            establishConnection(ipAddress, port);
        } catch (UnknownHostException e) {
            System.err.println("Host unknown! Address: " + ipAddress + " port: " + port);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessageToClient(String message) {
        if (outToServer != null) {
            try {
                outToServer.writeChars(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

