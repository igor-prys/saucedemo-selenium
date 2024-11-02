package com.epam.ta.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class InventoryPage {
    private final Logger logger = LoggerFactory.getLogger(InventoryPage.class);
    private final String PAGE_URL = "https://www.saucedemo.com/inventory.html";
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//*[@class='header_secondary_container']/span[@class='title']")
    private WebElement headerTitleElement;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        logger.info("Fetch title");
        return driver.getTitle();
    }

    public String getHeaderTitleElementValue() {
        logger.info("Fetch header text");
        return headerTitleElement.getText();
    }

}
