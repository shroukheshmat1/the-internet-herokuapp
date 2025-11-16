package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Test WYSIWYG (What You See Is What You Get) Editor
 * WYSIWYG editors are rich text editors that allow formatting text
 * We need to switch to the iframe to interact with the editor content
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

