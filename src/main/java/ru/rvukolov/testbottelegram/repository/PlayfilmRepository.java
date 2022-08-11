package ru.rvukolov.testbottelegram.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PlayfilmRepository {

    private String fileJson;
    private Map<String, String> chatIdFileJson = new HashMap<>();

    public void setFileJson(String chatId, String fileJson) {
        chatIdFileJson.put(chatId, fileJson);
    }

    public String getFileJson(String chatId) {
        return chatIdFileJson.get(chatId);
    }
}
