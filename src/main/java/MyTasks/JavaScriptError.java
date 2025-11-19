/*package MyTasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;

//Test JavaScript error on page load Capture and verify that a JavaScript error occurs when the page loads

public class JavaScriptError {

    public static void main(String[] args) {
        // Enable browser logging to capture JavaScript errors
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);

        ChromeOptions options = new ChromeOptions();
        options.setCapability("goog:loggingPrefs", loggingPreferences);

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/javascript_error");

        // Get browser console logs
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

        // Check if there are any JavaScript errors
        boolean errorFound = false;
        for (LogEntry entry : logEntries) {
            if (entry.getLevel().equals(Level.SEVERE)) {
                System.out.println("JavaScript error found: " + entry.getMessage());
                errorFound = true;
            }
        }

        // Verify that an error was found
        assert errorFound : "No JavaScript error was found in the console logs!";
        System.out.println("Test passed: JavaScript error successfully detected on page load.");

        driver.quit();
    }
}

*/
package MyTasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.List;

/**
 * Scenario: Capture console logs and ensure a JavaScript error is thrown.
 * Steps:
 * 1. Load the JavaScript error page.
 * 2. Read browser logs for the known “Cannot read properties of undefined” message and assert it exists.
 */
public class JavaScriptError {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_error");

        // Get browser console logs
        List<LogEntry> logs = driver.manage().logs().get(LogType.BROWSER).getAll();

        boolean jsErrorFound = false;

        for (LogEntry entry : logs) {
            System.out.println("Log entry: " + entry.getMessage());
            if (entry.getMessage().contains("Cannot read properties of undefined")) {
                jsErrorFound = true;
            }
        }

        if (jsErrorFound) {
            System.out.println("TEST PASSED: JavaScript error detected.");
        } else {
            System.out.println("TEST FAILED: No JavaScript error found.");
        }

        driver.quit();
    }
}
