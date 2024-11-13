package com.epam.ta.services;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDataReader {
    private static final Logger logger = LoggerFactory.getLogger(TestDataReader.class);

    private static final ResourceBundle resourceBundle = ResourceBundle.
            getBundle(System.getProperty("environment"));

    public static Optional<String> getProperty(String key) {
        try {
            return Optional.of(resourceBundle.getString(key));
        } catch (MissingResourceException e) {
            logger.warn("The data not defined for key", key);
            return Optional.empty();
        }
    }

    public static Optional<Integer> getIntProperty(String key) {
        try {
            String property = resourceBundle.getString(key);
            int value = Integer.parseInt(property);
            Optional.of(value);
        } catch (MissingResourceException e) {
            logger.warn("The data not defined for key", key);
        } catch (NumberFormatException e) {
            logger.warn("The data not defined for key", key);
        }
        return Optional.empty();
    }
}