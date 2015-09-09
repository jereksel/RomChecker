package pl.andrzejressel.romchecker.lib.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"repo", "groups", "copyfile", "src", "dest", "name"})
public class Manifest {

    @JacksonXmlProperty(localName = "remote")
    @JacksonXmlElementWrapper(localName = "remote", useWrapping = false)
    private List<Remote> remotes = new ArrayList<>();

    @JacksonXmlProperty(localName = "project")
    @JacksonXmlElementWrapper(localName = "project", useWrapping = false)
    private List<Project> projects = new ArrayList<>();

    @JacksonXmlProperty(localName = "default")
    private Default aDefault;


    public static Manifest getManifest(String xml) throws Exception {
        return new XmlMapper().readValue(xml, Manifest.class);
    }

    public String getUrlForPath(String path) {

        //First, we're looking for project with given path

        Project project = projects.parallelStream().filter(project1 -> project1.getPath().equalsIgnoreCase(path)).findFirst().get();

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
        Remote remote = remotes.parallelStream().filter(remote1 -> remote1.getName().equalsIgnoreCase(finalRemoteName)).findFirst().get();


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


        return remoteUrl + name + "/" + revision;

    }

}
