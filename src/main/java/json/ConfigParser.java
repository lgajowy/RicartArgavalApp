package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigParser extends JSONParser {
    private FileReader fileReader;
    private JSONObject parsedObject;

    public ConfigParser(String pathToJSONFile) {
        super();
        try {
            fileReader = new FileReader(pathToJSONFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ApplicationConfig parse() {
        try {
            parsedObject = (JSONObject) this.parse(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ApplicationConfig(parsedObject);
    }
}
