package pl.andrzejressel.romchecker.lib.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"sync-c", "sync-j", "sync-f"})
public class Default {

    String revision;
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
