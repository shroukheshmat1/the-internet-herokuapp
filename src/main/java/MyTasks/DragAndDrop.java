package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Scenario: Swap Column A and Column B using drag and drop.
 * Steps:
 * 1. Drag the first column onto the second with the Actions API.
 * 2. Verify their header text swapped to ensure the DnD succeeded.
 */
public class DragAndDrop {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Drag and Drop")).click();
        WebElement boxA = driver.findElement(By.id("column-a"));
        WebElement boxB = driver.findElement(By.id("column-b"));
        Actions action = new Actions(driver);
        action.dragAndDrop(boxA,boxB).perform();
        String textA = boxA.getText();
        String textB = boxB.getText();
        if (textA.equals("B") && textB.equals("A")) {
            System.out.println("Boxes swapped successfully!");
        } else {
            System.out.println("Boxes didn't swap!");
        }

        driver.quit();
    }
}
