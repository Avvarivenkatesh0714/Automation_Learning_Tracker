package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class WebLoader {

    public static WebDriver getDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //driver.get("https://testautomationpractice.blogspot.com/");
        driver.get("https://jqueryui.com/datepicker/");
        driver.manage().window().maximize();
        return driver;
    }
}