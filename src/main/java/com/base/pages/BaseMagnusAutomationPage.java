package com.base.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseMagnusAutomationPage {

	protected WebDriver driver = null;

	protected static final Logger logger = LogManager.getLogger(BaseMagnusAutomationPage.class.getName());

	public static String TEST_FILE_PATH = null;
	protected static Properties langXPathsProperties = null;

	public BaseMagnusAutomationPage(WebDriver driver) {
		this.driver = driver;
		if (TEST_FILE_PATH == null) {
			TEST_FILE_PATH = getTestFilePath();
			logger.debug("In Constructor " + TEST_FILE_PATH);
		}

		PageFactory.initElements(driver, this);
	}

	public WebDriver getWebDriver() {
		return this.driver;
	}

	public void setLanguageXPathProperties(Properties tlangXPathsProperties) {
		langXPathsProperties = tlangXPathsProperties;
	}

	public String getTestFilePath() {
		// String path = "src/main/resources/testdata";
		String path = "src/main/resources/";
		File file = new File(path);
		System.out.println(file.getAbsolutePath());
		return file.getAbsolutePath();
	}

	protected void selectDropdown(String id, String value) {
		logger.info("Starting of selectDropdown method");

		Select conditions = new Select(driver.findElement(By.id(id)));
		conditions.selectByValue(value);

		logger.info("Ending of selectDropdown method");
	}

	public void clickOnWebElement(WebElement webelement) {
		logger.info("Starting of clickOnWebElement method");

		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		jsExec.executeScript("arguments[0].click();", webelement);

		logger.info("Ending of clickOnWebElement method");
	}

	public void scrollDown(int scroll) {
		logger.info("Starting of scrollDown method");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, " + scroll + ")");

		logger.info("Ending of scrollDown method");
	}

	public void refresh() {
		logger.info("Starting of refresh method");

		driver.navigate().refresh();

		logger.info("Ending of refresh method");
	}

	public void impicitWait() {
		logger.info("Starting of impicitWait method");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

		logger.info("Ending of impicitWait method");
	}

	public void explicitWait(List<WebElement> categoryOptions) {
		logger.info("Startng of explicitWait method");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOfAllElements(categoryOptions));

		logger.info("Ending of explicitWait method");
	}

	public void explicitWait(WebElement categoryOptions) {
		logger.info("Starting of explicitWait method");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(categoryOptions));

		logger.info("Ending of explicitWait method");
	}

	public void pickFromWebElemetList(List<WebElement> webElements, String containsText) {
		logger.info("Staritng of pickFromWebElemetList method");

		for (WebElement webElement : webElements) {
			if (webElement.getText().contains(containsText)) {
				this.clickOnWebElement(webElement);
				break;
			}
		}

		logger.info("Ending of pickFromWebElemetList method");
	}

	public void pickFromWebElemetList(List<WebElement> webElements, List<WebElement> textWebElements,
			String containsText) {
		logger.info("Staritng of pickFromWebElemetList method");

		WebElement webElement = null;
		WebElement textWebElement = null;
		Object[] webElementsArray = webElements.toArray();
		Object[] xPathArray = textWebElements.toArray();

		for (int i = 0; i < webElements.size(); i++) {
			webElement = (WebElement) webElementsArray[i];
			textWebElement = (WebElement) xPathArray[i];
			if (textWebElement.getText().contains(containsText)) {
				this.clickOnWebElement(webElement);
				break;
			}
		}

		logger.info("Ending of pickFromWebElemetList method");
	}

	public void uploadFile(String filepath) {

		Robot robot = null;
		try {
			robot = new Robot();

			StringSelection stringSelection = new StringSelection(filepath);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(500);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (AWTException e2) {
			e2.printStackTrace();
		}

	}

	public void scrollIntoView(WebElement element) {
		logger.info("Starting of scrollIntoView method");

		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		jsExec.executeScript("arguments[0].scrollIntoView(true);", element);

		logger.info("Ending of scrollIntoView method");
	}

	public void switchToNewWindow() {
		logger.info("Starting of switchToNewWindow method");

		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(1));

		Set<String> windows = driver.getWindowHandles();
		for (int j = 0; j < 5; j++) {
			if (windows.size() < 2) {
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		windows = driver.getWindowHandles();
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[1]);

		logger.info("Ending of switchToNewWindow method");
	}

	public void closeWindow() {
		logger.info("Starting of closeWindow method");

		driver.close();
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(0));

		logger.info("Ending of closeWindow method");
	}

	public void switchToParentWindow() {
		logger.info("Starting of switchToParentWindow method");

		driver.close();
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(0));

		logger.info("Ending of switchToParentWindow method");
	}

	public void clickOutside() {
		logger.info("Starting of clickOutside method");

		Actions action = new Actions(driver);
		action.moveByOffset(0, 0).click().build().perform();

		logger.info("Ending of clickOutside method");
	}

	public void waitForElementVisibilty(WebElement element) {
		WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void dragAndSort(List<WebElement> webElementList, Integer targetIndex, Integer destIndex) {

		Actions action = new Actions(driver);
		WebElement target = webElementList.get(targetIndex);
		WebElement dest = webElementList.get(destIndex);

		action.click(target).clickAndHold().moveToElement(dest).moveByOffset(0, 50).release().build().perform();
	}

	public void mouseHover(WebElement element) {
		logger.info("Staritng of mouseHover method");

		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		// actions.moveToElement(element).perform();

		logger.info("Ending of mouseHover method");
	}

	public void fluentWaitForElement(WebDriver childDriver, String xPath) {

		try {

			// Reference : https://www.guru99.com/implicit-explicit-waits-selenium.html
			Wait<WebDriver> wait = new FluentWait<WebDriver>(childDriver).withTimeout(Duration.ofSeconds(3))
					.pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class);

			wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.xpath(xPath));
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void closeOSWindow() {
		Robot robot = null;
		try {
			if (System.getProperty("os.name").contains("Windows 10")) {
				robot = new Robot();
				for (int i = 0; i < 3; i++) {
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);

				}
				robot.keyPress(KeyEvent.VK_ENTER);

				robot.delay(500);

				robot.keyRelease(KeyEvent.VK_ENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public String getUniqueMail(String value) {

		logger.info("Starting of getUniqueMail method");
		logger.info("Ending of getUniqueMail method");

		Random rand = new Random();
		// Generate random integers in range 0 to 99
		int rand_int1 = rand.nextInt(100);
		return value + rand_int1 + "@gmail.com";
	}

	public String getUniqueNumber(String value) {

		logger.info("Starting of getUniqueNumber method");
		logger.info("Ending of getUniqueNumber method");

		Random rand = new Random();
		// Generate random integers in range 0 to 9
		int rand_int1 = rand.nextInt(10);

		return value + rand_int1;
	}

	public void slider(WebElement webElement, int xCoordinate) {
		logger.info("Starting of slider method");

		Actions actions = new Actions(driver);
		actions.dragAndDropBy(webElement, xCoordinate, 0).perform();

		logger.info("Ending of slider method");

	}
}
