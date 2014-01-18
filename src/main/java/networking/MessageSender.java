package networking;

import javax.net.ssl.SSLSocket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class MessageSender {
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

    public MessageSender(String ipAddress, int port) {
        try {
            outputSocket = new Socket(ipAddress, port);
            outToServer = new DataOutputStream(outputSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessageToClient(String message) {
        try {
            outToServer.writeChars(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

