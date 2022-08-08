package ru.rvukolov.testbottelegram.bot.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.rvukolov.testbottelegram.HtmlParsers.HtmlParser;
import ru.rvukolov.testbottelegram.repository.FilmLinkRepository;
import ru.rvukolov.testbottelegram.repository.PlayfilmRepository;

import java.io.IOException;

public class CallbackQueryHandler {
    private Update update;
    private FilmLinkRepository filmLinkRepository;
    private PlayfilmRepository playfilmRepository;

    public CallbackQueryHandler(Update update, FilmLinkRepository filmLinkRepository) {
        this.update = update;
        this.filmLinkRepository = filmLinkRepository;
    }

    public PlayfilmRepository getPlayfilmRepository() {
        return playfilmRepository;
    }

    public CallbackQueryHandler setPlayfilmRepository(PlayfilmRepository playfilmRepository) {
        this.playfilmRepository = playfilmRepository;
        return this;
    }

    public void handleCallbackQuery(HtmlParser hParser) {
        SendMessage message = new SendMessage();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        var callback = update.getCallbackQuery();
        switch (callback.getData().substring(0, callback.getData().indexOf(" "))) {
            case "/getFilmLink":
                try {
                    String[] arrayStr = callback.getData().split(" ");
                    var siteFilmLink = filmLinkRepository.getNameLinkPair().get(Integer.parseInt(arrayStr[1]));
                    var filmLinks = hParser.getFilmsJson(siteFilmLink.getLink());
                    var links = hParser.getFilms(filmLinks);

                    playfilmRepository.setFileJson(gson.toJson(links));
                    System.out.println(gson.toJson(links));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
