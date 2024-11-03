package com.epam.ta.tests;

import com.epam.ta.services.UserCreator;
import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.models.User;
import com.epam.ta.pages.InventoryPage;
import com.epam.ta.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Execution(ExecutionMode.CONCURRENT)
public class LoginPageTest {
    private static WebDriver driver;
    private LoginPage loginPage = new LoginPage(driver);
    private static User user;

    @BeforeAll
    public static void setup() {
        driver = DriverSingleton.getDriver();
        user = UserCreator.withCredentialsFromProperty();
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
        assertThat(loginPage.getErrorText(), containsString("Username is required"));
    }

    @Test
    public void shouldProhibitAccessWithoutPassword() {
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        assertThat(loginPage.getErrorText(), containsString("Password is required"));
    }

    @Test
    public void shouldProhibitAccessWithoutCredentials() {
        loginPage.clearUsername();
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        assertThat(loginPage.getErrorText(), containsString("Username is required"));
    }

    @Test
    public void shouldLoginWithCorrectCredentials() {
        InventoryPage inventoryPage = loginPage.acceptLoginButton();
        assertThat(inventoryPage.getTitle(), is(equalTo("Swag Labs")));
        assertThat(inventoryPage.getHeaderTitleElementValue(), is(equalTo("Products")));
    }
}
