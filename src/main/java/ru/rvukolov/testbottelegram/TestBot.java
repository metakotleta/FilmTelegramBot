package ru.rvukolov.testbottelegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.rvukolov.testbottelegram.HtmlParsers.HtmlParser;
import ru.rvukolov.testbottelegram.bot.handlers.CallbackQueryHandler;
import ru.rvukolov.testbottelegram.bot.handlers.MessagesHandler;
import ru.rvukolov.testbottelegram.repository.FilmLinkRepository;
import ru.rvukolov.testbottelegram.repository.PlayfilmRepository;
import ru.rvukolov.testbottelegram.service.PlayfilmService;

@Component
public class TestBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    final private FilmLinkRepository filmLinkRepository;
    private static final Logger log = LoggerFactory.getLogger(TestBot.class);


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
        HtmlParser htmlParser = new HtmlParser();
        SendMessage message;

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = new MessagesHandler(update, filmLinkRepository).handleMessage(htmlParser);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        } else if (update.hasCallbackQuery()) {
            message = new CallbackQueryHandler(update, filmLinkRepository)
           //         .setPlayfilmRepository(SpringContext.getApplicationContext().getBean(PlayfilmRepository.class))
                    .setPlayfilmRepository(TestBotTelegramApplication.context.getBean(PlayfilmRepository.class))
                    .handleCallbackQuery(htmlParser);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


}
