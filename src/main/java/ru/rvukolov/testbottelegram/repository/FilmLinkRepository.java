package ru.rvukolov.testbottelegram.repository;

import org.springframework.stereotype.Repository;
import ru.rvukolov.testbottelegram.model.ZetflixFilmLinkPair;

import java.util.List;
import java.util.Map;
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
