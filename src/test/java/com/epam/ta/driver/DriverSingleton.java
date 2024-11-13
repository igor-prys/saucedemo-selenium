package com.epam.ta.driver;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DriverSingleton {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Map<String, BrowserStrategy> strategies = new HashMap<>();

    static {
        strategies.put("chrome", new ChromeStrategy());
        strategies.put("firefox", new FirefoxStrategy());
        strategies.put("edge", new EdgeStrategy());
    }

    private DriverSingleton() {
    }

    private static String getBrowser() {
        return Optional.ofNullable(System.getProperty("browser")).orElse("chrome");
    }

    public static WebDriver getDriver() {
        if (null == driver.get()) {
            String browser = getBrowser();
            BrowserStrategy strategy = strategies.getOrDefault(browser, new ChromeStrategy());
            driver.set(strategy.createDriver());
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}