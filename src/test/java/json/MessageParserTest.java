package json;

import json.utils.MessageType;
import junit.framework.TestCase;

public class MessageParserTest extends TestCase {

    public void testParse() throws Exception {
        MessageParser parser = new MessageParser("{ \"clock\": 1, \"type\": \"order\" }");

        Message message = parser.parse();

        assertNotNull(message);
        assertEquals(new Long(1), message.getClockValue());
        assertEquals(MessageType.order, message.getMessageType());
    }
}
