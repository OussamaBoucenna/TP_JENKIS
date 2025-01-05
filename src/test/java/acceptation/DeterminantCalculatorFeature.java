package acceptation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/acceptation/DeterminantCalculator.feature",

        plugin = {"pretty", "json:build/reports/cucumber/cucumber.json", "html:build/reports/cucumber/cucumber.html"}
)
public class DeterminantCalculatorFeature {
}