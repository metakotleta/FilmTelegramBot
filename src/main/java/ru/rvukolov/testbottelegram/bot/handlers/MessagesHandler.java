package ru.rvukolov.testbottelegram.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.rvukolov.testbottelegram.HtmlParsers.HtmlParser;
import ru.rvukolov.testbottelegram.bot.keyboards.ZetflixFilmLinkInlineKeyboard;
import ru.rvukolov.testbottelegram.repository.FilmLinkRepository;

import java.io.IOException;

public class MessagesHandler {

    private Update update;
    private FilmLinkRepository filmLinkRepository;

    public MessagesHandler(Update update, FilmLinkRepository filmLinkRepository) {
        this.update = update;
        this.filmLinkRepository = filmLinkRepository;
    }

    public SendMessage handleMessage(HtmlParser htmlParser) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(update.getMessage().getChatId().toString());
        System.out.println(update.getMessage().toString());
        String searchLine = update.getMessage().getText();
        try {
            var filmList = htmlParser.getFilmList(searchLine);
            filmLinkRepository.setNameLinkPair(filmList);
            message.setReplyMarkup(new ZetflixFilmLinkInlineKeyboard(filmLinkRepository.getNameLinkPair()).getKeyboard());
            message.setText("Выбери фильм:");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
