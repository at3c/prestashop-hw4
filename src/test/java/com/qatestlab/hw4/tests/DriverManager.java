package com.qatestlab.hw4.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Static class creates a new <code>WebDriver</code> and
 * <code>EventFiringWebDriver</code> object;
 * Created by Sydorenko B. on 19.11.2018.
 * version 2.1
 */
public class DriverManager {

    private DriverManager(){
    }

    /**
     * method set url to web browser's driver,
     * create connection to different browsers
     * @param browserName the name of the browser to create
     *                    a connection
     * @return an object to use browser
     */
    public static WebDriver getDriver(String browserName) {
        String s = browserName.toLowerCase();
        if (s.equals("ff") || s.equals("firefox")) {
            System.setProperty("webdriver.geckodriver.driver", System.getProperty("user.dir") + "//resources//geckodriver.exe");
            return new FirefoxDriver();
        } else if (s.equals("ie") || s.equals("internet explorer")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//resources//IEDriverServer.exe");
            return new InternetExplorerDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//resources//chromedriver.exe");
            return new ChromeDriver();
        }
    }

    /**
     * method set url to web browser's driver,
     * create connection to different browsers.
     * configuration basic driver's settings.
     * @param browserName the name of the browser to create
     *                    a connection
     * @return an object to use browser as user and
     *         listen to the events during the test
     */
    public static EventFiringWebDriver getConfiguredDriver(String browserName) {
        EventFiringWebDriver driver = new EventFiringWebDriver(getDriver(browserName));
        driver.register(new TestEventHandler());

        driver.manage().timeouts().implicitlyWait(30,  TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60,  TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        driver.manage().window().maximize();
        return driver;
    }
}


