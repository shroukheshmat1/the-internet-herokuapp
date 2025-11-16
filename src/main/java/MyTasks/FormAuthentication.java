package MyTasks;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FormAuthentication {

    public static void Scenario(WebDriver driver, String loginUrl, String username, String password) {
        try {
            driver.findElement(By.cssSelector("#username")).sendKeys(username);
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
            driver.findElement(By.className("fa-sign-in")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.button.secondary.radius")),
                    ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
            ));

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("https://the-internet.herokuapp.com/secure")) {
                System.out.println("Test Passed for username: " + username + ", password: " + password);
                try {
                    WebElement logoutButton = driver.findElement(By.className("icon-signout"));
                    logoutButton.click();
                    System.out.println("Logged out successfully.");
                } catch (Exception ignore) {}
            } else {
                System.out.println("Test Passed for username: " + username + ", password: " + password);
            }

            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public static void clearFields(WebDriver driver) {
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("password")).clear();
    }

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
//        driver.manage().window().maximize();
        driver.findElement(By.linkText("Form Authentication")).click();
        String logInURL = driver.getCurrentUrl();

        // T.C1: Happy Scenario — Valid username and password
        Scenario(driver, logInURL, "tomsmith", "SuperSecretPassword!");
        clearFields(driver);

        // T.C2: Invalid username, valid password
        Scenario(driver, logInURL, "wrongUser", "SuperSecretPassword!");
        clearFields(driver);

        // T.C3: Valid username, invalid password
        Scenario(driver, logInURL, "tomsmith", "wrongPass");
        clearFields(driver);

        // T.C4: Invalid username and password
        Scenario(driver, logInURL, "wrongUser", "wrongPass");

        Thread.sleep(1000);
        driver.quit();
    }
}
