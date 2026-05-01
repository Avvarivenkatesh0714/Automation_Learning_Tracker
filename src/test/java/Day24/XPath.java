package Day24;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class XPath {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.get("https://examproai.onrender.com/login");

        driver.manage().window().maximize();

        //single attributes
//        driver.findElement(By.xpath("//input[@name='q']"))
//                .sendKeys("T-shirts");

        //multiple Attributes
//        driver.findElement(By.xpath("//input[@placeholder='Username'][@placeholder='Username']"))
//                .sendKeys("Ricky");

        //'and' 'or' operators

//        driver.findElement(By.xpath("input[@placeholder='Username' and @placeholder='Username']"))
//                .sendKeys("Ricky");
//        driver.findElement(By.xpath("input[@placeholder='Username' or @placeholder='Username']"))
//                .sendKeys("Ricky");

        //inner text -> text()
        driver.findElement(By.xpath("//*[text()='Forgot password?']"));

    }
}
