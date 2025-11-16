package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
 * Test pages with slow-loading resources
 * Verify that the page loads correctly even when some resources take time to load
 */

public class SlowResources {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/slow");

        // Create a wait with longer timeout for slow resources
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for the page to fully load (wait for a specific element to appear)
        WebElement heading = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.tagName("h3"))
        );

        System.out.println("Page loaded! Heading: " + heading.getText());

        // Verify the page content is displayed
        assert heading.isDisplayed() : "Page heading is not displayed!";
        assert heading.getText().contains("Slow") : 
            "Expected heading to contain 'Slow', but got: " + heading.getText();

        // Find and verify other elements on the page
        WebElement paragraph = driver.findElement(By.tagName("p"));
        System.out.println("Page content: " + paragraph.getText());

        // Verify the page is fully loaded
        String pageSource = driver.getPageSource();
        assert pageSource.length() > 0 : "Page source is empty!";

        System.out.println("\nSlow resources test completed successfully!");
        System.out.println("Page loaded correctly despite slow resources");

        driver.quit();
    }
}

