package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.List;

/**
 * Scenario: Reveal captions for each avatar on the Hovers page.
 * Steps:
 * 1. Hover every `.figure` element with the Actions API.
 * 2. Assert the nested `.figcaption` becomes visible and log its text.
 */
public class Hovers {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");

        driver.findElement(By.linkText("Hovers")).click();

        // Find all image elements
        List<WebElement> images = driver.findElements(By.cssSelector(".figure"));
        Actions actions = new Actions(driver);

        // Hover over each image and verify that the hidden caption is displayed
        for (int i = 0; i < images.size(); i++) {
            WebElement image = images.get(i);

            // Move the mouse to the image (hover)
            actions.moveToElement(image).perform();

            // Find the corresponding caption inside this image container
            WebElement caption = image.findElement(By.cssSelector(".figcaption"));

            // Check if the caption is displayed
            if (caption.isDisplayed()) {
                System.out.println("Caption displayed: " + caption.getText());
            } else {
                System.out.println("Caption NOT displayed!");
            }
        }

        // Close the browser
        driver.quit();
    }
}
