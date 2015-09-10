package pl.andrzejressel.romchecker.lib.repo;

import org.simpleframework.xml.Attribute;

public class Default {

    @Attribute(name = "revision")
    String revision;

    @Attribute(name = "remote")
    String remote;

    public Default() {
    }

    public String getRevision() {
        return revision;
    }

    public String getRemote() {
        return remote;
    }
}
