package pl.andrzejressel.romchecker.lib.repo;

import org.simpleframework.xml.Attribute;

public class Remote {

    @Attribute(name="name")
    String name;

    @Attribute(name="fetch")
    String fetch;

    @Attribute(name="revision", required = false)
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
