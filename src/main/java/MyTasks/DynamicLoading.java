package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Scenario: Validate both Dynamic Loading examples.
 * Steps:
 * 1. Trigger Example 1 and wait until the hidden element appears with “Hello World!”
 * 2. Navigate back, open Example 2, and assert the lazily rendered text matches as well.
 */
public class DynamicLoading {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading");
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();
        assert driver.getCurrentUrl().equals("https://the-internet.herokuapp.com/dynamic_loading/1") : "User was directed to a different URL";
        WebElement button = driver.findElement(By.cssSelector("#start button"));
        button.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        assert driver.findElement(By.id("finish")).getText().equals("Hello World!") : "The text is NOT Hello World";
        driver.navigate().back();
        assert driver.getCurrentUrl().equals("https://the-internet.herokuapp.com/dynamic_loading") : "User was directed to a different URL";
        driver.findElement(By.linkText("Example 2: Element rendered after the fact")).click();
        driver.findElement(By.id("start")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        assert driver.findElement(By.id("finish")).getText().equals("Hello World!") : "The text is NOT Hello World";
        driver.quit();
    }
}
