package com.hotels.objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//*[@id='search']/div[1]/div/h1")
    public WebElement searchResultHeader;
    @FindBy(xpath = "//*[@id='sort-submenu-price']")
    public WebElement priceHighToLow;
    @FindBy(xpath = "//*[@id='enhanced-sort']/li[5]/a")
    public WebElement price;
    @FindBy(xpath = "//*[@id='listings']/ol")
    public WebElement searchResult;
    @FindBy(xpath = "//*[@id='filter-popular-contents']")
    public WebElement filterBy;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getFilter(String filterType) {
        int i = 0;
        List<WebElement> filterList = filterBy.findElements(By.xpath("//*[@id=\"filter-popular-contents\"]/ul/li"));
        for (WebElement filterBy : filterList) {
            String xpath = "//*[@id=\"filter-popular-contents\"]/ul/li[" + ++i + "]";
            if (filterBy.findElement(By.xpath(xpath)).getText().equals(filterType)) {
                return filterBy;
            }
        }
        throw new NoSuchElementException("No Matching filter found");
    }


    public WebElement getPriceSort(String priceSortType) {
        price.click();
        int i = 0;
        List<WebElement> priceSortList = priceHighToLow.findElements(By.xpath("//*[@id=\"sort-submenu-price\"]/li"));
        for (WebElement priceSort : priceSortList) {
            String xpath = "//*[@id=\"sort-submenu-price\"]/li[" + ++i + "]";
            if (priceSort.findElement(By.xpath(xpath)).getText().equals(priceSortType)) {
                return priceSort;
            }
        }
        throw new NoSuchElementException("No Matching sort option found");
    }

    public List<Map<String, String>> getTopThree() {
        List<WebElement> hotelList = searchResult.findElements(By.tagName("li"));
        List<Map<String, String>> topThreeHotels = new ArrayList<>();
        String hotelName;
        String price;
        int i = 0;
        for (WebElement hotel : hotelList) {
            if (topThreeHotels.size() == 3) break;
            try {
                driver.findElement(By.xpath("//*[@id='listings']/ol/li[" + ++i + "]/article/section/aside/p")).isDisplayed();
            } catch (NoSuchElementException e) {
                if (driver.findElements(By.xpath("//*[@id='listings']/ol/li[" + i + "]/article/section/aside/div[1]/a/strong")).size() > 0) {
                    Map<String, String> row = new HashMap<>();
                    hotelName = hotel.findElement(By.xpath("//*[@id='listings']/ol/li[" + i + "]/article/section/div/h3/a")).getText();
                    price = hotel.findElement(By.xpath("//*[@id='listings']/ol/li[" + i + "]/article/section/aside/div[1]/a/strong")).getText();
                    row.put("HOTEL_NAME", hotelName);
                    row.put("PRICE", price);
                    topThreeHotels.add(row);
                }
            }
        }
        return topThreeHotels;

    }

}
