package pl.andrzejressel.romchecker.lib.features;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Features {

    @JacksonXmlElementWrapper(localName = "feature", useWrapping = false)
    @JacksonXmlProperty(localName = "feature")
    List<Feature> features = new ArrayList<>();

    public static Features getFeatures(String xmlInsides) throws Exception {
        return new XmlMapper().readValue(xmlInsides, Features.class);
    }

    private Features() {
    }

    public List<String> getFeatures() {
        return features.parallelStream().map(Feature::getName).collect(Collectors.toList());
    }

    public String getFeatureXML(String featureName, String featuresFileLocation) {

        try {
           // return IOUtils.toString(new URI(getFeatureXMLLocation(featureName, featuresFileLocation)));

            return FileUtils.readFileToString(new File(getFeatureXMLLocation(featureName, featuresFileLocation)));

        } catch (IOException  e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getFeatureXMLLocation(String featureName, String featuresFileLocation) {

        List<String> featuresList = getFeatures();

        if (!featuresList.contains(featureName)) {
            throw new IllegalArgumentException();
        }

        Feature feature = features.parallelStream().filter(e -> e.getName().equalsIgnoreCase(featureName)).findFirst().get();

        File file = new File(feature.getLocation());

        if (file.exists()) {
            return file.getAbsolutePath();
        }

        try {
            return new URL(feature.getLocation()).toString();
        } catch (MalformedURLException e) {
            //  e.printStackTrace();
        }


        File featuresFile = new File(featuresFileLocation);

        if (featuresFile.exists()) {
            //Features file is in filesystem (not from internet)

            File folder = featuresFile.getParentFile();

            return (folder.getAbsoluteFile() + File.separator + feature.getLocation());

        }


        try {
            URL featuresUrl = new URL(StringUtils.remove(featuresFileLocation, StringUtils.substringAfterLast(featuresFileLocation, "/")));
            return featuresUrl.toString() + feature.getLocation();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }


    //We should only access class from here (all access will be done in Features class)
    private static class Feature {

        public String location;
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
