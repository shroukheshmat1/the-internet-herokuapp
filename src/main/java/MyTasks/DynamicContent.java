package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Scenario: Compare the dynamic vs static versions of the Dynamic Content page.
 * Steps:
 * 1. Refresh the default view to confirm images/text actually change.
 * 2. Switch to the static view and prove the content now stays the same.
 */
public class DynamicContent {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Dynamic Content")).click();
        List<WebElement> imgsBeforeRefreshAndStatic = driver.findElements(By.cssSelector(".large-2.columns img"));
        List<WebElement> descriptionBeforeRefreshAndStatic = driver.findElements(By.cssSelector(".large-10.columns"));
        // BEFORE  Static
        driver.navigate().refresh();
        List<WebElement> imgsAfter = driver.findElements(By.cssSelector(".large-2.columns img"));
        List<WebElement> descriptionAfter = driver.findElements(By.cssSelector(".large-10.columns"));
        for (int i=0;i<imgsBeforeRefreshAndStatic.size();i++) {
            assert !imgsBeforeRefreshAndStatic.get(i).getAttribute("src").equals(imgsAfter.get(i).getAttribute("src")) : "The image number" + (i+1) + "didn't change after refreshing";
        }
        for (int i=0;i<imgsBeforeRefreshAndStatic.size();i++) {
            assert !descriptionBeforeRefreshAndStatic.get(i).getText().equals(descriptionAfter.get(i).getText()) : "The description number" + (i+1) + "didn't change after refreshing";
        }
        driver.findElement(By.linkText("click here")).click();
        // AFTER   Static
        List<WebElement> imgsAfterStaticBeforeRefresh  = driver.findElements(By.cssSelector(".large-2.columns img"));
        List<WebElement> descriptionAfterStaticBeforeRefresh  = driver.findElements(By.cssSelector(".large-10.columns"));
        driver.navigate().refresh();
        List<WebElement> imgsAfterStaticAfterRefresh  = driver.findElements(By.cssSelector(".large-2.columns img"));
        List<WebElement> descriptionAfterStaticAfterRefresh  = driver.findElements(By.cssSelector(".large-10.columns"));
        driver.navigate().refresh();
        for (int i=0;i<imgsBeforeRefreshAndStatic.size();i++) {
            assert imgsAfterStaticBeforeRefresh.get(i).getAttribute("src").equals(imgsAfterStaticAfterRefresh.get(i).getAttribute("src")) : "The image number" + (i+1) + "changed after refreshing a STATIC page content";
        }
        for (int i=0;i<imgsBeforeRefreshAndStatic.size();i++) {
            assert descriptionAfterStaticBeforeRefresh.get(i).getText().equals(descriptionAfterStaticAfterRefresh.get(i).getText()) : "The description number" + (i+1) + "changed after refreshing a STATIC page content";
        }
        driver.quit();
    }
}
