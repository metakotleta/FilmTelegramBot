package ru.rvukolov.testbottelegram.bot.keyboards;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.rvukolov.testbottelegram.properties.AppProperties;

import java.util.ArrayList;
import java.util.List;

public class DirectLinkKeyboard {
    private InlineKeyboardMarkup keyboard;
    private final static String SERVER_HOST = AppProperties.getInstance().getProps().getProperty("server.address");
    private final static String SERVER_PORT = AppProperties.getInstance().getProps().getProperty("server.port");

    public DirectLinkKeyboard(long chat_id) {

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("DirectLink").url("http://" + SERVER_HOST + ":" + SERVER_PORT + "/playfilm?id=" + chat_id).build());
        keyboard = InlineKeyboardMarkup.builder().keyboardRow(row).build();
    }

    public InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }
}
