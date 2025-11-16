package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/*
 * Test locating elements in a large and deeply nested DOM structure
 * Elements are nested 50 levels deep, testing locator strategies and performance
 */

public class LargeDeepDOM {

    public static void findAndVerifyElement(WebDriver driver, By locator, String expectedText, String elementName) {
        WebElement element = driver.findElement(locator);
        String actualText = element.getText();
        
        if (actualText.equals(expectedText)) {
            System.out.println("TEST PASSED: Found " + elementName + " - Text: " + actualText);
        } else {
            System.out.println("TEST FAILED: " + elementName + " - Expected: '" + expectedText + 
                    "', but got: '" + actualText + "'");
        }
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/large");

        // Test 1: Find element nested 50 levels deep (in "No Siblings" section)
        // Using XPath to find the element at the deepest level
        findAndVerifyElement(driver, 
            By.xpath("//div[@id='sibling-50.3']"), 
            "50.3", 
            "deeply nested element (50.3)");

        // Test 2: Find elements in the Siblings section
        findAndVerifyElement(driver, 
            By.xpath("//div[contains(@id, 'sibling-1.1')]"),
            "1.1", 
            "sibling element (1.1)");

        findAndVerifyElement(driver, 
            By.xpath("//div[contains(@id, 'sibling-25.2')]"), 
            "25.2", 
            "sibling element (25.2)");

        findAndVerifyElement(driver, 
            By.xpath("//div[contains(@id, 'sibling-50.1')]"), 
            "50.1", 
            "sibling element (50.1)");

        // Test 3: Find elements in the table
        findAndVerifyElement(driver, 
            By.xpath("//table//td[text()='1.1']"), 
            "1.1", 
            "table cell (1.1)");

        findAndVerifyElement(driver, 
            By.xpath("//table//td[text()='50.50']"), 
            "50.50", 
            "table cell (50.50)");

        // Test 4: Count all sibling elements
        List<WebElement> siblings = driver.findElements(By.xpath("//div[contains(@id, 'sibling-')]"));
        System.out.println("Total sibling elements found: " + siblings.size());
        assert siblings.size() >= 150 : "Expected at least 150 sibling elements (50 rows × 3 columns)";

        // Test 5: Count all table cells
        List<WebElement> tableCells = driver.findElements(By.xpath("//table//td"));
        System.out.println("Total table cells found: " + tableCells.size());
        assert tableCells.size() >= 2500 : "Expected at least 2500 table cells (50 rows × 50 columns)";

        System.out.println("\nAll Large & Deep DOM tests completed successfully!");

        driver.quit();
    }
}

