/**
 * 
 */
package com.base.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;

import com.base.pages.TestDataPropertiesEnum;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Harish
 *
 */

public class AnnotationParameterBuilder extends AbstractFindByBuilder {

    protected By buildParameterizedBy(FindByLanguage findByLanguage) {
        if (!"".equals(findByLanguage.xpath())) {
            return By.xpath(processParameter(findByLanguage.xpath()));
        }
        return null;
    }


    private By buildParameterizedBy(FindByEnvironment findByEnvironment) {
    	 if (!"".equals(findByEnvironment.xpath())) {
             return By.xpath(processParameter(findByEnvironment.xpath()));
         }
         return null;
	}
    
    @Override
    public By buildIt(Object annotation, Field field) {
    	
    	if (annotation instanceof FindByEnvironment) {
    		  FindByEnvironment findByLanguage = (FindByEnvironment)annotation;
   		     return buildParameterizedBy(findByLanguage);
    		
    	} else if (annotation instanceof FindByLanguage) {
    		  FindByLanguage findByLanguage = (FindByLanguage)annotation;
    		 return buildParameterizedBy(findByLanguage);
    	}
    	
        return null;
    }


	private static String processParameter(String input) {
        Pattern p = Pattern.compile("\\{(.+?)\\}");
        Matcher m = p.matcher(input);
        String result = input;
        while (m.find()) {
            String fullMatch = m.group();
            String propName = m.group(1);
            String propValue = ParameterProvider.getInstance().getParameter(TestDataPropertiesEnum.XPATHS.toString(), propName);
            System.out.println(propValue+" ============================= "+propName);
            if (propValue == null) {
                throw new IllegalArgumentException("Cannot find property: " + propName);
            }
            result = result.replace(fullMatch, propValue);
        }
        return result;
    }

}