package Day29;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CheckBoxes {
    public static void main(String[] args){

        WebDriver driver = new ChromeDriver();
        driver.get("https://testautomationpractice.blogspot.com/");
        driver.manage().window().maximize();

        //single cheskbox
        //driver.findElement(By.xpath("//label[@for='sunday']")).click();

        //multiple checkboxes
        List<WebElement> elements =  driver.findElements(By.xpath("//input[@class='form-check-input' and @type='checkbox']"));

//        for(int i = 0; i < elements.size(); i++){
//            elements.get(i).click();
//        }

//        for(WebElement element: elements){
//            element.click();
//        }

        int len = elements.size();

        int total_checkboxes_toselect =  3;

        int start = len - total_checkboxes_toselect;

        for (int i = start; i < len; i++){
            elements.get(i).click();
        }

    }
}
