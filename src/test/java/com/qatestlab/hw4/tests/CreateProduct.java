package com.qatestlab.hw4.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import javax.swing.*;
import java.util.function.Predicate;


/**
 * Created by Sydorenko B. on 19.11.2018.
 */
public class CreateProduct {

    private WebDriver driver;
    private WebDriverWait webDriverWaiter;


    @Parameters ({"browser"})
    @BeforeClass
    public void setUp(String browser) {
       driver = DriverManager.getConfiguredDriver(browser);
       webDriverWaiter = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Parameters ({"url"})
    @BeforeMethod
    public void prepareEnvironment(String url) {
        driver.get(url);
    }

    @AfterMethod
    public void clearEnvironment() {
        WebElement userMenu = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.className("employee_name")));
        userMenu.click();

        WebElement logout = webDriverWaiter.until(
                ExpectedConditions.elementToBeClickable(By.id("header_logout")));
        logout.click();
    }

    @Test(dataProvider  = "getLoginPass", dataProviderClass = DataTest.class)
    public void createTestProduct(String login, String passwd) {

        //find DOM elements and do actions
        WebElement loginInput = driver.findElement(By.id("email"));
        loginInput.sendKeys(login);

        WebElement passwdInpute = driver.findElement(By.id("passwd"));
        passwdInpute.sendKeys(passwd);
        passwdInpute.submit();

        WebElement catalog = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@id='subtab-AdminCatalog']/a"))
        );

        Actions action = new Actions(driver);
        action.moveToElement(catalog).build().perform();

        WebElement categoryProduct = webDriverWaiter.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("li#subtab-AdminProducts>a")));
        categoryProduct.click();

        WebElement addProduct = webDriverWaiter.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a#page-header-desc-configuration-add")));
        addProduct.click();


//        webDriverWaiter.until( new Predicate<WebDriver>() {
//                        boolean test(WebDriver driver) {
//                            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//                        }
//                    }
//        );

    }

//    @Test
//    public void checkTestProduct() {
//
//    }


}
