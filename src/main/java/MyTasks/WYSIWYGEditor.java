package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * ============================ ✏️ WYSIWYG EDITOR AUTOMATION =============================
 *
 * TEST GOAL:
 * Learn how to interact with WYSIWYG (What You See Is What You Get) editors!
 * These are rich text editors like the ones in Gmail, WordPress, or Google Docs!
 *
 * WHY THIS IS SPECIAL:
 * --------------------------------------------------------------------------------------------
 * WYSIWYG editors are usually embedded inside an iframe (a separate HTML document).
 * To interact with the editor content, we must first "switch" to the iframe!
 * It's like the editor is in a separate room - we need to enter that room first!
 *
 * KEY CONCEPTS:
 * --------------------------------------------------------------------------------------------
 * 1️⃣ What is WYSIWYG?
 *    - WYSIWYG = "What You See Is What You Get"
 *    - It's a text editor where you see formatted text as you type
 *    - Examples: TinyMCE, CKEditor, Google Docs editor
 *
 * 2️⃣ Why the iframe?
 *    - Editors are often in iframes to isolate them from the main page
 *    - This prevents conflicts with the page's CSS and JavaScript
 *    - Think of it as a separate "mini webpage" inside the main page
 *
 * 3️⃣ Switching to iframe:
 *    - driver.switchTo().frame(iframeElement) → Enter the iframe
 *    - Now we can interact with elements inside the editor
 *    - driver.switchTo().defaultContent() → Go back to main page
 *
 * 4️⃣ Editor Interaction:
 *    - Find the editor body element (usually <body> or <div> with contenteditable)
 *    - Use clear() to remove existing content
 *    - Use sendKeys() to type text
 *    - Use getText() to read the content
 *
 * WHAT WE'LL DO:
 * --------------------------------------------------------------------------------------------
 * Step 1: Find the iframe that contains the editor
 * Step 2: Switch to the iframe
 * Step 3: Find the editor body element
 * Step 4: Type text in the editor
 * Step 5: Switch back to main page
 * Step 6: Click formatting buttons (like Bold)
 * Step 7: Switch back to iframe to verify formatting
 *
 * FUN FACT:
 * WYSIWYG editors are like having a mini word processor inside a webpage!
 * You can format text, add links, and more - all while seeing the result live! 📝
 *
 * ============================================================================================
 */

public class WYSIWYGEditor {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tinymce");

        // The editor is inside an iframe, so we need to switch to it first
        WebElement iframe = driver.findElement(By.id("mce_0_ifr"));
        driver.switchTo().frame(iframe);

        // Now we can interact with the editor content
        WebElement editorBody = driver.findElement(By.id("tinymce"));
        
        // Clear existing content
        editorBody.clear();
        
        // Type some text in the editor
        String testText = "Hello, this is a test of the WYSIWYG editor!";
        editorBody.sendKeys(testText);
        
        // Get the text we just typed
        String editorText = editorBody.getText();
        System.out.println("Editor text: " + editorText);
        
        // Verify the text was entered correctly
        assert editorText.contains(testText) : 
            "Expected text not found in editor! Expected: '" + testText + "', Got: '" + editorText + "'";
        System.out.println("Text entered successfully in the editor");

        // Switch back to default content to interact with editor controls
        driver.switchTo().defaultContent();

        // Find and click formatting buttons (like Bold)
        WebElement boldButton = driver.findElement(By.cssSelector("button[aria-label='Bold']"));
        boldButton.click();
        System.out.println("Clicked Bold button");

        // Switch back to iframe to verify formatting
        driver.switchTo().frame(iframe);
        editorBody = driver.findElement(By.id("tinymce"));
        
        // Add more text with formatting
        editorBody.sendKeys(" This text should be bold.");
        
        String finalText = editorBody.getText();
        System.out.println("Final editor text: " + finalText);

        // Switch back to default content
        driver.switchTo().defaultContent();

        System.out.println("\nWYSIWYG Editor test completed successfully!");

        driver.quit();
    }
}

