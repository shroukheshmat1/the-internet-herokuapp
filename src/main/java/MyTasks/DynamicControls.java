package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Scenario: Exercise the checkbox and input field on the Dynamic Controls page.
 * Steps:
 * 1. Remove and add the checkbox while waiting for loading indicators/messages.
 * 2. Toggle the input field between enabled/disabled states and validate the UI hints.
 */
public class DynamicControls {
    public static void main(String[] args) {
        WebDriver driver = new EdgeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        WebElement checkBox = driver.findElement(By.id("checkbox"));
        Rectangle rect = checkBox.getRect();
        WebElement checkBoxButton = driver.findElement(By.cssSelector("#checkbox-example button"));
        checkBoxButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loading")));
        System.out.println("Loading appeared");
        wait.until(ExpectedConditions.invisibilityOf(loading));
        System.out.println("Loading disappeared");

        wait.until(driver1 -> checkBoxButton.getText().equals("Add"));
        System.out.println("Button text is now: " + checkBoxButton.getText());
        assert checkBoxButton.getText().equals("Add") : "Button did not change to Add";

        WebElement infoMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        assert infoMsg.getText().equals("It's gone!") : "The message 'It's gone!' wasn't displayed";

        assert !checkBox.isDisplayed() : "The checkbox is still displayed although we removed it!";
        checkBoxButton.click();
        loading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loading")));
        wait.until(ExpectedConditions.visibilityOf(loading));
        System.out.println("Loading appeared again");
        wait.until(ExpectedConditions.invisibilityOf(loading));
        System.out.println("Loading disappeared again");

        checkBox = driver.findElement(By.id("checkbox"));
        wait.until(ExpectedConditions.visibilityOf(checkBox));
        assert checkBox.isDisplayed() : "The checkbox didn't appear";

        wait.until(driver1 -> checkBoxButton.getText().equals("Remove"));
        System.out.println("Button text is now: " + checkBoxButton.getText());
        assert checkBoxButton.getText().equals("Add") : "Button did not change to Remove";
        infoMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        assert infoMsg.getText().equals("It's back!") : "The message 'It's back!' wasn't displayed";

        // ==========================================================================================
        WebElement textField = driver.findElement(By.xpath("//form[@id='input-example']/input"));
        WebElement fieldButton = driver.findElement(By.xpath("//form[@id='input-example']/button"));
        if(fieldButton.getText().equals("Enable")) {
            assert !textField.isEnabled() : "Text field should NOT be typable when button says Enable";
            fieldButton.click();
//            loading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loading")));
//            wait.until(ExpectedConditions.visibilityOf(loading));
//            System.out.println("Loading appeared after clicking on the 'Enable' button");
//            wait.until(ExpectedConditions.invisibilityOf(loading));
//            System.out.println("Loading disappeared");
            infoMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            assert infoMsg.getText().equals("It's enabled!") : "The message 'It's enabled!' wasn't displayed";
            assert fieldButton.getText().equals("Disable"): "The button should be 'Disable' now!";
            assert textField.isEnabled() : "Text field should be typable when button says Disable";

        }
        driver.quit();
    }
}



//package MyTasks;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Rectangle;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class DynamicControls {
//    public static void main(String[] args) {
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        // ================= Checkbox section =================
//        WebElement checkBox = driver.findElement(By.id("checkbox"));
//        Rectangle rect = checkBox.getRect();
//        WebElement checkBoxButton = driver.findElement(By.cssSelector("#checkbox-example button"));
//        checkBoxButton.click();
//
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
//        System.out.println("Loading disappeared");

//        wait.until(driver1 -> checkBoxButton.getText().equals("Add"));
//        System.out.println("Button text is now: " + checkBoxButton.getText());
//        assert checkBoxButton.getText().equals("Add") : "Button did not change to Add";
//
//        WebElement infoMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
//        assert infoMsg.getText().equals("It's gone!") : "The message 'It's gone!' wasn't displayed";
//
//        assert !checkBox.isDisplayed() : "The checkbox is still displayed although we removed it!";
//
//        checkBoxButton.click();
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
//        System.out.println("Loading disappeared again");
//
//        checkBox = driver.findElement(By.id("checkbox"));
//        wait.until(ExpectedConditions.visibilityOf(checkBox));
//        assert checkBox.isDisplayed() : "The checkbox didn't appear";
//
//        wait.until(driver1 -> checkBoxButton.getText().equals("Remove"));
//        System.out.println("Button text is now: " + checkBoxButton.getText());
//        assert checkBoxButton.getText().equals("Remove") : "Button did not change to Remove";
//
//        infoMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
//        assert infoMsg.getText().equals("It's back!") : "The message 'It's back!' wasn't displayed";
//
//        // ================= Input section =================
//        WebElement textField = driver.findElement(By.xpath("//form[@id='input-example']/input"));
//        WebElement fieldButton = driver.findElement(By.xpath("//form[@id='input-example']/button"));
//
//        if (fieldButton.getText().equals("Enable")) {
//            assert !textField.isEnabled() : "Text field should NOT be typable when button says Enable";
//
//            fieldButton.click();
//
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
//            System.out.println("Loading (if any) disappeared after clicking 'Enable'");
//
//
//            infoMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
//            assert infoMsg.getText().equals("It's enabled!") : "The message 'It's enabled!' wasn't displayed";
//
//            assert fieldButton.getText().equals("Disable") : "The button should be 'Disable' now!";
//            assert textField.isEnabled() : "Text field should be typable when button says Disable";
//        }
//
//        driver.quit();
//    }
//}
