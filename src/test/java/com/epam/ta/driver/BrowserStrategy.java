package com.epam.ta.driver;

import org.openqa.selenium.WebDriver;

public interface BrowserStrategy {
    WebDriver createDriver();
}