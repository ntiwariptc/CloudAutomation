package config;


/**
 * BASE_URL	Defines the test environment URL.
 * BROWSER	Reads the browser type from system properties, defaults to Chrome.
 * USERNAME	Hardcoded test account email.
 * PASSWORD	Hardcoded test account password
 */
public class Configuration {

    public static final String BASE_URL = "https://ondeck.console.test.cloud.ibm.com/infrastructure";
    public static final String BROWSER = System.getProperty("browser", "chrome"); // Default to chrome
    public static final String USERNAME= " ";
    public static final String PASSWORD =" ";
}