package com.epam.ta.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.Duration;

public class LoginPage {
    private final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final String PAGE_URL = "https://www.saucedemo.com/";
    private WebDriver driver;
    private WebDriverWait wait;


    @FindBy(xpath = "//*[@id='user-name']")
    private WebElement username;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//*[@id='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterUserName(String text) {
        username.sendKeys(text);
    }

    public void enterPassword(String text) {
        password.sendKeys(text);
    }

    public InventoryPage acceptLoginButton() {
        wait.until(ExpectedConditions
                .elementToBeClickable(loginButton)).click();
        return new InventoryPage(driver);
    }

    public String getErrorText() {
        return errorMessage.getText();
    }

    public LoginPage openPage() {
        logger.info("Open login page");
        driver.navigate().to(PAGE_URL);
        logger.debug("Login page is opened");
        return this;
    }

    private void clearInputElement(WebElement element) {
        int usernameLength = element.getAttribute("value").length();
        for (int i = 0; i < usernameLength; i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void clearUsername() {
        logger.info("Clear username");
        clearInputElement(username);
        logger.debug("Username is cleared");
    }

    public void clearPassword() {
        logger.info("Clear password");
        clearInputElement(password);
        logger.debug("Password is cleared");
    }
}
