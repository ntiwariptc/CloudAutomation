package com.stepDefinitions;

import config.Configuration;
import com.pages.LoginPage;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class LoginStepdefinition {

    // WebDriver instance to control the browser
    public static WebDriver driver;

    // LoginPage object to interact with login-related web elements
    private static LoginPage loginPage;

    // Logger for logging messages
    public static final Logger logger = LoggerFactory.getLogger(LoginStepdefinition.class);

    // Stores failure message in case of errors
    private static String failureMessage = "";

    // Scenario object to attach logs for reporting
    public static Scenario scenario;

    /**
     * Setup method that runs before each test scenario.
     * It initializes the WebDriver based on the specified browser.
     *
     * @param scenario The Cucumber scenario being executed.
     */
    @Before
    public void setup(Scenario scenario) throws Exception {
        this.scenario = scenario;

        // Setting up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().clearResolutionCache().setup();

        String browser = Configuration.BROWSER; // Fetch browser from configuration

        // Browser selection based on configuration
        switch (browser.toLowerCase()) {
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize(); // Maximizing the browser window
        loginPage = new LoginPage(driver); // Initializing the login page object

        // Configuring Log4j for logging
        System.setProperty("log4j.configurationFile", "src/test/resources/log4j.xml");
    }

    /**
     * Teardown method that runs after each test scenario.
     * It closes the browser session.
     */
    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Navigates to the IBM Cloud Console login page.
     */
    @Given("User is on the IBM Cloud Console login page")
    public static void iAmOnTheIBMCloudConsoleLoginPage() throws Exception {
        try {
            loginPage.goToLoginPage();
            logger.info("Navigating to IBM Cloud Console login page");
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Retrieves failure message for reporting.
     *
     * @return Failure message string
     */
    public static String getFailureMessage() {
        return (failureMessage == null || failureMessage.isEmpty()) ? "No failure recorded" : failureMessage;
    }

    /**
     * Enters a valid username in the login form.
     */
    @When("User enter a valid username")
    public static void iEnterAValidUsername() throws Exception {
        try {
            loginPage.enterUsername();
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Enters a valid password and clicks the login button.
     */
    @And("User enter a valid password and click on login")
    public static void iEnterAValidPassword() throws Exception {
        try {
            loginPage.enterPassword();
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Clicks the login button.
     */
    @And("User click the login button")
    public static void iClickTheLoginButton() throws Exception {
        try {
            loginPage.clickLogin();
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Verifies that the user has successfully logged in and the dashboard is visible.
     */
    @And("User should be logged in and see the dashboard")
    public static void iShouldBeLoggedInAndSeeTheDashboard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//header[@class='genesis--OverviewPage-header genesis--OverviewPage-isLightTheme cpc--world-level-page-header']")
        ));
        Assertions.assertTrue(element.isDisplayed(), "Dashboard element not found. Login may have failed.");
    }

    /**
     * Clicks the "Network" tab in the application.
     */
    @And("User click on Network Tab")
    public static void clickInfrastructure() {
        loginPage.clickNetworkTab();
    }

    /**
     * Clicks the "Cluster Network" tab.
     */
    @And("User click on Cluster Network Tab")
    public static void clickClusterNetwork() {
        loginPage.clickClusterNetworkTab();
    }

    /**
     * Clicks the "Create Cluster Network" button.
     */
    @And("User click on Cluster Network Create Button")
    public static void clickClusterNetworkCreateButton() {
        loginPage.clickClusterNetworkCreateButton();
    }

    /**
     * Clicks the API Documentation link.
     */
    @And("User click on API Docs Link")
    public static void clickAPIDocsLink() {
        loginPage.clickAPIDocsLink();
    }

    /**
     * Handles exceptions by logging and attaching failure details to the scenario report.
     *
     * @param e Exception thrown during execution
     */
    private static void handleException(Exception e) throws Exception {
        String failureReason = "Failure Reason: " + e.getMessage();
        failureMessage = failureReason;
        logger.info(failureReason);
        scenario.attach(failureReason, "text/plain", "Failure Details");
        throw e;
    }
}
