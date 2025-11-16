package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Frames {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        //To reach bottom Frame
        driver.switchTo().frame("frame-bottom");
        System.out.println("Bottom Frame Reached!");
        driver.switchTo().defaultContent();
        System.out.println("Out Of Bottom Frame");

        //To reach Top Frame
        driver.switchTo().frame("frame-top");
        System.out.println("Top Frame Reached!");
        //To reach Top-Left Frame
        driver.switchTo().frame("frame-left");
        String textInsideFrame = driver.findElement(By.tagName("body")).getText();
        System.out.println(textInsideFrame);
        driver.switchTo().parentFrame();
        //To reach Top-Middle Frame
        driver.switchTo().frame("frame-middle");
        textInsideFrame = driver.findElement(By.tagName("body")).getText();
        System.out.println(textInsideFrame);
        driver.switchTo().parentFrame();
        //To reach Top-Right Frame
        driver.switchTo().frame("frame-right");
        textInsideFrame = driver.findElement(By.tagName("body")).getText();
        System.out.println(textInsideFrame);
        driver.switchTo().defaultContent();
        System.out.println("Out Of Top Frame");
        driver.get("https://the-internet.herokuapp.com/iframe");
        driver.quit();
    }
}
