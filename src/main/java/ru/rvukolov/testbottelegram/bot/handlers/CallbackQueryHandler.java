package ru.rvukolov.testbottelegram.bot.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.rvukolov.testbottelegram.HtmlParsers.HtmlParser;
import ru.rvukolov.testbottelegram.bot.keyboards.DirectLinkKeyboard;
import ru.rvukolov.testbottelegram.repository.FilmLinkRepository;
import ru.rvukolov.testbottelegram.repository.PlayfilmRepository;

import java.io.IOException;

public class CallbackQueryHandler {
    private Update update;
    private FilmLinkRepository filmLinkRepository;
    private PlayfilmRepository playfilmRepository;
    private static final Logger log = LoggerFactory.getLogger(CallbackQueryHandler.class);

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

    public SendMessage handleCallbackQuery(HtmlParser hParser) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        var callback = update.getCallbackQuery();
        var chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        log.info("Receive callback query with cmd {}", callback.getData());
        switch (callback.getData().substring(0, callback.getData().indexOf(" "))) {
            case "/getFilmLink":
                try {
                    String[] arrayStr = callback.getData().split(" ");
                    var siteFilmLink = filmLinkRepository.getNameLinkPair().get(Integer.parseInt(arrayStr[1]));
                    log.info("FilmLinkPair is {} - {}", siteFilmLink.getName(), siteFilmLink.getLink());
                    var filmLinks = hParser.getFilmsJson(siteFilmLink.getLink());
                    var links = hParser.getFilms(filmLinks);
                    log.info("Generated links JSON: {}", gson.toJson(links));
                    playfilmRepository.setFileJson(chatId, gson.toJson(links));
                    log.info("JSON set to playfilmRepository");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        var keyboard = new DirectLinkKeyboard(update.getCallbackQuery().getMessage().getChatId()).getKeyboard();
        message.setReplyMarkup(keyboard);
        message.setText("Ссылка для просмотра");
        message.setChatId(chatId);
        log.info("Send generated link: {} to chat {}", keyboard.getKeyboard().get(0).get(0).getUrl(), chatId);
        return message;
    }
}
