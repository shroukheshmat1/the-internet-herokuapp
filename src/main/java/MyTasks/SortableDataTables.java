package MyTasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/*
 * Test sortable data tables
 * Verify that table data can be read and tables can be sorted by clicking column headers
 */

public class SortableDataTables {

    public static void printTableData(WebDriver driver, int tableIndex) {
        // Find the table (there are 2 tables on the page)
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        WebElement table = tables.get(tableIndex);

        System.out.println("\n=== Table " + (tableIndex + 1) + " ===");

        // Get all rows in the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Print header row
        WebElement headerRow = rows.get(0);
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));
        System.out.print("Headers: ");
        for (WebElement header : headers) {
            System.out.print(header.getText() + " | ");
        }
        System.out.println();

        // Print data rows
        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            System.out.print("Row " + i + ": ");
            for (WebElement cell : cells) {
                System.out.print(cell.getText() + " | ");
            }
            System.out.println();
        }
    }

    public static void testSorting(WebDriver driver, int tableIndex, int columnIndex) {
        // Find the table
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        WebElement table = tables.get(tableIndex);

        // Find the column header and click it to sort
        WebElement headerRow = table.findElement(By.tagName("tr"));
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));
        WebElement columnHeader = headers.get(columnIndex);

        String columnName = columnHeader.getText();
        System.out.println("Clicking column header to sort: " + columnName);
        columnHeader.click();

        // Get the first data row after sorting
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        if (rows.size() > 1) {
            List<WebElement> firstRowCells = rows.get(1).findElements(By.tagName("td"));
            System.out.println("First row after sorting: " + firstRowCells.get(columnIndex).getText());
        }
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tables");

        // Print data from both tables
        printTableData(driver, 0);
        printTableData(driver, 1);

        // Test sorting on first table
        System.out.println("\n=== Testing Sorting ===");
        testSorting(driver, 0, 0); // Sort by first column (Last Name)
        testSorting(driver, 0, 1); // Sort by second column (First Name)

        // Verify specific data in the table
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        WebElement firstTable = tables.get(0);
        List<WebElement> rows = firstTable.findElements(By.tagName("tr"));

        // Check if we can find a specific value
        boolean foundSmith = false;
        for (WebElement row : rows) {
            if (row.getText().contains("Smith")) {
                foundSmith = true;
                break;
            }
        }

        assert foundSmith : "Could not find 'Smith' in the table!";
        System.out.println("Verified: Found 'Smith' in the table");

        System.out.println("\nSortable data tables test completed successfully!");

        driver.quit();
    }
}

