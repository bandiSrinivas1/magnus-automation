package com.b2b.test.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.b2b.test.base.BaseAutomationTest;

public class AutomationUtil extends BaseAutomationTest {

	public static void main(String[] args) {
		
		String TEST_DATA_PROPERTIES = "testdata_en.properties";
		String EXPECTED_ASS_PROPERTIES = "expectedassertions_en.properties";
		String LANG_ASS_PROPERTIES = "xpaths_en.properties";
		System.out.println("French " + getLanguageFilePath(TEST_DATA_PROPERTIES, "fr"));
		System.out.println("French " + getLanguageFilePath(EXPECTED_ASS_PROPERTIES, "fr"));

		TEST_DATA_PROPERTIES = "testdata.properties";
		EXPECTED_ASS_PROPERTIES = "expectedassertions.properties";
		System.out.println("Default " + getLanguageFilePath(TEST_DATA_PROPERTIES, "en"));
		System.out.println("French " + getLanguageFilePath(EXPECTED_ASS_PROPERTIES, "en"));

		String langxPathFile = getLanguageFilePath(LANG_ASS_PROPERTIES, "en");
		FileReader langxPathReader;
		try {
			langxPathReader = new FileReader(langxPathFile);
			Properties langXPathsProp = new Properties();
			langXPathsProp.load(langxPathReader);

			System.out.println("Property value " + langXPathsProp.getProperty("loginpage.btn.proceed.secure.xpath"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getLanguageFilePath(String fileName, String lang) {

		if (lang == null) {
			lang = "en";
		}

		if (fileName.contains("_")) {
			fileName = fileName.split("_")[0] + "_" + lang + ".properties";
		}

		System.out.println(
				"+++++++++++++++++++++++++++++" + BASE_DIR + "/" + "src/main/resources" + "/" + lang + "/" + fileName);

		return BASE_DIR + "/" + Constants.BASE_PATH + lang + "/" + fileName;
	}
}
