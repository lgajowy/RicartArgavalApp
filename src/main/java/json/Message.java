package json;

import messageType.MessageType;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Message extends JSONObject {

    public Message(int clockValue, MessageType type) {
        super();
        this.put("clock", clockValue);
        this.put("type", type.toString());
    }

    public void writeToFile(String filePath) {
        try {
            FileWriter file = new FileWriter(filePath);
            file.write(this.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
