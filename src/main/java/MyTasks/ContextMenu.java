package MyTasks;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Scenario: Right-click inside the Context Menu hotspot.
 * Steps:
 * 1. Trigger the custom context menu via Actions.contextClick.
 * 2. Assert the alert text and dismiss it before closing the browser.
 */
public class ContextMenu {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Context Menu")).click();
        WebElement box = driver.findElement(By.id("hot-spot"));
        Actions actions = new Actions(driver);
        actions.contextClick(box).perform();
        Alert alert = driver.switchTo().alert();
        assert alert.getText().equals("You selected a context menu"):"The alert message differs from what we expect";
        alert.accept();
        driver.quit();
    }
}
