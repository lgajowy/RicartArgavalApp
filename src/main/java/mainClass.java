import networking.InputConnectionManager;
import networking.OutputConnectionManager;

import java.io.IOException;

public class mainClass {
    public static void main(String[] args) throws InterruptedException {
        InputConnectionManager inputConnectionManager = new InputConnectionManager(2000, 4);
        new Thread(inputConnectionManager).start();

        System.out.println("Nie blokuje ");

        OutputConnectionManager ocm = new OutputConnectionManager(4);
        ocm.connectToServer("127.0.0.1", 2001);

        while (true) {
            Thread.sleep(1500);
            try {
                ocm.getMessageSender("127.0.0.1").writeMessageToClient("asdasdasd");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
