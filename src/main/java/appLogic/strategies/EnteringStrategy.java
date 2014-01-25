package appLogic.strategies;

import appLogic.interfaces.IMessageHandlingStrategy;

/**
 * Created by lukasz on 1/26/14.
 */
public class EnteringStrategy implements IMessageHandlingStrategy {

    @Override
    public void handleOrderMessage() {
        System.out.println("entering strategy order");
    }

    @Override
    public void handleOkMessage() {
        System.out.println("entering strategy ok");
    }
}
