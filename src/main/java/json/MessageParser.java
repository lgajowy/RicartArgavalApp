package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageParser extends JSONParser {
    private JSONObject parsedObject;

    public MessageParser(String jsonData) {
        super();
        try {
            parsedObject = (JSONObject) parse(jsonData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private int getClockValue() {
        return Integer.parseInt((String) parsedObject.get("clock"));
    }

    private String getMessageType() {
        return (String) parsedObject.get("type");
    }

    public Message getParsedMessage() {
        return new Message(getClockValue(), getMessageType());

    }
}
