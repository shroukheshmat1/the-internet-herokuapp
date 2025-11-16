package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/*
 * Test secure file download with authentication
 * Download a file that requires authentication and verify it was downloaded successfully
 */

public class SecureFileDownload {

    public static Path createDownloadDirectory() throws Exception {
        Path downloadDir = Paths.get(System.getProperty("user.dir"), "downloads");
        Files.createDirectories(downloadDir);
        return downloadDir;
    }

    public static ChromeOptions configureChromeOptions(String downloadPath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("safebrowsing.enabled", true);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    public static void waitForDownloadToComplete(WebDriver driver, Path downloadDir, String expectedFileName, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        wait.until((ExpectedCondition<Boolean>) d -> {
            try {
                File folder = downloadDir.toFile();
                File[] files = folder.listFiles();

                if (files == null) {
                    return false;
                }

                for (File f : files) {
                    if (f.isFile() && f.getName().equals(expectedFileName)) {
                        return true;
                    }
                }

                return false;

            } catch (Exception e) {
                return false;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        // Create download directory
        Path downloadDir = createDownloadDirectory();

        // Configure ChromeOptions for downloading
        String downloadPath = downloadDir.toAbsolutePath().toString();
        ChromeOptions options = configureChromeOptions(downloadPath);

        // Launch Chrome with custom settings
        WebDriver driver = new ChromeDriver(options);
        
        // Navigate to secure download page with authentication
        // Format: http://username:password@domain.com/path
        driver.get("https://admin:admin@the-internet.herokuapp.com/download_secure");

        // Find the download link
        WebElement downloadLink = driver.findElement(By.cssSelector(".example a"));
        String downloadUrl = downloadLink.getAttribute("href");
        
        // Extract filename from URL
        String expectedFileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        System.out.println("Expected filename: " + expectedFileName);

        // Count files before download
        File folder = downloadDir.toFile();
        File[] filesBefore = folder.listFiles();
        int fileCountBefore = (filesBefore == null) ? 0 : filesBefore.length;

        // Click the download link
        downloadLink.click();
        System.out.println("Clicked download link");

        // Wait for download to complete
        System.out.println("Waiting for download to complete...");
        waitForDownloadToComplete(driver, downloadDir, expectedFileName, 20);
        System.out.println("Download completed!");

        // Verify the file exists and has content
        File downloadedFile = new File(downloadDir.toFile(), expectedFileName);
        
        if (!downloadedFile.exists()) {
            throw new AssertionError("Downloaded file does not exist: " + expectedFileName);
        }
        
        long fileSize = downloadedFile.length();
        if (fileSize == 0) {
            throw new AssertionError("Downloaded file is empty: " + expectedFileName);
        }
        
        System.out.println("File downloaded successfully: " + expectedFileName + " | Size = " + fileSize + " bytes");

        // Verify a new file was added
        File[] filesAfter = folder.listFiles();
        int fileCountAfter = (filesAfter == null) ? 0 : filesAfter.length;
        
        assert fileCountAfter > fileCountBefore : "No new file was added to the download folder!";

        System.out.println("\nSecure file download test completed successfully!");

        driver.quit();
    }
}

