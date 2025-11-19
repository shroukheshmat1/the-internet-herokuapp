package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Scenario: Verify HTTP basic authentication.
 * Steps:
 * 1. Open the protected page using username/password inside the URL.
 * 2. Assert that the success paragraph is displayed with the expected message.
 * 3. Print a friendly log to confirm the flow and close the browser.
 */
public class BasicAuth {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            String username = "admin";
            String password = "admin";
            String authUrl = String.format("https://%s:%s@the-internet.herokuapp.com/basic_auth", username, password);

            driver.get(authUrl);

            WebElement successParagraph = driver.findElement(By.cssSelector("div.example p"));
            String successText = successParagraph.getText();

            assert successParagraph.isDisplayed() : "Success paragraph is not visible";
            assert successText.contains("Congratulations!") : "Unexpected success text: " + successText;

            System.out.println("Basic Auth passed ✅ - Message: " + successText);
        } finally {
            driver.quit();
        }
    }
}
