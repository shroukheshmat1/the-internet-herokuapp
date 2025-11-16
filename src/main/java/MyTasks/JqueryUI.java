package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/*
 * ============================ 🎯 JQUERY UI MENU AUTOMATION =============================
 *
 * TEST GOAL:
 * Navigate through a JQuery UI nested menu, click on a download option (PDF/CSV/Excel),
 * and verify that the file is actually downloaded successfully with content!
 *
 * The menu structure is: Enabled → Downloads → PDF/CSV/Excel
 *
 * WHY THIS IS INTERESTING:
 * --------------------------------------------------------------------------------------------
 * JQuery UI menus are a bit "tricky" because they work differently than regular HTML menus!
 *
 * 1️⃣ Mouse Hover is Required:
 *    - Submenus are HIDDEN by default (display: none in CSS)
 *    - They only appear when you HOVER over the parent menu item
 *    - You can't just click - you MUST hover first to reveal hidden items
 *    - This is why we use Actions.moveToElement() - it simulates mouse movement
 *
 * 2️⃣ Synchronization Challenge:
 *    - Elements exist in the HTML (you can find them with findElement)
 *    - BUT they're not visible until you hover over the parent
 *    - JQuery controls visibility dynamically (not the HTML itself)
 *    - So we need to WAIT for elements to become visible before interacting
 *
 * 3️⃣ The "Visibility vs Presence" Puzzle:
 *    - An element can be PRESENT in the DOM (exists in HTML)
 *    - But NOT VISIBLE (can't see it, can't click it)
 *    - Only after hovering, JQuery makes it visible
 *    - This is why we use ExpectedConditions.visibilityOfElementLocated()
 *
 * 4️⃣ Download Verification:
 *    - We don't just click and hope for the best!
 *    - We verify the link is actually downloadable (check href attribute)
 *    - We configure Chrome to download to OUR folder (not browser's default)
 *    - We wait for the download to complete
 *    - We verify the file exists AND has content (not empty)
 *
 * WHAT WE'LL DO:
 * --------------------------------------------------------------------------------------------
 * Step 1: Create a custom download folder and configure Chrome to use it
 * Step 2: Navigate to the JQuery UI Menu page
 * Step 3: Hover over "Enabled" menu item (this reveals its submenu)
 * Step 4: Wait for "Downloads" to become visible, then hover over it
 * Step 5: Wait for PDF/CSV/Excel options to become visible
 * Step 6: Verify the download link is actually downloadable (check href)
 * Step 7: Click on the download option
 * Step 8: Wait for the download to complete
 * Step 9: Verify the file was downloaded and has content
 *
 * FUN FACT:
 * The "Disabled" menu item is actually disabled (you can't interact with it),
 * but "Enabled" is our friend - it opens up a whole world of submenus! 🎉
 *
 * ============================================================================================
 */

public class JqueryUI {

    /**
     * STEP 1️⃣: Create a folder for storing downloaded files.
     *
     * WHY:
     * We don't want the files to go into the browser's default "Downloads" folder,
     * because that makes it hard to track them in an automated script.
     *
     * HOW:
     * - System.getProperty("user.dir") gives the path to our current project folder.
     * - Paths.get(..., "downloads") creates a "downloads" subfolder path inside it.
     * - Files.createDirectories() creates that folder if it doesn't exist.
     */
    public static Path createDownloadDirectory() throws Exception {
        Path downloadDir = Paths.get(System.getProperty("user.dir"), "downloads");
        Files.createDirectories(downloadDir); // Will NOT throw error if already exists
        return downloadDir;
    }

    /**
     * STEP 2️⃣: Configure Chrome to automatically download files into our custom folder.
     *
     * WHY:
     * By default, Chrome asks the user "Where do you want to save this file?"
     * But we can override that behavior using ChromeOptions to automate downloads silently.
     *
     * HOW:
     * - We create a "prefs" map to hold Chrome's internal preferences.
     * - We disable popups, prompts, and force a fixed download directory.
     */
    public static ChromeOptions configureChromeOptions(String downloadPath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);  // Set custom download folder
        prefs.put("download.prompt_for_download", false);       // Disable "Save as..." dialog
        prefs.put("profile.default_content_settings.popups", 0); // Disable download popups
        prefs.put("safebrowsing.enabled", true);                // Allow downloads without warnings

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);          // Apply preferences to Chrome
        return options;
    }

    /**
     * STEP 8️⃣: Wait until the download is finished.
     *
     * WHY:
     * After clicking a download link, we must wait until the file
     * actually appears in the folder before proceeding — otherwise, verification
     * might start before the download is done.
     *
     * HOW:
     * - We use WebDriverWait (explicit wait) to repeatedly check if the file exists.
     * - We look for a file with the expected name in the download directory.
     * - Once the file exists, the condition passes.
     */
    public static void waitForDownloadToComplete(WebDriver driver, Path downloadDir, String expectedFileName, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        wait.until((ExpectedCondition<Boolean>) d -> {
            try {
                File folder = downloadDir.toFile();
                File[] files = folder.listFiles();

                if (files == null) {
                    return false; // Folder empty or not accessible yet
                }

                // Check if the expected file exists
                for (File f : files) {
                    if (f.isFile() && f.getName().equals(expectedFileName)) {
                        return true; // File found!
                    }
                }

                return false; // File not found yet, keep waiting

            } catch (Exception e) {
                return false; // Ignore errors, retry until timeout
            }
        });
    }

    public static void main(String[] args) throws Exception {

        // 1️⃣ Create download directory
        Path downloadDir = createDownloadDirectory();

        // 2️⃣ Configure ChromeOptions for downloading
        String downloadPath = downloadDir.toAbsolutePath().toString();
        ChromeOptions options = configureChromeOptions(downloadPath);

        // 3️⃣ Launch Chrome with our custom settings
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("JQuery UI Menus")).click();

        // Create Actions object - this is our "mouse controller"
        // It lets us simulate mouse movements like hover, click, drag, etc.
        Actions actions = new Actions(driver);

        // Create explicit wait - this helps us wait for elements to become ready
        // We'll use this to wait for submenus to appear after hovering
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ====================================================================================
        // STEP 4: Find and hover over "Enabled" menu item
        // ====================================================================================
        // WHY: "Enabled" is the parent menu. When we hover over it, JQuery will reveal
        //      its hidden submenu which contains "Downloads" and "Back to JQuery UI"
        //
        // HOW: We find it by its ID (ui-id-3) and use moveToElement() to hover

        WebElement enabledMenuItem = driver.findElement(By.id("ui-id-3"));
        actions.moveToElement(enabledMenuItem).perform();
        System.out.println("Hovered over 'Enabled' menu item");

        // ====================================================================================
        // STEP 5: Wait for "Downloads" to appear, then hover over it
        // ====================================================================================
        // WHY: "Downloads" is nested under "Enabled". It's hidden until we hover over "Enabled"
        //      We need to WAIT for it to become visible before we can interact with it
        //
        // HOW: We use wait.until() with ExpectedConditions.visibilityOfElementLocated()
        //      This will keep checking every 500ms until the element becomes visible
        //      (or timeout after 10 seconds if something goes wrong)

        WebElement downloadsMenuItem = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("ui-id-4"))
        );
        System.out.println("'Downloads' submenu is now visible!");

        // Now hover over "Downloads" to reveal its nested submenu (PDF/CSV/Excel)
        actions.moveToElement(downloadsMenuItem).perform();
        System.out.println("Hovered over 'Downloads' submenu");

        // ====================================================================================
        // STEP 6: Wait for download options to appear, verify it's downloadable, then click
        // ====================================================================================
        // WHY: PDF/CSV/Excel options are nested under "Downloads"
        //      They only become visible after hovering over "Downloads"
        //
        // HOW: We wait for the PDF option (ui-id-5) to become clickable
        //      Then we verify it has a valid download link (href attribute)
        //      Then we click it!
        //
        // NOTE: You can change this to click CSV (ui-id-6) or Excel (ui-id-7) instead

        WebElement pdfOption = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("ui-id-5"))
        );
        System.out.println("PDF option is now clickable!");

        // Verify that this is actually a download link
        // We find the <a> tag inside the menu item and check its href attribute
        WebElement pdfLink = pdfOption.findElement(By.tagName("a"));
        String downloadUrl = pdfLink.getAttribute("href");
        
        if (downloadUrl == null || downloadUrl.isEmpty()) {
            throw new AssertionError("The PDF link does not have a valid href attribute!");
        }
        
        // Extract the expected filename from the URL
        // Example: "/download/jqueryui/menu/menu.pdf" -> "menu.pdf"
        String expectedFileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        System.out.println("Verified download link: " + downloadUrl);
        System.out.println("Expected filename: " + expectedFileName);

        // Count files before download to verify a new file appears
        File folder = downloadDir.toFile();
        File[] filesBefore = folder.listFiles();
        int fileCountBefore = (filesBefore == null) ? 0 : filesBefore.length;

        // Click on PDF to start the download
        pdfLink.click();
        System.out.println("Clicked on 'PDF' download link!");

        // ====================================================================================
        // STEP 7: Wait for the download to complete
        // ====================================================================================
        // WHY: Downloads take time! We need to wait until the file actually appears
        //      in our download folder before we can verify it.
        //
        // HOW: We use waitForDownloadToComplete() which checks the folder repeatedly
        //      until the expected file appears (or timeout after 20 seconds)

        System.out.println("Waiting for download to complete...");
        waitForDownloadToComplete(driver, downloadDir, expectedFileName, 20);
        System.out.println("Download completed! File found: " + expectedFileName);

        // ====================================================================================
        // STEP 8: Verify the downloaded file exists and has content
        // ====================================================================================
        // WHY: We don't just trust that clicking worked - we verify!
        //      - The file should exist in our download folder
        //      - The file should have content (not be empty)
        //
        // HOW: We look for the file in the download directory and check its size

        File downloadedFile = new File(downloadDir.toFile(), expectedFileName);
        
        if (!downloadedFile.exists()) {
            throw new AssertionError("Downloaded file does not exist: " + expectedFileName);
        }
        
        System.out.println("File exists: " + downloadedFile.getAbsolutePath());
        
        // Verify the file has content (not empty)
        long fileSize = downloadedFile.length();
        if (fileSize == 0) {
            throw new AssertionError("Downloaded file is empty: " + expectedFileName);
        }
        
        System.out.println("File contains data: " + expectedFileName + " | Size = " + fileSize + " bytes");
        
        // Verify that a new file was actually added (not just an existing file)
        File[] filesAfter = folder.listFiles();
        int fileCountAfter = (filesAfter == null) ? 0 : filesAfter.length;
        
        if (fileCountAfter <= fileCountBefore) {
            throw new AssertionError("No new file was added to the download folder!");
        }
        
        System.out.println("\nTest completed successfully!");
        System.out.println("\nKEY TAKEAWAYS:");
        System.out.println("   • JQuery UI menus need mouse hover to reveal submenus");
        System.out.println("   • Always wait for elements to become visible/clickable");
        System.out.println("   • Use Actions.moveToElement() for hover operations");
        System.out.println("   • Verify download links before clicking");
        System.out.println("   • Always verify downloads completed successfully with content");

        // Close the browser
        driver.quit();
    }
}
