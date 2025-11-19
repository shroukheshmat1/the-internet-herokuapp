package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * ============================ 👻 SHADOW DOM AUTOMATION =============================
 *
 * TEST GOAL:
 * Learn how to interact with Shadow DOM elements using JavaScript!
 * Shadow DOM is like a hidden world inside regular HTML - normal Selenium can't see it!
 *
 * WHY THIS IS SPECIAL:
 * --------------------------------------------------------------------------------------------
 * Shadow DOM is a web standard that allows developers to create "encapsulated" HTML.
 * Elements inside Shadow DOM are hidden from normal DOM queries - they're like
 * elements in a secret room that only JavaScript can access!
 *
 * KEY CONCEPTS:
 * --------------------------------------------------------------------------------------------
 * 1️⃣ What is Shadow DOM?
 *    - Shadow DOM creates a separate DOM tree inside an element
 *    - It's "encapsulated" - styles and scripts from outside can't affect it
 *    - It's like a private room inside a house - you need special access!
 *
 * 2️⃣ Why Can't Selenium Find It?
 *    - Normal Selenium locators (By.id, By.cssSelector, etc.) only search the "light DOM"
 *    - Shadow DOM elements are in a separate tree that Selenium can't see
 *    - It's like trying to find someone in a hidden basement - you need special directions!
 *
 * 3️⃣ The Solution: JavaScript!
 *    - JavaScript can access Shadow DOM through the shadowRoot property
 *    - We use JavascriptExecutor to run JavaScript code in the browser
 *    - This lets us "peek inside" the Shadow DOM and find elements
 *
 * 4️⃣ How It Works:
 *    - element.shadowRoot - Gets the shadow root (the entry point to Shadow DOM)
 *    - shadowRoot.querySelector() - Finds elements inside the Shadow DOM
 *    - We chain these together to access nested Shadow DOM elements
 *
 * WHAT WE'LL DO:
 * --------------------------------------------------------------------------------------------
 * Step 1: Find the "shadow host" element (the element that contains the Shadow DOM)
 * Step 2: Use JavaScript to access the shadow root
 * Step 3: Use JavaScript to find elements inside the Shadow DOM
 * Step 4: Get the text content from the Shadow DOM element
 * Step 5: Verify we can't find it with normal Selenium (demonstration)
 *
 * FUN FACT:
 * Shadow DOM is like a secret compartment in a drawer - you know it's there,
 * but you need the right key (JavaScript) to open it! 🔑
 *
 * ============================================================================================
 */

public class ShadowDOM {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/shadowdom");

        // STEP 1: Shadow DOM elements are not accessible with normal findElement
        // We need to use JavaScript to access them - this is our "special key"!
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // STEP 2: Find the shadow host element (the element that contains the shadow DOM)
        // This is like finding the drawer that has the secret compartment
        WebElement shadowHost = driver.findElement(By.tagName("my-paragraph"));

        // STEP 3: Use JavaScript to access the shadow root and then find elements inside it
        // This JavaScript code does three things:
        //   1. Gets the shadow root of the element (opens the secret compartment)
        //   2. Finds the element with id "text" inside the shadow DOM (finds what we want)
        //   3. Returns its text content (gets the value)
        // 
        // Think of it like: drawer.shadowRoot.querySelector('#text').textContent
        String shadowText = (String) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('#text').textContent;",
            shadowHost
        );

        System.out.println("Shadow DOM text: " + shadowText);

        // STEP 4: Verify the text content
        // Now we can verify what we found inside the Shadow DOM!
        String expectedText = "My default text";
        assert shadowText.equals(expectedText) : 
            "Expected: '" + expectedText + "', but got: '" + shadowText + "'";
        System.out.println("Shadow DOM text verified successfully!");

        // STEP 5: Try to find the element with normal Selenium (this will fail)
        // This demonstrates that normal Selenium locators can't see Shadow DOM elements
        try {
            WebElement normalElement = driver.findElement(By.id("text"));
            System.out.println("WARNING: Found element with normal locator (unexpected!)");
        } catch (Exception e) {
            System.out.println("As expected, normal locator cannot find Shadow DOM element");
            System.out.println("This proves that Shadow DOM is hidden from normal DOM queries!");
        }

        System.out.println("\nShadow DOM test completed successfully!");

        driver.quit();
    }
}

