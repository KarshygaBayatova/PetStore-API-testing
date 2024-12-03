package com.petstore.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:target/cucumber-reports.html")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:target/cucumber.json")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "me.jvt.cucumber.report.PrettyReports:target/cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "rerun:target/rerun.txt")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.petstore.step_definitions")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "")
@ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
public class CukesRunner {

    static {
        String dryRun = System.getProperty("cucumber.options.dryRun", "false");
        if (Boolean.parseBoolean(dryRun)) {
            System.setProperty("cucumber.options", "--dry-run");
        }
    }
}