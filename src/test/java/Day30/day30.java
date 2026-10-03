package Day30;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.log4testng.Logger;

import java.time.Duration;

public class day30 {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://ui.vision/demo/webtest/frames/");
        driver.manage().window().maximize();

        // Frame 1
        WebElement frame1 = driver.findElement(By.xpath("//frame[@src='frame_1.html']"));

        driver.switchTo().frame(frame1);

        driver.findElement(By.xpath("//input[@name='mytext1']")).sendKeys("Venkatesh");

        driver.switchTo().defaultContent(); //back to the page from iframe
        // Frame 2

        WebElement frame2 = driver.findElement(By.xpath("//frame[@src='frame_2.html']"));
        driver.switchTo().frame(frame2);
        driver.findElement(By.xpath("//input[@name='mytext2']")).sendKeys("Pandu");

        driver.switchTo().defaultContent();

        // Frame 3 (Frame inside the frame)

        WebElement frame3 = driver.findElement(By.xpath("//frame[@src='frame_3.html']"));
        driver.switchTo().frame(frame3);
        driver.findElement(By.xpath("//input[@name='mytext3']")).sendKeys("MLFW");

        // Inner Frame from Frane 3

        driver.switchTo().frame(0);

        driver.findElement(By.xpath("//div[@id='i9']//div[@class='AB7Lab Id5V1']")).click();

        driver.findElement(By.xpath("//div[@id='i21']//div[@class='uHMk6b fsHoPb']")).click();

        driver.findElement(By.xpath(" //div[@id='i24']//div[@class='uHMk6b fsHoPb']")).click();

        driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();


    }
}
