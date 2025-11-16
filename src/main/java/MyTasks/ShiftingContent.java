package MyTasks;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class ShiftingContent {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/");

        // CLICK Shifting Content link
        driver.findElement(By.linkText("Shifting Content")).click();

        // ----------------------------
        // Example 1: Menu Element
        // ----------------------------
        driver.findElement(By.linkText("Example 1: Menu Element")).click();

        List<WebElement> menuBefore = driver.findElements(By.cssSelector(".example a"));
        List<String> beforeMenuTexts = new ArrayList<>();
        for (WebElement e : menuBefore) {
            beforeMenuTexts.add(e.getText());
        }

        System.out.println("Menu BEFORE refresh: " + beforeMenuTexts);

        driver.navigate().refresh();

        List<WebElement> menuAfter = driver.findElements(By.cssSelector(".example a"));
        List<String> afterMenuTexts = new ArrayList<>();
        for (WebElement e : menuAfter) {
            afterMenuTexts.add(e.getText());
        }

        System.out.println("Menu AFTER refresh: " + afterMenuTexts);

        if (!beforeMenuTexts.equals(afterMenuTexts)) {
            System.out.println("Example 1 RESULT: SHIFT DETECTED ✓");
        } else {
            System.out.println("Example 1 RESULT: NO SHIFT ✗");
        }

        driver.navigate().back();

        // ----------------------------
        // Example 2: Image
        // ----------------------------
        driver.findElement(By.linkText("Example 2: An image")).click();

        WebElement imgBefore = driver.findElement(By.cssSelector(".example img"));
        Point locBefore = imgBefore.getLocation();
        Dimension sizeBefore = imgBefore.getSize();

        System.out.println("Image BEFORE refresh -> Location: " + locBefore + ", Size: " + sizeBefore);

        driver.navigate().refresh();

        WebElement imgAfter = driver.findElement(By.cssSelector(".example img"));
        Point locAfter = imgAfter.getLocation();
        Dimension sizeAfter = imgAfter.getSize();

        System.out.println("Image AFTER refresh -> Location: " + locAfter + ", Size: " + sizeAfter);

        if (!locBefore.equals(locAfter) || !sizeBefore.equals(sizeAfter)) {
            System.out.println("Example 2 RESULT: SHIFT DETECTED ✓");
        } else {
            System.out.println("Example 2 RESULT: NO SHIFT ✗");
        }

        driver.navigate().back();

        // ----------------------------
        // Example 3: List
        // ----------------------------
        driver.findElement(By.linkText("Example 3: List")).click();

        List<WebElement> listBefore = driver.findElements(By.cssSelector("#content li"));
        List<String> beforeListTexts = new ArrayList<>();
        for (WebElement e : listBefore) {
            beforeListTexts.add(e.getText());
        }

        System.out.println("List BEFORE refresh: " + beforeListTexts);

        driver.navigate().refresh();

        List<WebElement> listAfter = driver.findElements(By.cssSelector("#content li"));
        List<String> afterListTexts = new ArrayList<>();
        for (WebElement e : listAfter) {
            afterListTexts.add(e.getText());
        }

        System.out.println("List AFTER refresh: " + afterListTexts);

        if (!beforeListTexts.equals(afterListTexts)) {
            System.out.println("Example 3 RESULT: SHIFT DETECTED ✓");
        } else {
            System.out.println("Example 3 RESULT: NO SHIFT ✗");
        }

        // Close browser
        driver.quit();
    }
}
