package com.qatestlab.hw4.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Random;

/**
 * Created by Sydorenko B. on 19.11.2018.
 */
public class CreateProduct {

    private WebDriver driver;
    private WebDriverWait webDriverWaiter;
    private String productName;
    private String randomNumber;
    private String randomPrice;



//    @Parameters({"browser"})
//    @BeforeClass
//    public void setUp(String browser) {
//        driver = DriverManager.getConfiguredDriver(browser);
//        webDriverWaiter = new WebDriverWait(driver, 10);
//    }
//
//    @AfterClass
//    public void tearDown() {
//        WebElement userMenu = webDriverWaiter.until(
//                ExpectedConditions.presenceOfElementLocated(By.cssSelector("nav.main-header .person")));
//        userMenu.click();
//
//        WebElement logout = webDriverWaiter.until(
//                ExpectedConditions.elementToBeClickable(By.id("header_logout")));
//        logout.click();
//
//        driver.quit();
//    }
//
//    @Parameters({"url"})
//    @BeforeMethod
//    public void prepareEnvironment(String url) {
//        driver.get(url);
//    }
//
//    @AfterMethod
//    public void clearEnvironment() {
//
//    }
//
//    @Test(dataProvider = "getLoginPass", dataProviderClass = DataTest.class)
//    public void createTestProduct(String login, String passwd) {
//
//        //find DOM elements and do actions
//        WebElement loginInput = driver.findElement(By.id("email"));
//        loginInput.sendKeys(login);
//
//        WebElement passwdInpute = driver.findElement(By.id("passwd"));
//        passwdInpute.sendKeys(passwd);
//        passwdInpute.submit();
//
//        WebElement catalog = webDriverWaiter.until(
//                ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[data-submenu='9']>a"))
//        );
//
//        Actions action = new Actions(driver);
//        action.moveToElement(catalog).build().perform();
//
//        WebElement productSubCatalog = webDriverWaiter.until(
//                ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-submenu='10']>a")));
//        productSubCatalog.click();
//
//        WebElement addProduct = webDriverWaiter.until(
//                ExpectedConditions.elementToBeClickable(By.cssSelector("a#page-header-desc-configuration-add")));
//        addProduct.click();
//
//        //create anonymous class with applay() method,
//        // that check page load state and return Boolean value
//        ExpectedCondition<Boolean> pageLoadCondition = new
//                ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver driver) {
//                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//                    }
//                };
//
//        //waites untile load page
//        webDriverWaiter.until(pageLoadCondition);
//
//        WebElement nameProduct = driver.findElement(By.id("form_step1_name_1"));
//        productName = "SBV_prod_" + getProductName();
//        nameProduct.sendKeys(productName);
//
//        WebElement priceTab = driver.findElement(By.cssSelector("#tab_step2 > a"));
//        priceTab.click();
//
//        WebElement priceProduct = webDriverWaiter.until(
//                ExpectedConditions.presenceOfElementLocated(By.id("form_step2_price")));
//
//        Random ran = new Random ();
//        randomPrice = ("" + ((double)(ran.nextInt(101))/10 + (double)ran.nextInt(91)));
//        priceProduct.sendKeys(randomPrice);
//
//        WebElement numberProduct = driver.findElement(By.cssSelector("#tab_step3 > a"));
//        numberProduct.click();
//
//        WebElement numberProductInpute = webDriverWaiter.until(
//                ExpectedConditions.presenceOfElementLocated(By.id("form_step3_qty_0")));
//        randomNumber = "" + ran.nextInt(101);
//        numberProductInpute.sendKeys(randomNumber);
//    }
//
//    @Test(dependsOnMethods = "createTestProduct")
    @Test
    public void checkTestProduct() {


        driver = DriverManager.getConfiguredDriver("chrome");
        webDriverWaiter = new WebDriverWait(driver, 10);

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        //find DOM elements and do actions
        WebElement loginInput = driver.findElement(By.id("email"));
        loginInput.sendKeys("webinar.test@gmail.com");

        WebElement passwdInpute = driver.findElement(By.id("passwd"));
        passwdInpute.sendKeys(" Xcg7299bnSmMuRLp9ITw");
        passwdInpute.submit();

//        WebElement logo = driver.findElement(By.className("logo"));
//        logo.click();

        //create anonymous class with applay() method,
        // that check page load state and return Boolean value
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };

        //waites untile load page
        webDriverWaiter.until(pageLoadCondition);

        WebElement catalog = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.id("subtab-AdminCatalog"))
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(catalog).build().perform();

        WebElement productSubCatalog = webDriverWaiter.until(
                ExpectedConditions.elementToBeClickable(By.id("subtab-AdminProducts")));
        productSubCatalog.click();

        WebElement inputpProductFiltrName = driver.findElement(By.cssSelector("input.form-control[name='filter_column_name']"));
        inputpProductFiltrName.sendKeys(productName);
        inputpProductFiltrName.submit();

        WebElement product = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("td > tr:nth-child(3)[data-product-id='1' ] > a")));
        Assert.assertEquals(product.getText(), productName);
        product.click();


    }


    public static String getProductName() {
        String symbols = "1234567890";
        StringBuilder randString = new StringBuilder();
        int count = (int)(Math.random()*10);
        for(int i=0;i<count;i++)
            randString.append(symbols.charAt((int)(Math.random()*symbols.length())));
        return String.valueOf(randString);
    }


}