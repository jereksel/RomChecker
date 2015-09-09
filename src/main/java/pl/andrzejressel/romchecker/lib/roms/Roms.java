package pl.andrzejressel.romchecker.lib.roms;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class Roms {

    @JacksonXmlProperty(localName = "rom")
    @JacksonXmlElementWrapper(localName = "rom", useWrapping = false)
    private List<Rom> roms = new ArrayList<>();

    private Roms() {
    }

    public List<Rom> getRomsList() {
        return roms;
    }

    public static Roms getRoms(String xml) throws Exception {
        return new XmlMapper().readValue(xml, Roms.class);
    }

}
