package pl.andrzejressel.romchecker.cmd;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pl.andrzejressel.romchecker.lib.Checking;
import pl.andrzejressel.romchecker.lib.feature.Feature;
import pl.andrzejressel.romchecker.lib.features.Features;
import pl.andrzejressel.romchecker.lib.repo.Manifest;

import java.io.File;
import java.net.URL;
import java.util.Collections;
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

        final Manifest manifest = Manifest.getManifest(manifestString);

        String changeString;

        try {
            changeString = IOUtils.toString(new URL(files[1]));
        } catch (Exception e) {
            changeString = FileUtils.readFileToString(new File(files[1]));
        }


        if (!line.hasOption("features")) {

            final Feature feature = Feature.getChange(changeString);

            checkFeature(feature, manifest);

        } else {

            Features features = Features.getFeatures(changeString);

            List<String> featuresList = features.getFeatures();

            Collections.sort(featuresList);

            for (String string : featuresList) {
                checkFeature(Feature.getChange(features.getFeatureXML(string, files[1])), manifest);
            }

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
        System.exit(1);
    }

    private static Options getOptions() {

        Option help = new Option("h", "help", false, "print this message");
        Option features = new Option("f", "features", false, "use features list instead of feature");
        Option fancy = new Option(null, "fancy", false, "use fancy unicode characters (doesn't work on Windows terminal)");

        Option files =  Option.builder().longOpt("files").argName("Manifest file> <Feature file").numberOfArgs(2)
                .hasArgs().optionalArg(false).valueSeparator(' ').desc("files to parse").required(true)
                .build();

        Options options = new Options();
        options.addOption(help);
        options.addOption(features);
        options.addOption(files);
        options.addOption(fancy);

        return options;

    }


}
