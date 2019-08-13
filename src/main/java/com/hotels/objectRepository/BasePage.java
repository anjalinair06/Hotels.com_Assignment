package com.hotels.objectRepository;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BasePage {

    public WebDriver driver;
    private String pageURL = "https://in.hotels.com";

    BasePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
    }

    public void getHomePage() {
        driver.get(pageURL);
    }

}
