package ru.rvukolov.testbottelegram.bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.rvukolov.testbottelegram.model.ZetflixFilmLinkPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZetflixFilmLinkInlineKeyboard {

    private final static String COMMAND = "/getFilmLink";
    InlineKeyboardMarkup keyboard;

    public ZetflixFilmLinkInlineKeyboard(List<ZetflixFilmLinkPair> filmLinkList) {
        var keyboardBuilder = InlineKeyboardMarkup.builder();
        for (int i = 0; i < filmLinkList.size(); i++) {
            List<InlineKeyboardButton> buttonList = new ArrayList<>(Arrays.asList(InlineKeyboardButton.builder()
                    .text(filmLinkList.get(i).getName())
                    .callbackData(COMMAND + " " + i)
                    .build()));
            keyboardBuilder.keyboardRow(buttonList);
        }
        keyboard = keyboardBuilder.build();
    }

    public InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }
}
