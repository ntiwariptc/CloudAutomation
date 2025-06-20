package runner;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class CustomReportHooks {

    private WebDriver driver;  // WebDriver instance
    private Scenario scenario; // Scenario instance to track test execution

    /**
     * This method is executed before each scenario starts.
     * It initializes the scenario instance and starts a custom report for the test.
     *
     * @param scenario The Cucumber scenario that is about to be executed
     */
    @Before
    public void beforeScenario(Scenario scenario) {
        this.scenario = scenario;
        CustomReport.startTest(); // Starts logging/reporting for the test
    }

    /**
     * This method is executed after each scenario finishes.
     * It checks if the scenario passed or failed and logs the result in the custom report.
     *
     * @param scenario The Cucumber scenario that has just finished execution
     */
    @After
    public void afterScenario(Scenario scenario) {
        boolean isPassed = !scenario.isFailed(); // Checks if the scenario passed
        CustomReport.addTestResult(scenario.getName(), isPassed, scenario); // Logs the test result
    }

    /**
     * This method is executed once after all scenarios have finished execution.
     * It triggers the final generation of the custom test report.
     */
    @AfterAll
    public static void generateCustomReport() {
        CustomReport.generateReport(); // Generates the final test report after all scenarios
    }
}
