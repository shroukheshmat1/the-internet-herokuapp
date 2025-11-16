package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ============================ 📘 FILE UPLOAD AUTOMATION IN SELENIUM =============================
 *
 * GOAL:
 * Automate uploading files to a webpage, including:
 *   ✅ Standard <input type='file'> uploads
 *   ✅ Drag-and-drop file uploads
 *
 * WHY THIS IS SPECIAL:
 * - Regular input fields can be automated easily with sendKeys().
 * - Drag-and-drop areas require simulating the browser's drag event,
 *   which Selenium cannot do natively. For this, we inject JavaScript.
 *
 * ============================================================================================
 */
public class FileUpload {

    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/upload"); // Open demo upload page

        // 2️⃣ Specify file path to upload
        Path filePath = Paths.get(System.getProperty("user.dir"), "sample.txt");
        File file = filePath.toFile();

        // 3️⃣ If file doesn’t exist, create one (for demo purposes)
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("Created test file: " + file.getAbsolutePath());
        }

        // 4️⃣ Locate the standard upload input element (<input type='file'>)
        WebElement uploadInput = driver.findElement(By.id("file-upload"));

        // 5️⃣ Send the file path directly to the input field
        // ✅ Selenium will simulate a user choosing this file
        uploadInput.sendKeys(file.getAbsolutePath());

        // 6️⃣ Click the upload button to submit
        driver.findElement(By.id("file-submit")).click();

        // 7️⃣ Verify success message
        WebElement uploadedMsg = driver.findElement(By.tagName("h3"));
        assert uploadedMsg.isDisplayed();
        assert uploadedMsg.getText().equals("sample.txt") :
                "The file name displayed is different than the one uploaded!";
        System.out.println("Upload message: " + uploadedMsg.getText());

        // ======================================================
        // 8️⃣ Drag-and-Drop Upload
        // ======================================================
        driver.navigate().back(); // Go back to the page with the drag-drop area

        // Locate the drop area element
        WebElement dropArea = driver.findElement(By.id("drag-drop-upload"));

        /*
         * WHY WE USE JAVASCRIPT:
         * Selenium cannot natively simulate drag-and-drop file uploads,
         * because browsers restrict drag-and-drop events for security.
         *
         * HOW THIS JS WORKS:
         * 1. Create a hidden <input type='file'> element in the page.
         * 2. Create a DataTransfer object to mimic files being dragged.
         * 3. Add a new File to the DataTransfer (we provide file content and name).
         * 4. Create a 'drop' event and attach the DataTransfer object to it.
         * 5. Dispatch the event on the drop area element.
         * 6. Remove the temporary input element.
         *
         * Conceptually: we are tricking the page into thinking the user
         * dragged-and-dropped a real file, but Selenium passes it via JS.
         */
        String jsDropFile =
                "var target = arguments[0]," +
                        "    fileInput = document.createElement('INPUT');" +
                        "fileInput.type = 'file';" +
                        "fileInput.style.display = 'none';" +
                        "document.body.appendChild(fileInput);" +
                        "var dt = new DataTransfer();" +
                        "var file = new File([arguments[1]], arguments[2], {type: 'text/plain'});" +
                        "dt.items.add(file);" +
                        "var event = new DragEvent('drop', { dataTransfer: dt });" +
                        "target.dispatchEvent(event);" +
                        "document.body.removeChild(fileInput);";

        // Execute the JS code to simulate drag & drop upload
        ((JavascriptExecutor) driver).executeScript(jsDropFile, dropArea, "File content here", "sample.txt");

        // ✅ Close browser
        driver.quit();
    }
}
