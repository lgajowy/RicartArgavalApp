package appLogic;

import json.ConfigParser;
import networking.InputConnectionManager;
import networking.OutputConnectionManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Application {

    private static InetAddress thisHostIPAddress = null;
    private static int occuptaionTime = 0;
    private static int totalNumberOfNeighborNodes = 3; //TODO: for now.
    private static String enteredText;

    public static int getOccuptaionTime() {
        return occuptaionTime;
    }

    public static InetAddress getThisHostIPAddress() {
        return thisHostIPAddress;
    }

    public static void main(String[] args) {
        parseInputArguments(args);
        ConfigParser configurationParser = new ConfigParser(args[0]);

        DeferredMessagesManager msgManager = new DeferredMessagesManager();
        RACriticalSection section = new RACriticalSection(msgManager);
        MessageInterpreter incomingJsonMsgIngerpretter = new MessageInterpreter(msgManager);

        try {
            thisHostIPAddress = InetAddress.getByName(configurationParser.getThisHostAddress());
            occuptaionTime = configurationParser.getCriticalSectionOccupationTime();
            JSONArray addressesAndPorts = (JSONArray) configurationParser.getOtherNodesAddressesAndPorts();
            totalNumberOfNeighborNodes = addressesAndPorts.size();
            initInputAndOutputConnections(addressesAndPorts, incomingJsonMsgIngerpretter);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        scanStandardInputForCommands(section);
    }

    private static void parseInputArguments(String[] args) {
        if (args.length != 1) {
            printHelp();
            System.exit(1);
        }
    }

    private static void printHelp() {
        System.err.println("Please provide path to configuration file as program argument!");
    }

    private static void initInputAndOutputConnections(JSONArray addressesAndPorts, MessageInterpreter incomingJsonMsgIngerpretter) {
        JSONObject addressAndPort;
        OutputConnectionManager outputConnectionManager = new OutputConnectionManager(totalNumberOfNeighborNodes);
        for (int i = 0; i < addressesAndPorts.size(); i++) {
            addressAndPort = (JSONObject) addressesAndPorts.get(i);
            outputConnectionManager.connectToServer(addressAndPort.get("ipAddress").toString(), Integer.parseInt((String) addressAndPort.get("port")));
        }
        InputConnectionManager inputConnectionManager = new InputConnectionManager(44444, totalNumberOfNeighborNodes, incomingJsonMsgIngerpretter);
        new Thread(inputConnectionManager).start();
    }

    private static void scanStandardInputForCommands(RACriticalSection section) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            enteredText = input.nextLine();
            if (enteredText.contains("enter")) {
                section.preEnter();
            } else if (enteredText.contains("leave")) {
                section.leave();
            } else if (enteredText.contains("quit")) {
                input.close();
                System.exit(0);
            }
        }
        input.close();
    }
}


