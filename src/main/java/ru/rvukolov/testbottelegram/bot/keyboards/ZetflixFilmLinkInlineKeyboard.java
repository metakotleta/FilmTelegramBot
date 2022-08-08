package ru.rvukolov.testbottelegram.bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.rvukolov.testbottelegram.model.ZetflixFilmLinkPair;

import java.util.ArrayList;
import java.util.List;

public class ZetflixFilmLinkInlineKeyboard {

    private final static String COMMAND = "/getFilmLink";
    InlineKeyboardMarkup keyboard;

    public ZetflixFilmLinkInlineKeyboard(List<ZetflixFilmLinkPair> filmLinkList) {
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        for (int i = 0; i < filmLinkList.size(); i++) {
            buttonList.add(InlineKeyboardButton.builder().text(filmLinkList.get(i).getName()).callbackData(COMMAND + " " + i).build());
        }
        keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(buttonList)
                .build();
    }

    public InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }
}
