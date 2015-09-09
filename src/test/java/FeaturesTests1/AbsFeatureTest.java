package featuresTests;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import pl.andrzejressel.romchecker.lib.Checking;
import pl.andrzejressel.romchecker.lib.feature.Feature;
import pl.andrzejressel.romchecker.lib.features.Features;
import pl.andrzejressel.romchecker.lib.repo.Manifest;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbsFeatureTest {

    protected List<Pair<String, Boolean>> pairs = new ArrayList<>();

    //"Insides" of xml file
    protected String xml;

    //Used only for IsItAndroidTest
    protected boolean noIndirect = false;

    //Feature name from features.xml
    protected String featureName;


    @Before
    public abstract void setUp() throws Exception;

    @Test
    public void testDirect() throws Exception {

        Validate.notNull(xml);
        Validate.notEmpty(pairs);

        final Feature feature = Feature.getChange(xml);

        testFeature(feature);

    }

    @Test
    public void testIndirect() throws Exception {

        if (noIndirect) {
            return;
        }

        Validate.notEmpty(pairs);
        Validate.notNull(featureName);

        Features features = Features.getFeatures(FileUtils.readFileToString(new File("xmls/features.xml")));

        URI fileLocation = features.getFeatureXMLLocation(featureName, "xmls/features.xml");

        testFeature(Feature.getChange(IOUtils.toString(fileLocation)));

    }


    private void testFeature(Feature feature) throws Exception {

        for (Pair<String, Boolean> pair : pairs) {
            assertEquals(pair.getLeft(), pair.getRight(), Checking.checkChangeWithManifest(feature, Manifest.getManifest(IOUtils.toString(new URL(pair.getLeft())))));
        }

    }

}
