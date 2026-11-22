package Day35;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DragAndDrop {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://testautomationcentral.com/demo/drag_and_drop.html");

            Actions actions = new Actions(driver);

            WebElement dragMe = driver.findElement(By.id("draggable"));
            WebElement dragHere = driver.findElement(By.id("droppable"));

            actions.dragAndDrop(dragMe, dragHere).perform();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement statusElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("drop-message"))
            );

            String message = statusElement.getText().trim();
            System.out.println("The status is: " + message);

            if (message.equals("Dropped successfully!")) {
                System.out.println("Drag and drop successful");
            } else {
                System.out.println("Drag and drop failed");
            }
        } finally {
            driver.quit();
        }
    }
}
