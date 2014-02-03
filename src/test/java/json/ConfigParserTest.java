package json;

import junit.framework.TestCase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigParserTest extends TestCase {

    public static final String TEST_CONFIG_FILE_PATH = "config.json";
    public static final Long OCCUPATION_TIME = new Long(100);

    public void setUp() throws Exception {
        super.setUp();

        JSONObject configuration = new JSONObject();

        JSONObject nodeAddressAndPort = prepareJSONAddressAndPort("127.0.0.1", 2000);
        JSONArray addresses = new JSONArray();
        addresses.add(prepareJSONAddressAndPort("1.1.1.1", 1111));
        addresses.add(prepareJSONAddressAndPort("2.2.2.2", 2222));
        addresses.add(prepareJSONAddressAndPort("3.3.3.3", 3333));

        configuration.put("occupationTime", OCCUPATION_TIME);
        configuration.put("thisNodeAddressAndPort", nodeAddressAndPort);
        configuration.put("addressesAndPorts", addresses);

        try {
            FileWriter testConfig = new FileWriter(TEST_CONFIG_FILE_PATH);
            testConfig.write(configuration.toJSONString());
            testConfig.flush();
            testConfig.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject prepareJSONAddressAndPort(String ipAddress, int port) {
        JSONObject addresAndPortJSON = new JSONObject();
        addresAndPortJSON.put("address", ipAddress);
        addresAndPortJSON.put("port", port);
        return addresAndPortJSON;
    }

    public void tearDown() throws Exception {
        super.tearDown();
        File configFile = new File(TEST_CONFIG_FILE_PATH);
        configFile.delete();
    }

    @Test
    public void testGetCriticalSectionOccupationTime() throws Exception {
        ConfigParser parser = new ConfigParser(TEST_CONFIG_FILE_PATH);
        assertEquals(OCCUPATION_TIME, parser.getCriticalSectionOccupationTime());
    }

    @Test
    public void testGetOtherNodesAddressesAndPorts() throws Exception {
        ConfigParser parser = new ConfigParser(TEST_CONFIG_FILE_PATH);
        JSONArray addresses = new JSONArray();

        addresses.add(prepareJSONAddressAndPort("1.1.1.1", 1111));
        addresses.add(prepareJSONAddressAndPort("2.2.2.2", 2222));
        addresses.add(prepareJSONAddressAndPort("3.3.3.3", 3333));

        assertEquals(addresses, parser.getOtherNodesAddressesAndPorts());
    }

    public void testGetThisNodeAddress() throws Exception {
        ConfigParser parser = new ConfigParser(TEST_CONFIG_FILE_PATH);
        assertEquals("127.0.0.1", parser.getThisNodeAddress());
    }

    public void testGetThisNodePort() throws Exception {
        ConfigParser parser = new ConfigParser(TEST_CONFIG_FILE_PATH);
        assertEquals(new Long(2000), parser.getThisNodePort());


    }
}
