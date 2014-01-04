package json;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class ConfigParser extends Parser {

    public ConfigParser(String pathToJSONFile) {
        super(pathToJSONFile);
    }

    public long getCriticalSectionOccupationTime() {
        return (Long)parsedObject.get("occupationTime");
    }

    public ArrayList<String> getOtherNodesAddresses() {
        return (JSONArray) parsedObject.get("addresses");
    }
}
