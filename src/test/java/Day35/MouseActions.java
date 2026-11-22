package Day35;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MouseActions {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(20));
            driver.get("https://demo.opencart.com/");

            Actions actions = new Actions(driver);

            // Mouse hover on Desktops
            WebElement desktops = driver.findElement(By.xpath("//a[normalize-space()='Desktops']"));
            actions.moveToElement(desktops).perform();

            // Click the Mac submenu after it becomes visible
            WebElement mac = driver.findElement(By.xpath("//a[normalize-space()='Mac (1)']"));
            actions.moveToElement(mac).click().perform();

            // Right-click on Desktops after returning to the home page
            driver.navigate().back();
            desktops = driver.findElement(By.xpath("//a[normalize-space()='Desktops']"));
            actions.contextClick(desktops).perform();

            // Optional mouse actions:
            // actions.doubleClick(element).perform();
            // actions.dragAndDrop(source, target).perform();
            // actions.clickAndHold(source).moveToElement(target).release().perform();
        } finally {
            driver.quit();
        }
    }
}
