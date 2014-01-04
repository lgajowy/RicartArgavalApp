import json.ConfigParser;
import json.Message;
import json.MessageParser;
import messageType.MessageType;

public class Main {

    private static String messageFilePath = "/home/lukasz/dev/javaProjects/RicartArgavalApp/utils/message.json";
    private static String configFilePath = "/home/lukasz/dev/javaProjects/RicartArgavalApp/utils/config.json";

    public static void main(String[] args) {

        Message msg = new Message(1, MessageType.order);
        msg.writeToFile(messageFilePath);

        MessageParser msgParser = new MessageParser(messageFilePath);

        System.out.println(msgParser.getClockValue());
        System.out.println(msgParser.getMessageType());

        ConfigParser cfgParser = new ConfigParser(configFilePath);
        System.out.println(cfgParser.getCriticalSectionOccupationTime());
        System.out.println(cfgParser.getOtherNodesAddresses());


    }
}
