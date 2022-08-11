package ru.rvukolov.testbottelegram.properties;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
//TODO: возможно переписать под многопоточку
    private static AppProperties instance;
    private static Properties props;

    private AppProperties() {
        try (InputStream is = this.getClass().getResourceAsStream("/application.properties")) {
            this.props = new Properties();
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }

    public Properties getProps() {
        return props;
    }
}