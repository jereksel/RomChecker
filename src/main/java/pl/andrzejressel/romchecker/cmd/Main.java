package pl.andrzejressel.romchecker.cmd;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pl.andrzejressel.romchecker.lib.Checking;
import pl.andrzejressel.romchecker.lib.feature.Feature;
import pl.andrzejressel.romchecker.lib.repo.Manifest;

import java.io.File;
import java.net.URL;

public class Main {

    public static final char tick = '+';
    public static final char cross = '-';
    public static final String usage = "Usage: <rom manifest file (URL/Filesystem)> <feature manifest file (URL/Filesystem)>";

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            usage();
            return;
        }

        String manifestString;

        try {
            manifestString = IOUtils.toString(new URL(args[0]));
        } catch (Exception e) {
            manifestString = FileUtils.readFileToString(new File(args[0]));
        }

        final Manifest manifest = Manifest.getManifest(manifestString);


        String changeString;

        try {
            changeString = IOUtils.toString(new URL(args[1]));
        } catch (Exception e) {
            changeString = FileUtils.readFileToString(new File(args[1]));
        }


        final Feature feature = Feature.getChange(changeString);

        System.out.print(feature.getName() + " ");

        if (Checking.checkChangeWithManifest(feature, manifest)) {
            System.out.print(tick);
        } else {
            System.out.print(cross);
        }

        System.out.println();

    }

    private static void usage() {
        System.out.println(usage);
    }

}
