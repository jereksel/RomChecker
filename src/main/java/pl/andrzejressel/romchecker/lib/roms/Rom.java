package pl.andrzejressel.romchecker.lib.roms;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

public class Rom {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "manifest")
    private String manifest;

    private Rom() {
    }

    public String getName() {
        return name;
    }

    public String getManifest() {
        return manifest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rom rom = (Rom) o;
        return Objects.equals(name, rom.name) &&
                Objects.equals(manifest, rom.manifest);
    }

}
