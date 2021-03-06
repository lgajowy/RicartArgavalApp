package networking;

import appLogic.interfaces.IMessageArrivedListener;
import networking.events.MessageArrived;
import networking.utils.MessageState;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MessageReceiver implements Runnable {

    private Scanner messageScanner;
    private boolean isWorking;
    private ArrayList<IMessageArrivedListener> arrivingMessageListeners = new ArrayList<IMessageArrivedListener>();
    private Socket clientSocket;

    public MessageReceiver(Socket clientSocket, IMessageArrivedListener messageHandler) {
        this.clientSocket = clientSocket;
        try {
            addArivingMessageListener(messageHandler);
            messageScanner = new Scanner(new InputStreamReader(clientSocket.getInputStream())).useDelimiter("\\z");
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

    private synchronized void addArivingMessageListener(IMessageArrivedListener listeningInstance) {
        arrivingMessageListeners.add(listeningInstance);
    }

    private synchronized void fireMessageArrivedEvent(String message) {

        MessageArrived messageEvent = new MessageArrived(this, message, clientSocket.getInetAddress());
        Iterator listeners = arrivingMessageListeners.iterator();
        while (listeners.hasNext()) {
            ((IMessageArrivedListener) listeners.next()).onMessageArrived(messageEvent);
        }
    }

    private void listenForClientMessages() {
        StringBuilder message = null;
        MessageState previousState = MessageState.noMessage;
        MessageState actualState;
        while (messageScanner.hasNext()) {
            String line = messageScanner.next();
            int indexOfLeftBracket = line.lastIndexOf("{");
            int indexOfRightBracket = line.indexOf("}", indexOfLeftBracket);
            actualState = MessageState.determineMessageState(indexOfLeftBracket, indexOfRightBracket, previousState);

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
            }
            previousState = actualState;

            if (actualState == MessageState.ended || actualState == MessageState.oneliner) {
                fireMessageArrivedEvent(message.toString());
            }
        }
    }
}
