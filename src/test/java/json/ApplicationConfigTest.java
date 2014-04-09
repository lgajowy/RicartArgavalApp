package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationConfigTest {

    private final String configuration = "{\n" +
            "\"thisNodeAddressAndPort\":{\"port\":2001,\"address\":\"127.0.0.1\"},\n" +
            "\"addressesAndPorts\":[{\"port\":2000,\"address\":\"127.0.0.1\"}],\n" +
            "\"occupationTime\":3000\n}";
    private ApplicationConfig config;


    @Before
    public void setUp() throws Exception {
        JSONObject applicationConfigJSON = (JSONObject)new JSONParser().parse(configuration);
        config = new ApplicationConfig(applicationConfigJSON);
    }

    @Test
    public void shouldGetCriticalSectionOccupationTime() throws Exception {
        assertThat(config.getCriticalSectionOccupationTime()).isEqualTo(3000L);
    }

    @Test
    public void shouldGetOthersAddressesAndPorts() throws Exception {
        JSONArray expected = prepareSampleJSONArray();
        assertThat(config.getOtherNodesAddressesAndPorts()).isEqualTo(expected);
    }

    private JSONArray prepareSampleJSONArray() {
        JSONObject obj = new JSONObject();
        obj.put("port", 2000L);
        obj.put("address", "127.0.0.1");
        JSONArray expected = new JSONArray();
        expected.add(obj);
        return expected;
    }

    @Test
    public void shouldGetThisNodeAddress() throws Exception {
        assertThat(config.getThisNodeAddress()).isEqualTo("127.0.0.1");
    }

    @Test
    public void shouldGetThisNodePort() throws Exception {
        assertThat(config.getThisNodePort()).isEqualTo(2001L);
    }
}
