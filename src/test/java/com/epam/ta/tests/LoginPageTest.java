package com.epam.ta.tests;

import com.epam.ta.service.UserCreator;
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
        loginPage.enterCredentials(user);
    }

    @AfterAll()
    public static void stopBrowser() {
        DriverSingleton.closeDriver();
    }

    @Test
    public void shouldProhibitAccessWithoutUsername() {
        loginPage.clearUsername();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Username is required", loginPage.getErrorText());
    }

    @Test
    public void shouldProhibitAccessWithoutPassword() {
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Password is required", loginPage.getErrorText());
    }

    @Test
    public void shouldProhibitAccessWithoutCredentials() {
        loginPage.clearUsername();
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Username is required", loginPage.getErrorText());
    }

    @Test
    public void shouldLoginWithCorrectCredentials() {
        InventoryPage inventoryPage = loginPage.acceptLoginButton();
        Assertions.assertEquals("Swag Labs", inventoryPage.getTitle());
        Assertions.assertEquals("Products", inventoryPage.getHeaderTitleElementValue());
    }
}
