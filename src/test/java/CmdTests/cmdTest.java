package CmdTests;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.andrzejressel.romchecker.cmd.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class cmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void noArgsTest() throws Exception {
        Main.main(new String[]{"1"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), Main.usage) <= 2);
    }


    @Test
    public void noArgsTest2() throws Exception {
        Main.main(new String[]{"1", "2", "3"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), Main.usage) <= 2);
    }


    @Test
    public void tickUrlTest() throws Exception {

        Main.main(new String[]{"https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});

        assertFalse(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.cross) <= 2);
        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.tick) <= 2);

    }

    @Test
    public void crossUrlTest() throws Exception {

        Main.main(new String[]{"https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.cross) <= 2);

    }

    @Test
    public void tickFileTest() throws Exception {

        Main.main(new String[]{"https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "xmls/features/slim_pie.xml"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.tick) <= 2);


    }

}
