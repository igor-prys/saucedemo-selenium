package com.epam.ta.tests;

import com.epam.ta.services.UserCreator;
import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.models.User;
import com.epam.ta.pages.InventoryPage;
import com.epam.ta.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class LoginPageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private User user;
    private boolean isSameThread;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        driver = DriverSingleton.getDriver();
        loginPage = new LoginPage(driver);
        user = UserCreator.withCredentialsFromProperty();
        loginPage.openPage();
        loginPage.enterCredentials(user);

        // just for demo how test methods can be run per method and per class
        isSameThread = testInfo.getTestMethod()
                .map(method -> method.isAnnotationPresent(Execution.class) &&
                        method.getAnnotation(Execution.class).value() == ExecutionMode.SAME_THREAD)
                .orElse(false);
    }

    @AfterEach
    public void afterEach() {
        if (!isSameThread) {
            DriverSingleton.closeDriver();
        }
    }

    @AfterAll
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
    @Execution(ExecutionMode.SAME_THREAD)
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
