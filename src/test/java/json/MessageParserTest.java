package json;

import junit.framework.TestCase;
import json.utils.MessageType;

public class MessageParserTest extends TestCase {

    public void testParse() throws Exception {
        MessageParser parser = new MessageParser("{ \"clock\": \"1\", \"type\": \"order\" }");

        Message message = parser.parse();

        assertNotNull(message);
        assertEquals(1, message.getClockValue());
        assertEquals(MessageType.order, message.getMessageType());
    }
}
