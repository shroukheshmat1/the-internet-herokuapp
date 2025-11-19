package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Scenario: Validate the dynamic table and action buttons on the Challenging DOM page.
 * Key checks:
 * 1. Ensure the table renders 10 rows with 7 columns each.
 * 2. Confirm every row exposes all three action links (edit/delete/clone).
 * 3. Click the blue, red, and green buttons to make sure the page remains interactive.
 */
public class ChallengingDOM {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://the-internet.herokuapp.com/challenging_dom");

            WebElement title = driver.findElement(By.tagName("h3"));
            assert title.getText().equals("Challenging DOM") : "Unexpected page header";

            List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
            assert rows.size() == 10 : "Expected 10 table rows but found " + rows.size();

            for (int i = 0; i < rows.size(); i++) {
                List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
                assert columns.size() == 7 : "Row " + i + " does not contain 7 columns";

                WebElement actionCell = columns.get(6);
                List<WebElement> actionLinks = actionCell.findElements(By.tagName("a"));
                assert actionLinks.size() == 3 : "Row " + i + " does not have 3 action links";
            }

            WebElement blueButton = driver.findElement(By.cssSelector(".button"));
            WebElement redButton = driver.findElement(By.cssSelector(".button.alert"));
            WebElement greenButton = driver.findElement(By.cssSelector(".button.success"));

            blueButton.click();
            redButton.click();
            greenButton.click();

            System.out.println("Challenging DOM checks passed ✅");
        } finally {
            driver.quit();
        }
    }
}
