package pl.andrzejressel.romchecker.lib.feature;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.IOException;
import java.util.*;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class Feature {

    //No instances for you
    private Feature() {
    }

    public static Feature getChange(String xml) throws IOException {
        return new XmlMapper().readValue(xml, Feature.class);
    }

    @XmlAttribute(name = "repo")
    private String repo;

    @XmlAttribute(name = "name")
    private String name;

    @JacksonXmlElementWrapper(localName = "section", useWrapping = false)
    @JacksonXmlProperty(localName = "section")
    List<Section> sections = new ArrayList<>();

    public String getRepo() {
        return repo;
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getName() {
        return name;
    }


}
