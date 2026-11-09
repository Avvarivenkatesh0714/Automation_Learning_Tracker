package Day32;

import Utilities.WebLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class StaticWebTable {
    public static void main(String[] args) {
        WebDriver driver = WebLoader.getDriver();

        List<WebElement> list = driver.findElements(By.xpath("//table[@name='BookTable']//tbody//tr"));
//        System.out.println("Total rows in the table: " + list.size());

//        WebElement row2 = driver.findElement(By.xpath("//table[@name='BookTable']//tbody//tr[2]"));
//        String text = row2.getText();
//        System.out.println("Row 2 text: " + text);

//        for(int i=1;i<=list.size();i++){
//            WebElement row = driver.findElement(By.xpath("//table[@name='BookTable']//tbody//tr["+i+"]"));
//            String text = row.getText();
//            System.out.println(text);
//        }

        //challenge read the author and print the book

//        for(int i=2;i<=list.size();i++){
//            String AuthorName = driver.findElement(By.xpath("//table[@name='BookTable']//tbody//tr["+i+"]//td[2]")).getText();
//
//            if(AuthorName.equals("Mukesh")){
//                String bookName = driver.findElement(By.xpath("//table[@name='BookTable']//tbody//tr["+i+"]//td[1]")).getText();
//                System.out.println("Book by Mukesh: " + bookName);
//            }
//        }

        int cost = 0;
        for(int i=2;i<= list.size();i++){
            String prices = driver.findElement(By.xpath("//table[@name='BookTable']//tbody//tr["+i+"]//td[4]")).getText();
            cost = cost + Integer.parseInt(prices);
        }
        System.out.println(cost);

    }
}
