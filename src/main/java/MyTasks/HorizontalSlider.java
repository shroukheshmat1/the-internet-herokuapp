package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Scenario: Move the horizontal slider back and forth via keyboard events.
 * Steps:
 * 1. Increment the slider until max, validating each change equals one step.
 * 2. Decrement back to min to ensure boundary checks behave as expected.
 */
public class HorizontalSlider {

    // Move slider RIGHT by one step
    public static void moveRight(WebElement slider) {
        slider.sendKeys(Keys.ARROW_RIGHT);
    }

    // Move slider LEFT by one step
    public static void moveLeft(WebElement slider) {
        slider.sendKeys(Keys.ARROW_LEFT);
    }

    // Check if the slider value increased/decreased correctly
    public static void verifyChange(double oldValue, double newValue, double step, double min, double max) {
        if (oldValue < max && oldValue > min) { // Inside range
            if (Math.abs(newValue - (oldValue + step)) < 0.001) {
                System.out.println("Increased correctly: " + oldValue + " → " + newValue);
            } else if (Math.abs(newValue - (oldValue - step)) < 0.001) {
                System.out.println("Decreased correctly: " + oldValue + " → " + newValue);
            } else {
                System.out.println("Error: Value didn’t change correctly! Old: " + oldValue + ", New: " + newValue);
            }
        } else if (oldValue >= max && newValue != oldValue) {
            System.out.println("Error: Value changed after reaching max!");
        } else if (oldValue <= min && newValue != oldValue) {
            System.out.println("Error: Value changed after reaching min!");
        }
    }

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        WebElement slider = driver.findElement(By.tagName("input"));
        WebElement sliderVal = driver.findElement(By.id("range"));

        slider.click(); // focus slider

        double step = 0.5;
        double min = 0.0;
        double max = 5.0;
        double oldValue, newValue;

        // Move RIGHT until max
        for (int i = 0; i < 15; i++) {
            oldValue = Double.parseDouble(sliderVal.getText());
            moveRight(slider);
            newValue = Double.parseDouble(sliderVal.getText());
            verifyChange(oldValue, newValue, step, min, max);
        }

        // Move LEFT until min
        for (int i = 0; i < 15; i++) {
            oldValue = Double.parseDouble(sliderVal.getText());
            moveLeft(slider);
            newValue = Double.parseDouble(sliderVal.getText());
            verifyChange(oldValue, newValue, step, min, max);
        }

        driver.quit();
    }
}
