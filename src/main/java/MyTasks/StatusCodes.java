package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/*
 * Test HTTP status codes
 * Click links that return different HTTP status codes and verify the response
 */

public class StatusCodes {

    public static void testStatusCode(WebDriver driver, WebDriverWait wait, String linkText, int expectedStatusCode) {
        // Navigate to status codes page
        driver.get("https://the-internet.herokuapp.com/status_codes");

        // Find and click the status code link
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();

        // Wait for the page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Get the current URL to check if it contains the status code
        String currentUrl = driver.getCurrentUrl();
        System.out.println("After clicking '" + linkText + "', URL: " + currentUrl);

        // Verify the URL contains the status code
        assert currentUrl.contains(String.valueOf(expectedStatusCode)) : 
            "URL should contain status code " + expectedStatusCode + " but got: " + currentUrl;

        // Verify the page content mentions the status code
        String pageText = driver.findElement(By.tagName("body")).getText();
        assert pageText.contains(String.valueOf(expectedStatusCode)) : 
            "Page should contain status code " + expectedStatusCode;

        System.out.println("Status code " + expectedStatusCode + " verified successfully!");
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Test different status codes
        testStatusCode(driver, wait, "200", 200);
        testStatusCode(driver, wait, "301", 301);
        testStatusCode(driver, wait, "404", 404);
        testStatusCode(driver, wait, "500", 500);

        // Test finding all status code links
        driver.get("https://the-internet.herokuapp.com/status_codes");
        List<WebElement> statusCodeLinks = driver.findElements(By.cssSelector("ul li a"));
        System.out.println("\nTotal status code links found: " + statusCodeLinks.size());

        for (WebElement link : statusCodeLinks) {
            System.out.println("Status code link: " + link.getText());
        }

        System.out.println("\nStatus codes test completed successfully!");

        driver.quit();
    }
}

