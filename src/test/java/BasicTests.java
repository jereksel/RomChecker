import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import pl.andrzejressel.romchecker.lib.feature.Feature;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicTests {

    @Test
    public void basicChangeParsingTest() throws Exception {


        final Feature feature = Feature.getFeature(IOUtils.toString(new File("xmls/features/slim_pie.xml").toURI(), "UTF-8"));

        assertEquals("frameworks/base", feature.getSections().get(0).getFiles().get(0).getRepo());
        assertEquals("PIE (Slim)", feature.getName());

        assertEquals(1, feature.getSections().size());
        assertEquals(null, feature.getSections().get(0).getFiles().get(1).getCode());
        assertTrue(StringUtils.isEmpty(feature.getSections().get(0).getFiles().get(0).getCode()));
        assertEquals(2, feature.getSections().get(0).getFiles().size());
    }

}