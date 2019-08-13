package com.hotels.objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        getHomePage();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='qf-0q-destination']")
    public WebElement destination;

    @FindBy(xpath = "//table/tbody[1][@class='autosuggest-city']")
    private WebElement city;

    @FindBy(xpath = "/html/body/div[6]/div[1]/div[2]/div[1]/button[2]")
    public WebElement nextMonth;

    @FindBy(xpath = "//*[@id='widget-query-label-1']")
    public WebElement checkInDatePicker;

    @FindBy(xpath = "/html/body/div[6]/div[1]/div[1]/div[2]/table/tbody")
    private WebElement checkInDateTable;

    @FindBy(xpath = "//select[@id='qf-0q-compact-occupancy']")
    public WebElement rooms;

    @FindBy(xpath = "//*[@id='hds-marquee']/div[3]/div[1]/div/form/div[5]/button")
    public WebElement search;

    @FindBy(xpath = "//*[@id=''managed-overlay']/button")
    public WebElement popUpClose;

    @FindBy(xpath = "//*[@id='managed-overlay']/div/div/div[4]/div/div/div/div/a")
    public WebElement continueToHotelDotCom;

    public WebElement getCity(String cityName) {
        int i = 0;
        List<WebElement> cityList = city.findElements(By.xpath("//table/tbody[1][@class='autosuggest-city']/tr"));
        for (WebElement city : cityList) {
            String xpath = "//table/tbody[1][@class='autosuggest-city']/tr[" + ++i + "]/td/div[2]";
            if (city.findElement(By.xpath(xpath)).getText().equals(cityName)) {
                return city;
            }
        }
        throw new NoSuchElementException("No Matching City found");
    }

    public WebElement getCheckInDate() {
        WebElement checkInDate = getDate(checkInDateTable.findElements(By.tagName("tr")));
        if (checkInDate != null)
            return checkInDate;
        else
            throw new NoSuchElementException("Unable to find the check in date");
    }

    public void selectFromDropDown(WebElement webElement, String visibleText) {
        Select select = new Select(webElement);
        select.selectByVisibleText(visibleText);
    }

    private WebElement getDate(List<WebElement> dateRows) {
        int dayOfMonth = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH);
        for (WebElement row : dateRows) {
            List<WebElement> dateCols = row.findElements(By.tagName("td"));
            for (WebElement col : dateCols) {
                if (col.getText().equals(Integer.toString(dayOfMonth))) {
                    return col;
                }
            }
        }
        return null;
    }
}
