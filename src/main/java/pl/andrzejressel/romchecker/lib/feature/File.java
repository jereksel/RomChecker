package pl.andrzejressel.romchecker.lib.feature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAttribute;

@JsonIgnoreProperties(ignoreUnknown=true)
public class File {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "code")
    private String code;

    @XmlAttribute(name = "repo")
    private String repo;


    public File() {
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getRepo() {
        return repo;
    }
}
