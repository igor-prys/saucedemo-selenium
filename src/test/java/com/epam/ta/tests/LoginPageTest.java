package com.epam.ta.tests;

import Service.UserCreator;
import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.models.User;
import com.epam.ta.pages.InventoryPage;
import com.epam.ta.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public class LoginPageTest {
    protected static WebDriver driver;

    @BeforeAll
    public static void setup() {
        driver= DriverSingleton.getDriver();
    }
    @BeforeEach
    public void before(){
        loginPage.openPage();
    }

    @AfterAll()
    public static void stopBrowser() {
        DriverSingleton.closeDriver();
    }

    LoginPage loginPage = new LoginPage(driver);

    @Test
    public void shouldProhibitAccessWithoutUsername() {
        loginPage.enterUserName("");
        loginPage.enterPassword("abc");
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Username is required",loginPage.getErrorText());
    }

    @Test
    public void shouldProhibitAccessWithoutPassword(){
        loginPage.enterUserName("abc");
        loginPage.enterPassword("abc");
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Password is required",loginPage.getErrorText());
    }

    @Test
    public void shouldProhibitAccessWithoutCredentials(){
        loginPage.enterUserName("abc");
        loginPage.enterPassword("abc");
        loginPage.clearUsername();
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        Assertions.assertEquals("Epic sadface: Username is required",loginPage.getErrorText());
    }

    @Test
    public void shouldLoginWithCorrectCredentials(){
        User user = UserCreator.withCredentialsFromProperty();
        loginPage.enterUserName(user.getUsername());
        loginPage.enterPassword(user.getPassword());
       InventoryPage inventoryPage= loginPage.acceptLoginButton();
        Assertions.assertEquals("Swag Labs",inventoryPage.getTitle());
        Assertions.assertEquals("Products",inventoryPage.getHeaderTitleElementValue());
    }


}
