package com.hotels.test;

import com.hotels.contants.Constants;
import com.hotels.objectRepository.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SearchTest {
    private HomePage homePage;
    private WebDriver driver;

    @BeforeTest
    public void navigateToHomePage(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        Assert.assertEquals(driver.getTitle(), "Hotels.com India", "Error in loading Home page");
        Assert.assertTrue(homePage.destination.isDisplayed(),"Error in loading Home page");
    }

    @Test
    public void searchTest() throws Exception {
        List<Map<String, String>> dataTable = Constants.TEST_DATA;
        for(Map<String, String> row : dataTable){
            homePage.destination.sendKeys(row.get("DESTINATION"));
            TimeUnit.SECONDS.sleep(5);
            homePage.getCity(row.get("CITY")).click();
            homePage.checkInDatePicker.click();
            for(int j=0; j<Integer.parseInt(row.get("CHECK_AVAILABILITY_AFTER")); j++) homePage.nextMonth.click();
            homePage.getCheckInDate().click();
            homePage.rooms.click();
            homePage.selectFromDropDown(homePage.rooms, row.get("NO_OF_ROOMS_PERSONS"));
            TimeUnit.SECONDS.sleep(2);
            homePage.search.click();
        }
    }

    @AfterTest
    public void finalMethod(){
        driver.close();
    }
}
