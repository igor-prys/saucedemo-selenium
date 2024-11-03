package com.epam.ta.tests;

import Service.UserCreator;
import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.models.User;
import com.epam.ta.pages.InventoryPage;
import com.epam.ta.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public class LoginPageTest {
    private static WebDriver driver;
    private LoginPage loginPage = new LoginPage(driver);
    private static User user;

    @BeforeAll
    public static void setup() {
        driver = DriverSingleton.getDriver();
        user=UserCreator.withCredentialsFromProperty();
    }

    @BeforeEach
    public void before() {
        loginPage.openPage();
    }

    @AfterAll()
    public static void stopBrowser() {
        DriverSingleton.closeDriver();
    }


    @Test
    public void shouldProhibitAccessWithoutUsername() {
        loginPage.enterUserName(user.getUsername());
        loginPage.enterPassword(user.getPassword());
        loginPage.clearUsername();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Username is required", loginPage.getErrorText());
    }

    @Test
    public void shouldProhibitAccessWithoutPassword() {
        loginPage.enterUserName(user.getUsername());
        loginPage.enterPassword(user.getPassword());
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Password is required", loginPage.getErrorText());
    }

    @Test
    public void shouldProhibitAccessWithoutCredentials() {
        loginPage.enterUserName(user.getUsername());
        loginPage.enterPassword(user.getPassword());
        loginPage.clearUsername();
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Username is required", loginPage.getErrorText());
    }

    @Test
    public void shouldLoginWithCorrectCredentials() {
        User user = UserCreator.withCredentialsFromProperty();
        loginPage.enterUserName(user.getUsername());
        loginPage.enterPassword(user.getPassword());
        InventoryPage inventoryPage = loginPage.acceptLoginButton();
        Assertions.assertEquals("Swag Labs", inventoryPage.getTitle());
        Assertions.assertEquals("Products", inventoryPage.getHeaderTitleElementValue());
    }


}
