package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

/*
 * ============================ 🪟 MULTIPLE WINDOWS AUTOMATION =============================
 *
 * TEST GOAL:
 * Learn how to handle multiple browser windows/tabs in Selenium!
 * When a link opens a new window, we need to "switch" to it to interact with it.
 *
 * WHY THIS IS IMPORTANT:
 * --------------------------------------------------------------------------------------------
 * In real web applications, clicking a link might open a new window or tab.
 * By default, Selenium stays focused on the original window. To interact with
 * the new window, we must explicitly tell Selenium to "switch" to it!
 *
 * KEY CONCEPTS:
 * --------------------------------------------------------------------------------------------
 * 1️⃣ Window Handle:
 *    - Each browser window has a unique ID called a "handle"
 *    - Think of it like a window's "name tag" - it helps Selenium identify which window is which
 *    - We use getWindowHandle() to get the current window's handle
 *    - We use getWindowHandles() to get ALL open windows' handles
 *
 * 2️⃣ Switching Windows:
 *    - driver.switchTo().window(handle) - Switches to a specific window
 *    - driver.close() - Closes the CURRENT window (not all windows!)
 *    - driver.quit() - Closes ALL windows and ends the session
 *
 * 3️⃣ Finding the New Window:
 *    - After clicking a link that opens a new window, we have 2 windows
 *    - We compare all window handles to find the one that's NOT the original
 *    - That's our new window!
 *
 * WHAT WE'LL DO:
 * --------------------------------------------------------------------------------------------
 * Step 1: Open the page and get the original window's handle
 * Step 2: Click a link that opens a new window
 * Step 3: Get all window handles (should be 2 now!)
 * Step 4: Find which handle belongs to the new window
 * Step 5: Switch to the new window and verify its content
 * Step 6: Close the new window
 * Step 7: Switch back to the original window and verify we're back
 *
 * FUN FACT:
 * This is like having two TV remotes - you need to tell Selenium which "TV" (window)
 * you want to control! 🎮
 *
 * ============================================================================================
 */

public class MultipleWindows {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/windows");

        // STEP 1: Get the current window handle (the original window)
        // This is like taking a photo of the window's ID card - we'll need it later!
        String originalWindow = driver.getWindowHandle();
        System.out.println("Original window handle: " + originalWindow);

        // STEP 2: Click the link that opens a new window
        // This will open a new browser window, but Selenium is still focused on the original one
        WebElement clickHereLink = driver.findElement(By.linkText("Click Here"));
        clickHereLink.click();

        // STEP 3: Get all window handles (should be 2 now: original + new window)
        // This gives us a Set (collection) of all open window handles
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("Total windows open: " + allWindows.size());

        // STEP 4: Find the new window handle (the one that's not the original)
        // We loop through all windows and find the one that's different from our original
        String newWindow = null;
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                newWindow = window;  // Found it! This is our new window
                break;
            }
        }

        // STEP 5: Switch to the new window
        // Now Selenium will interact with the new window instead of the original
        driver.switchTo().window(newWindow);
        System.out.println("Switched to new window: " + newWindow);

        // STEP 6: Verify the content in the new window
        // Now that we're in the new window, we can find elements in it
        WebElement newWindowText = driver.findElement(By.tagName("h3"));
        String expectedText = "New Window";
        assert newWindowText.getText().equals(expectedText) : 
            "Expected: '" + expectedText + "', but got: '" + newWindowText.getText() + "'";
        System.out.println("New window content verified: " + newWindowText.getText());

        // STEP 7: Close the new window
        // This closes only the CURRENT window (the new one we're in)
        driver.close();
        System.out.println("Closed the new window");

        // STEP 8: Switch back to the original window
        // Important! After closing a window, we must switch back to continue working
        driver.switchTo().window(originalWindow);
        System.out.println("Switched back to original window");

        // STEP 9: Verify we're back in the original window
        // Let's check that we're really back where we started
        WebElement originalPageText = driver.findElement(By.tagName("h3"));
        assert originalPageText.getText().equals("Opening a new window") : 
            "Not back in the original window!";
        System.out.println("Back in original window: " + originalPageText.getText());

        System.out.println("\nMultiple windows test completed successfully!");

        driver.quit();
    }
}

