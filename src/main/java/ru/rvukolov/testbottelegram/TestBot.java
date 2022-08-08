package ru.rvukolov.testbottelegram;

import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.thymeleaf.spring5.context.SpringContextUtils;
import ru.rvukolov.testbottelegram.HtmlParsers.HtmlParser;
import ru.rvukolov.testbottelegram.bot.handlers.CallbackQueryHandler;
import ru.rvukolov.testbottelegram.bot.keyboards.ZetflixFilmLinkInlineKeyboard;
import ru.rvukolov.testbottelegram.repository.FilmLinkRepository;
import ru.rvukolov.testbottelegram.repository.PlayfilmRepository;

import java.io.IOException;

@Component
public class TestBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    final private FilmLinkRepository filmLinkRepository;



    public TestBot(FilmLinkRepository filmLinkRepository) {
        this.filmLinkRepository = filmLinkRepository;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        HtmlParser hParser = new HtmlParser();
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            String searchLine = update.getMessage().getText();

            try {
                filmLinkRepository.setNameLinkPair(hParser.getFilmList(searchLine));
                message.setReplyMarkup(new ZetflixFilmLinkInlineKeyboard(filmLinkRepository.getNameLinkPair()).getKeyboard());
                message.setText("Выбери фильм:");
                execute(message); // Call method to send the message

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            new CallbackQueryHandler(update, filmLinkRepository).
                    setPlayfilmRepository(SpringContext.getApplicationContext().getBean(PlayfilmRepository.class))
                    .handleCallbackQuery(hParser);
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setText("http://192.168.31.68:8080/playfilm");
            message.setChatId(update.getCallbackQuery().getMessage().getChatId());
           try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
