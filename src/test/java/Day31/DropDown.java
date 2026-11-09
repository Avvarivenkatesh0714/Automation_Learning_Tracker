package Day31;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class DropDown {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://testautomationpractice.blogspot.com/");
        driver.manage().window().maximize();

        WebElement select = driver.findElement(By.xpath("//select[@id='country']"));
        Select s = new Select(select);
        //s.selectByIndex(1);
        //s.selectByValue("canada");
        //s.selectByVisibleText("Canada");
        //s.getFirstSelectedOption().click();
          s.getOptions().forEach(option -> {
              System.out.println(option.getText());
          });


    }
}
