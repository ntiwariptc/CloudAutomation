package runner;

import com.stepDefinitions.LoginStepdefinition;
import io.cucumber.java.Scenario;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static utilities.ScreenshotUtil.captureScreenshot;

public class CustomReport {

    // List to store test results in HTML table row format
    private static final List<String> reportRows = new ArrayList<>();

    // Variable to track the start time of a test
    private static Instant startTime;

    /**
     * -- SETTER --
     *  Sets the path for storing screenshots.
     *
     * @param path  The path where the screenshot is stored.
     */
    // Path for storing the screenshot of failed tests
    @Setter
    private static String screenshotPath = null;

    // Logger instance for logging information
    private static final Logger logger = LoggerFactory.getLogger(CustomReport.class);

    /**
     * Initializes the start time of the test execution.
     */
    public static void startTest() {
        startTime = Instant.now();
    }

    /**
     * Adds test results to the report.
     *
     * @param testName  The name of the test case.
     * @param isPassed  Boolean indicating if the test passed or failed.
     * @param scenario  The scenario object from Cucumber.
     */
    public static void addTestResult(String testName, boolean isPassed, Scenario scenario) {
        // Calculate the elapsed time
        Instant endTime = Instant.now();
        long timeElapsed = Duration.between(startTime, endTime).toSeconds();
        double timeInMinutes = timeElapsed / 60.0;

        // Get failure remarks if test fails
        String remarks = isPassed ? "Successful Execution" : LoginStepdefinition.getFailureMessage();
        if (!isPassed && (remarks.equals("No failure recorded") || remarks.isEmpty())) {
            remarks = "Failure reason not found in logs.";
        }

        // Capture screenshot if the test failed
        String screenshotHTML = "";
        if (!isPassed) {
            String screenshotPath = captureScreenshot();
            if (!screenshotPath.isEmpty()) {
                logger.info("Screenshot captured at: " + screenshotPath);
                screenshotHTML = "<a href='" + screenshotPath + "' target='_blank'>View Screenshot</a>";
            } else {
                logger.info("Screenshot path is empty!");
            }
        }

        // Add test result as an HTML table row
        reportRows.add("<tr style='background-color: " + (isPassed ? "#CCFFCC" : "#FFCCCC") + ";'>"
                + "<td>" + (reportRows.size() + 1) + "</td>"
                + "<td>" + testName + "</td>"
                + "<td>" + String.format("%.2f", timeInMinutes) + "</td>"
                + "<td>" + (isPassed ? "<b style='color:green;'>Passed</b>" : "<b style='color:red;'>Failed</b>") + "</td>"
                + "<td>" + remarks + "</td>"
                + "<td>" + screenshotHTML + "</td></tr>");

        screenshotPath = null;
    }

    /**
     * Generates the final test execution report as an HTML file.
     */
    public static void generateReport() {
        try (FileWriter writer = new FileWriter(getReportFilePath())) {
            writer.write("<html><head><title>Custom Test Report</title>");
            writer.write("<style>table {width: 100%; border-collapse: collapse;} "
                    + "th, td {border: 2px solid black; padding: 10px; text-align: center;} "
                    + "th {background-color: #f2f2f2;}</style>");
            writer.write("</head><body>");
            writer.write("<h2>BDD Framework Execution Report</h2>");
            writer.write("<table><tr><th>Sl.No</th><th>Test Name</th><th>Time (Mins)</th><th>Result</th><th>Remarks</th><th>Screenshot</th></tr>");

            // Add all test results
            for (String row : reportRows) {
                writer.write(row);
            }

            writer.write("</table></body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a unique file path for the test execution report.
     *
     * @return  The generated report file path.
     */
    public static String getReportFilePath() {
        String reportDir = "reports/";
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return reportDir + "Execution_Report_" + timestamp + ".html";
    }
}
