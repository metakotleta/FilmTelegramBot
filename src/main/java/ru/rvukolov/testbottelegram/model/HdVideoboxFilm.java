package ru.rvukolov.testbottelegram.model;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HdVideoboxFilm {
    private String name;
    @Expose
    private String title;
    @Expose
    private String file;
    private List<LinkAndResolution> linkAndResolutionList;
    private String poster;

    public HdVideoboxFilm() {
    }

    public HdVideoboxFilm(String title, String file, List<LinkAndResolution> linkAndResolutionList) {
        this.title = title;
        this.file = file;
        this.linkAndResolutionList = linkAndResolutionList;
    }

    public HdVideoboxFilm(String file, List<LinkAndResolution> linkAndResolutionList) {
        this.file = file;
        this.linkAndResolutionList = linkAndResolutionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public HdVideoboxFilm setLinkAndResolutions() {
        String[] links = file.split(",");
        this.linkAndResolutionList = Arrays.stream(links).map(LinkAndResolution::new).collect(Collectors.toList());
        return this;
    }

    public List<LinkAndResolution> getLinkAndResolutionList() {
        return linkAndResolutionList;
    }

}
