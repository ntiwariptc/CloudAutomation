package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;


 /**
  * This class is responsible for running Cucumber test cases.
  * It integrates Cucumber with JUnit.
  */

@CucumberContextConfiguration // Enables Cucumber to use Spring context if needed
@RunWith(Cucumber.class) // Specifies that JUnit should run this class using the Cucumber test runner
@CucumberOptions(
        features = "src/test/resources/feature/Login.feature", // Path to the feature files
        glue = {"com.stepDefinitions", "com.pages", "runner"}, // Packages containing step definitions and hooks
        monochrome = true ,// Makes console output more readable
        tags = "@TC01 or @TC02" // Uncomment to run tests tagged with @TC01 or @TC02
        // tags="@TC01" // Uncomment to run only @TC01 test cases
)
/**
 * This is the test runner class that triggers execution of Cucumber test cases.
 */
public class CucumberTestRunner  {

}
