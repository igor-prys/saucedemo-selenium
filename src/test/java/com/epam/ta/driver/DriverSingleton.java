package com.epam.ta.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DriverSingleton {

    private static WebDriver driver;
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
        if (null == driver) {
            String browser = getBrowser();
            BrowserStrategy strategy = strategies.getOrDefault(browser, new ChromeStrategy());
            driver = strategy.createDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}