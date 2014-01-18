package networking;

import networking.utils.MessageState;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MessageReceiver implements Runnable {

    private BufferedReader readerOfDataFromClient;
    private Scanner messageScanner;
    private boolean isWorking;
    private String sourceIpAddress;

    public MessageReceiver(Socket clientSocket) {
        try {
            sourceIpAddress = clientSocket.getInetAddress().toString();
            messageScanner = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
            readerOfDataFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        isWorking = true;
        while (isWorking) {
            listenForClientMessages();
        }
    }

    private void listenForClientMessages() {
        //TODO: Trigger event after receiving json!

        StringBuilder message = null;
        //StringBuilder lineBuffer = null;
        boolean isMessageOngoing = false;
        MessageState previousState = MessageState.unknown;
        MessageState actualState;
        while (messageScanner.hasNextLine()) {
            String line = messageScanner.nextLine();
            actualState = MessageState.determineMessageState(line, previousState);


            int indexOfLeftBracket = line.lastIndexOf("{");
            int indexOfRightBracket = line.indexOf("}", indexOfLeftBracket);


            if (indexOfLeftBracket != -1) {
                if (indexOfRightBracket != -1) {
                    message = new StringBuilder(line.substring(indexOfLeftBracket, indexOfRightBracket + 1));
                } else {
                    message = new StringBuilder();
                    message.append(line.substring(indexOfLeftBracket) + "\n");
                    isMessageOngoing = true;
                }
            } else if (isMessageOngoing && indexOfRightBracket != -1) {
                isMessageOngoing = false;
                message.append(line, 0, indexOfRightBracket + 1);

            } else {
                if (isMessageOngoing) {
                    message.append(line + "\n");
                }
            }

            previousState = actualState;
            if (isMessageOngoing == false) {
                System.out.println("MESSAGE:\n" + message.toString());
            }
        }
    }
}
