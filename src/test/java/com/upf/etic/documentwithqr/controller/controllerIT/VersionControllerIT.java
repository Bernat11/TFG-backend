package com.upf.etic.documentwithqr.controller.controllerIT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.*;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class VersionControllerIT extends AbstractTestNGSpringContextTests {

    @Test
    public void getVersionSuccess(){
        when().
                get(APPLICATION_TEST_HOST + APPLICATION_PORT_TEST + "/api/version").
        then().
                statusCode(200);
    }

}
