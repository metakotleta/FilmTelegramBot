package ru.rvukolov.testbottelegram.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class UserFilm {

    @Id
    private String chatId;
    private String filmName;
    private String fileLinks;
    private Timestamp naviDate;

    public UserFilm() {
    }

    public UserFilm(String chatId, String filmName, String fileLinks, Timestamp naviDate) {
        this.chatId = chatId;
        this.filmName = filmName;
        this.fileLinks = fileLinks;
        this.naviDate = naviDate;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFileLinks() {
        return fileLinks;
    }

    public void setFileLinks(String fileLinks) {
        this.fileLinks = fileLinks;
    }

    public Timestamp getNaviDate() {
        return naviDate;
    }

    public void setNaviDate(Timestamp naviDate) {
        this.naviDate = naviDate;
    }

    @Override
    public String toString() {
        return "UserFilm{" +
                "chatId='" + chatId + '\'' +
                ", filmName='" + filmName + '\'' +
                ", fileLinks='" + fileLinks + '\'' +
                ", naviDate=" + naviDate +
                '}';
    }
}
