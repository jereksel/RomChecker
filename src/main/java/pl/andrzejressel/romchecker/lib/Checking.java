package pl.andrzejressel.romchecker.lib;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import pl.andrzejressel.romchecker.lib.feature.Feature;
import pl.andrzejressel.romchecker.lib.feature.File;
import pl.andrzejressel.romchecker.lib.feature.Section;
import pl.andrzejressel.romchecker.lib.repo.Manifest;

import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;

public class Checking {

    //No instances
    private Checking() {
    }

    public static boolean checkChangeWithManifest(Feature feature, Manifest manifest) {

        //   return feature.getSections().parallelStream().anyMatch(section ->
        //           section.getFiles().parallelStream().allMatch(file -> checkFileWithManifest(file, manifest)));


        for (Section section : feature.getSections()) {

            int i = 0;

            for (File file : section.getFiles()) {

                if (checkFileWithManifest(file, manifest)) {
                    i++;
                } else {
                    break;
                }
            }

            if (i == section.getFiles().size()) {
                return true;
            }


        }

        return false;

    }


    private static boolean checkFileWithManifest(File file, Manifest manifest) {

        String url;

        try {
            url = manifest.getUrlForPath(file.getRepo());
        } catch (NoSuchElementException ignored) {
            //No repo in manifest
            return false;
        }

        if (file.getName() == null) {
            //No file - we only check repo existence
            return true;
        }

        url = StringUtils.substringAfter(url, "github.com/");

        String url2 = "https://raw.githubusercontent.com/" + url + "/" + file.getName();

        //download File

        String fileData;

        try {
            fileData = IOUtils.toString(new URL(url2));
        } catch (IOException e) {
            // e.printStackTrace();
            return false;
        }


        if (StringUtils.isEmpty(fileData)) {
            return false;
        }

        //If code is empty we only check file existence
        //noinspection SimplifiableIfStatement
        if (StringUtils.isEmpty(file.getCode())) {
            return true;
        }

        return StringUtils.deleteWhitespace(fileData).contains(StringUtils.deleteWhitespace(file.getCode()));
    }
}
