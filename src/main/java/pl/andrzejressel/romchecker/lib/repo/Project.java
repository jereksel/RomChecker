package pl.andrzejressel.romchecker.lib.repo;


import org.simpleframework.xml.Attribute;

public class Project {

    @Attribute(name="path")
    String path;

    @Attribute(name="name")
    String name;

    @Attribute(name="remote", required = false)
    String remote;

    @Attribute(name="revision", required = false)
    String revision;

    public Project() {
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getRemote() {
        return remote;
    }

    public String getRevision() {
        return revision;
    }
}
