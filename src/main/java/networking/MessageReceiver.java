package networking;

import appLogic.interfaces.IMessageArrivedListener;
import networking.events.MessageArrived;
import networking.utils.MessageState;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MessageReceiver implements Runnable {

    private Scanner messageScanner;
    private boolean isWorking;
    private ArrayList<IMessageArrivedListener> arrivingMessageListeners = new ArrayList<IMessageArrivedListener>();

    public MessageReceiver(Socket clientSocket, IMessageArrivedListener messageHandler) {
        try {
            arrivingMessageListeners.add(messageHandler);
            messageScanner = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
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

    public synchronized void addArivingMessageListener(IMessageArrivedListener listeningInstance) {
        arrivingMessageListeners.add(listeningInstance);
    }

    private synchronized void fireMessageArrivedEvent(String message) {
        MessageArrived messageEvent = new MessageArrived(this, message);
        Iterator listeners = arrivingMessageListeners.iterator();
        while (listeners.hasNext()) {
            ((IMessageArrivedListener) listeners.next()).onMessageArrived(messageEvent);
        }
    }

    private void listenForClientMessages() {
        StringBuilder message = null;
        MessageState previousState = MessageState.noMessage;
        MessageState actualState;
        while (messageScanner.hasNextLine()) {
            String line = messageScanner.nextLine();
            int indexOfLeftBracket = line.lastIndexOf("{");
            int indexOfRightBracket = line.indexOf("}", indexOfLeftBracket);
            actualState = MessageState.determineMessageState(indexOfLeftBracket, indexOfRightBracket, previousState);
            System.err.println(actualState);

            if (actualState == MessageState.oneliner) {
                message = new StringBuilder(line.substring(indexOfLeftBracket, indexOfRightBracket + 1));
            } else if (actualState == MessageState.started) {
                message = new StringBuilder();
                message.append(line.substring(indexOfLeftBracket)).append("\n");
            } else if (actualState == MessageState.ongoing) {
                assert message != null;
                message.append(line).append("\n");
            } else if (actualState == MessageState.ended) {
                assert message != null;
                message.append(line, 0, indexOfRightBracket + 1);
            } else {
                System.out.println(actualState);
            }
            previousState = actualState;

            if (actualState == MessageState.ended || actualState == MessageState.oneliner) {
                fireMessageArrivedEvent(message.toString());
            }
        }
    }
}
