import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pl.andrzejressel.romchecker.lib.features.Features;

import java.io.File;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FeaturesTest {

    @Test
    public void testWithRelativePath() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        URI location = features.getFeatureXMLLocation("Test Feature with relative path", featuresFile.getAbsolutePath());

        assertTrue(new File(location).exists());

    }

    @Test
    public void testWithRelativeURL() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        URI location = features.getFeatureXMLLocation("Test Feature with relative path", "https://www.abc.com/app/testfeatures.xml");

        assertEquals("https://www.abc.com/app/features/foo.xml", location.toString());

    }


    @Test
    public void testWithAbsoluteURL() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        URI location = features.getFeatureXMLLocation("Test Feature with absolute url", "https://www.abc.com/app/testfeatures.xml");

        assertEquals("https://www.foo.bar/features/foo.xml", location.toString());
    }

    @Test
    public void testGetXMLData() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        String data = features.getFeatureXML("Test Feature with relative path", featuresFile.getAbsolutePath());

        assertEquals(data, FileUtils.readFileToString(new File("src/test/resources/features/foo.xml")));

    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgument() throws Exception {

        File featuresFile = new File("src/test/resources/testfeatures.xml");
        Features features = Features.getFeatures(FileUtils.readFileToString(featuresFile));

        String data = features.getFeatureXML("NOT EXISTING", featuresFile.getAbsolutePath());


    }

    @Test
    public void xmlWithAbsolutePath() throws Exception {

        File feature = new File("src/test/resources/features/foo.xml");

        assertTrue(feature.exists());

        String xml = "<?xml version=\"1.0\"?>\n" +
                "<features>\n" +
                "    <feature location=\"" + feature.getAbsolutePath() + "\" name=\"Absolute existing path\"/>\n" +
                "</features>";


        Features features = Features.getFeatures(xml);
        URI location = features.getFeatureXMLLocation("Absolute existing path", "we don't care");

        assertEquals(feature.toURI(), location);

    }

}
