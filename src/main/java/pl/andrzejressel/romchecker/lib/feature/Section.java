package pl.andrzejressel.romchecker.lib.feature;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

public class Section {

    @ElementList(inline=true)
    List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return files;
    }
}
