package pl.andrzejressel.romchecker.lib.roms;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

public class Roms {

    @ElementList(inline=true)
    private List<Rom> roms = new ArrayList<>();

    private Roms() {
    }

    public List<Rom> getRomsList() {
        return roms;
    }

    public static Roms getRoms(String xml) throws Exception {
        return new Persister().read(Roms.class, xml, false);
    }

}
