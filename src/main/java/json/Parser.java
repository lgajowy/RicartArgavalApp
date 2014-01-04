package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Parser extends JSONParser {

    protected JSONObject parsedObject;

    public Parser(String pathToJSONFile) {
        super();

        try {
            parsedObject = (JSONObject) this.parse(new FileReader(pathToJSONFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
