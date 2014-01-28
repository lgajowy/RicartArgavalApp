package appLogic;

import json.ConfigParser;
import networking.InputConnectionManager;
import networking.OutputConnectionManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.UnknownHostException;
import java.util.Scanner;

import java.io.InputStreamReader;
import java.net.InetAddress;

public class Application {

    private static InetAddress thisHostIPAddress = null;
    private static int occuptaionTime = 0;
    private static int totalNumberOfNeighborNodes = 3; //TODO: for now.

    public static int getOccuptaionTime() {
        return occuptaionTime;
    }

    public static int getTotalNumberOfNeighborNodes() {
        return totalNumberOfNeighborNodes;
    }

    public static InetAddress getThisHostIPAddress() {
        return thisHostIPAddress;
    }

    public static void setThisHostIPAddress(InetAddress thisHostIPAddress) {
        Application.thisHostIPAddress = thisHostIPAddress;
    }

    public static void main(String[] args) {

        if (args[0] == null) {
            printHelp();
            return;
        }

        ConfigParser configurationParser = new ConfigParser(args[0]);
        try {
            setThisHostIPAddress(InetAddress.getByName(configurationParser.getThisHostAddress()));
            occuptaionTime = configurationParser.getCriticalSectionOccupationTime();
            JSONArray addressesAndPorts = (JSONArray) configurationParser.getOtherNodesAddressesAndPorts();
            totalNumberOfNeighborNodes = addressesAndPorts.size();
            initInputAndOutputConnections(addressesAndPorts);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        RACriticalSection section = new RACriticalSection();
        scanStandardInputForCommands(section);
    }

    private static void initInputAndOutputConnections(JSONArray addressesAndPorts) {
        JSONObject addressAndPort;
        OutputConnectionManager outputConnectionManager = new OutputConnectionManager(totalNumberOfNeighborNodes);
        for (int i = 0; i < addressesAndPorts.size(); i++) {
            addressAndPort = (JSONObject) addressesAndPorts.get(i);
            outputConnectionManager.connectToServer(addressAndPort.get("ipAddress").toString(), Integer.parseInt((String) addressAndPort.get("port")));
        }
        InputConnectionManager inputConnectionManager = new InputConnectionManager(2003, totalNumberOfNeighborNodes, new MessageInterpreter());
        new Thread(inputConnectionManager).start();
    }

    private static void scanStandardInputForCommands(RACriticalSection section) {
        Scanner input = new Scanner(new InputStreamReader(System.in));
        String line = "";

        while (!line.equalsIgnoreCase("quit")) {
            line = input.nextLine();
            if (line.contains("enter")) {
                section.enter();
            }
        }
        input.close();
    }

    private static void printHelp() {
        System.err.println("Please provide path to configuration file as program argument!");
    }
}
