package appLogic.strategies;

import appLogic.interfaces.IMessageHandlingStrategy;

/**
 * Created by lukasz on 1/26/14.
 */
public class IdleStrategy implements IMessageHandlingStrategy {

    @Override
    public void handleOrderMessage() {

    }

    @Override
    public void handleOkMessage() {
        System.out.println("idle strategy ok");
    }
}
