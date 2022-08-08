package ru.rvukolov.testbottelegram.service;

import org.springframework.stereotype.Service;
import ru.rvukolov.testbottelegram.repository.PlayfilmRepository;

@Service
public class PlayfilmService {

    private PlayfilmRepository playfilmRepository;

    public PlayfilmService(PlayfilmRepository playfilmRepository) {
        this.playfilmRepository = playfilmRepository;
    }

    public String getFileJson() {
        return playfilmRepository.getFileJson();
    }
}
