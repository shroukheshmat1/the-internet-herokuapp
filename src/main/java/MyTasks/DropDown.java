package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropDown {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Dropdown")).click();
        WebElement selectHeader = driver.findElement(By.id("dropdown"));
        Select selectedOption = new Select(selectHeader);
        selectedOption.selectByIndex(1);
        WebElement selected = selectedOption.getFirstSelectedOption();
        System.out.println(selected.getText());
        String selectedText = selected.getText();
        assert selectedText.equals("Option 1") : "The selected element is not the same as the expected one";
        driver.quit();
    }
}
