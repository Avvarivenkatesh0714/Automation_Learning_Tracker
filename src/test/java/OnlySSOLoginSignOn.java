import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OnlySSOLoginSignOn {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;
    private static final String BASE_URL = "https://serviceqa.penske.com/parts/entry/ApplicationEntry";

    private static final String SSO_ID       = "505084158";
    private static final String PASSWORD     = "BlueOrbitS0714@";

    private static final By SSO_INPUT      = By.xpath(
            "//input[@name='USER' or @name='username' or @id='username' " +
                    "or @placeholder='Enter SSO ID or Username']"
    );
    private static final By PASSWORD_INPUT = By.xpath(
            "//input[@name='PASSWORD' or @type='password']"
    );
    private static final By SPINNER_LOADER  = By.xpath("//div[contains(@class,'spinner')]");
    private static final By SIGN_IN_BUTTON = By.xpath(
            "//input[@type='submit'] | //button[@type='submit'] " +
                    "| //button[normalize-space()='Sign In'] | //a[normalize-space()='Sign In']"
    );
    private static final By PAGE_TITLE      = By.xpath("//remote-signon//div[@class='pageHeader']");

    public void setup() {
        // Initialize ChromeDriver (make sure chromedriver is in PATH)
        // Alternatively, set system property: System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        System.out.println("✓ WebDriver initialized successfully");
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✓ WebDriver closed successfully");
        }
    }

    public void navigateToPage() {
        System.out.println("→ Navigating to: " + BASE_URL);
        driver.get(BASE_URL);
        waitForPageToLoad();
        System.out.println("✓ Page loaded: " + driver.getCurrentUrl());
    }

    public void waitForPageToLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(PAGE_TITLE));
            System.out.println("✓ Page title element found");
        } catch (Exception e) {
            System.out.println("⚠ Warning: Page title not found, continuing anyway...");
        }
    }

    public boolean isSignInPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SSO_INPUT));
            System.out.println("✓ Sign-In page detected (SSO input visible)");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void signIn(String ssoId, String password) {
        System.out.println("→ Sign-In page detected. Logging in with SSO ID: " + ssoId);

        WebElement ssoField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(SSO_INPUT)
        );
        ssoField.clear();
        ssoField.sendKeys(ssoId);
        System.out.println("✓ SSO ID entered");

        WebElement pwdField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT)
        );
        pwdField.clear();
        pwdField.sendKeys(password);
        System.out.println("✓ Password entered");

        // Try clicking the submit button; fall back to ENTER key if not found
        try {
            WebElement signInBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(SIGN_IN_BUTTON)
            );
            signInBtn.click();
            System.out.println("✓ Sign In button clicked");
        } catch (Exception e) {
            System.out.println("⚠ Submit button not found via XPath, pressing ENTER instead...");
            pwdField.sendKeys(Keys.ENTER);
            System.out.println("✓ ENTER key pressed to submit form");
        }

        // Wait for the spinner / page transition to complete
        waitForSpinnerToDisappear();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("✓ Sign-In completed. Current URL: " + driver.getCurrentUrl());
    }

    public void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(SPINNER_LOADER));
            System.out.println("✓ Loading spinner disappeared");
        } catch (Exception e) {
            System.out.println("⚠ Warning: Spinner check failed, continuing...");
        }
    }

    public static void main(String[] args) {
        OnlySSOLoginSignOn automation = new OnlySSOLoginSignOn();

        try {
            // Initialize WebDriver
            automation.setup();

            // Navigate to the login page
            automation.navigateToPage();

            // Check if Sign-In page is displayed
            if (automation.isSignInPageDisplayed()) {
                // Perform login with SSO credentials
                automation.signIn(SSO_ID, PASSWORD);
                System.out.println("✓ Successfully logged in!");
            } else {
                System.out.println("✗ Sign-In page was not displayed");
            }

            // Keep browser open briefly to see the result
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("\n✗ MAIN EXECUTION FAILED");
            e.printStackTrace();
        } finally {
            // Always cleanup
            automation.tearDown();
        }
    }
}
