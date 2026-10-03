package Day30;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class day30 {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://ui.vision/demo/webtest/frames/");

        driver.manage().window().maximize();

        WebElement frame1 = driver.findElement(By.xpath("//frame[@src='frame_2.html']"));

        driver.switchTo().frame(frame1);

        driver.findElement(By.xpath("//input[@name='mytext2']")).sendKeys("Venkatesh");

        Thread.sleep(7000);
        driver.close();

    }
}
