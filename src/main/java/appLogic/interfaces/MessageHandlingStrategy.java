package appLogic.interfaces;

/**
 * Created by lukasz on 1/25/14.
 */
public interface MessageHandlingStrategy {
    public void handleOrderMessage();
    public void handleOkMessage();

}
