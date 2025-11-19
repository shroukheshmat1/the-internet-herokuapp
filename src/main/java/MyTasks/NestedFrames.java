package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * ============================ 📦 NESTED FRAMES AUTOMATION =============================
 *
 * TEST GOAL:
 * Learn how to navigate through nested iframes (frames inside frames)!
 * Frames are like "pages within a page" - and sometimes they're nested like Russian dolls!
 *
 * WHY THIS IS CHALLENGING:
 * --------------------------------------------------------------------------------------------
 * Frames create separate HTML documents inside the main page. To interact with elements
 * inside a frame, we must first "switch" to that frame. When frames are nested (frame
 * inside a frame), we need to switch multiple times, going deeper and deeper!
 *
 * KEY CONCEPTS:
 * --------------------------------------------------------------------------------------------
 * 1️⃣ What is a Frame?
 *    - A frame is like a separate webpage embedded inside another webpage
 *    - Each frame has its own HTML document
 *    - You can't access elements in a frame without switching to it first
 *
 * 2️⃣ Nested Frames:
 *    - Sometimes frames contain other frames (frame inside a frame!)
 *    - This creates a hierarchy: Main Page → Top Frame → Left/Middle/Right Frames
 *    - We must navigate this hierarchy step by step
 *
 * 3️⃣ Switching Frames:
 *    - driver.switchTo().frame(name) - Switch to a frame by name or ID
 *    - driver.switchTo().parentFrame() - Go back to the parent frame (one level up)
 *    - driver.switchTo().defaultContent() - Go all the way back to the main page
 *
 * 4️⃣ Frame Hierarchy:
 *    - Default Content (main page)
 *      └── frame-top (parent frame)
 *          ├── frame-left (child frame)
 *          ├── frame-middle (child frame)
 *          └── frame-right (child frame)
 *      └── frame-bottom (sibling of frame-top)
 *
 * WHAT WE'LL DO:
 * --------------------------------------------------------------------------------------------
 * Step 1: Switch to the top frame (parent frame)
 * Step 2: Switch to the left frame (child of top frame) and verify text
 * Step 3: Go back to top frame using parentFrame()
 * Step 4: Switch to middle frame and verify text
 * Step 5: Go back to top frame
 * Step 6: Switch to right frame and verify text
 * Step 7: Go back to default content (main page)
 * Step 8: Switch to bottom frame (directly under main page) and verify text
 *
 * FUN FACT:
 * Navigating nested frames is like exploring a building with multiple floors and rooms!
 * You need to know which "room" (frame) you're in and how to get back! 🏢
 *
 * ============================================================================================
 */

public class NestedFrames {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/nested_frames");

        // STEP 1: Switch to the top frame (parent frame)
        // This is like entering the first room in our building
        driver.switchTo().frame("frame-top");
        System.out.println("Switched to top frame");

        // STEP 2: Switch to the left frame (child frame inside top frame)
        // Now we're going deeper - into a room inside the first room!
        driver.switchTo().frame("frame-left");
        System.out.println("Switched to left frame");

        // STEP 3: Get and verify the text in the left frame
        // Now we can find elements inside this nested frame
        WebElement leftFrameText = driver.findElement(By.tagName("body"));
        assert leftFrameText.getText().equals("LEFT") : 
            "Expected 'LEFT', but got: " + leftFrameText.getText();
        System.out.println("Left frame text verified: " + leftFrameText.getText());

        // STEP 4: Switch back to top frame (parent of left frame)
        // parentFrame() takes us one level up - back to the parent
        driver.switchTo().parentFrame();
        System.out.println("Switched back to top frame");

        // STEP 5: Switch to the middle frame
        // We're still in the top frame, now let's go to a different child frame
        driver.switchTo().frame("frame-middle");
        System.out.println("Switched to middle frame");

        // STEP 6: Get and verify the text in the middle frame
        WebElement middleFrameText = driver.findElement(By.tagName("body"));
        assert middleFrameText.getText().equals("MIDDLE") : 
            "Expected 'MIDDLE', but got: " + middleFrameText.getText();
        System.out.println("Middle frame text verified: " + middleFrameText.getText());

        // STEP 7: Switch back to top frame
        // Going back up one level again
        driver.switchTo().parentFrame();
        System.out.println("Switched back to top frame");

        // STEP 8: Switch to the right frame
        // Another child frame of the top frame
        driver.switchTo().frame("frame-right");
        System.out.println("Switched to right frame");

        // STEP 9: Get and verify the text in the right frame
        WebElement rightFrameText = driver.findElement(By.tagName("body"));
        assert rightFrameText.getText().equals("RIGHT") : 
            "Expected 'RIGHT', but got: " + rightFrameText.getText();
        System.out.println("Right frame text verified: " + rightFrameText.getText());

        // STEP 10: Switch back to top frame, then to default content
        // parentFrame() takes us to top frame, defaultContent() takes us all the way to main page
        driver.switchTo().parentFrame();  // Back to top frame
        driver.switchTo().defaultContent();  // All the way back to main page
        System.out.println("Switched back to default content");

        // STEP 11: Switch to the bottom frame (directly under default content)
        // This frame is a sibling of frame-top, not a child
        driver.switchTo().frame("frame-bottom");
        System.out.println("Switched to bottom frame");

        // STEP 12: Get and verify the text in the bottom frame
        WebElement bottomFrameText = driver.findElement(By.tagName("body"));
        assert bottomFrameText.getText().equals("BOTTOM") : 
            "Expected 'BOTTOM', but got: " + bottomFrameText.getText();
        System.out.println("Bottom frame text verified: " + bottomFrameText.getText());

        System.out.println("\nNested frames test completed successfully!");

        driver.quit();
    }
}

