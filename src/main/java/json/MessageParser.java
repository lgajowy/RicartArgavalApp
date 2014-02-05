package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
                return null;
            }
        } catch (Exception e) {
            System.err.println("This text is not JSON (or bad format)!!!");
            return null;
        }
    }

    private Long getClockValue() {
        return (Long) parsedObject.get("clock");
    }

    private String getMessageType() {
        return (String) parsedObject.get("type");
    }
}
