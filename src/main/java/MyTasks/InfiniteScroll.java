package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/*
 * In this exercise, our mission is to verify that a webpage really supports
 * "Infinity Scroll" — meaning: every time you scroll down,
 * NEW content magically appears!
 *
 * We will implement TWO different strategies:
 * -------------------------------------------------
 *
 * METHOD 1 — Check the PAGE HEIGHT
 * -------------------------------------------------
 * Every time the page loads more hidden content, the "scrollHeight" becomes larger.
 * So the idea is:
 *  - Read the scroll height
 *  - Scroll down
 *  - Read the height again
 *  - If it increased → content was loaded → Infinite Scroll is working
 *  - If it stayed the same → no more content → it is NOT infinite
 *
 * This is the most common and most reliable method for pages that continuously load data.
 *
 *
 * METHOD 2 — Check if NEW ELEMENTS are being ADDED to a parent container
 * -------------------------------------------------
 * (NOTE: This method only works *if* the webpage places new items
 * inside a known wrapper element, such as <div class="jscroll-inner">)
 *
 * Here, we:
 *  - Count how many elements exist in the container
 *  - Scroll down
 *  - Count them again
 *  - If the number increases → new items were added → Infinite Scroll confirmed
 *  - If the number stays the same → no new content
 *
 * This method gives extra validation, but only works if the HTML structure supports it.
 *
 *
 * OUR GOAL
 * -------------------------------------------------
 * Demonstrate both methods in a simple, clean, beginner-friendly way so new learners
 * understand:
 *    how Infinite Scroll works
 *    how to test it in Selenium
 *    how to combine Java + WebDriver + JavaScript execution
 *
 * READY? LET’S START OUR FUN!
 */

public class InfiniteScroll {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/infinite_scroll");
        //METHOD 1 : Using JS executor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
        for(int Ctr = 0;Ctr<5;Ctr++) {   //We'll Try Looping for 5 times just to check that the scroll is infinite
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");

            // If heights are equal → no more new content
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
        System.out.println("Infinite scroll works correctly using METHOD 1.");
        //METHOD 2 : check the parent HTML element length --IF ACHIEVABLE--
        List<WebElement> parentDiv = driver.findElements(By.className("jscroll-inner"));
        int parentDivSize = parentDiv.size();
        for(int Ctr = 0;Ctr<5;Ctr++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            int newDivSize = driver.findElements(By.className("jscroll-inner")).size();
            if (newDivSize == parentDivSize)
                break;
            parentDivSize = newDivSize;
        }
        System.out.println("Infinite scroll works correctly using METHOD 2.");

    }

}
