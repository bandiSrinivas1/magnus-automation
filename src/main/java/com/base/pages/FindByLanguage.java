package com.base.pages;

import java.lang.annotation.Retention;

import org.openqa.selenium.support.PageFactoryFinder;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Harish
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD , ElementType.FIELD})
@PageFactoryFinder(AnnotationParameterBuilder.class)
public @interface FindByLanguage{

    String xpath() default "";

}

