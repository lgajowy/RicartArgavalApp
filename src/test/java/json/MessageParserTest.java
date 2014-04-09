package json;

import json.utils.MessageType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;


public class MessageParserTest {

    final String sampleMessage = "{\"clock\": 10, \"type\": \"SAMPLE_TYPE\" }";
    final String sampleMessageNotJSON = "xyz";
    private MessageParser messageParser;

    @Before
    public void setUp() throws Exception {
        messageParser = new MessageParser(sampleMessage);
    }

    @Test
    public void shouldParseMessage() throws Exception {
        Assert.assertNotNull(messageParser.parse());
    }

    @Test
    public void shouldNotThrowWhenParseTextIsNotJSON() throws Exception {
        messageParser = new MessageParser(sampleMessageNotJSON);

        Message parsedMessage = catchException(messageParser, Exception.class).parse();

        assertThat(caughtException()).isNull();
    }

    @Test
    public void shouldReturnEmptyMessageWhenParsedTextIsNotJSON() throws Exception {
        messageParser = new MessageParser(sampleMessageNotJSON);

        assertThat(messageParser.parse()).isEqualsToByComparingFields(new Message(-1L, MessageType.unknown));
    }
}
