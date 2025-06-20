package commands;

import com.pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class commonCommands extends BasePage {

    public WebDriverWait wait;

    public commonCommands(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    public WebElement findElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void click(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        element = wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public static void type(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        element = wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text + Keys.ENTER);
    }

    public String getText(WebElement element) {
        return findElement(element).getText();
    }

    public boolean isElementPresent(WebElement element, int timeout) {
        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            localWait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void jClick(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        element = wait.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
}