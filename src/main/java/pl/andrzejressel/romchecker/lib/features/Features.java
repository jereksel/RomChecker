package pl.andrzejressel.romchecker.lib.features;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Features {

    @ElementList(inline=true)
    List<Feature> features = new ArrayList<>();

    public static Features getFeatures(String xmlInsides) throws Exception {
        return new Persister().read(Features.class, xmlInsides, false);
    }

    private Features() {
    }

    public List<String> getFeatures() {
       // return features.parallelStream().map(Feature::getName).collect(Collectors.toList());

        List<String> stringList = new ArrayList<>();

        for (Feature feature : features) {
            stringList.add(feature.getName());
        }


        return stringList;

    }

    public String getFeatureXML(String featureName, String featuresFileLocation) {

        URI featureLocation = getFeatureXMLLocation(featureName, featuresFileLocation);

        try {
            return IOUtils.toString(featureLocation);
        } catch (IOException ignored) {
        }

        return null;

    }

    public URI getFeatureXMLLocation(String featureName, String featuresFileLocation) {

        List<String> featuresList = getFeatures();

        if (!featuresList.contains(featureName)) {
            throw new IllegalArgumentException();
        }

     //   Feature feature = features.parallelStream().filter(e -> e.getName().equalsIgnoreCase(featureName)).findFirst().get();

        Feature feature = null;

        //  Remote remote = remotes.parallelStream().filter(remote1 -> remote1.getName().equalsIgnoreCase(finalRemoteName)).findFirst().get();

        for (Feature featureTemp : features) {

            if (featureTemp.getName().equalsIgnoreCase(featureName)) {
                feature = featureTemp;
                break;
            }

        }

        if (feature == null) {
            throw new NoSuchElementException();
        }


        File file = new File(feature.getLocation());

        if (file.exists()) {
            return file.toURI();
        }

        try {
            return new URL(feature.getLocation()).toURI();
        } catch (MalformedURLException | URISyntaxException ignored) {
        }


        File featuresFile = new File(featuresFileLocation);

        if (featuresFile.exists()) {
            //Features file is in filesystem (not from internet)

            File folder = featuresFile.getParentFile();

            return new File(folder.getAbsoluteFile() + File.separator + feature.getLocation()).toURI();

           // return featuresFile.toURI();

        }


        try {
            URL featuresUrl = new URL(StringUtils.remove(featuresFileLocation, StringUtils.substringAfterLast(featuresFileLocation, "/")));
            return new URL(featuresUrl.toString() + feature.getLocation()).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
           // e.printStackTrace();
        }

        return null;
    }


    //We should only access class from here (all access will be done in Features class)
    private static class Feature {

        @Attribute(name="location")
        public String location;

        @Attribute(name="name")
        public String name;

        Feature() {
        }

        public String getLocation() {
            return location;
        }

        public String getName() {
            return name;
        }
    }


}
