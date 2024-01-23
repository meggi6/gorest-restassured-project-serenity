package com.gorest.testbase;

import com.gorest.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;


public class TestBase {
    @BeforeClass
    public static void inIt() {
       RestAssured.baseURI = PropertyReader.getInstance().getProperty("baseUrl");
    }
}
