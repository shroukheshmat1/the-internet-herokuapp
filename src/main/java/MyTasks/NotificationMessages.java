package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
 * Test notification messages that appear after clicking a link
 * Click the link multiple times and verify different notification messages appear
 */

public class NotificationMessages {

    public static void verifyNotification(WebDriver driver, WebDriverWait wait, String expectedMessage) {
        // Click the link to trigger a notification
        WebElement link = driver.findElement(By.linkText("Click here"));
        link.click();

        // Wait for the notification message to appear
        WebElement notification = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        // Get the notification text
        String notificationText = notification.getText();

        // Verify the notification contains the expected message
        if (notificationText.contains(expectedMessage)) {
            System.out.println("TEST PASSED: Notification contains '" + expectedMessage + "'");
        } else {
            System.out.println("TEST FAILED: Expected notification to contain '" + expectedMessage + 
                    "', but got: " + notificationText);
        }
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/notification_message_rendered");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click the link multiple times to see different notification messages
        // The page shows different messages: "Action successful", "Action unsuccesful, please try again", etc.
        for (int i = 0; i < 5; i++) {
            WebElement link = driver.findElement(By.linkText("Click here"));
            link.click();

            // Wait for notification to appear
            WebElement notification = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
            );

            String notificationText = notification.getText();
            System.out.println("Notification " + (i + 1) + ": " + notificationText);

            // Verify notification is displayed
            assert notification.isDisplayed() : "Notification is not displayed!";
        }

        System.out.println("\nNotification messages test completed successfully!");

        driver.quit();
    }
}

