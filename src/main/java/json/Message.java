package json;

import com.google.common.base.Enums;
import json.utils.MessageType;
import org.json.simple.JSONObject;

public class Message {

    private JSONObject jsonMsg;

    public Message(Long clockValue, MessageType type) {
        if (clockValue == null) {
            throw new NullPointerException();
        }

        jsonMsg = new JSONObject();
        jsonMsg.put("clock", clockValue);
        jsonMsg.put("type", type.toString());
    }

    public Message(Long clockValue, String type) {
        this(clockValue, Enums.getIfPresent(MessageType.class, type).or(MessageType.unknown));
    }

    public Long getClockValue() {
        return (Long) jsonMsg.get("clock");
    }

    public MessageType getMessageType() {
        String typeFieldFromJsonMessage = (String) jsonMsg.get("type");
        return Enums.getIfPresent(MessageType.class, typeFieldFromJsonMessage).or(MessageType.unknown);
    }

    public String toString() {
        return jsonMsg.toJSONString();
    }
}
