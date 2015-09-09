package pl.andrzejressel.romchecker.lib.feature;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class Section {

    @JacksonXmlElementWrapper(localName = "file", useWrapping = false)
    @JacksonXmlProperty(localName = "file")
    List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return files;
    }
}
