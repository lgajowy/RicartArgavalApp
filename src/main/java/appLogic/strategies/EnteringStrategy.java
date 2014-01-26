package appLogic.strategies;

import appLogic.interfaces.IMessageHandlingStrategy;
import appLogic.utils.Order;

import java.net.InetAddress;

/**
 * Created by lukasz on 1/26/14.
 */
public class EnteringStrategy implements IMessageHandlingStrategy {

    @Override
    public void handleOrderMessage(Order message) {

    }

    @Override
    public void handleOkMessage(InetAddress incommingMsgAddress) {

    }
}
