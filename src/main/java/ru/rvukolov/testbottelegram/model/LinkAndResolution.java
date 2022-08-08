package ru.rvukolov.testbottelegram.model;

public class LinkAndResolution {
    private String resolution;
    private String link;

    public LinkAndResolution(String string) {
        String[] arr = string.substring(1).split("]");
        this.resolution = arr[0];
        this.link = arr[1];
    }

    public String getResolution() {
        return resolution;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return resolution + ": " + link;
    }
}
