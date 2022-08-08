package ru.rvukolov.testbottelegram.model;

public class ZetflixFilmLinkPair {
    private String name;
    private String link;

    public ZetflixFilmLinkPair(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }
}
