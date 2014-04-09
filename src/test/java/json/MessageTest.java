package json;

import json.utils.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageTest {

    final Long clockValue = 10L;
    final String validMessageType = "ok";
    final String invalidValidMessageType = "blablabla";
    private Message msg;

    @Before
    public void setUp() throws Exception {
        msg = new Message(clockValue, validMessageType);

    }

    @Test
    public void shouldGetProperClockValue() throws Exception {
        assertThat(msg.getClockValue()).isEqualTo(clockValue);
    }

    @Test
    public void shouldThrowNullPointerExceptionsWhenClockValueIsNull() throws Exception {
        try {
            new Message(null, MessageType.unknown);
        } catch (NullPointerException ex) {
            assertThat(ex).isNotNull();
        }
    }

    @Test
    public void shouldGetOkMessageType() throws Exception {
        assertThat(msg.getMessageType()).isEqualTo(MessageType.ok);
    }

    @Test
    public void shouldGetUnknownMessageTypeWhenTypeIsInvalid() throws Exception {
        msg = new Message(0L, invalidValidMessageType);

        assertThat(msg.getMessageType()).isEqualTo(MessageType.unknown);
    }

    @Test
    public void shouldReturnStringJSON() throws Exception {
        assertThat(msg.toString()).isEqualTo("{\"clock\":10,\"type\":\"ok\"}");

    }
}
