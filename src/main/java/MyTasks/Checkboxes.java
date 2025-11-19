package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Scenario: Toggle both checkboxes on the Checkboxes page.
 * Steps:
 * 1. Record how many boxes are selected/deselected before interaction.
 * 2. Click each checkbox to flip its state and assert the counters change.
 */
public class Checkboxes {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Checkboxes")).click();
        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
        int selectedBefore = 0;
        int deselectedBefore = 0;

        for(WebElement checkbox : checkboxes){
            if(checkbox.isSelected()){
                selectedBefore++;
                checkbox.click(); // uncheck
            } else {
                deselectedBefore++;
                checkbox.click(); // check
            }
        }


        int selectedAfter = 0;
        int deselectedAfter = 0;
        for(WebElement checkbox : checkboxes){
            if(checkbox.isSelected()) selectedAfter++;
            else deselectedAfter++;
        }

        System.out.println("Selected Before = "+selectedBefore+", Selected After = "+selectedAfter);
        System.out.println("Deselected Before = "+deselectedBefore+", Deselected After = "+deselectedAfter);
        driver.quit();
    }
}
