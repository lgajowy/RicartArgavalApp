package json;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import javafx.util.StringConverter;
import messageType.MessageType;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    JSONObject jsonMsg;

    public Message(int clockValue, MessageType type) {
        jsonMsg = new JSONObject();
        jsonMsg.put("clock", clockValue);
        jsonMsg.put("type", type.toString());
    }

    public Message(int clockValue, String type) {
        this(clockValue, Enums.getIfPresent(MessageType.class, type).or(MessageType.unknown));
    }

    public int getClockValue() {
        return (Integer) jsonMsg.get("clock");
    }

    public MessageType getMessageType() {
        String typeFieldFromJsonMessage = (String) jsonMsg.get("type");
        return Enums.getIfPresent(MessageType.class, typeFieldFromJsonMessage).or(MessageType.unknown);
    }
}
