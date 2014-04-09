package json;

import json.utils.MessageType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MessageParser extends JSONParser {
    private JSONObject parsedObject;
    private String textToBeParsed;

    public MessageParser(String textToBeParsed) {
        super();
        this.textToBeParsed = textToBeParsed;
    }

    public Message parse() {
        try {
            parsedObject = (JSONObject) parse(textToBeParsed);

            Long clockValue = getClockValue();
            String messageType = getMessageType();

            if (null != clockValue && null != messageType) {
                return new Message(clockValue, messageType);
            } else {
                return new Message(-1L, MessageType.unknown);
            }
        } catch (ParseException e) {
            System.err.println("This text is not JSON (or bad format)!!!");
            return new Message(-1L, MessageType.unknown);
        }
    }

    private Long getClockValue() {
        return (Long) parsedObject.get("clock");
    }

    private String getMessageType() {
        return (String) parsedObject.get("type");
    }
}
