package Day23;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CssSelectors {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");

        driver.manage().window().maximize();

//        driver.findElement(By.cssSelector("input#small-searchterms"))
//                .sendKeys("T-shirts");
        WebElement button = driver.findElement(By.tagName("button"));
        String text = button.getText();
        System.out.println(text);

        String title = driver.getTitle();
        String currUrl = driver.getCurrentUrl();
        String page = driver.getPageSource();
        String winHandle = driver.getWindowHandle();

        System.out.println("title: "+title);
        System.out.println("currUrl: "+currUrl);
        System.out.println("page: "+page);
        System.out.println("winHandle: "+winHandle);


        driver.findElement(By.cssSelector("input.search-box-text"))
                .sendKeys("Pants");

        driver.findElement(By.cssSelector("input[placeholder='Search store']"))
                .sendKeys("T shirts");
        if(text.equals("SEARCH")){
            button.click();
            System.out.println("Clicked");
        }

        else{
            driver.quit();
            System.out.println("Quited");
        }
//        driver.close();
    }
}
