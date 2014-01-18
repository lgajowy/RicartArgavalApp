package json;

import junit.framework.TestCase;
import json.utils.MessageType;
import org.junit.Test;

public class MessageTest  extends TestCase {

    public static final int CLOCK_VALUE = 1;
    public static final MessageType OK = MessageType.ok;

    @Test
    public void testGetClockValue() throws Exception {
        Message msg = new Message(CLOCK_VALUE, OK);
        assertEquals(CLOCK_VALUE, msg.getClockValue());
    }

    @Test
    public void testGetMessageType() throws Exception {
        Message msg = new Message(CLOCK_VALUE, MessageType.ok);
        assertEquals(OK, msg.getMessageType());
    }
}
