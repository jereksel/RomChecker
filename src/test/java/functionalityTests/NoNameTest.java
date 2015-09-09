package functionalityTests;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import pl.andrzejressel.romchecker.lib.Checking;
import pl.andrzejressel.romchecker.lib.feature.Feature;
import pl.andrzejressel.romchecker.lib.repo.Manifest;

import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoNameTest {

    @Test
    public void noNameFalse() throws Exception {

        //100% there is no themechecker repo in aosp

        String manifestString = IOUtils.toString(new URL("https://raw.githubusercontent.com/android/platform_manifest/android-5.1.1_r13/default.xml"));

        Manifest manifest = Manifest.getManifest(manifestString);


        String featureString = "<feature name=\"CyanogenMod Theme Engine\">\n" +
                "    <section>\n" +
                "        <file repo=\"packages/apps/ThemeChooser\"/>\n" +
                "    </section>\n" +
                "</feature>";

        Feature feature = Feature.getChange(featureString);


        assertFalse(Checking.checkChangeWithManifest(feature, manifest));


    }


    @Test
    public void noNameTrue() throws Exception {


        //100% there is themechecker repo in cm

        String manifestString = IOUtils.toString(new URL("https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml"));

        Manifest manifest = Manifest.getManifest(manifestString);


        String featureString = "<feature name=\"CyanogenMod Theme Engine\">\n" +
                "    <section>\n" +
                "        <file repo=\"packages/apps/ThemeChooser\"/>\n" +
                "    </section>\n" +
                "</feature>";

        Feature feature = Feature.getChange(featureString);


        assertTrue(Checking.checkChangeWithManifest(feature, manifest));



    }

}
