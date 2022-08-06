package ru.rvukolov.testbottelegram.HtmlParsers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HtmlParser {
    private static final String ZETFLIX_HOME = "https://hd2.zetfix.online";
    private static final String URI_LINE = "https://hd2.zetfix.online/index.php?do=search&subaction=search&search_start=0&full_search=0&result_from=1&story=";
    private static final String USER_AGENT = "Chrome/4.0.249.0 Safari/532.5";
    private static final String REFERRER = "http://www.google.com";
    private Document document;


    public String getFilmList() throws IOException {
        //TODO: реализовать поиск по имени
        //подставляем в searchLine название искомого фильма TODO (принять параметром)
        String searchLine = URI_LINE + URLEncoder.encode("доктор", StandardCharsets.UTF_8);
        //получаем html ответа
        document = Jsoup.connect(searchLine).userAgent(USER_AGENT).referrer(REFERRER).get();
        //поиск элементов по классу sres-wrap clearfix (там находится ссылка в href
        var elements = document.getElementsByClass("sres-wrap clearfix");
        for (Element element : elements) {
            System.out.println(element.attr("href"));
            //поиск элемента, у которого class содержит sres-text (название фильма)
            var e = element.childNodes().stream().filter(c -> c.attr("class").equals("sres-text")).findFirst().get();
            //TODO: доделать корректный вывод названия
            System.out.println(e);
        }
        return elements.get(0).attr("href");
    }

    public String getFileUri(String uri) throws IOException {
        //запрашиваем страницу по ссылке, полученной из getFilmList()
        document = Jsoup.connect(uri).referrer(REFERRER).get();
        //выдергиваем элементы с тэгом iframe, забираем оттуда атрибут src (путь до плеера iplayer/player.php)
        var element = document.getElementsByTag("iframe").attr("src");
        //TODO:придумать что-то с реферером
        //запрашиваем плеер (обязательно указывать referrer - предыдущая страница, откуда "перешли"), дёргаем оттуда
        //элемент с тэгом iframe, берём src (прямая ссылка на videodb.php, видимо подгружает файлы в плеер)
        var filmDbUri = Jsoup.connect( ZETFLIX_HOME + element).referrer(uri).get()
                .getElementsByTag("iframe").attr("src");
        //здесь запрашиваем уже ссылки на сами фильмы, которые в ответ отдаёт videodb.php
        System.out.println(Jsoup.connect(ZETFLIX_HOME + filmDbUri).referrer(filmDbUri).get().getElementsByTag("script").toString());
     //   var splitted = filmFinalGetUri.split("\\D\\d{3,4}\\w\\D");
        return null;
    }
}
