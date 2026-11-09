package Day31;

import Utilities.WebLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicDropDown {
    public static void main(String[] args) {
        WebDriver driver = WebLoader.getDriver();

        driver.findElement(By.xpath("//input[@id='Wikipedia1_wikipedia-search-input']"))
                .sendKeys("selenium");
        driver.findElement(By.xpath("//input[@type='submit']"))
                .click();

        driver.findElement(By.xpath("//div[@id='Wikipedia1_wikipedia-search-results']//a[.='Selenium']"))
                .click();

        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

    }
}