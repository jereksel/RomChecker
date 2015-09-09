package featuresTests;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;

//Mostly to check if AOSP is working
public class IsItAndroidTest extends AbsFeatureTest {

    @Before
    public void setUp() {

        noIndirect = true;

        xml = "<?xml version=\"1.0\"?>\n" +
                "<change name=\"NO NAME\">\n" +
                "    <section>\n" +
                "        <file repo=\"frameworks/base\" name=\"Android.mk\"/>\n" +
                "    </section>\n" +
                "</change>";

        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml", true));
        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/android/platform_manifest/android-5.1.1_r13/default.xml", true));
        pairs.add(new ImmutablePair<>("https://raw.githubusercontent.com/MinimalOS/platform_manifest/lp-mr1/default.xml", true));
    }


}
