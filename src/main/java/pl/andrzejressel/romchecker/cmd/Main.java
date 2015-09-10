package pl.andrzejressel.romchecker.cmd;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import pl.andrzejressel.romchecker.lib.Checking;
import pl.andrzejressel.romchecker.lib.feature.Feature;
import pl.andrzejressel.romchecker.lib.features.Features;
import pl.andrzejressel.romchecker.lib.repo.Manifest;
import pl.andrzejressel.romchecker.lib.roms.Rom;
import pl.andrzejressel.romchecker.lib.roms.Roms;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final char tick = '+';
    public static final char tickFancy = '\u2611';

    public static final char cross = 'X';
    public static final char crossFancy = '\u2612';

    private static boolean fancy = false;

    public static void main(String[] args) throws Exception {

        CommandLineParser parser = new DefaultParser();

        Options options = getOptions();

        CommandLine line;

        try {
            line = parser.parse(options, args);
        } catch (MissingOptionException e) {
            printUsageAndExit();
            return;
        }


        if (line.hasOption("help")) {
            printUsageAndExit();
            return;
        }

        if (line.hasOption("fancy")) {
            fancy = true;
        }


        String[] files = line.getOptionValues("files");

        String manifestString;

        try {
            manifestString = IOUtils.toString(new URL(files[0]));
        } catch (Exception e) {
            manifestString = FileUtils.readFileToString(new File(files[0]));
        }

        String featureString;

        try {
            featureString = IOUtils.toString(new URL(files[1]));
        } catch (Exception e) {
            featureString = FileUtils.readFileToString(new File(files[1]));
        }


        List<Pair<Manifest, String>> manifests = new ArrayList<>();


        if (!line.hasOption("roms")) {
            manifests.add(new ImmutablePair<>(Manifest.getManifest(manifestString), ""));
        } else {

            Roms roms = Roms.getRoms(manifestString);

            for (Rom rom : roms.getRomsList()) {
                manifests.add(new ImmutablePair<>(Manifest.getManifest(IOUtils.toString(new URL(rom.getManifest()))), rom.getName()));
            }

        }


        List<Feature> features = new ArrayList<>();

        if (!line.hasOption("features")) {
            features.add(Feature.getFeature(featureString));
        } else {

            Features featuresTemp = Features.getFeatures(featureString);

            for (String string : featuresTemp.getFeatures()) {
                features.add(Feature.getFeature(featuresTemp.getFeatureXML(string, files[1])));
            }

        }

        for (Pair<Manifest, String> manifest : manifests) {

            System.out.println(manifest.getValue());

            for (Feature feature : features) {
                checkFeature(feature, manifest.getKey());
            }

            System.out.println();

        }
    }


    private static void checkFeature(Feature feature, Manifest manifest) {


        System.out.print(feature.getName() + " ");

        if (Checking.checkChangeWithManifest(feature, manifest)) {
            System.out.println(fancy ? tickFancy : tick);
        } else {
            System.out.println(fancy ? crossFancy : cross);
        }

    }

    private static void printUsageAndExit() {
        new HelpFormatter().printHelp("RomChecker", getOptions(), true);
    }

    private static Options getOptions() {

        Option help = new Option("h", "help", false, "print this message");
        Option features = new Option("f", "features", false, "use features list instead of feature");
        Option rom = new Option("r", "roms", false, "use roms list instead of rom");
        Option fancy = new Option(null, "fancy", false, "use fancy unicode characters (doesn't work on Windows terminal)");

        Option files = Option.builder().longOpt("files").argName("Manifest file> <Feature file").numberOfArgs(2)
                .hasArgs().optionalArg(false).valueSeparator(' ').desc("files to parse").required(true)
                .build();

        Options options = new Options();
        options.addOption(help);
        options.addOption(features);
        options.addOption(files);
        options.addOption(fancy);
        options.addOption(rom);

        return options;

    }


}
