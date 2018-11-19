package com.qatestlab.hw4.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

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
    public void signIn(String login, String passwd) {
        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        //find DOM elements and do actions
        WebElement loginInput = driver.findElement(By.id("email"));
        loginInput.sendKeys("webinar.test@gmail.com");

        WebElement passwdInpute = driver.findElement(By.id("passwd"));
        passwdInpute.sendKeys("Xcg7299bnSmMuRLp9ITw");
        passwdInpute.submit();
    }


}
