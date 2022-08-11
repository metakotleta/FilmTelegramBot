package ru.rvukolov.testbottelegram.repository;

import org.springframework.stereotype.Repository;
import ru.rvukolov.testbottelegram.model.ZetflixFilmLinkPair;

import java.util.List;

@Repository
public class FilmLinkRepository {
    private List<ZetflixFilmLinkPair> nameLinkPair;

    public List<ZetflixFilmLinkPair> getNameLinkPair() {
        return nameLinkPair;
    }

    public void setNameLinkPair(List<ZetflixFilmLinkPair> nameLinkMap) {
        this.nameLinkPair = nameLinkMap;
    }
}
