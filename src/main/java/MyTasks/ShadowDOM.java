package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Test Shadow DOM elements
 * Shadow DOM elements are not directly accessible with normal Selenium locators
 * We need to use JavaScript to access elements inside Shadow DOM
 */

public class ShadowDOM {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/shadowdom");

        // Shadow DOM elements are not accessible with normal findElement
        // We need to use JavaScript to access them
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Find the shadow host element (the element that contains the shadow DOM)
        WebElement shadowHost = driver.findElement(By.tagName("my-paragraph"));

        // Use JavaScript to access the shadow root and then find elements inside it
        // This JavaScript code:
        // 1. Gets the shadow root of the element
        // 2. Finds the element with id "text" inside the shadow DOM
        // 3. Returns its text content
        String shadowText = (String) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('#text').textContent;",
            shadowHost
        );

        System.out.println("Shadow DOM text: " + shadowText);

        // Verify the text content
        String expectedText = "My default text";
        assert shadowText.equals(expectedText) : 
            "Expected: '" + expectedText + "', but got: '" + shadowText + "'";
        System.out.println("Shadow DOM text verified successfully!");

        // Try to find the element with normal Selenium (this will fail)
        try {
            WebElement normalElement = driver.findElement(By.id("text"));
            System.out.println("WARNING: Found element with normal locator (unexpected!)");
        } catch (Exception e) {
            System.out.println("As expected, normal locator cannot find Shadow DOM element");
        }

        System.out.println("\nShadow DOM test completed successfully!");

        driver.quit();
    }
}

