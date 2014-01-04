package json;

public class MessageParser extends Parser {
    public MessageParser(String pathToJSONFile) {
        super(pathToJSONFile);
    }

    public long getClockValue() {
        return (Long)parsedObject.get("clock");
    }

    public String getMessageType() {
        return (String) parsedObject.get("type");
    }
}
