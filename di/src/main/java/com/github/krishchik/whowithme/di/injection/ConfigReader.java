package com.github.krishchik.whowithme.di.injection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigReader {
    private static ConfigReader instance;
    private Properties properties;

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public Properties readConfig(String path) throws FileNotFoundException {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(path)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException | NullPointerException e) {
            throw new FileNotFoundException("Property file not found");
        }
        return properties;
    }

}
