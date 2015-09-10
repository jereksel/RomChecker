package pl.andrzejressel.romchecker.lib.repo;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Manifest {

    @ElementList(inline=true)
    private List<Remote> remotes = new ArrayList<>();

    @ElementList(inline=true)
    private List<Project> projects = new ArrayList<>();

    @Element(name="default")
    private Default aDefault;


    public static Manifest getManifest(String xml) throws Exception {
        return new Persister().read(Manifest.class, xml, false);
    }

    public String getUrlForPath(String path) {

        //First, we're looking for project with given path

      //  Project project = projects.parallelStream().filter(project1 -> project1.getPath().equalsIgnoreCase(path)).findFirst().get();


        Project project = null;

        for (Project projectTemp : projects) {

            if (projectTemp.getPath().equalsIgnoreCase(path)) {
                project = projectTemp;
                break;
            }

        }

        if (project == null) {
            throw new NoSuchElementException();
        }



        String name = project.getName();

        String remoteName = project.getRemote();

        if (StringUtils.isEmpty(remoteName)) {
            remoteName = aDefault.getRemote();
        }


        String revision = project.getRevision();

        if (StringUtils.isEmpty(revision)) {
            revision = aDefault.getRevision();
        }

        revision = StringUtils.remove(revision, "refs/heads/");
        revision = StringUtils.remove(revision, "refs/tags/");


        //Then get remote
        final String finalRemoteName = remoteName;

        Remote remote = null;

        //  Remote remote = remotes.parallelStream().filter(remote1 -> remote1.getName().equalsIgnoreCase(finalRemoteName)).findFirst().get();

        for (Remote remoteTemp : remotes) {

            if (remoteTemp.getName().equalsIgnoreCase(finalRemoteName)) {
                remote = remoteTemp;
                break;
            }

        }

        if (remote == null) {
            throw new NoSuchElementException();
        }


        //Parsing it

        String remoteUrl = remote.getFetch();

        if (remoteUrl.equalsIgnoreCase("..")) {
            remoteUrl = "https://github.com/";
        }

        //AOSP hax
        //If name starts with "platform/" replace all "/" with "_"
        if (StringUtils.startsWith(name, "platform/")) {
            name = StringUtils.replace(name, "/", "_");
            name = "android/" + name;
        }

        //We may have to add "/" at the end of remoteUrl (e.g. Frank)
        if (remoteUrl.charAt(remoteUrl.length() - 1) != '/') {
            remoteUrl += "/";
        }

        return remoteUrl + name + "/" + revision;
    }

}
