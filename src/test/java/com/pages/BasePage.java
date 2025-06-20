package com.pages;

import config.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {


    /**
     * BasePage class serves as a parent class for all page objects.
     * It initializes the WebDriver instance and provides common functionalities for navigation.
     */

    // Static WebDriver instance shared across all child pages
    protected static WebDriver driver;
    // WebDriverWait instance for handling explicit waits
    public WebDriverWait wait;
    // Base URL for the application, fetched from the Configuration clas
    public String baseUrl;

    /**
     * Constructor to initialize the WebDriver instance and other utilities.
     *
     * @param driver WebDriver instance for interacting with the web browser
     */
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.baseUrl = Configuration.BASE_URL;
    }

    /**
     * Navigates to a specified path by appending it to the base URL.
     *
     * @param path The relative path of the page to navigate to
     */
    public void navigateTo(String path)  {
        driver.get(baseUrl + path);
    }
}