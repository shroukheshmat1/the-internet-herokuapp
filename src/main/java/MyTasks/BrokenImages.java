package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenImages {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/broken_images");

        List<WebElement> images = driver.findElements(By.xpath("//div[@class = 'example']//img"));
        int brokenCount = 0;


        for (int i = 0; i < images.size(); i++) {
            WebElement img = images.get(i);
            String imageUrl = img.getAttribute("src");

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();

                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    System.out.println("Broken image found at index " + i + " : " + imageUrl);
                    brokenCount++;
                } else {
                    System.out.println("Image OK at index " + i + " : " + imageUrl);
                }

            } catch (Exception e) {
                System.out.println("Error checking image at index " + i + " : " + imageUrl);
                brokenCount++;
            }
        }

        System.out.println("Total broken images: " + brokenCount);

        driver.quit();
    }
}
