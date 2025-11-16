package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * TEST GOAL:
 * Verify that the “Floating Menu” (a menu bar that is supposed to stick at the top)
 * stays visible in all situations:
 *   1. DURING scrolling down
 *   2. After reaching the bottom of the page
 *   3. DURING scrolling up
 *   4. After reaching the top of the page
 *
 * WHY use JavaScript here?
 * Selenium does not have built-in methods to scroll the page in pixels.
 * JSExecutor allows us to run JavaScript directly in the browser.
 * We can use it to:
 *   - Scroll the page by pixels or to specific positions
 *   - Get the total height of the page (so we know how far to scroll)
 *   - Get the viewport height (height of visible part of the browser window)
 *
 * EXPLANATION OF SCROLL CONCEPTS:
 * - pageHeight: total height of the web page, including the part that is currently off-screen
 *   (we can’t see it until we scroll)
 * - viewportHeight: the height of the visible part of the page (what the user sees without scrolling)
 * - step: how many pixels to move in each scroll iteration. Small enough to “catch” issues during scroll,
 *   but large enough to avoid unnecessarily long test execution.
 *
 * STRATEGY:
 * - Scroll down in increments and check menu visibility at each step
 * - Scroll to bottom and check menu visibility
 * - Scroll up in increments and check menu visibility at each step
 * - Scroll to top and check menu visibility
 *
 * This simulates the user scrolling while ensuring the floating menu never disappears.
 */

public class FloatingMenu {
    public static void main(String[] args) {

        // 1. Launch Chrome browser
        ChromeDriver driver = new ChromeDriver();

        // 2. Open the page with floating menu
        driver.get("https://the-internet.herokuapp.com/floating_menu");

        // 3. Locate the floating menu element by its ID
        WebElement floatingMenu = driver.findElement(By.id("menu"));

        // 4. JSExecutor lets us run JavaScript in the browser
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 5. Get the total page height (scrollable content)
        Long pageHeight = (Long) js.executeScript(
                "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);"
        );

        // 6. Get the height of the visible browser area (viewport)
        Long viewportHeight = (Long) js.executeScript("return window.innerHeight;");

        // 7. Set the scroll step: 25% of viewport height
        //    ensures checks happen frequently without taking too long
        int step = (int)(viewportHeight * 0.25);

        // ================= SCROLL DOWN =================
        // Scroll down the page in increments
        // After each small scroll, verify that the floating menu is still displayed
        for (int i = 0; i < pageHeight; i += step) {
            js.executeScript("window.scrollTo(0, arguments[0]);", i);
            assert floatingMenu.isDisplayed() : "Floating menu disappeared during scroll down at position: " + i;
        }

        // Scroll to bottom and verify
        js.executeScript("window.scrollTo(0, arguments[0]);", pageHeight);
        assert floatingMenu.isDisplayed() : "Floating menu disappeared at the bottom!";

        // ================= SCROLL UP =================
        // Scroll up the page in increments
        // After each small scroll, verify that the floating menu is still displayed
        for (int i = pageHeight.intValue(); i >= 0; i -= step) {
            js.executeScript("window.scrollTo(0, arguments[0]);", i);
            assert floatingMenu.isDisplayed() : "Floating menu disappeared during scroll up at position: " + i;
        }

        // Scroll to top and verify
        js.executeScript("window.scrollTo(0, 0);");
        assert floatingMenu.isDisplayed() : "Floating menu disappeared at the top!";

        // ================= CLEANUP =================
        // Close the browser
        driver.quit();
    }
}
