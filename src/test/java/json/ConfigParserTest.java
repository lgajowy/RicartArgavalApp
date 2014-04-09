package json;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.Files;

public class ConfigParserTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String sampleJSON = "{\"thisNodeAddressAndPort\":{\"port\":2001,\"address\":\"127.0.0.1\"}," +
            " \"addressesAndPorts\":[{\"port\":2000,\"address\":\"127.0.0.1\"}], \"occupationTime\":3000}";
    private File tempJSONFile;

    @Before
    public void setUp() throws Exception {
        tempJSONFile = testFolder.newFile("sample.json");
        FileWriter writer = new FileWriter(tempJSONFile);
        writer.write(sampleJSON);
        writer.close();
    }

    @Test
    public void shouldParseFileWithoutExceptions() throws Exception {
        ConfigParser jsonConfigParser = new ConfigParser(tempJSONFile.getAbsolutePath());

        ApplicationConfig parsedConfig = catchException(jsonConfigParser, Exception.class).parse();

        assertThat(caughtException()).isNull();
    }
}
