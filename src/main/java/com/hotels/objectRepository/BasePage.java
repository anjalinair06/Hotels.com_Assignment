package com.hotels.objectRepository;

import org.openqa.selenium.WebDriver;

public class BasePage {

    private WebDriver driver;
    private String pageURL = "https://in.hotels.com";

    BasePage(WebDriver driver){
        this.driver = driver;
    }

    public void getHomePage(){
        driver.get(pageURL);
    }
}
