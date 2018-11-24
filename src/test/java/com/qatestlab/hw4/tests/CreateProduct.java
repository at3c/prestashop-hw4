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

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 * Created by Sydorenko B. on 19.11.2018.
 */
public class CreateProduct {

    private WebDriver driver;
    private WebDriverWait webDriverWaiter;
    private String productName;
    private String randomNumber;
    private String randomPrice;



    @Parameters({"browser"})
    @BeforeClass
    public void setUp(String browser) {
        driver = DriverManager.getConfiguredDriver(browser);
        webDriverWaiter = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public void tearDown() {
        //driver.quit();
    }


    @BeforeMethod
    public void prepareEnvironment() {

    }

    @AfterMethod
    public void clearEnvironment() {

    }

    @Test(dataProvider = "getLoginPass", dataProviderClass = DataTest.class)
    public void createTestProduct(String login, String passwd) throws InterruptedException {

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        //find DOM elements and do actions
        WebElement loginInpute = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.id("email")));

        loginInpute.clear();
        loginInpute.sendKeys(login);

        WebElement passInput = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.id("passwd")));
        passInput.clear();
        passInput.sendKeys(passwd);
        passInput.submit();

        WebElement catalog = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#subtab-AdminCatalog > a")));

        Actions action = new Actions(driver);
        action.moveToElement(catalog).build().perform();

        WebElement productSubCatalog = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#subtab-AdminProducts > a")));
        productSubCatalog.click();

        WebElement addProduct = webDriverWaiter.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a#page-header-desc-configuration-add")));
        addProduct.click();

        ExpectedCondition<Boolean> pageLoadCondition = pageLoadExpectd();

        //waites untile load page
        webDriverWaiter.until(pageLoadCondition);

        Random ran = new Random ();

        WebElement nameProduct = driver.findElement(By.id("form_step1_name_1"));
        productName = "SBV_prod_" + ran.nextInt(1000);
        nameProduct.sendKeys(productName);

        WebElement priceTab = driver.findElement(By.cssSelector("#tab_step2 > a"));
        priceTab.click();

        WebElement priceProduct = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.id("form_step2_price")));

        randomPrice = ("" + ((double)(ran.nextInt(101))/10 + (double)ran.nextInt(91))).replace(".",",") + "0";
        priceProduct.clear();
        priceProduct.sendKeys(randomPrice);

        WebElement numberProduct = driver.findElement(By.cssSelector("#tab_step3 > a"));
        numberProduct.click();

        WebElement numberProductInpute = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.id("form_step3_qty_0")));
        randomNumber = "" + ran.nextInt(101);
        numberProductInpute.sendKeys(randomNumber);

        WebElement switchStatus = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.className("switch-input ")));
        switchStatus.click();

        WebElement mssgActiveStatusClose = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#growls .growl-close")));
        mssgActiveStatusClose.click();

        WebElement btnSubmit = driver.findElement(By.cssSelector("button.js-btn-save"));
        btnSubmit.submit();

        webDriverWaiter.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("growl")));

        WebElement mssgSubmitStatusClose = webDriverWaiter.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#growls .growl-close")));
        mssgSubmitStatusClose.click();

        Thread.sleep(1000);

        //logOut from Admin panel
        WebElement userMenu = webDriverWaiter.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".person i")));
        userMenu.click();

        WebElement logout = webDriverWaiter.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("header_logout")));
        action.moveToElement(logout).build().perform();
        logout.click();

    }

    @Test(dependsOnMethods = "createTestProduct")
    public void checkTestProduct() {

        driver.get("http://prestashop-automation.qatestlab.com.ua/");


        WebElement allProducts =  webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.className("all-product-link")));
        allProducts.click();

        WebElement searchField = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("form input[name='s']")));
        searchField.sendKeys(productName);
        searchField.submit();

        ExpectedCondition<Boolean> pageLoadCondition = pageLoadExpectd();
        //waites untile load page
        webDriverWaiter.until(pageLoadCondition);

        List<WebElement> addProducts = driver.findElements(By.linkText(productName));
        Assert.assertTrue(true, "Didn't find new product");
        addProducts.get(0).click();

        WebElement nameProductActualWebElement = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1[itemprop='name']")));
        String nameProductActualString = nameProductActualWebElement.getText();
        Assert.assertEquals(nameProductActualString.toLowerCase(), productName.toLowerCase(),
                "Product name is not expected result");

        WebElement quantitiesProductActualWebElement = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-quantities span")));
        String quantitiesProductActualString = quantitiesProductActualWebElement.getText().replaceAll("[^0-9]","");
        Assert.assertEquals(quantitiesProductActualString, randomNumber, "Quantitie is not expected result");

        WebElement priceProductActualWebElement = webDriverWaiter.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".current-price span")));
        String priceProductActualString = priceProductActualWebElement.getText().replaceAll("[^0-9?!\\,]", "");
        Assert.assertEquals(priceProductActualString, randomPrice, "Price is not expected result");
    }

    private ExpectedCondition<Boolean> pageLoadExpectd() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        return pageLoadCondition;
    }
}