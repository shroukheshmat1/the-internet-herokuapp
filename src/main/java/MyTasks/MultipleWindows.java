package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

/*
 * Test handling multiple browser windows/tabs
 * Click a link that opens a new window, switch to it, verify content, then switch back
 */

public class MultipleWindows {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/windows");

        // Get the current window handle (the original window)
        String originalWindow = driver.getWindowHandle();
        System.out.println("Original window handle: " + originalWindow);

        // Click the link that opens a new window
        WebElement clickHereLink = driver.findElement(By.linkText("Click Here"));
        clickHereLink.click();

        // Get all window handles (should be 2 now: original + new window)
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("Total windows open: " + allWindows.size());

        // Find the new window handle (the one that's not the original)
        String newWindow = null;
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                newWindow = window;
                break;
            }
        }

        // Switch to the new window
        driver.switchTo().window(newWindow);
        System.out.println("Switched to new window: " + newWindow);

        // Verify the content in the new window
        WebElement newWindowText = driver.findElement(By.tagName("h3"));
        String expectedText = "New Window";
        assert newWindowText.getText().equals(expectedText) : 
            "Expected: '" + expectedText + "', but got: '" + newWindowText.getText() + "'";
        System.out.println("New window content verified: " + newWindowText.getText());

        // Close the new window
        driver.close();
        System.out.println("Closed the new window");

        // Switch back to the original window
        driver.switchTo().window(originalWindow);
        System.out.println("Switched back to original window");

        // Verify we're back in the original window
        WebElement originalPageText = driver.findElement(By.tagName("h3"));
        assert originalPageText.getText().equals("Opening a new window") : 
            "Not back in the original window!";
        System.out.println("Back in original window: " + originalPageText.getText());

        System.out.println("\nMultiple windows test completed successfully!");

        driver.quit();
    }
}

