package ru.rvukolov.testbottelegram.bot.keyboards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.rvukolov.testbottelegram.properties.AppProperties;

import java.util.ArrayList;
import java.util.List;

public class DirectLinkKeyboard {
    private InlineKeyboardMarkup keyboard;
    private final static Logger log = LoggerFactory.getLogger(DirectLinkKeyboard.class);
    private final static String SERVER_HOST = AppProperties.getInstance().getProps().getProperty("server.address.external");
    private final static String SERVER_PORT = AppProperties.getInstance().getProps().getProperty("server.port.external");

    public DirectLinkKeyboard(long chatId) {
        log.debug("Chat_id value is {}", chatId);
        log.debug("Server socket: {}, {}", SERVER_HOST, SERVER_PORT);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("DirectLink").url("http://" + SERVER_HOST + ":" + SERVER_PORT + "/playfilm?id=" + chatId).build());
        keyboard = InlineKeyboardMarkup.builder().keyboardRow(row).build();
    }

    public InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }
}
