package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
 * Test HTTP redirects
 * Click a link that redirects to another page and verify the redirect happened
 */

public class RedirectLink {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/redirector");

        // Get the initial URL
        String initialUrl = driver.getCurrentUrl();
        System.out.println("Initial URL: " + initialUrl);

        // Find and click the redirect link
        WebElement redirectLink = driver.findElement(By.id("redirect"));
        redirectLink.click();

        // Wait for the URL to change (redirect to happen)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("status_codes"));

        // Get the new URL after redirect
        String redirectedUrl = driver.getCurrentUrl();
        System.out.println("Redirected URL: " + redirectedUrl);

        // Verify the redirect happened (URL should contain "status_codes")
        assert redirectedUrl.contains("status_codes") : 
            "Redirect failed! URL should contain 'status_codes' but got: " + redirectedUrl;

        // Verify we're on the status codes page
        WebElement statusCodesHeading = driver.findElement(By.tagName("h3"));
        assert statusCodesHeading.getText().equals("Status Codes") : 
            "Not on the status codes page!";
        System.out.println("Redirect successful! Now on: " + statusCodesHeading.getText() + " page");

        System.out.println("\nRedirect link test completed successfully!");

        driver.quit();
    }
}

