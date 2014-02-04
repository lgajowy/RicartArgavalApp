package networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageSender implements Runnable {

    private final int RECONNECTION_PEIROD = 3000;

    private String address;
    private int port;

    private Socket outputSocket;
    private DataOutputStream outToServer;

    public MessageSender(String address, int port) throws InterruptedException {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        establishConnection(address, port);
        System.out.println("connection established!");
    }

    private void establishConnection(String ipAddress, int port) {
        try {
            outputSocket = new Socket(ipAddress, port);
            outToServer = new DataOutputStream(outputSocket.getOutputStream());
        } catch (ConnectException e) {
            //e.printStackTrace();
            try {
                Thread.sleep(RECONNECTION_PEIROD);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.err.println("Trying to connect to address: " + ipAddress + " on port: " + port);
            establishConnection(ipAddress, port);
        } catch (UnknownHostException e) {
            System.err.println("Host unknown! Address: " + ipAddress + " port: " + port);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeMessageToClient(String message) {
        if (outToServer != null) {
            try {
                outToServer.writeBytes(message);
            } catch (IOException e) {
                System.err.println("Connection has been broken! Reconnecting...");
                establishConnection(address, port);
                System.err.println("Repeating message");
                writeMessageToClient(message);
            }
        } else {
            System.err.println("No output connection");
        }
    }
}
