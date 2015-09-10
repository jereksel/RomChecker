import org.junit.Test;
import pl.andrzejressel.romchecker.lib.feature.Feature;

public class UnknownFieldTest {

    @Test
    public void test() throws Exception {

        String xml = "<?xml version=\"1.0\"?>\n" +
                "<feature name=\"CyanogenMod Theme Engine\">\n" +
                "    <section>\n" +
                "        <file test=\"test\" repo=\"packages/apps/ThemeChooser\"/>\n" +
                "    </section>\n" +
                "</feature>";


        Feature.getFeature(xml);


    }

}
