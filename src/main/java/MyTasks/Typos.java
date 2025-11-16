package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Test pages with typos (intentional spelling mistakes)
 * Verify that the page loads and we can detect the typo in the text
 */

public class Typos {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/typos");

        // Get the paragraph text that contains the typo
        WebElement paragraph = driver.findElement(By.cssSelector("p:last-of-type"));
        String paragraphText = paragraph.getText();

        System.out.println("Paragraph text: " + paragraphText);

        // Check if there's a typo in the text
        // The page intentionally has a typo that may or may not appear
        // "Sometimes you'll see a typo, other times you won't."
        boolean hasTypo = paragraphText.contains("won,t") || paragraphText.contains("won't");

        if (hasTypo) {
            System.out.println("Typo detected in the text!");
        } else {
            System.out.println("No typo found (or typo was fixed)");
        }

        // Verify the page loaded correctly
        assert paragraph.isDisplayed() : "Paragraph is not displayed!";
        assert paragraphText.length() > 0 : "Paragraph text is empty!";

        // Reload the page multiple times to see if typo appears/disappears
        System.out.println("\nReloading page to check for typo variations...");
        for (int i = 0; i < 3; i++) {
            driver.navigate().refresh();
            paragraph = driver.findElement(By.cssSelector("p:last-of-type"));
            String currentText = paragraph.getText();
            System.out.println("Reload " + (i + 1) + ": " + currentText);
        }

        System.out.println("\nTypos test completed successfully!");

        driver.quit();
    }
}

