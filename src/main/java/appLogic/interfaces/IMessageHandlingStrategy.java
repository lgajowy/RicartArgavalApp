package appLogic.interfaces;

import appLogic.utils.Order;
import java.net.InetAddress;

public interface IMessageHandlingStrategy {
    public void handleOrderMessage(Order message);
    public void handleOkMessage(InetAddress incommingMsgAddress); //TODO: doesnt ok need Ok class similar to Order?
}