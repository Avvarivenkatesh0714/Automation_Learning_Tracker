package Day35;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DoubleClick {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml15_ev_ondblclick");
        driver.manage().window().maximize();

        // Switch to frame
        driver.switchTo().frame("iframeResult");

        WebElement box1 = driver.findElement(By.xpath("//input[@id='field1']"));
        WebElement box2 = driver.findElement(By.xpath("//input[@id='field2']"));
        WebElement button = driver.findElement(By.xpath("//button[normalize-space()='Copy Text']"));

        box1.clear(); // clears box1
        box1.sendKeys("WELCOME");

        // Double click action on the button
        Actions act = new Actions(driver);

        act.doubleClick(button).perform();

        // Validate Box2: same text from box1 is copied or not

        if(box2.equals("WELCOME")){
            System.out.println("Text copied successfully");
        }
        else{
            System.out.println("Text not copied");
        }

    }
}
