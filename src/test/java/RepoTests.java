import org.apache.commons.io.IOUtils;
import org.junit.Test;
import pl.andrzejressel.romchecker.lib.repo.Manifest;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class RepoTests {

    @Test
    public void parsingCMManifest() throws Exception {
        String manifestString = IOUtils.toString(new URL("https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml"));

        final Manifest manifest = Manifest.getManifest(manifestString);
        assertEquals("https://github.com/CyanogenMod/android_frameworks_base/cm-12.1", manifest.getUrlForPath("frameworks/base"));
    }

    @Test
    public void parsingMinimalOSManifest() throws Exception {

        String manifestString = IOUtils.toString(new URL("https://raw.githubusercontent.com/MinimalOS/platform_manifest/lp-mr1/default.xml"));

        final Manifest manifest = Manifest.getManifest(manifestString);
        assertEquals("https://github.com/MinimalOS/android_frameworks_base/lp-mr1", manifest.getUrlForPath("frameworks/base"));
    }

    @Test
    public void parsingAOSPManifest() throws Exception {

        String manifestString = IOUtils.toString(new URL("https://raw.githubusercontent.com/android/platform_manifest/android-5.1.1_r13/default.xml"));

        final Manifest manifest = Manifest.getManifest(manifestString);
        assertEquals("https://github.com/android/platform_frameworks_base/android-5.1.1_r13", manifest.getUrlForPath("frameworks/base"));
    }


}
