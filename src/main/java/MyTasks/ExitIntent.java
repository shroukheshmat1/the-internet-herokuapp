package MyTasks;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.Robot;
import java.time.Duration;
import java.util.List;

/**
 * Scenario: Trigger the Exit Intent modal with a simulated mouse exit.
 * Steps:
 * 1. Use Robot to move the pointer above the viewport and wait for the modal.
 * 2. Validate the title, close the modal, and ensure it is hidden again.
 */
public class ExitIntent {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/exit_intent");

        List<WebElement> modalWindow = driver.findElements(By.id("ouibounce-modal"));
        if (modalWindow.size() == 1) {
            assert !modalWindow.get(0).isDisplayed() : "The Window is displayed although we're still in the viewport";
        } else {
            System.out.println("The window doesn't exist in the DOM");
        }

        // Get browser window position
        Point windowPosition = driver.manage().window().getPosition();

        // Use Robot to move actual mouse cursor
        Robot robot = new Robot();

        // Move mouse to middle of browser window first
        robot.mouseMove(windowPosition.getX() + 400, windowPosition.getY() + 300);
        Thread.sleep(100);

        // Move mouse above the browser window to trigger exit intent
        robot.mouseMove(windowPosition.getX() + 400, windowPosition.getY() - 10);
        Thread.sleep(500);

        // Wait for modal to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ouibounce-modal")));

        modalWindow = driver.findElements(By.id("ouibounce-modal"));
        assert modalWindow.get(0).isDisplayed() : "The Window is NOT displayed although we're out of the viewport";

        WebElement modalTitle = driver.findElement(By.xpath("//div[@class='modal-title']/h3"));
        assert modalTitle.getText().equals("This is a modal window") : "The title is not as expected";

        By modalFooterLocator = By.xpath("//div[@class='modal-footer']/p");
        WebElement modalFooter = wait.until(ExpectedConditions.elementToBeClickable(modalFooterLocator));
        modalFooter.click();

        // Wait for modal to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ouibounce-modal")));

        modalWindow = driver.findElements(By.id("ouibounce-modal"));
        if (modalWindow.size() == 1) {
            assert !modalWindow.get(0).isDisplayed() : "The Window is displayed although we closed it";
        } else {
            System.out.println("The window doesn't exist in the DOM");
        }

        driver.quit();
    }
}