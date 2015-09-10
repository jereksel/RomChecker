package pl.andrzejressel.romchecker.lib.feature;

import org.simpleframework.xml.Attribute;

public class File {

    @Attribute(name = "name", required = false)
    private String name;

    @Attribute(name = "code", required = false)
    private String code;

    @Attribute(name = "repo")
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
