package com.epam.ta.pages;

import com.epam.ta.driver.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InventoryPage extends BasePage {
    private final Logger logger = LoggerFactory.getLogger(InventoryPage.class);
    private WebDriver driver;

    @FindBy(xpath = "//*[@class='header_secondary_container']/span[@class='title']")
    private WebElement headerTitleElement;

    public InventoryPage() {
        this.driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }


    @Override
    protected String getRelativeUrl() {
        return "/inventory";
    }

    @Override
    protected void navigateByDirectLink() {
        driver.navigate().to( getBaseUrl() + getRelativeUrl());
    }

    public String getHeaderTitleElementValue() {
        logger.info("Fetch header text");
        return headerTitleElement.getText();
    }
}
