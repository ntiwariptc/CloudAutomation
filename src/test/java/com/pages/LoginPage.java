package com.pages;


import lombok.extern.apachecommons.CommonsLog;
import org.openqa.selenium.WebDriver;
import config.Configuration;
import com.pageObjects.ObjectRepository;
import static com.stepDefinitions.LoginStepdefinition.logger;
import static commands.commonCommands.*;


 /**
  * LoginPage class handles login-related actions for the web application.
  * It extends BasePage and interacts with the LoginPageLocators.
  */

@CommonsLog
public class LoginPage extends BasePage {
    ObjectRepository locators;

    /**
     * Constructor to initialize the LoginPage class.
     *
     * @param driver WebDriver instance used for executing browser actions.
     */

    public LoginPage(WebDriver driver) {
        super(driver);
        this.locators = new ObjectRepository(driver);
    }

    //Navigates to the login page.
    public void goToLoginPage() {
        navigateTo("");
        logger.info("URL Navigated:"+Configuration.BASE_URL);
    }

    //Enters the username into the login form.
    public void enterUsername( )  {
        type(locators.usernameField, Configuration.USERNAME);
        logger.info("Username entered");
    }


    //Enters the password into the login form.
    public void enterPassword( ) {
        type(locators.passwordField,Configuration.PASSWORD);
        logger.info("Password entered");
    }

    //Clicks the login button.
    public void clickLogin() {
        click(locators.loginButton);
        logger.info("Clicked Login button");
    }


    /**
     * Performs a complete login action with the provided credentials.
     *
     * @param username The username to log in.
     * @param password The password to log in.
     * @throws Exception If any error occurs during login.
     */
    public void login(String username, String password) throws Exception {
        goToLoginPage();
        enterUsername( );
        enterPassword( );
        clickLogin();
    }

    //Clicks on the "Network" tab in the UI.
    public void clickNetworkTab() {
        click(locators.infrastructureNetwork);
        logger.info("Clicked Network button");
    }

    //Clicks on the "Cluster Network" tab.
    public void clickClusterNetworkTab() {
        click(locators.infrastructureClusterNetwork);
        logger.info("Clicked Cluster Network button");
    }

    //Clicks the "Create Cluster Network" button.
    public void clickClusterNetworkCreateButton()  {
        jClick(locators.clusterNetworkCreateButton);
        logger.info("Clicked Cluster Network Create button");
    }

    //Clicks the API Documentation link.
    public void clickAPIDocsLink()  {
        click(locators.apiDocsLink);
        logger.info("Clicked API Docs Link");
    }
}