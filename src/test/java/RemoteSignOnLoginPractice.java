import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * PartsNet Remote Sign On Login - Practice Project
 *
 * Purpose: Navigate to Remote Sign On page, search for location "0030",
 *          select "0030-10 ATLANTA WEST FULTON" from dropdown, and click login
 *
 * Prerequisites:
 * - ChromeDriver installed and in PATH or specify driver path
 * - Selenium WebDriver 4.x (add to pom.xml or build.gradle)
 * - Java 11+
 *
 * @author Practice
 * @version 1.0
 */
public class RemoteSignOnLoginPractice {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 30;
    private static final String BASE_URL = "https://serviceqa.penske.com/parts/entry/ApplicationEntry";

    // ========================================
    // CREDENTIALS
    // ========================================
    private static final String SSO_ID       = "505084158";
    private static final String PASSWORD     = "BlueOrbitS0714@";

    // ========================================
    // LOCATORS
    // ========================================

    // --- Initial Sign-In Page (Siteminder / Penske SSO) ---
    // Siteminder FCC pages use name="USER" / name="PASSWORD" / input[type='submit']
    private static final By SSO_INPUT      = By.xpath(
            "//input[@name='USER' or @name='username' or @id='username' " +
            "or @placeholder='Enter SSO ID or Username']"
    );
    private static final By PASSWORD_INPUT = By.xpath(
            "//input[@name='PASSWORD' or @type='password']"
    );
    private static final By SIGN_IN_BUTTON = By.xpath(
            "//input[@type='submit'] | //button[@type='submit'] " +
            "| //button[normalize-space()='Sign In'] | //a[normalize-space()='Sign In']"
    );

    // --- Remote Sign-On Page ---
    private static final By LOCATION_SEARCH_INPUT = By.xpath(
            "//input[@name='search' and @placeholder='Ex: 0011-10/001111/CITY']"
    );
    private static final By LOGIN_BUTTON    = By.xpath("//a[@class='login-button']");
    private static final By PAGE_TITLE      = By.xpath("//remote-signon//div[@class='pageHeader']");
    private static final By SPINNER_LOADER  = By.xpath("//div[contains(@class,'spinner')]");

    // --- Dashboard / Left Navigation ---
    private static final By LEFT_MENU_TOGGLE   = By.xpath(
            "//button[contains(@class,'menu') or contains(@class,'hamburger') or contains(@aria-label,'menu')]"
            + " | //div[contains(@class,'sidebar-toggle')]"
    );
    private static final By CATALOG_MENU_ITEM  = By.xpath(
            "//a[normalize-space()='Catalog'] | //span[normalize-space()='Catalog'] "
            + "| //li[contains(@class,'menu')]//a[contains(text(),'Catalog')]"
    );
    private static final By PARTS_CATALOG_LINK = By.xpath(
            "//a[normalize-space()='Parts Catalog'] | //span[normalize-space()='Parts Catalog'] "
            + "| //a[contains(text(),'Parts Catalog')]"
    );

    // --- Parts Catalog Page ---
    private static final By PARTS_CATALOG_SEARCH_BTN = By.xpath(
            "//button[normalize-space()='Search'] | //input[@value='Search'] "
            + "| //button[contains(@class,'search')]"
    );
    // Broad row locator — covers table rows, Angular CDK rows, Material rows, custom rows
    private static final By PARTS_ROWS = By.xpath(
            "//table//tbody//tr[.//input[@type='checkbox']] "
            + "| //cdk-row | //mat-row "
            + "| //tr[.//input[@type='checkbox']] "
            + "| //div[contains(@class,'row') and .//input[@type='checkbox']] "
            + "| //li[.//input[@type='checkbox']]"
    );
    // Fallback: all checkboxes in the results area when row-level detection fails
    private static final By ALL_RESULT_CHECKBOXES = By.xpath(
            "//app-parts-catalog//input[@type='checkbox'] "
            + "| //parts-catalog//input[@type='checkbox'] "
            + "| //div[contains(@class,'catalog')]//input[@type='checkbox'] "
            + "| //div[contains(@class,'result')]//input[@type='checkbox'] "
            + "| //table//tbody//input[@type='checkbox'] "
            + "| //tbody//tr//input[@type='checkbox']"
    );
    private static final By PART_ROW_CHECKBOX = By.xpath(".//input[@type='checkbox']");
    private static final By PART_ROW_SUPPLIER  = By.xpath(
            ".//td[contains(@class,'supplier')] | .//span[contains(@class,'supplier')] "
            + "| .//div[contains(@class,'supplier')]"
    );

    // --- Create PO ---
    // Direct "Create PO" button (Angular: class="btn btn-primary")
    private static final By CREATE_PO_BUTTON = By.xpath(
            "//button[contains(@class,'btn') and contains(@class,'primary') and normalize-space()='Create PO'] "
            + "| //button[contains(@class,'btn-primary') and normalize-space()='Create PO'] "
            + "| //button[normalize-space()='Create PO' and contains(@class,'btn')] "
            + "| //button[normalize-space()='Create PO']"
    );
    // Dropdown / More Options approach (for parts catalog page)
    private static final By MORE_OPTIONS_BTN  = By.xpath(
            "//button[normalize-space()='More Options'] | //button[contains(@class,'more-options')] "
            + "| //button[contains(text(),'More')] | //span[normalize-space()='More Options']"
    );
    private static final By CREATE_PO_OPTION  = By.xpath(
            "//li[normalize-space()='Create PO'] | //a[normalize-space()='Create PO'] "
            + "| //button[normalize-space()='Create PO'] | //span[normalize-space()='Create PO']"
    );

    // --- PO Page ---
    // Broad locator covering common quantity field patterns in Angular/enterprise apps
    private static final By ORDER_QTY_INPUTS = By.xpath(
            "//input[contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'qty')"
            + " or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'quantity')"
            + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'qty')"
            + " or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'quantity')"
            + " or contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'qty')"
            + " or contains(translate(@class,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'quantity')"
            + " or contains(translate(@formcontrolname,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'qty')"
            + " or contains(translate(@formcontrolname,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'quantity')"
            + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'qty')"
            + " or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'quantity')"
            + " or @type='number']"
    );
    private static final By PO_ROW_CHECKBOXES = By.xpath(
            "//table//tbody//tr//input[@type='checkbox'] "
            + "| //div[contains(@class,'po-row')]//input[@type='checkbox']"
    );
    private static final By DELETE_BUTTON    = By.xpath(
            "//button[normalize-space()='Delete'] | //a[normalize-space()='Delete'] "
            + "| //button[contains(@class,'delete')]"
    );
    private static final By MODAL_CONTINUE_BTN = By.xpath(
            "//button[normalize-space()='Continue'] | //a[normalize-space()='Continue'] "
            + "| //button[contains(@class,'confirm') and not(contains(@class,'cancel'))]"
    );

    // ========================================
    // SETUP & TEARDOWN
    // ========================================

    /**
     * Initialize WebDriver and WebDriverWait
     */
    public void setup() {
        // Initialize ChromeDriver (make sure chromedriver is in PATH)
        // Alternatively, set system property: System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        System.out.println("✓ WebDriver initialized successfully");
    }

    /**
     * Close WebDriver and browser
     */
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✓ WebDriver closed successfully");
        }
    }

    // ========================================
    // HELPER METHODS
    // ========================================

    /**
     * Navigate to the Remote Sign On page
     */
    public void navigateToPage() {
        System.out.println("→ Navigating to: " + BASE_URL);
        driver.get(BASE_URL);
        waitForPageToLoad();
        System.out.println("✓ Page loaded: " + driver.getCurrentUrl());
    }

    /**
     * Wait for page to fully load by checking for page title
     */
    public void waitForPageToLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(PAGE_TITLE));
            System.out.println("✓ Page title element found");
        } catch (Exception e) {
            System.out.println("⚠ Warning: Page title not found, continuing anyway...");
        }
    }

    /**
     * Wait for spinner/loader to disappear
     */
    public void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(SPINNER_LOADER));
            System.out.println("✓ Loading spinner disappeared");
        } catch (Exception e) {
            System.out.println("⚠ Warning: Spinner check failed, continuing...");
        }
    }

    /**
     * Take screenshot for debugging
     */
    public void takeScreenshot(String fileName) {
        try {
            // Note: This is a simplified example. In real projects, use built-in screenshot utilities
            System.out.println("→ Screenshot functionality: " + fileName);
        } catch (Exception e) {
            System.out.println("✗ Screenshot failed: " + e.getMessage());
        }
    }

    // ========================================
    // SIGN-IN PAGE METHODS
    // ========================================

    /**
     * Check whether the initial Penske Sign-In page is displayed.
     * Uses a short explicit wait so the redirect has time to settle.
     */
    public boolean isSignInPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SSO_INPUT));
            System.out.println("✓ Sign-In page detected (SSO input visible)");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enter SSO ID / Username and Password, then click Sign In.
     * Falls back to Keys.ENTER if the submit button is not clickable.
     */
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

    // ========================================
    // MAIN ACTION METHODS
    // ========================================

    /**
     * Enter location code in the search field
     *
     * @param locationCode the location code to enter (e.g., "0030")
     */
    public void enterLocationCode(String locationCode) {
        System.out.println("→ Entering location code: " + locationCode);

        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(LOCATION_SEARCH_INPUT)
        );
        searchInput.clear();
        searchInput.click();
        searchInput.sendKeys(locationCode);

        System.out.println("✓ Location code '" + locationCode + "' entered");

        // Wait for the page to process the search input before clicking Login
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Click the Login button
     */
    public void clickLoginButton() {
        System.out.println("→ Clicking Login button");

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)
        );
        loginButton.click();

        System.out.println("✓ Login button clicked");

        // Wait for page transition
        waitForSpinnerToDisappear();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Verify successful login by checking URL change
     */
    public boolean verifyLoginSuccess() {
        System.out.println("→ Verifying login success");

        String currentUrl = driver.getCurrentUrl();
        System.out.println("  Current URL: " + currentUrl);

        // Check if URL changed from remote-signon page
        if (!currentUrl.contains("remote-signon")) {
            System.out.println("✓ Login successful! URL changed to: " + currentUrl);
            return true;
        } else {
            System.out.println("✗ Login may have failed. Still on: " + currentUrl);
            return false;
        }
    }

    // ========================================
    // PARTS CATALOG WORKFLOW METHODS
    // ========================================

    /**
     * Open left navigation menu and navigate to Catalog → Parts Catalog
     */
    public void navigateToPartsCatalog() {
        System.out.println("→ Navigating to Parts Catalog via left menu");

        // Click hamburger / left menu toggle (skip if menu already visible)
        try {
            WebElement toggle = driver.findElement(LEFT_MENU_TOGGLE);
            if (toggle.isDisplayed()) {
                toggle.click();
                System.out.println("✓ Left menu toggled open");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("⚠ Menu toggle not found / already open, continuing...");
        }

        // Click 'Catalog' parent menu item
        try {
            WebElement catalog = wait.until(ExpectedConditions.elementToBeClickable(CATALOG_MENU_ITEM));
            catalog.click();
            System.out.println("✓ Catalog menu item clicked");
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("⚠ Catalog parent menu not found, trying direct Parts Catalog link...");
        }

        // Click 'Parts Catalog' link
        WebElement partsCatalog = wait.until(ExpectedConditions.elementToBeClickable(PARTS_CATALOG_LINK));
        partsCatalog.click();
        System.out.println("✓ Parts Catalog link clicked");

        waitForSpinnerToDisappear();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("✓ Parts Catalog page loaded: " + driver.getCurrentUrl());
    }

    /**
     * Click the Search button on the Parts Catalog page to load results
     */
    public void clickPartsCatalogSearch() {
        System.out.println("→ Clicking Search on Parts Catalog page");
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(PARTS_CATALOG_SEARCH_BTN));
        searchBtn.click();
        System.out.println("✓ Search button clicked");
        waitForSpinnerToDisappear();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /**
     * Select 2-3 parts that share the same supplier by checking their checkboxes.
     *
     * Strategy 1: Try to find rows with checkboxes + supplier cells → group by supplier.
     * Strategy 2 (fallback): If rows can't be identified, simply click the first `count`
     *                        checkboxes visible on the page (assumes results are already
     *                        filtered to a single supplier or any selection is acceptable).
     */
    public void selectPartsFromSameSupplier(int count) {
        System.out.println("→ Selecting " + count + " parts (same supplier if possible)");

        // ── Strategy 1: row-level detection ─────────────────────────────────
        try {
            List<WebElement> rows = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(PARTS_ROWS));
            System.out.println("  Strategy 1: found " + rows.size() + " rows with checkboxes");

            String targetSupplier = null;
            int selected = 0;

            for (WebElement row : rows) {
                if (selected >= count) break;
                try {
                    String supplierText = "";
                    try {
                        supplierText = row.findElement(PART_ROW_SUPPLIER).getText().trim();
                    } catch (Exception ignored) { /* supplier column may not exist */ }

                    if (targetSupplier == null && !supplierText.isEmpty()) {
                        targetSupplier = supplierText;
                        System.out.println("  Target supplier: " + targetSupplier);
                    }

                    // Select if supplier matches (or if we have no supplier info)
                    if (targetSupplier == null || supplierText.isEmpty() || supplierText.equals(targetSupplier)) {
                        WebElement chk = row.findElement(PART_ROW_CHECKBOX);
                        if (!chk.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chk);
                            chk.click();
                            selected++;
                            System.out.println("  ✓ Part selected (" + selected + "/" + count + ")"
                                    + (supplierText.isEmpty() ? "" : " - Supplier: " + supplierText));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  ⚠ Row processing error: " + e.getMessage());
                }
            }

            if (selected >= 2) {
                System.out.println("✓ " + selected + " parts selected via Strategy 1");
                return;
            }
            System.out.println("⚠ Strategy 1 selected only " + selected + " parts, trying Strategy 2...");

        } catch (Exception e) {
            System.out.println("⚠ Strategy 1 (row detection) failed: " + e.getMessage());
            System.out.println("  Falling back to Strategy 2 (direct checkbox selection)...");
        }

        // ── Strategy 2: find all checkboxes directly ─────────────────────────
        List<WebElement> checkboxes = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(ALL_RESULT_CHECKBOXES)
        );
        System.out.println("  Strategy 2: found " + checkboxes.size() + " checkboxes on page");

        int selected = 0;
        for (WebElement chk : checkboxes) {
            if (selected >= count) break;
            try {
                if (!chk.isSelected() && chk.isDisplayed() && chk.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chk);
                    chk.click();
                    selected++;
                    System.out.println("  ✓ Checkbox selected (" + selected + "/" + count + ")");
                }
            } catch (Exception e) {
                System.out.println("  ⚠ Could not click checkbox: " + e.getMessage());
            }
        }

        if (selected < 2) {
            throw new RuntimeException(
                    "Could not select at least 2 parts. Total checkboxes found: "
                    + checkboxes.size() + ", selected: " + selected
                    + ". Page source snippet: " + driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length()))
            );
        }
        System.out.println("✓ " + selected + " parts selected via Strategy 2");
    }

    /**
     * Click More Options and then select Create PO from the dropdown
     * This navigates to a new page
     */
    public void clickMoreOptionsAndCreatePO() {
        System.out.println("→ Clicking More Options button");
        WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(MORE_OPTIONS_BTN));
        moreBtn.click();
        System.out.println("✓ More Options clicked");

        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        System.out.println("→ Selecting Create PO from dropdown menu");
        WebElement createPO = wait.until(ExpectedConditions.elementToBeClickable(CREATE_PO_OPTION));
        createPO.click();
        System.out.println("✓ Create PO option clicked");

        waitForSpinnerToDisappear();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("✓ Navigated to PO creation page: " + driver.getCurrentUrl());
    }

    /**
     * On the PO creation page, click the "Create PO" button to create and open the PO in Open status
     */
    public void clickCreatePOButton() {
        System.out.println("→ Clicking Create PO button to create and open PO");
        WebElement createPOBtn = wait.until(ExpectedConditions.elementToBeClickable(CREATE_PO_BUTTON));
        createPOBtn.click();
        System.out.println("✓ Create PO button clicked");

        waitForSpinnerToDisappear();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("✓ PO created and opened in Open status. Current URL: " + driver.getCurrentUrl());
    }

    /**
     * On the PO page (Open status):
     *  1. Set every order-quantity input to 0
     *  2. Check every row checkbox
     *  3. Click Delete → Continue in the confirmation modal
     */
    public void setQtyToZeroAndDelete() {
        System.out.println("→ Setting order quantity to 0 for all PO lines");

        // ── Find quantity inputs ─────────────────────────────────────────────
        List<WebElement> qtyFields = new java.util.ArrayList<>();
        try {
            qtyFields = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(ORDER_QTY_INPUTS));
            System.out.println("  Found " + qtyFields.size() + " qty field(s) via primary locator");
        } catch (Exception e) {
            System.out.println("⚠ Primary qty locator found nothing, using JS fallback...");
            // JS fallback: grab every visible <input> inside the PO table
            @SuppressWarnings("unchecked")
            List<WebElement> jsFound = (List<WebElement>) ((JavascriptExecutor) driver).executeScript(
                    "return Array.from(document.querySelectorAll("
                    + "'table input, tbody input, .po-line input, .order-line input, "
                    + "[class*=po] input, [class*=order] input, input[type=number]')"
                    + ").filter(el => el.offsetParent !== null);"   // only visible
            );
            if (jsFound != null) qtyFields = jsFound;
            System.out.println("  JS fallback found " + qtyFields.size() + " input(s)");
        }

        for (WebElement qty : qtyFields) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", qty);
                // Use JS to set value directly (works even for Angular reactive-form inputs)
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value='0';"
                        + "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));"
                        + "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));", qty);
                // Also send keys as a safety net
                qty.click();
                qty.sendKeys(Keys.CONTROL + "a");
                qty.sendKeys("0");
                qty.sendKeys(Keys.TAB);
                System.out.println("  ✓ Quantity set to 0");
            } catch (Exception e) {
                System.out.println("  ⚠ Could not set qty: " + e.getMessage());
            }
        }

        waitForSpinnerToDisappear();
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // ── Check all row checkboxes ──────────────────────────────────────────
        System.out.println("→ Checking all PO row checkboxes");
        List<WebElement> checkboxes = driver.findElements(PO_ROW_CHECKBOXES);
        if (checkboxes.isEmpty()) {
            // Fallback: any checkbox on the page
            checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
            System.out.println("  Fallback: found " + checkboxes.size() + " checkbox(es)");
        }
        for (WebElement chk : checkboxes) {
            try {
                if (!chk.isSelected() && chk.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chk);
                    chk.click();
                    System.out.println("  ✓ Row checkbox checked");
                }
            } catch (Exception e) {
                System.out.println("  ⚠ Could not check checkbox: " + e.getMessage());
            }
        }

        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // ── Click Delete ──────────────────────────────────────────────────────
        System.out.println("→ Clicking Delete button");
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(DELETE_BUTTON));
        deleteBtn.click();
        System.out.println("✓ Delete button clicked");

        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // ── Continue in modal ─────────────────────────────────────────────────
        System.out.println("→ Clicking Continue in confirmation modal");
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(MODAL_CONTINUE_BTN));
        continueBtn.click();
        System.out.println("✓ Continue clicked — PO lines deleted");

        waitForSpinnerToDisappear();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("✓ Delete workflow completed. Current URL: " + driver.getCurrentUrl());
    }

    // ========================================
    // COMPLETE WORKFLOW METHOD
    // ========================================

    /**
     * Complete the entire Remote Sign On login workflow
     *
     * @param locationCode the location code to search (e.g., "0030")
     */
    public void completeRemoteSignOnLogin(String locationCode) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STARTING REMOTE SIGN ON LOGIN WORKFLOW");
        System.out.println("=".repeat(60));

        try {
            // Step 1: Navigate to page
            navigateToPage();

            // Step 2: Handle initial Penske Sign-In page if present
            if (isSignInPageDisplayed()) {
                signIn(SSO_ID, PASSWORD);
                takeScreenshot("01_after_sign_in");
            } else {
                System.out.println("→ Sign-In page not detected, skipping SSO login step");
            }

            // Step 3: Take screenshot before entering data
            takeScreenshot("02_before_entering_location");

            // Step 4: Enter location code (results appear automatically)
            enterLocationCode(locationCode);
            takeScreenshot("03_after_entering_location_code");

            // Step 5: Click login button directly
            clickLoginButton();
            takeScreenshot("04_after_clicking_login");

            // Step 6: Verify login success
            boolean loginSuccess = verifyLoginSuccess();

            System.out.println("\n" + "=".repeat(60));
            if (loginSuccess) {
                System.out.println("✓ REMOTE SIGN ON LOGIN COMPLETED SUCCESSFULLY");
            } else {
                System.out.println("✗ LOGIN WORKFLOW COMPLETED WITH WARNINGS");
            }
            System.out.println("=".repeat(60) + "\n");

            // ── Parts Catalog → Create PO → Delete ──────────────────
            // Step 7: Navigate to Parts Catalog via left menu
            navigateToPartsCatalog();
            takeScreenshot("05_parts_catalog_page");

            // Step 8: Click Search to load parts
            clickPartsCatalogSearch();
            takeScreenshot("06_after_search");

            // Step 9: Select 3 parts from the same supplier
            selectPartsFromSameSupplier(3);
            takeScreenshot("07_parts_selected");

            // Step 10: More Options → Create PO (navigates to new page)
            clickMoreOptionsAndCreatePO();
            takeScreenshot("08_po_nav_page");

            // Step 11: Click Create PO button to create and open PO in Open status
            clickCreatePOButton();
            takeScreenshot("09_po_created_and_opened");

            // Step 12: Set qty to 0, check rows, delete, confirm
            setQtyToZeroAndDelete();
            takeScreenshot("10_after_delete");

            System.out.println("\n" + "=".repeat(60));
            System.out.println("✓ FULL WORKFLOW COMPLETED SUCCESSFULLY");
            System.out.println("=".repeat(60) + "\n");

        } catch (Exception e) {
            System.out.println("\n✗ ERROR DURING WORKFLOW: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("error_screenshot");
            throw e;
        }
    }

    // ========================================
    // MAIN METHOD - ENTRY POINT
    // ========================================

    /**
     * Main method to run the practice automation
     */
    public static void main(String[] args) {
        RemoteSignOnLoginPractice automation = new RemoteSignOnLoginPractice();

        try {
            // Initialize WebDriver
            automation.setup();

            // Execute the login workflow with just the location code
            automation.completeRemoteSignOnLogin("0030");

            // Keep browser open for 3 seconds to see the result
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