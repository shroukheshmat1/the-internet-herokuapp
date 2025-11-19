package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Scenario: Allow the Geolocation API prompt and verify coordinates display.
 * Steps:
 * 1. Start Chrome with geolocation permission granted.
 * 2. Click “Where am I?” then assert latitude, longitude, and map link are surfaced.
 */
public class Geolocation {
    public static void main(String[] args) {
        Map<String,Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs",prefs);
        WebDriver driver  = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/geolocation");
        driver.findElement(By.xpath("//button[text() = 'Where am I?']")).click();
        assert driver.findElement(By.id("lat-value")).isDisplayed();
        assert driver.findElement(By.id("long-value")).isDisplayed();
        assert driver.findElement(By.id("map-link")).isEnabled();

        driver.quit();
    }
}
