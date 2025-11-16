package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Test key presses on the input field
 * Press different keys and verify the result text displays correctly
 */

public class KeyPresses {

    public static void testKeyPress(WebDriver driver, WebElement inputField, Keys key, String expectedResult) {
        // Clear the input field and focus on it
        inputField.clear();
        inputField.click();
        
        // Press the key
        inputField.sendKeys(key);
        
        // Verify the result text
        WebElement result = driver.findElement(By.id("result"));
        String actualResult = result.getText();
        
        if (!actualResult.equals(expectedResult)) {
            System.out.println("TEST FAILED: Expected = '" + expectedResult + 
                    "', but got = '" + actualResult + "'");
        } else {
            System.out.println("TEST PASSED: Key press '" + key.name() + "' - Result: " + actualResult);
        }
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/key_presses");

        WebElement inputField = driver.findElement(By.id("target"));

        // Test various key presses
        testKeyPress(driver, inputField, Keys.ENTER, "You entered: ENTER");
        testKeyPress(driver, inputField, Keys.ESCAPE, "You entered: ESCAPE");
        testKeyPress(driver, inputField, Keys.SPACE, "You entered: SPACE");
        testKeyPress(driver, inputField, Keys.TAB, "You entered: TAB");
        testKeyPress(driver, inputField, Keys.BACK_SPACE, "You entered: BACK_SPACE");
        testKeyPress(driver, inputField, Keys.ARROW_UP, "You entered: UP");
        testKeyPress(driver, inputField, Keys.ARROW_DOWN, "You entered: DOWN");
        testKeyPress(driver, inputField, Keys.ARROW_LEFT, "You entered: LEFT");
        testKeyPress(driver, inputField, Keys.ARROW_RIGHT, "You entered: RIGHT");

        System.out.println("\nAll key press tests completed!");

        driver.quit();
    }
}
