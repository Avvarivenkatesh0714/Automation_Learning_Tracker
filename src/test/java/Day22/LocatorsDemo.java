package Day22;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocatorsDemo {

    WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = new ChromeDriver();

        driver.get("https://examproai.onrender.com/index");
        driver.manage().window().maximize();

        driver.findElement(By.id("header-logo")).isDisplayed();

    }

//    @Test
//    public void username(){
//
//        driver.findElement(By.name("username")).sendKeys("Venkatesh");
//    }

//    @Test
//    public void linktext(){
//
//        driver.findElement(By.linkText("Login")).click();
//    }
}
