package Day33;

import Utilities.WebLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DynamicWebTable {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = WebLoader.getDriver();

        while (true) {
            List<WebElement> checkboxes = driver.findElements(By.xpath("//table[@id='productTable']//tbody//input[@type='checkbox']"));

            for (WebElement checkbox : checkboxes) {
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
            }

            List<WebElement> pageLinks = driver.findElements(By.xpath("//ul[@id='pagination']//a"));
            if (pageLinks.isEmpty()) {
                break;
            }

            String activePageText = driver.findElement(By.xpath("//ul[@id='pagination']//a[@class='active']")).getText();
            int activePage = Integer.parseInt(activePageText.trim());

            if (activePage >= pageLinks.size()) {
                break;
            }

            driver.findElement(By.xpath("//ul[@id='pagination']//a[not(contains(@class, 'active'))][1]")).click();
            Thread.sleep(2000);
        }

        driver.quit();
    }
}
