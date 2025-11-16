package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Test navigating through nested iframes (frames inside frames)
 * Switch to parent frame, then to child frame, and verify the text content
 */

public class NestedFrames {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/nested_frames");

        // Switch to the top frame (parent frame)
        driver.switchTo().frame("frame-top");
        System.out.println("Switched to top frame");

        // Switch to the left frame (child frame inside top frame)
        driver.switchTo().frame("frame-left");
        System.out.println("Switched to left frame");

        // Get and verify the text in the left frame
        WebElement leftFrameText = driver.findElement(By.tagName("body"));
        assert leftFrameText.getText().equals("LEFT") : 
            "Expected 'LEFT', but got: " + leftFrameText.getText();
        System.out.println("Left frame text verified: " + leftFrameText.getText());

        // Switch back to top frame (parent of left frame)
        driver.switchTo().parentFrame();
        System.out.println("Switched back to top frame");

        // Switch to the middle frame
        driver.switchTo().frame("frame-middle");
        System.out.println("Switched to middle frame");

        // Get and verify the text in the middle frame
        WebElement middleFrameText = driver.findElement(By.tagName("body"));
        assert middleFrameText.getText().equals("MIDDLE") : 
            "Expected 'MIDDLE', but got: " + middleFrameText.getText();
        System.out.println("Middle frame text verified: " + middleFrameText.getText());

        // Switch back to top frame
        driver.switchTo().parentFrame();
        System.out.println("Switched back to top frame");

        // Switch to the right frame
        driver.switchTo().frame("frame-right");
        System.out.println("Switched to right frame");

        // Get and verify the text in the right frame
        WebElement rightFrameText = driver.findElement(By.tagName("body"));
        assert rightFrameText.getText().equals("RIGHT") : 
            "Expected 'RIGHT', but got: " + rightFrameText.getText();
        System.out.println("Right frame text verified: " + rightFrameText.getText());

        // Switch back to top frame, then to default content
        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
        System.out.println("Switched back to default content");

        // Switch to the bottom frame (directly under default content)
        driver.switchTo().frame("frame-bottom");
        System.out.println("Switched to bottom frame");

        // Get and verify the text in the bottom frame
        WebElement bottomFrameText = driver.findElement(By.tagName("body"));
        assert bottomFrameText.getText().equals("BOTTOM") : 
            "Expected 'BOTTOM', but got: " + bottomFrameText.getText();
        System.out.println("Bottom frame text verified: " + bottomFrameText.getText());

        System.out.println("\nNested frames test completed successfully!");

        driver.quit();
    }
}

