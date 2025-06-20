package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

import static com.stepDefinitions.LoginStepdefinition.driver;
import static com.stepDefinitions.LoginStepdefinition.scenario;

/**
 * Utility class for capturing screenshots in case of test failures.
 */
public class ScreenshotUtil {

    /**
     * Captures a screenshot if the scenario has failed.
     *
     * @return The relative path to the captured screenshot, or an empty string if no screenshot is taken.
     */
    public static String captureScreenshot() {
        // Check if the test scenario has failed
        if (scenario.isFailed()) {
            try {
                // Define the directory where screenshots will be saved
                File screenshotDir = new File("reports/screenshots");

                // Create the directory if it does not exist
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdirs();
                }

                // Capture the screenshot and store it in a temporary file
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Generate a filename based on the scenario name (replacing spaces with underscores)
                String screenshotFileName = scenario.getName().replaceAll("\\s+", "_") + ".png";

                // Define the full path where the screenshot will be saved
                String screenshotFullPath = "reports/screenshots/" + screenshotFileName;
                File screenshotFile = new File(screenshotFullPath);

                // Copy the screenshot from the temporary location to the reports directory
                FileUtils.copyFile(src, screenshotFile);

                // Return the relative path of the saved screenshot
                return "screenshots/" + screenshotFileName;
            } catch (Exception e) {
                // Print stack trace in case of an exception
                e.printStackTrace();
            }
        }
        // Return an empty string if no screenshot was taken
        return "";
    }
}
