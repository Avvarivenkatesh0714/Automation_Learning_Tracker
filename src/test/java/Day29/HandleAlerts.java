package Day29;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleAlerts {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

//        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Alert']")).click();


//// 1 - OK
////        driver.switchTo().alert().accept();
//        Alert myAlert =  driver.switchTo().alert();
//
//        System.out.println(myAlert.getText());
//        Thread.sleep(5000);
//        myAlert.accept();

        // 2 - Ok and cancel

        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")).click();
        Alert myAlerts = driver.switchTo().alert();

        System.out.println(myAlerts.getText());

        Thread.sleep(3000);

        myAlerts.dismiss();

    }
}
