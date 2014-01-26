package appLogic.interfaces;

import java.net.InetAddress;

public interface IMessageHandlingStrategy {
    public void handleOrderMessage(InetAddress incommingMsgAddress);
    public void handleOkMessage(InetAddress incommingMsgAddress);

}