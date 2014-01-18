import networking.ConnectionManager;
import networking.MessageSender;

public class mainClass {
    public static void main(String[] args) throws InterruptedException {
        new ConnectionManager(2000, 4);

//        MessageSender msg = new MessageSender("127.0.0.1", 2000);
//        while ( true){
//            Thread.sleep(1000);
//            msg.writeMessageToClient("{dziala conieco}\n");
//        }


    }
}
