package com.gorest.crudtest;

import com.gorest.testbase.TestBase;
import com.gorest.userinfo.UserSteps;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {
    static String name = "prime" + TestUtils.getRandomValue() ;
    static String email = TestUtils.getRandomValue() + "PrimeUser@gmail.com";
    static String gender = "Female";
    static String status = "Active";
    static int userID;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(name, email, gender, status);
        response.statusCode(201);
        userID = response.log().all().extract().path("id");
    }

    @Test
    public void test002() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByUserID(userID);
        Assert.assertThat(userMap, hasValue(name));

    }

    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {
        name = "Megha" + TestUtils.getRandomValue();
        email = TestUtils.getRandomValue() + "megha@gmail.com" ;
        userSteps.updateUser(userID, name, email, gender, status).statusCode(200);
        HashMap<String, Object> userMap = userSteps.getUserInfoByUserID(userID);
        Assert.assertThat(userMap, hasValue(userID));
    }

    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test004() {
        userSteps.deleteUser(userID).statusCode(204);
        userSteps.getUserById(userID).statusCode(404);
    }
}