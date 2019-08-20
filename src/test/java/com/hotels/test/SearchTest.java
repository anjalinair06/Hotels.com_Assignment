package com.hotels.test;

import com.hotels.constants.Constants;
import com.hotels.objectRepository.HomePage;
import com.hotels.objectRepository.SearchResultsPage;
import com.hotels.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SearchTest {
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private WebDriver driver;

    @BeforeTest
    public void navigateToHomePage() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver1.exe");
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        Assert.assertEquals(driver.getTitle(), "Hotels.com India", "Error on loading Home page");
        Assert.assertTrue(homePage.destination.isDisplayed(), "Error on loading Home page");
    }

    @Test
    public void searchTest() throws Exception {
        homePage.continueToHotelDotCom.click();
        List<Map<String, String>> dataTable = Constants.TEST_DATA;
        for (Map<String, String> row : dataTable) {
            homePage.destination.sendKeys(row.get("DESTINATION"));
            TimeUnit.SECONDS.sleep(5);
            homePage.getCity(row.get("CITY")).click();
            homePage.checkInDatePicker.click();
            for (int j = 0; j < Integer.parseInt(row.get("CHECK_AVAILABILITY_AFTER")); j++) homePage.nextMonth.click();
            homePage.getCheckInDate().click();
            homePage.rooms.click();
            homePage.selectFromDropDown(homePage.rooms, row.get("NO_OF_ROOMS_PERSONS"));
            homePage.search.click();
            WebDriverWait wait = new WebDriverWait(driver, 5);
            searchResultsPage = new SearchResultsPage(driver);
            wait.until(ExpectedConditions.visibilityOf(searchResultsPage.searchResultHeader));
            searchResultsPage.getFilter(row.get("FILTER_OPTION")).click();
            searchResultsPage.getPriceSort(row.get("SORT_OPTION")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Price (high to low)")));
            ExcelUtils.exportToCSV(searchResultsPage.getTopThree(), Constants.SAMPLE_CSV_FILE);
        }
    }

    @AfterTest
    public void finalMethod() {
        driver.close();
    }
}
