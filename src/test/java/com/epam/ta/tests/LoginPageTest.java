package com.epam.ta.tests;

import com.epam.ta.services.UserCreator;
import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.models.User;
import com.epam.ta.pages.InventoryPage;
import com.epam.ta.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


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
    @DisplayName("US-5-additional: clear username")
    public void shouldProhibitAccessWithoutUsername() {
        loginPage.clearUsername();
        loginPage.acceptLoginButton();
        assertThat(loginPage.getErrorText(), containsString("Username is required"));
    }

    @Test
    @DisplayName("UC-2: clear password")
    public void shouldProhibitAccessWithoutPassword() {
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        assertThat(loginPage.getErrorText(), containsString("Password is required"));
    }

    @Test
    @DisplayName("UC-1: clear inputs")
    public void shouldProhibitAccessWithoutCredentials() {
        loginPage.clearUsername();
        loginPage.clearPassword();
        loginPage.acceptLoginButton();
        assertThat(loginPage.getErrorText(), containsString("Username is required"));
    }

    @Test
    @DisplayName("US-4-additional: verify success login")
    public void shouldLoginWithCorrectCredentials() {
        InventoryPage inventoryPage = loginPage.acceptLoginButton();
        assertThat(inventoryPage.getTitle(), is(equalTo("Swag Labs")));
        assertThat(inventoryPage.getHeaderTitleElementValue(), is(equalTo("Products")));
    }

    @ParameterizedTest
    @MethodSource("getUsernames")
    @DisplayName("UC-3: login form with credentials by passing Username & Password")
    public void shouldTestCredentials(String username) {
        loginPage.clearUsername();
        loginPage.enterUsername(username);
        loginPage.acceptLoginButton();

        String title = driver.getTitle();

        assertThat("Title should be 'Swag Labs'", title, is("Swag Labs"));
    }

    private static Stream<String> getUsernames() {
        return Stream.of("standard_user",
                "locked_out_user",
                "problem_user",
                "performance_glitch_user",
                "error_user",
                "visual_user"
        );
    }

}
