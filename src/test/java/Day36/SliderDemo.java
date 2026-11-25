package Day36;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class SliderDemo {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.jqueryscript.net/demo/Price-Range-Slider-jQuery-UI/");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        Actions act = new Actions(driver);

        // Minimum Slider
        WebElement min_slider =  driver.findElement(By.xpath("//span[1]"));
        System.out.println("Default Locations of Min-Slider is: "+min_slider.getLocation());
        act.dragAndDropBy(min_slider, 100, 249).perform();
        System.out.println("After dragged Locations of Min-Slider is: "+min_slider.getLocation());

        // Maximum Slider
        WebElement max_slider =  driver.findElement(By.xpath("//span[2]"));
        System.out.println("Default Locations of Max-Slider is: "+max_slider.getLocation());
        act.dragAndDropBy(max_slider, -200, 249).perform();
        System.out.println("After dragged Locations of Min-Slider is: "+max_slider.getLocation());

        driver.quit();
    }
}
