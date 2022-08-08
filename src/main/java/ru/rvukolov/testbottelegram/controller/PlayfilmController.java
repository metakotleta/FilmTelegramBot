package ru.rvukolov.testbottelegram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String playfilm(Model model) throws IOException {
        model.addAttribute("msg", playfilmService.getFileJson());
        System.out.println(model.getAttribute("msg"));
        return "playfilm";
    }
}
