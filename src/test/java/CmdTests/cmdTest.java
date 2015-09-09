package cmdTests;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pl.andrzejressel.romchecker.cmd.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    public void createMain() {
        Main main = new Main();
        assertNotNull(main);
    }

    @Test
    public void wrongArgsTest() throws Exception {
        Main.main(new String[]{"1"});
        assertTrue(outContent.toString().contains("usage:"));
    }

    @Test
    public void usageTest() throws Exception {
        Main.main(new String[]{"-h", "--files", "https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});
        assertTrue(outContent.toString().contains("usage:"));
    }

    @Test
    public void wrongArgsTest2() throws Exception {
        Main.main(new String[]{"1", "2", "3"});
        assertTrue(outContent.toString().contains("usage:"));
    }


    @Test
    public void tickUrlTest() throws Exception {

        Main.main(new String[]{"--files", "https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});

        assertTrue(outContent.toString().contains("PIE (Slim) " + Main.tick));
    }

    @Test
    public void crossUrlTest() throws Exception {
        Main.main(new String[]{"--files", "https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml",
                "https://gist.githubusercontent.com/jereksel/927ba7046ed519250580/raw/6e7da36a242e10dc942788b68320581ec985d88a/slim_pie.xml"});

        assertTrue(outContent.toString().contains("PIE (Slim) " + Main.cross));
    }

    @Test
    public void tickFileTest() throws Exception {

        Main.main(new String[]{"--files", "https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml",
                "src/test/resources/slim_pie.xml"});

        assertTrue(outContent.toString().contains("PIE (Slim) " + Main.tick));
    }

    @Test
    public void fancyTestWithManifestFile() throws Exception {

        Main.main(new String[]{"--fancy", "--files", "src/test/resources/manifests/slim.xml",
                "src/test/resources/slim_pie.xml"});

        String data = outContent.toString();

        assertFalse(data.contains("PIE (Slim) " + Main.tick));

    }

}
