package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Inputs {
    public boolean testInput(WebElement element, String valueToType, String expectedValue) {
        element.clear();
        element.sendKeys(valueToType);

        String actualValue = element.getAttribute("value");

        if (!actualValue.equals(expectedValue)) {
            System.out.println("TEST FAILED: Expected = '" + expectedValue +
                    "', but got = '" + actualValue + "'");
            return false;
        } else {
            System.out.println("TEST PASSED: Input behaved correctly.");
            return true;
        }
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/inputs");

        WebElement inputElement = driver.findElement(By.xpath("//div[@class='example']/input"));

        Inputs obj = new Inputs();

        // Test 1 → Letters should NOT be accepted → Expected value = e and this is NOT a bug,BECAUSE THE INPUT TYPE IS
        // 'number' OR HAS VALIDATION, BUT THIS IS NOT A FAIL. SELENIUM SENDKEYS SOMETIMES TRIGGERS DEFAULT BROWSER
        // BEHAVIOR BEFORE THE FULL TEXT IS TYPED.
        obj.testInput(inputElement, "Test", "");

        // Test 2 → Symbols should NOT be accepted → Expected value = ""
        obj.testInput(inputElement, "$%", "");

        // Test 3 → Numbers should be accepted → Expected value = "5"
        obj.testInput(inputElement, "5", "5");

        driver.quit();
    }

}
