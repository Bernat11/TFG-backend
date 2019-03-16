package com.upf.etic.documentwithqr.controller.controllerIT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.APPLICATION_HOST;
import static com.upf.etic.documentwithqr.constants.ApplicationConstants.APPLICATION_PORT;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class VersionControllerIT extends AbstractTestNGSpringContextTests {

    @Test
    public void getVersionSuccess(){
        when().
                get(APPLICATION_HOST + APPLICATION_PORT + "/api/version").
        then().
                statusCode(200);
    }

}
