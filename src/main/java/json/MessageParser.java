package json;

import com.google.common.primitives.Ints;
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


            //String clockValueString = getClockValue();
            //Integer clockValue = castClockValueToInteger(clockValueString);
            Long clockValue = getClockValue();
            String messageType = getMessageType();

            if (null != clockValue && null != messageType) {
                return new Message(clockValue, messageType);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("This text is not JSON!!!");
            return null;
        }
    }

    private Integer castClockValueToInteger(String clockValueString) {
        Integer clockValue = null;
        if (clockValueString != null) {
            clockValue = Ints.tryParse(clockValueString);
        }
        return clockValue;
    }

    private Long getClockValue() {
        return (Long) parsedObject.get("clock");
    }

    private String getMessageType() {
        return (String) parsedObject.get("type");
    }
}
