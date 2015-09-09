package featuresTests;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

public class PieCheckingTests extends AbsFeatureTest {


    @Before
    public void setUp() throws IOException {

        xml = IOUtils.toString(new File("xmls/features/slim_pie.xml").toURI());

        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml", false));
        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/android/platform_manifest/android-5.1.1_r13/default.xml", false));
        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/MinimalOS/platform_manifest/lp-mr1/default.xml", false));
        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml", true));

        featureName = "PIE (Slim)";

    }


}
