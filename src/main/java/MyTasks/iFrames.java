package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Scenario: Practice working with single and nested iframes on demo.automationtesting.in.
 * Steps:
 * 1. Type text inside the simple iframe (id = singleframe).
 * 2. Switch back, open the nested iframe tab, then type inside the deepest iframe.
 * 3. Use assertions/logs to ensure focus changes are successful before quitting.
 */
public class iFrames {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demo.automationtesting.in/Frames.html");

            driver.switchTo().frame("singleframe");
            WebElement singleInput = driver.findElement(By.tagName("input"));
            singleInput.clear();
            singleInput.sendKeys("Typing inside the single iframe ✅");

            driver.switchTo().defaultContent();

            driver.findElement(By.xpath("//a[contains(text(),'Iframe with in an Iframe')]")).click();

            WebElement outerFrame = driver.findElement(By.cssSelector("iframe[src='MultipleFrames.html']"));
            driver.switchTo().frame(outerFrame);

            WebElement innerFrame = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(innerFrame);

            WebElement nestedInput = driver.findElement(By.tagName("input"));
            nestedInput.clear();
            nestedInput.sendKeys("Typing inside the nested iframe ✅");

            System.out.println("Iframe interactions completed successfully.");
        } finally {
            driver.quit();
        }
    }
}
