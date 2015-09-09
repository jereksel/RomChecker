package cmdTests;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import pl.andrzejressel.romchecker.cmd.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class cmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

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
    public void wrongArgsTest() throws Exception {
        exit.expectSystemExitWithStatus(1);
        Main.main(new String[]{"1"});
    }


    @Test
    public void wrongArgsTest2() throws Exception {
        exit.expectSystemExitWithStatus(1);
        Main.main(new String[]{"1", "2", "3"});
    }


    @Test
    public void tickUrlTest() throws Exception {

        Main.main(new String[]{"--files", "https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.tick) <= 2);

    }

    @Test
    public void crossUrlTest() throws Exception {
        Main.main(new String[]{"--files", "https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.cross) <= 2);

    }

    @Test
    public void tickFileTest() throws Exception {

        Main.main(new String[]{"--files", "https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "src/test/resources/slim_pie.xml"});

        assertTrue(StringUtils.getLevenshteinDistance(outContent.toString(), "PIE (Slim) " + Main.tick) <= 2);


    }

}
