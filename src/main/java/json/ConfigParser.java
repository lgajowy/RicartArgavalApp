package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigParser extends JSONParser {
    private FileReader fileReader;
    private JSONObject parsedConfig;

    public ConfigParser(String pathToJSONFile){
        try {
            fileReader = new FileReader(pathToJSONFile);
        } catch (FileNotFoundException e) {
            System.err.println("Config file not Found");
        }
    }

    public ApplicationConfig parse() {
        try {
            parsedConfig = (JSONObject) super.parse(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ApplicationConfig(parsedConfig);
    }
}
