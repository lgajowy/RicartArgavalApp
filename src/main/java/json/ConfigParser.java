package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigParser extends JSONParser {
    private JSONObject parsedObject;
    private JSONObject thisNodeAddressAndPort;

    public ConfigParser(String pathToJSONFile) {
        super();
        try {
            parsedObject = (JSONObject) this.parse(new FileReader(pathToJSONFile));
            setThisNodeAddressAndPort();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Long getCriticalSectionOccupationTime() {
        return (Long) parsedObject.get("occupationTime");
    }

    public ArrayList<String> getOtherNodesAddressesAndPorts() {
        return (JSONArray) parsedObject.get("addressesAndPorts");
    }

    private void setThisNodeAddressAndPort() {
        thisNodeAddressAndPort = (JSONObject) parsedObject.get("thisNodeAddressAndPort");
    }

    public String getThisNodeAddress() {
        return (String) thisNodeAddressAndPort.get("address");
    }

    public Long getThisNodePort() {
        return (Long) thisNodeAddressAndPort.get("port");
    }

}
