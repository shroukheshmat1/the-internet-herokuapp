package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Scenario: Observe the flakiness of the “Gallery” link on the Disappearing Elements page.
 * Steps:
 * 1. Refresh the page multiple times and log whenever the Gallery link exists.
 * 2. Assert that it both appears and disappears at least once, proving the behavior.
 */
public class DisappearingElements {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Disappearing Elements")).click();

        boolean appearedOnce = false;
        boolean disappearedOnce = false;
        boolean isGalleryDisplayed;
        for (int i = 0; i < 10; i++) {
            List<WebElement> galleryElement = driver.findElements(By.linkText("Gallery"));
            isGalleryDisplayed = !galleryElement.isEmpty();

            if (isGalleryDisplayed) {
                appearedOnce = true;
                System.out.println("Iteration " + (i+1) + ": Gallery Displayed");
            } else {
                disappearedOnce = true;
                System.out.println("Iteration " + (i+1) + ": Gallery NOT Displayed");
            }
            if (appearedOnce && disappearedOnce) {
                System.out.println("\nGallery appeared and disappeared for at least once, Confirmed✅.");
                break;
            }

            driver.navigate().refresh();
            Thread.sleep(1500);
        }

        if (!appearedOnce || !disappearedOnce) {
            System.out.println("\nResult:");
            if (!appearedOnce)
                System.out.println("Gallery didn't appear for at least once!");
            if (!disappearedOnce)
                System.out.println("Gallery didn't disappear for at least once!");
        }

        driver.quit();
    }
}
