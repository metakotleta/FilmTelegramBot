package ru.rvukolov.testbottelegram.HtmlParsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.stereotype.Component;
import ru.rvukolov.testbottelegram.model.HdVideoboxFilm;
import ru.rvukolov.testbottelegram.model.HdVideoboxFilmSimple;
import ru.rvukolov.testbottelegram.model.LinkAndResolution;
import ru.rvukolov.testbottelegram.model.ZetflixFilmLinkPair;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class HtmlParser {
    private static final String ZETFLIX_HOME = "https://hd2.zetfix.online";
    private static final String URI_LINE = "https://hd2.zetfix.online/index.php?do=search&subaction=search&search_start=0&full_search=0&result_from=1&story=";
    private static final String USER_AGENT = "Chrome/4.0.249.0 Safari/532.5";
    private static final String REFERRER = "http://www.google.com";
    private Document document;
    private Gson gson = new Gson();

    public List<ZetflixFilmLinkPair> getFilmList(String name) throws IOException {
        //TODO: реализовать поиск по имени
        //подставляем в searchLine название искомого фильма TODO (принять параметром)
        String searchLine = URI_LINE + URLEncoder.encode(name, StandardCharsets.UTF_8);
        //получаем html ответа
        document = Jsoup.connect(searchLine).userAgent(USER_AGENT).referrer(REFERRER).get();
        //поиск элементов по классу sres-wrap clearfix (там находится ссылка в href
        var elements = document.getElementsByClass("sres-wrap clearfix");

        List<ZetflixFilmLinkPair> resultList = new ArrayList<>();
        //проходим циклом по нодам и достаём название + ссылку, кладём всё в мапу
        for (int i = 0; i < elements.size(); i++) {
            var e = elements.get(i);
            var link = e.attr("href");
            var ehtml = e.html();
            var eName = ehtml.substring(ehtml.indexOf("<h2>"),ehtml.indexOf("</h2>")).replace("<h2>","");
            resultList.add(new ZetflixFilmLinkPair(eName, link));
        }
        return resultList;

    }

    public String getFilmsJson(String uri) throws IOException {
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
        var filmFinalGetUri = Jsoup.connect(ZETFLIX_HOME + filmDbUri.substring(0, filmDbUri.indexOf("&"))).referrer(filmDbUri).get().body().getElementsByTag("script").toString();
        return filmFinalGetUri.substring(filmFinalGetUri.indexOf("file:") + "file:".length(), filmFinalGetUri.indexOf("}) ;"));
    }

    public List<HdVideoboxFilm> getFilms(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (json.contains("title")) {
            var filmObj = gson.fromJson(json, HdVideoboxFilm[].class);
            return Arrays.stream(filmObj).filter(Objects::nonNull).map(HdVideoboxFilm::setLinkAndResolutions).collect(Collectors.toList());
        } else {
            var filmObj = json;
            var film = new HdVideoboxFilmSimple().setFile(filmObj).setLinkAndResolutions().getHdVideoboxFilm();
            List<HdVideoboxFilm> filmList= new ArrayList<>();
            filmList.add(film);
            return filmList;
        }
    }
}
