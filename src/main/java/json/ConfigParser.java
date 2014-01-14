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

    public ConfigParser(String pathToJSONFile) {
        super();
        try {
            parsedObject = (JSONObject) this.parse(new FileReader(pathToJSONFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getCriticalSectionOccupationTime() {
        return Integer.parseInt((String) parsedObject.get("occupationTime"));
    }

    public ArrayList<String> getOtherNodesAddresses() {
        return (JSONArray) parsedObject.get("addresses");
    }
}
