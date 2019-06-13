package com.upf.etic.documentwithqr.controller.controllerIT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.APPLICATION_PORT_TEST;
import static com.upf.etic.documentwithqr.constants.ApplicationConstants.APPLICATION_TEST_HOST;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class DocumentWithQRcontrollerIT extends AbstractTestNGSpringContextTests {

    @Test
    public void encodeUrlSuccess() {
        given().
                param("url", "http://www.documentaconqr.com/api/encodeurl").
                param("height", 300).
                param("width", 300).
                param("id", "1").
        when().
                get(APPLICATION_TEST_HOST + APPLICATION_PORT_TEST + "/api/encodeurl").
        then().
                statusCode(200);
    }

}
