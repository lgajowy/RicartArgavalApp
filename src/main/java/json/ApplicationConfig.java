package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ApplicationConfig {

    private JSONObject parsedConfig;
    private JSONObject thisNodeAddressAndPort;

    public ApplicationConfig(JSONObject parsedConfig) {
        this.parsedConfig = parsedConfig;
        this.thisNodeAddressAndPort = (JSONObject) parsedConfig.get("thisNodeAddressAndPort");
    }

    public Long getCriticalSectionOccupationTime() {
        return (Long) parsedConfig.get("occupationTime");
    }

    public JSONArray getOtherNodesAddressesAndPorts() {
        return (JSONArray) parsedConfig.get("addressesAndPorts");
    }

    public String getThisNodeAddress() {
        return (String) thisNodeAddressAndPort.get("address");
    }

    public Long getThisNodePort() {
        return (Long) thisNodeAddressAndPort.get("port");
    }
}
