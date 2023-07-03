package com.b2b.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.b2b.support.B2BPageFactory;
import com.opencsv.CSVReader;

public class ClassplusBaseAutomationPage extends B2BBaseAutomationPage {

	private static final Logger log = LogManager.getLogger(ClassplusBaseAutomationPage.class);

	public static String TEST_FILE_PATH = null;

	public ClassplusBaseAutomationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		log.debug("in classplus base");
		if (TEST_FILE_PATH == null) {
			TEST_FILE_PATH = getTestFilePath();

			log.debug("In Constructor " + TEST_FILE_PATH);
		}
		log.debug("middle in classplus base");
		B2BPageFactory.initElements(driver, this);
		log.debug("end in classplus base");
	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public boolean getAddedContentText(List<WebElement> element, String contentName) {
		log.info("Starting of getAddedContentTexts method");

		for (WebElement e : element) {
			hardWait(2);
			if (e.getText().equalsIgnoreCase(contentName)) {
				return true;
			}
		}

		log.info("Ending of getAddedContentTexts method");
		return false;
	}

	public void deleteInputFields(WebElement element) {
		if (!System.getProperty("os.name").contains("Mac")) {
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.DELETE);
		} else {
			element.sendKeys(Keys.COMMAND + "a");
			element.sendKeys(Keys.DELETE);

		}
	}

	public void mouseHover(WebElement element) {
		log.info("Staritng of mouseHover method");

		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		// actions.moveToElement(element).perform();

		log.info("Ending of mouseHover method");
	}

	public WebElement webElementWithReplacement(WebElement webElement, String replaceText) {
		log.info("Starting of webElementWithReplacement method");

		String el = webElement.toString();
		String xpath = el.substring(el.indexOf("//"), el.length() - 1);
		WebElement ele = driver.findElement(By.xpath(xpath.replaceAll("\\$\\{.+\\}", replaceText)));
		log.info("Ending of webElementWithReplacement method");

		return ele;
	}

	public boolean getPaymentStatus(List<WebElement> paymentDetail, List<WebElement> activeStatus, String FullPayment,
			String ActiveStatus) {
		log.info("Starting of getPaymentStatus method");
		log.info("ending of getPaymentStatus method");

		for (int i = 0; i < paymentDetail.size(); i++) {
			if (paymentDetail.get(i).getText().contains(FullPayment)) {
				if (!(activeStatus.get(i).getText().equalsIgnoreCase(ActiveStatus))) {
					return false;
				}
			}
		}
		return true;

	}

	public void clickOnLeftMenuLabels(List<WebElement> ele, String leftmenuLabelName) {
		for (WebElement e : ele) {
			if (e.getText().equalsIgnoreCase(leftmenuLabelName)) {
				clickOnWebElement(e);
				break;
			}
		}
	}

	public List<String> getElementsList(List<WebElement> ele) {
		log.info("Starting of getElementsList method");
		log.info("Ending of getElementsList method");
		List<String> courseList = new ArrayList<String>();

		for (WebElement element : ele) {
			courseList.add(element.getText());
		}
		return courseList;
	}
	
	public void uploadImage(WebElement element, String image) {
		String osPath = System.getProperty("os.name");
		if (osPath.contains("Linux")) {
			element.sendKeys(TEST_FILE_PATH + "/testdata/" + image);

		} else {
			element.sendKeys(TEST_FILE_PATH + File.separator + "testdata" + File.separator + image);
		}

	}
	public static List<String> readTheDataFromCSV() throws Exception {
		Object[][] mydata = new Object[10][10];
		CSVReader reader = null;

		try {
			// Read existing file
			reader = new CSVReader(new FileReader(getLastModified()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		java.util.List<String[]> csvBody = reader.readAll();
		System.out.println(csvBody.toString());
		List<String> courseList = new ArrayList<String>();

		for (String[] strErrorData : csvBody) {

			for (String strReadEachData : strErrorData) {
				courseList.add(strReadEachData);
				System.out.print(strReadEachData + " ");
			}
		}

		reader.close();
		return courseList;
	}

	public void slider(WebElement webElement, int xCoordinate) {
		log.info("Starting of slider method");

		Actions actions = new Actions(driver);
		actions.dragAndDropBy(webElement, xCoordinate, 0).perform();

		log.info("Ending of slider method");
	}
	public void switchToDefault() {
		log.info("starting of clickOnTutorDropdown method");

		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(0));

		log.info("Ending of clickOnTutorDropdown method");
	}
	
	public void navigateBack() {
		log.info("Starting of navigateBack method");

		driver.navigate().back();

		log.info("Ending of navigateBack method");
	}
	public static File getLastModified() {
		File directory = new File("C:\\Users\\Hello\\Downloads");
		File[] files = directory.listFiles(File::isFile);
		long lastModifiedTime = Long.MIN_VALUE;
		File chosenFile = null;

		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					chosenFile = file;
					lastModifiedTime = file.lastModified();
				}
			}
		}
		System.out.println("File path is " + chosenFile + "/n");
		return chosenFile;
	}

	public void pickFromWebElementListRadio(List<WebElement> webElements, List<WebElement> textWebElements,
				String containsText) {
			log.info("Starting of pickFromWebElemetList method");

			WebElement webElement = null;
			WebElement textWebElement = null;
			Object[] webElementsArray = webElements.toArray();
			Object[] xPathArray = textWebElements.toArray();

			for (int i = 0; i < webElements.size(); i++) {
				webElement = (WebElement) webElementsArray[i];
				textWebElement = (WebElement) xPathArray[i];
				if (textWebElement.getText().equals(containsText)) {
					if (!webElement.isSelected())

					{
						this.clickOnWebElement(webElement);
						break;
					}
				}
			}
			log.info("Ending of pickFromWebElemetList method");

		}


	public void pickFromWebElement(List<WebElement> webElements, String containsText) {
			log.info("Starting of pickFromWebElemetList method");

			for (WebElement webElement : webElements) {
				if (webElement.getText().contains(containsText)) {
					this.clickOnWebElement(webElement);
					break;
				}
			}
			log.info("Ending of pickFromWebElemetList method");
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


	public void pickFromWebElementList(List<WebElement> webElements, List<WebElement> textWebElements,
				String containsText) {
			log.info("Staritng of pickFromWebElemetList method");

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

			log.info("Ending of pickFromWebElemetList method");
		}


	public boolean checkElementDisplayed(WebElement element) {
			boolean flag = false;
			try {
				if (element.isDisplayed())
					flag = true;
			} catch (NoSuchElementException e) {
				flag = false;
			}

			return flag;
		}
	
	public String convertDateToFormatDDMMYY(int time) {
		// displaying current date and time
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
		cal.add(Calendar.YEAR, time);
		System.out.println("Today's date and time = " + simpleformat.format(cal.getTime()));
		return simpleformat.format(cal.getTime());
	}
	
	public String convertDateToFormatDDMMMYY(int time) {
		// displaying current date and time
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd MMM yy");
		cal.add(Calendar.YEAR, time);
		System.out.println("Today's date and time = " + simpleformat.format(cal.getTime()));
		return simpleformat.format(cal.getTime());
	}

	
	public String convertDateToFormatDDMMMYYYY(int time) {
		// displaying current date and time
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd MMM yyyy");
		cal.add(Calendar.YEAR, time);
		System.out.println("Today's date and time = " + simpleformat.format(cal.getTime()));
		return simpleformat.format(cal.getTime());
	}

	public void resetImplicitTimeout(int newTimeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
	}
	
	public Integer getCountText(WebElement lblCount) {
		log.info("Starting of getCountText method");
		
		String batchCount = lblCount.getText();
		log.info(batchCount);
		String returnCount="";
		String str[] = batchCount.split("\\D");
		for(String s:str)
			if(s!="")
				returnCount=s;
		int countValue=0;
		if(!returnCount.equals(""))
		countValue=Integer.parseInt(returnCount);
		System.out.println("Count=" + countValue);
		
		log.info("Ending of getCountText method");
		
		return countValue;
	}

}