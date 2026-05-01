import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class GoogleTest {

    WebDriver driver;
    WebElement heading;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://examproai.onrender.com/index");

        heading = driver.findElement(By.tagName("h1"));
    }

    @Test
    public void headingTextVerification() {

        String heading_text = heading.getText();

        if(heading_text.equals("ExamPro AI")){
            System.out.println("Text is correct");
        } else {
            System.out.println("Text is NOT correct");
        }
    }

    @Test
    public void headingTextSizeVerification() {

        String fontSize = heading.getCssValue("font-size");

        if(fontSize.contains("33")) {
            System.out.println("Font size is correct");
        } else {
            System.out.println("Font size is NOT correct");
        }
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}