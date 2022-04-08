package io.weavr.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/cucumber.json",
                "html:target/default-html-reports",
                "rerun:target/rerun.txt"},
        features = "src/test/resources/featureFiles",
        glue = "io/weavr/stepDefs",
        dryRun = false,
        tags = "@smoke"

)

public class CucumberRunner {

}
