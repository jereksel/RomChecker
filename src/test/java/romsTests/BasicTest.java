package romsTests;

import org.junit.Test;
import pl.andrzejressel.romchecker.lib.roms.Rom;
import pl.andrzejressel.romchecker.lib.roms.Roms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicTest {

    @Test
    public void test() throws Exception {

        String xml = "<?xml version=\"1.0\"?>\n" +
                "<roms>\n" +
                "    <rom name=\"AOSP\"\n" +
                "         manifest=\"https://raw.githubusercontent.com/android/platform_manifest/android-5.1.1_r13/default.xml\"/>\n" +
                "    <rom name=\"CyanogenMod 12.1\" manifest=\"https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml\"/>\n" +
                "    <rom name=\"SlimLP\" manifest=\"https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml\"/>\n" +
                "</roms>";


        Roms roms = Roms.getRoms(xml);

        assertEquals("A", createRom("A", "B").getName());
        assertEquals("B", createRom("A", "B").getManifest());
        assertFalse(roms.getRomsList().contains(createRom("AOSP", "asd")));
        assertTrue(roms.getRomsList().contains(createRom("AOSP", "https://raw.githubusercontent.com/android/platform_manifest/android-5.1.1_r13/default.xml")));
        assertTrue(roms.getRomsList().contains(createRom("CyanogenMod 12.1", "https://raw.githubusercontent.com/CyanogenMod/android/cm-12.1/default.xml")));
        assertTrue(roms.getRomsList().contains(createRom("SlimLP", "https://raw.githubusercontent.com/SlimRoms/platform_manifest/lp5.1/default.xml")));
        assertEquals(3, roms.getRomsList().size());
    }

    private Rom createRom(String name, String manifest) throws Exception {


        Constructor<Rom> constructor = Rom.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Rom rom = constructor.newInstance();

        Field nameField = Rom.class.getDeclaredField("name");
        Field manifestField = Rom.class.getDeclaredField("manifest");
        nameField.setAccessible(true);
        manifestField.setAccessible(true);

        nameField.set(rom, name);
        manifestField.set(rom, manifest);

        return rom;

    }

}
