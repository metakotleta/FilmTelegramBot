package ru.rvukolov.testbottelegram.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HdVideoboxFilmSimple {
    private String file;
    private List<LinkAndResolution> linkAndResolutionList;

    public HdVideoboxFilmSimple setFile(String file) {
        this.file = file;
        return this;
    }

    public String getFile() {
        return file;
    }
    public HdVideoboxFilmSimple setLinkAndResolutions() {
        String[] links = file.split(",");
        this.linkAndResolutionList = Arrays.stream(links).map(LinkAndResolution::new).collect(Collectors.toList());
        return this;
    }

    public List<LinkAndResolution> getLinkAndResolutionList() {
        return linkAndResolutionList;
    }

    public HdVideoboxFilm getHdVideoboxFilm() {
        return new HdVideoboxFilm(file, linkAndResolutionList);
    }
}
