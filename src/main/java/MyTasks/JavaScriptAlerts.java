package MyTasks;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/*
 * Test JavaScript alerts: Alert, Confirm, and Prompt
 * After each alert interaction, verify the result text is displayed correctly
 */

public class JavaScriptAlerts {

    public static void testAlert(WebDriver driver, WebDriverWait wait, WebElement button, 
                                  String inputText, boolean accept, String expectedResult) {
        // Click the button
        button.click();
        
        // Wait for alert to appear
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        
        // Send keys if it's a prompt and inputText is provided
        if (inputText != null && !inputText.isEmpty()) {
            alert.sendKeys(inputText);
        }
        
        // Accept or dismiss
        if (accept) {
            alert.accept();
        } else {
            alert.dismiss();
        }
        
        // Verify result text
        WebElement result = driver.findElement(By.id("result"));
        assert result.getText().equals(expectedResult) : 
            "Expected: '" + expectedResult + "', but got: '" + result.getText() + "'";
        System.out.println("Test passed: " + result.getText());
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Find all buttons in the list
        List<WebElement> buttons = driver.findElements(By.cssSelector("ul li button"));

        // Test JS Alert (first button) - just accept
        testAlert(driver, wait, buttons.get(0), null, true, "You successfully clicked an alert");

        // Test JS Confirm (second button) - accept
        testAlert(driver, wait, buttons.get(1), null, true, "You clicked: Ok");

        // Test JS Confirm (second button) - dismiss
        testAlert(driver, wait, buttons.get(1), null, false, "You clicked: Cancel");

        // Test JS Prompt (third button) - accept with text
        testAlert(driver, wait, buttons.get(2), "Hello Selenium", true, "You entered: Hello Selenium");

        // Test JS Prompt (third button) - dismiss
        testAlert(driver, wait, buttons.get(2), "Test", false, "You entered: null");

        // Test JS Prompt (third button) - accept with empty text
        testAlert(driver, wait, buttons.get(2), "", true, "You entered: ");

        System.out.println("\nAll JavaScript alert tests completed successfully!");

        driver.quit();
    }
}
