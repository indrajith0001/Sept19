package Genericutility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;

/**
 * SeleniumUtils.java
 * ---------------------------------------------------------
 * A generic, reusable WebDriver utility class.
 * Drop this into a "utils" package and call the static
 * methods directly from your test / page classes, e.g.:
 *
 *      SeleniumUtils.waitForVisibility(driver, loginBtn, 10);
 *      SeleniumUtils.click(driver, loginBtn);
 *      SeleniumUtils.type(driver, usernameField, "admin");
 *
 * All methods take WebDriver as the first parameter so the
 * class stays stateless and thread-safe (good for parallel
 * execution with ThreadLocal<WebDriver> setups).
 * ---------------------------------------------------------
 */
public class SeleniumUtils {

    // Default explicit wait timeout (seconds) — change as needed
    public static final int DEFAULT_TIMEOUT = 15;
    public static final int DEFAULT_POLLING_MILLIS = 300;

    public SeleniumUtils() {
        // prevent instantiation — utility class
    }

    // =========================================================
    // 1. WAIT UTILITIES
    // =========================================================

    public WebDriverWait getWait(WebDriver driver, int timeoutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public WebElement waitForVisibility(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForVisibility(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickability(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForClickability(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForInvisibility(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean waitForTextToBePresent(WebDriver driver, WebElement element, String text, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public boolean waitForUrlContains(WebDriver driver, String partialUrl, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.urlContains(partialUrl));
    }

    public boolean waitForTitleContains(WebDriver driver, String partialTitle, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.titleContains(partialTitle));
    }

    /** Waits until the number of open windows/tabs equals expectedCount */
    public boolean waitForNumberOfWindows(WebDriver driver, int expectedCount, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(ExpectedConditions.numberOfWindowsToBe(expectedCount));
    }

    /** Hard sleep — use only as a last resort (e.g. animations) */
    public void hardWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // =========================================================
    // 2. ELEMENT INTERACTION UTILITIES
    // =========================================================

    public void click(WebDriver driver, WebElement element) {
        waitForClickability(driver, element, DEFAULT_TIMEOUT).click();
    }

    public void click(WebDriver driver, By locator) {
        waitForClickability(driver, locator, DEFAULT_TIMEOUT).click();
    }

    /** Clicks via JavaScript — use when a normal click is intercepted/blocked */
    public static void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void type(WebDriver driver, WebElement element, String text) {
        WebElement el = waitForVisibility(driver, element, DEFAULT_TIMEOUT);
        el.clear();
        el.sendKeys(text);
    }

    public void type(WebDriver driver, By locator, String text) {
        WebElement el = waitForVisibility(driver, locator, DEFAULT_TIMEOUT);
        el.clear();
        el.sendKeys(text);
    }

    /** Types without clearing existing text first */
    public void appendText(WebDriver driver, WebElement element, String text) {
        waitForVisibility(driver, element, DEFAULT_TIMEOUT).sendKeys(text);
    }

    public String getText(WebDriver driver, WebElement element) {
        return waitForVisibility(driver, element, DEFAULT_TIMEOUT).getText();
    }

    public String getAttribute(WebDriver driver, WebElement element, String attributeName) {
        return waitForVisibility(driver, element, DEFAULT_TIMEOUT).getAttribute(attributeName);
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isElementPresent(WebDriver driver, By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isEnabled(WebElement element) {
        return element.isEnabled();
    }

    public boolean isSelected(WebElement element) {
        return element.isSelected();
    }

    /** Clears an input field reliably (handles cases .clear() fails) */
    public void clearField(WebElement element) {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    // =========================================================
    // 3. DROPDOWN (SELECT) UTILITIES
    // =========================================================

    public void selectByVisibleText(WebElement dropdown, String visibleText) {
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public void selectByValue(WebElement dropdown, String value) {
        new Select(dropdown).selectByValue(value);
    }

    public void selectByIndex(WebElement dropdown, int index) {
        new Select(dropdown).selectByIndex(index);
    }

    public String getSelectedOptionText(WebElement dropdown) {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    public List<WebElement> getAllDropdownOptions(WebElement dropdown) {
        return new Select(dropdown).getOptions();
    }

    // =========================================================
    // 4. MOUSE / KEYBOARD ACTIONS
    // =========================================================

    public void mouseHover(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public void doubleClick(WebDriver driver, WebElement element) {
        new Actions(driver).doubleClick(element).perform();
    }

    public void rightClick(WebDriver driver, WebElement element) {
        new Actions(driver).contextClick(element).perform();
    }

    public void dragAndDrop(WebDriver driver, WebElement source, WebElement target) {
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    public void pressEnter(WebDriver driver, WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

    public void pressTab(WebDriver driver, WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    /** Click and hold, move by offset, then release — useful for sliders */
    public void clickAndDragByOffset(WebDriver driver, WebElement element, int xOffset, int yOffset) {
        new Actions(driver).clickAndHold(element).moveByOffset(xOffset, yOffset).release().perform();
    }

    // =========================================================
    // 5. ALERT UTILITIES
    // =========================================================

    public void acceptAlert(WebDriver driver) {
        getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void dismissAlert(WebDriver driver) {
        getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    public String getAlertText(WebDriver driver) {
        return getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent()).getText();
    }

    public void typeInAlert(WebDriver driver, String text) {
        Alert alert = getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
    }

    // =========================================================
    // 6. WINDOW / TAB / FRAME UTILITIES
    // =========================================================

    /** Switches to the newly opened window/tab (relative to the given original handle) */
    public void switchToNewWindow(WebDriver driver, String originalWindowHandle) {
        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            if (!handle.equals(originalWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String title) {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(title)) {
                return;
            }
        }
        throw new NoSuchElementException("No window found with title: " + title);
    }

    public void closeCurrentWindowAndSwitchBack(WebDriver driver, String originalWindowHandle) {
        driver.close();
        driver.switchTo().window(originalWindowHandle);
    }

    public void switchToFrame(WebDriver driver, WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    public void switchToFrame(WebDriver driver, String frameNameOrId) {
        driver.switchTo().frame(frameNameOrId);
    }

    public void switchToFrame(WebDriver driver, int frameIndex) {
        driver.switchTo().frame(frameIndex);
    }

    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void switchToParentFrame(WebDriver driver) {
        driver.switchTo().parentFrame();
    }

    // =========================================================
    // 7. JAVASCRIPT EXECUTOR UTILITIES
    // =========================================================

    public Object executeJs(WebDriver driver, String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public void scrollIntoView(WebDriver driver, WebElement element) {
        executeJs(driver, "arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void scrollToTop(WebDriver driver) {
        executeJs(driver, "window.scrollTo(0, 0);");
    }

    public void scrollToBottom(WebDriver driver) {
        executeJs(driver, "window.scrollTo(0, document.body.scrollHeight);");
    }

    public void highlightElement(WebDriver driver, WebElement element) {
        executeJs(driver, "arguments[0].style.border='3px solid red';", element);
    }

    public void setValueUsingJs(WebDriver driver, WebElement element, String value) {
        executeJs(driver, "arguments[0].value=arguments[1];", element, value);
    }

    public String getPageTitleUsingJs(WebDriver driver) {
        return (String) executeJs(driver, "return document.title;");
    }

    public boolean isPageFullyLoaded(WebDriver driver) {
        return executeJs(driver, "return document.readyState;").equals("complete");
    }

    /** Waits until document.readyState is 'complete' (full page load) */
    public void waitForPageLoad(WebDriver driver, int timeoutInSeconds) {
        getWait(driver, timeoutInSeconds).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    // =========================================================
    // 8. SCREENSHOT UTILITIES
    // =========================================================

    public String takeScreenshot(WebDriver driver, String filePathWithName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePathWithName);
            org.apache.commons.io.FileUtils.copyFile(src, dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }

    public String takeElementScreenshot(WebElement element, String filePathWithName) {
        try {
            File src = element.getScreenshotAs(OutputType.FILE);
            File dest = new File(filePathWithName);
            org.apache.commons.io.FileUtils.copyFile(src, dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture element screenshot: " + e.getMessage(), e);
        }
    }

    // =========================================================
    // 9. NAVIGATION UTILITIES
    // =========================================================

    public void navigateTo(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    public void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void goBack(WebDriver driver) {
        driver.navigate().back();
    }

    public void goForward(WebDriver driver) {
        driver.navigate().forward();
    }

    // =========================================================
    // 10. ELEMENT LIST / TABLE UTILITIES
    // =========================================================

    public int getElementCount(WebDriver driver, By locator) {
        return driver.findElements(locator).size();
    }

    public List<String> getAllElementTexts(WebDriver driver, By locator) {
        return driver.findElements(locator).stream().map(WebElement::getText).toList();
    }

    /** Clicks the element at the given index from a list of matching elements */
    public void clickByIndex(WebDriver driver, By locator, int index) {
        List<WebElement> elements = driver.findElements(locator);
        if (index >= elements.size()) {
            throw new IndexOutOfBoundsException("No element at index " + index + " for locator: " + locator);
        }
        click(driver, elements.get(index));
    }

    // =========================================================
    // 11. FILE UPLOAD UTILITY
    // =========================================================

    /** Works for <input type="file"> elements — no OS-level dialog needed */
    public void uploadFile(WebElement fileInputElement, String absoluteFilePath) {
        fileInputElement.sendKeys(absoluteFilePath);
    }

    // =========================================================
    // 12. BROWSER / DRIVER LEVEL UTILITIES
    // =========================================================

    public void maximizeWindow(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public void deleteAllCookies(WebDriver driver) {
        driver.manage().deleteAllCookies();
    }

    public void setImplicitWait(WebDriver driver, int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    public void quitDriverSafely(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
                // driver already closed/crashed — ignore
            }
        }
    }
}
