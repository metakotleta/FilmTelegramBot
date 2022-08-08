package ru.rvukolov.testbottelegram.repository;

import org.springframework.stereotype.Repository;

@Repository
public class PlayfilmRepository {

    private String fileJson;

    public void setFileJson(String fileJson) {
        this.fileJson = fileJson;
    }

    public String getFileJson() {
        return fileJson;
    }
}
