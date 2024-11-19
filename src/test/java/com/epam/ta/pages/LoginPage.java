package com.epam.ta.pages;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.models.User;
import com.epam.ta.services.TestDataReader;
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
import java.util.Optional;

public class LoginPage extends BasePage {
    private final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final String WAIT_PROPERTY = "wait.timeout.seconds";
    private final int WAIT_DEFAULT = 10;
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

    public LoginPage() {
        this.driver = DriverSingleton.getDriver();
        int waitDuration = TestDataReader.getIntProperty(WAIT_PROPERTY).orElse(WAIT_DEFAULT);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String text) {
        username.sendKeys(text);
    }

    public void enterPassword(String text) {
        password.sendKeys(text);
    }

    public void enterCredentials(User user) {
        enterUsername(user.username());
        enterPassword(user.password());
    }

    public InventoryPage acceptLoginButton() {
        wait.until(ExpectedConditions
                .elementToBeClickable(loginButton)).click();
        return new InventoryPage();
    }

    public String getErrorText() {
        return errorMessage.getText();
    }

    public LoginPage openPage() {
        logger.info("Open login page");
        navigateByDirectLink();
        logger.debug("Login page is opened");
        return this;
    }

    private void clearInputElement(WebElement element) {
        int usernameLength = Optional.ofNullable(element.getAttribute("value"))
                .map(v -> v.length())
                .orElse(0);
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

    @Override
    protected String getRelativeUrl() {
        return "";
    }

    @Override
    protected void navigateByDirectLink() {
        driver.navigate().to( getBaseUrl() + getRelativeUrl());
    }

}
