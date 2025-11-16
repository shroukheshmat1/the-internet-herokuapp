package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class EnableAd {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        // 1- Navigate to "Entry Ad" Page
        driver.get("https://the-internet.herokuapp.com/entry_ad");
        // 2- make sure from the displaying of "modal" window
        WebElement modalWindow = driver.findElement(By.id("modal"));
        WebElement modalWindowTitle = modalWindow.findElement(By.className("modal-title"));
        Set<String> allCurrentWindowHandles = driver.getWindowHandles();
        int numOfWindows = allCurrentWindowHandles.size();
        assert numOfWindows == 1 : "The modal was opened in a NEW WINDOW! which is unexpected!";
        assert modalWindow.isDisplayed() : "the window STILL not displayed!";//to make sure that the modal window is now displayed to the user and display:block NOT none
        // 3- make sure from the correct title window
        assert modalWindowTitle.getText().equals("This is a modal window") : "The title is different from what we expect";
        // 4- close the modal window
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".modal-footer p")));
        closeBtn.click();        // 5- make sure from the disappearing of the modal window
        assert !modalWindow.isDisplayed() : "The window still displayed although we closed it!";
        List<WebElement> elements = driver.findElements(By.id("modal"));
        assert elements.size() > 0 : "The window displayed from the DOM! which's unexpected! it should just be hidden!";
        // 6- click on restart here
        driver.findElement(By.linkText("click here")).click();
        // 7- make sure from displaying the modal window
        assert modalWindow.isDisplayed() : "the window STILL not displayed!";//to make sure that the modal window is now displayed to the user and display:block NOT none
        System.out.println("Test Passed Successfully");
        driver.quit();
    }
}
