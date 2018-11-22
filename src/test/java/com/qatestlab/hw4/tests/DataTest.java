package com.qatestlab.hw4.tests;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;

/**
 * Created by Admin on 19.11.2018.
 */
public class DataTest {
    @DataProvider (name = "getLoginPass")
    public static Object[][] getLoginPass(){
        return new Object[][]{
            {"webinar.test@gmail.com","Xcg7299bnSmMuRLp9ITw"}
        };
    }
}
