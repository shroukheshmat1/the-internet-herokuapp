package MyTasks;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;

/**
 * ============================ 📘 FILE DOWNLOAD AUTOMATION IN SELENIUM =============================
 *
 * GOAL:
 * Automate downloading all files from the "File Download" page and verify:
 *   ✅ All files are downloaded successfully.
 *   ✅ None of them are empty.
 *
 * WHY THIS IS SPECIAL:
 * Usually, we just click a download link — but here we’re checking that:
 *   - Downloads actually finish.
 *   - The files exist in a specific folder.
 *   - Each file has content (not empty).
 *
 * MAIN STEPS:
 * --------------------------------------------------------------------------------------------
 * 1️⃣ Create a custom "downloads" folder inside the project directory.
 * 2️⃣ Tell Chrome to use THAT folder instead of its default "Downloads".
 * 3️⃣ Click every download link on the page.
 * 4️⃣ Wait until ALL downloads finish.
 * 5️⃣ Verify each downloaded file has data inside it.
 *
 * ============================================================================================
 */
public class FileDownload {

    /**
     * STEP 1️⃣: Create a folder for storing downloaded files.
     *
     * WHY:
     * We don’t want the files to go into the browser’s default "Downloads" folder,
     * because that makes it hard to track them in an automated script.
     *
     * HOW:
     * - System.getProperty("user.dir") gives the path to our current project folder.
     * - Paths.get(..., "downloads") creates a "downloads" subfolder path inside it.
     * - Files.createDirectories() creates that folder if it doesn’t exist.
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
        prefs.put("download.default_directory", downloadPath);  // ✅ Set custom download folder
        prefs.put("download.prompt_for_download", false);       // ✅ Disable "Save as..." dialog
        prefs.put("profile.default_content_settings.popups", 0); // ✅ Disable download popups
        prefs.put("safebrowsing.enabled", true);                // ✅ Allow downloads without warnings

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);          // Apply preferences to Chrome
        return options;
    }

    /**
     * STEP 4️⃣: Wait until all downloads are finished.
     *
     * WHY:
     * After clicking multiple download links, we must wait until the files
     * actually appear in the folder before proceeding — otherwise, verification
     * might start before downloads are done.
     *
     * HOW:
     * - We use WebDriverWait (explicit wait) to repeatedly check if all files exist.
     * - We count how many files are in the folder every second.
     * - Once the count >= number of links clicked, the condition passes.
     */
    public static void waitForAllDownloadsToComplete(WebDriver driver, Path downloadDir, int expectedFilesCount, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        wait.until((ExpectedCondition<Boolean>) d -> {
            try {
                File folder = downloadDir.toFile();
                File[] files = folder.listFiles();

                if (files == null) {
                    return false; // Folder empty or not accessible yet
                }

                int count = 0;
                for (File f : files) {
                    if (f.isFile()) {
                        count++;
                    }
                }

                // ✅ Condition becomes true when all expected files are downloaded
                return count >= expectedFilesCount;

            } catch (Exception e) {
                return false; // Ignore errors, retry until timeout
            }
        });
    }

    /**
     * STEP 5️⃣: Collect all downloaded files into a List.
     *
     * WHY:
     * To loop through them later for validation (like checking if they’re empty or not).
     *
     * HOW:
     * - Convert downloadDir (Path) to File.
     * - Use listFiles() to get all files inside.
     * - Add only real files (ignore folders) to a List<File>.
     */
    public static List<File> getDownloadedFiles(Path downloadDir) throws Exception {
        List<File> downloadedFiles = new ArrayList<>();

        File folder = downloadDir.toFile();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    downloadedFiles.add(f);
                }
            }
        }

        return downloadedFiles;
    }

    /**
     * STEP 6️⃣: Verify that all files have content.
     *
     * WHY:
     * Sometimes, the download may succeed but the file might be empty due to
     * network failure or server issues.
     *
     * HOW:
     * - Loop through all downloaded files.
     * - Check if file.length() == 0 (which means empty).
     * - Print file info and size for debugging and visibility.
     */
    public static boolean verifyFilesNotEmpty(List<File> files) {
        boolean allValid = true;

        for (File file : files) {
            if (file.length() == 0) {
                System.out.println("❌ This file is empty: " + file.getName());
                allValid = false;
            } else {
                System.out.println("✅ File contains data: " + file.getName() + " | Size = " + file.length() + " bytes");
            }
        }

        return allValid;
    }

    /**
     * MAIN METHOD 🧠
     * This is the entry point of our program where everything connects.
     */
    public static void main(String[] args) throws Exception {

        // 1️⃣ Create download directory
        Path downloadDir = createDownloadDirectory();

        // 2️⃣ Configure ChromeOptions for downloading
        String downloadPath = downloadDir.toAbsolutePath().toString();
        ChromeOptions options = configureChromeOptions(downloadPath);

        // 3️⃣ Launch Chrome with our custom settings
        WebDriver driver = new ChromeDriver(options);

        // 4️⃣ Open the test page
        driver.get("https://the-internet.herokuapp.com/download");

        // 5️⃣ Find all download links
        List<WebElement> allDownloads = driver.findElements(By.xpath("//div[@class='example']/a"));
        int expectedFilesCount = allDownloads.size();
        System.out.println("Found " + expectedFilesCount + " downloadable files.");

        // 6️⃣ Click on each link to start downloading
        for (WebElement element : allDownloads) {
            element.click();
        }

        // 7️⃣ Wait for all downloads to complete
        waitForAllDownloadsToComplete(driver, downloadDir, expectedFilesCount, 20);

        // 8️⃣ Retrieve the list of downloaded files
        List<File> downloadedFiles = getDownloadedFiles(downloadDir);

        // 9️⃣ Validate that all files contain data
        boolean allFilesValid = verifyFilesNotEmpty(downloadedFiles);

        // 🔟 Final assertion
        if (!allFilesValid) {
            throw new AssertionError("Some files were empty or failed to download correctly.");
        } else {
            System.out.println("🎉 All files were downloaded successfully and contain data!");
        }

        // ✅ Close browser
        driver.quit();
    }
}
