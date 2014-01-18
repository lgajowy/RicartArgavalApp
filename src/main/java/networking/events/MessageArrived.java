package networking.events;

import java.util.EventObject;

public class MessageArrived extends EventObject {

    private String arrivingMessage;

    public MessageArrived(Object source, String message) {
        super(source);
        this.arrivingMessage = message;
    }

    public String getMessage() {
        return arrivingMessage;
    }
}
