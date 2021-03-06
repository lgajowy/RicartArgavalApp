package appLogic;

import json.ConfigParser;
import networking.InputConnectionManager;
import networking.OutputConnectionManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Application {

    private static InetAddress thisNodeIPAddress = null;
    private static Integer thisNodePort = null;
    private static Integer occupationTime = 0;
    private static int totalNumberOfNeighborNodes = 3;
    private static HashSet<String> otherNodesAddresses = new HashSet<String>();
    private static String enteredText;

    public static HashSet<String> getOtherNodesAddresses() {
        return otherNodesAddresses;
    }

    public static Integer getOccupationTime() {
        return occupationTime;
    }

    public static InetAddress getThisNodeIPAddress() {
        return thisNodeIPAddress;
    }

    public static int getTotalNumberOfNeighborNodes() {
        return totalNumberOfNeighborNodes;
    }

    public static Integer getIntOrNull(Long value) {
        return value <= Integer.MAX_VALUE ? value.intValue() : null;
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

    private static void startConnections(JSONArray addressesAndPorts, MessageInterpreter incomingJsonMsgIngerpretter) {
        OutputConnectionManager outputConections = new OutputConnectionManager(totalNumberOfNeighborNodes);
        outputConections.startMultipleOutputConnections(addressesAndPorts);
        startInputConnections(incomingJsonMsgIngerpretter);
    }

    private static void startInputConnections(MessageInterpreter incomingJsonMsgIngerpretter) {
        InputConnectionManager inputConnectionManager = new InputConnectionManager(thisNodePort, totalNumberOfNeighborNodes, incomingJsonMsgIngerpretter);
        new Thread(inputConnectionManager).start();
    }

    private static void scanStandardInputForCommands(RACriticalSection section) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            enteredText = input.nextLine();
            if (enteredText.contains("enter")) {
                section.startEnteringSection();
            } else if (enteredText.contains("leave")) {
                section.leave();
            } else if (enteredText.contains("quit")) {
                input.close();
                System.exit(0);
            }
        }
        input.close();
    }

    public static void main(String[] args) {
        parseInputArguments(args);
        ConfigParser configurationParser = new ConfigParser(args[0]);

        DeferredMessagesManager msgManager = new DeferredMessagesManager();
        OkMessageManagerForEnteringState okRecorder = new OkMessageManagerForEnteringState();
        RACriticalSection section = new RACriticalSection(msgManager, okRecorder);
        MessageInterpreter incomingJsonMsgIngerpretter = new MessageInterpreter(msgManager, okRecorder);

        try {

            thisNodeIPAddress = InetAddress.getByName(configurationParser.getThisNodeAddress());
            thisNodePort = getIntOrNull(configurationParser.getThisNodePort());
            occupationTime = getIntOrNull(configurationParser.getCriticalSectionOccupationTime());
            JSONArray addressesAndPorts = (JSONArray) configurationParser.getOtherNodesAddressesAndPorts();
            totalNumberOfNeighborNodes = addressesAndPorts.size();

            //todo: Oooglayy hack. ;/
            JSONObject obj;
            for (int i = 0; i < addressesAndPorts.size(); i++) {
                obj = (JSONObject) addressesAndPorts.get(i);
                otherNodesAddresses.add((String) obj.get("address"));
            }

            startConnections(addressesAndPorts, incomingJsonMsgIngerpretter);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        scanStandardInputForCommands(section);
    }
}


