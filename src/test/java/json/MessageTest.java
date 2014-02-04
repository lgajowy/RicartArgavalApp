package json;

import json.utils.MessageType;
import junit.framework.TestCase;
import org.junit.Test;

public class MessageTest extends TestCase {

    private static final Long CLOCK_VALUE = new Long(1);
    private static final MessageType OK = MessageType.ok;

    private Message msg;

    public void setUp() throws Exception {
        super.setUp();
        msg = new Message(CLOCK_VALUE, OK);
    }

    @Test
    public void testGetClockValue() throws Exception {
        assertEquals(CLOCK_VALUE, msg.getClockValue());
    }

    @Test
    public void testGetMessageType() throws Exception {
        assertEquals(OK, msg.getMessageType());
    }

    @Test
    public void testToString() {
        assertEquals("{\"clock\":1,\"type\":\"ok\"}", msg.toString());
    }
}
