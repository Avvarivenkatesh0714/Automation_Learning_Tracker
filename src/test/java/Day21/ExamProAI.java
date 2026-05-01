package Day21;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ExamProAI {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get("https://examproai.onrender.com/index");
    }

    @Test
    public void loginTest() {

        // Click Start button
        WebElement startButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button.button"))
        );
        startButton.click();

        // Click Continue / Next button
        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))
        );
        continueButton.click();

        String[] usernames = {"test", "Venkatesh"};
        String[] passwords = {"123", "12345"};

        for (int i = 0; i < usernames.length; i++) {

            // Username field
            WebElement username = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("input[placeholder='Username']"))
            );
            username.clear();
            username.sendKeys(usernames[i]);

            // Password field
            WebElement password = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("input[placeholder='Password']"))
            );
            password.clear();
            password.sendKeys(passwords[i]);

            // Login button
            WebElement loginButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button[type='submit']"))
            );
            loginButton.click();
        }

        // ✅ Correct assertion: expect navigation AFTER login
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("index")
        ));

        Assert.assertFalse(
                driver.getCurrentUrl().contains("index"),
                "Login failed or page did not navigate"
        );
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}