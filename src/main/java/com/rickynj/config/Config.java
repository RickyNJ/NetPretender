package com.rickynj.config;

import java.util.Properties;

public class Config {
    static Properties properties = new Properties();

    static {
        try (var in = Config.class.getClassLoader().getResourceAsStream(".properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            properties.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
