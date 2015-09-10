package pl.andrzejressel.romchecker.lib.feature;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

public class Feature {

    //No instances for you
    private Feature() {
    }

    public static Feature getFeature(String xml) throws Exception {
      //  return new XmlMapper().readValue(xml, Feature.class);
        return new Persister().read(Feature.class, xml, false);
    }

    @Attribute(name="name")
    private String name;

    @ElementList(inline=true)
    List<Section> sections = new ArrayList<>();

    public List<Section> getSections() {
        return sections;
    }

    public String getName() {
        return name;
    }


}
