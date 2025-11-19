package MyTasks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Scenario: Validate the Add/Remove Elements page.
 * Steps:
 * 1. Count delete buttons, add a new element, and assert the count increases by one.
 * 2. Remove an element again and ensure the counter decreases.
 */
public class AddRemoveElements {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        List<WebElement> before = driver.findElements(By.cssSelector("button.added-manually"));
        int countBeforeAdd = before.size();
        driver.findElement(By.xpath("//button[text() = 'Add Element']")).click();
        List<WebElement> after = driver.findElements(By.cssSelector("button.added-manually"));
        int countAfterAdd = after.size();
        assert countAfterAdd == countBeforeAdd + 1 : "The pressed button was NOT added";
        System.out.println("The pressed button was added successfully - PASSED");
        driver.findElement(By.cssSelector("button.added-manually")).click();
        List<WebElement> afterDel = driver.findElements(By.cssSelector("button.added-manually"));
        int countAfterDel = afterDel.size();
        assert countAfterDel == countAfterAdd-1 : "The pressed button was NOT deleted";
        System.out.println("The pressed button was deleted successfully - PASSED");
        driver.quit();
    }
}
