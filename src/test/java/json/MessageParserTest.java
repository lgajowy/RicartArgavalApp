package json;

import junit.framework.TestCase;
import messageType.MessageType;

public class MessageParserTest extends TestCase {

    public void testGetParsedOrderMsg() throws Exception {
        MessageParser parser = new MessageParser("{ \"clock\": \"1\", \"type\": \"order\" }");
        Message msg = parser.getParsedMessage();

        assertNotNull(msg);
        assertEquals(1, msg.getClockValue());
        assertEquals(MessageType.order, msg.getMessageType());
    }

    public void testGetParsedOkMsg() throws Exception {
        MessageParser parser = new MessageParser("{ \"clock\": \"1\", \"type\": \"ok\" }");
        Message msg = parser.getParsedMessage();

        assertNotNull(msg);
        assertEquals(1, msg.getClockValue());
        assertEquals(MessageType.ok, msg.getMessageType());
    }

    public void testGetParsedUnknownMsg() throws Exception {
        MessageParser parser = new MessageParser("{ \"clock\": \"1\", \"type\": \"xxx\" }");
        Message msg = parser.getParsedMessage();

        assertNotNull(msg);
        assertEquals(1, msg.getClockValue());
        assertEquals(MessageType.unknown, msg.getMessageType());
    }
}
