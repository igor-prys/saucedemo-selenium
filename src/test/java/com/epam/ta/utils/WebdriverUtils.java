package com.epam.ta.utils;

import com.epam.ta.driver.DriverSingleton;
import org.openqa.selenium.WebDriver;

public class WebdriverUtils {
    private static WebDriver webDriver = DriverSingleton.getDriver();

    private WebdriverUtils() {
        throw new RuntimeException("Not allowed to instantiate");
    }

    public static String getCurrentTitle() {
        return webDriver.getTitle();
    }

    public static String getCurrentUrL() {
        return webDriver.getCurrentUrl();
    }

    public static void refreshPage() {
        webDriver.navigate().refresh();
    }
}
