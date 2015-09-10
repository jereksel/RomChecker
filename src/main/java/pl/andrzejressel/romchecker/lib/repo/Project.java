package pl.andrzejressel.romchecker.lib.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Project {

    String path;
    String name;
    String remote;
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
