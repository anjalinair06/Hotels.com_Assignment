package com.hotels.objectRepository;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class SearchResultsPage extends BasePage {

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"filter-popular-contents\"]")
    private WebElement filterBy;

    @FindBy(xpath = "//*[@id=\"sort-submenu-price\"]")
    public WebElement priceSort;

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
        Actions action = new Actions(driver);
        action.moveToElement(priceSort).build().perform();
        int i = 0;
        List<WebElement> priceSortList = priceSort.findElements(By.xpath("//*[@id=\"sort-submenu-price\"]/li"));
        for (WebElement priceSort : priceSortList) {
            String xpath = "//*[@id=\"sort-submenu-price\"]/li[" + ++i + "]";
            if (priceSort.findElement(By.xpath(xpath)).getText().equals(priceSortType)) {
                return priceSort;
            }
//            JavascriptExecutor js = (JavascriptExecutor)driver;
//            js.executeScript("arguments[0].click();", priceSort);
        }
        throw new NoSuchElementException("No Matching sort option found");
    }


}
