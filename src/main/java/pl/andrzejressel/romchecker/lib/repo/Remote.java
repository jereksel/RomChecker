package pl.andrzejressel.romchecker.lib.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAttribute;

@JsonIgnoreProperties({"review"})
public class Remote {

    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "fetch")
    String fetch;

    @XmlAttribute(name = "revision")
    String revision;

    public Remote() {
    }

    public String getName() {
        return name;
    }

    public String getFetch() {
        return fetch;
    }

    public String getRevision() {
        return revision;
    }

}
