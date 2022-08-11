package ru.rvukolov.testbottelegram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rvukolov.testbottelegram.HtmlParsers.HtmlParser;
import ru.rvukolov.testbottelegram.model.HdVideoboxFilm;
import ru.rvukolov.testbottelegram.service.PlayfilmService;

import java.io.IOException;

@Controller
public class PlayfilmController {

    private PlayfilmService playfilmService;

    public PlayfilmController(PlayfilmService playfilmService) {
        this.playfilmService = playfilmService;
    }

    @GetMapping("/playfilm")
    public String playfilm(Model model, @RequestParam String id) throws IOException {
        model.addAttribute("msg", playfilmService.getFileJson(id));
        return "playfilm";
    }
}
