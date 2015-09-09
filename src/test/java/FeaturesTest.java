import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pl.andrzejressel.romchecker.lib.features.Features;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FeaturesTest {

    @Test
    public void testWithRelativePath() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        String location = features.getFeatureXMLLocation("Test Feature with relative path", featuresFile.getAbsolutePath());

        assertTrue(new File(location).exists());

    }

    @Test
    public void testWithRelativeURL() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        String location = features.getFeatureXMLLocation("Test Feature with relative path", "https://www.abc.com/app/testfeatures.xml");

        assertEquals("https://www.abc.com/app/features/foo.xml", location);

    }


    @Test
    public void testWithAbsoluteURL() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        String location = features.getFeatureXMLLocation("Test Feature with absolute url", "https://www.abc.com/app/testfeatures.xml");

        assertEquals("https://www.foo.bar/features/foo.xml", location);
    }

}
