package Day29;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class checkBoxesAssignment {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://testautomationpractice.blogspot.com/");
        driver.manage().window().maximize();

        //multiple checkboxes - Get all checkboxes from the product table
        List<WebElement> checkboxes = driver.findElements(By.xpath("//table[@id='productTable']/tbody/tr/td[4]/input[@type='checkbox']"));

        System.out.println("Total checkboxes found: " + checkboxes.size());

        //Click all checkboxes
        for(WebElement checkbox : checkboxes){
            if(!checkbox.isSelected()){
                checkbox.click();
                System.out.println("Checkbox clicked");
            }
        }

        Thread.sleep(5000);
        driver.close();


    }
}
