package appLogic.strategies;

import appLogic.interfaces.IMessageHandlingStrategy;

/**
 * Created by lukasz on 1/26/14.
 */
public class OccupiedSectionStrategy implements IMessageHandlingStrategy {
    @Override
    public void handleOrderMessage() {

    }

    @Override
    public void handleOkMessage() {
        System.out.println("OccupiedSection strategy ok");
    }
}
