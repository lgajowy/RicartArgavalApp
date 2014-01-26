package networking.events;

import java.net.InetAddress;
import java.util.EventObject;

public class MessageArrived extends EventObject {

    private String arrivingMessage;
    private InetAddress address;

    public MessageArrived(Object source, String message, InetAddress inetAddress) {
        super(source);
        this.arrivingMessage = message;
        this.address = inetAddress;
    }

    public String getMessage() {
        return arrivingMessage;
    }

    public InetAddress getINetAddress() {
        return address;
    }

}
