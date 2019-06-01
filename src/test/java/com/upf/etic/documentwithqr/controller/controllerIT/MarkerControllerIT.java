package com.upf.etic.documentwithqr.controller.controllerIT;

import com.upf.etic.documentwithqr.error.exception.JSONReaderException;
import com.upf.etic.documentwithqr.util.Utils;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.File;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class MarkerControllerIT extends AbstractTestNGSpringContextTests {

    public static String pathToResoruces = "src\\test\\resources\\markers";
    private String host = APPLICATION_TEST_HOST;
    private String port = APPLICATION_PORT_TEST;

    @DirtiesContext
    @Test
    public void createMarker() throws JSONReaderException {
        JSONObject jsonObject = Utils.getJSONfromFile(pathToResoruces + "\\create\\Graffiti dragón poblenou\\grafiti_dragon.json");
        File imagen = new File(pathToResoruces + "\\create\\Graffiti dragón poblenou\\dragon_grafiti_pb9.jpg");
        given().
                multiPart("imagen", imagen).
                multiPart("stringMarker", jsonObject.toJSONString()).
        when().
                post(host + port + "/api/markers").
        then().
                statusCode(201);
    }

    @DirtiesContext
    @Test
    public void getMarkerByIdSuccess() throws JSONReaderException {
        createMarker();
        when().
                get(host + port + "/api/markers/1").
        then().
                statusCode(200).
                body("id", Matchers.is(1)).
                body("titulo", Matchers.is("Grafiti dragon")).
                body("descripcion", Matchers.is("Grafiti de un dragon en poblenou"));
    }

    @DirtiesContext
    @Test
    public void getMarkersSuccess() throws JSONReaderException {
        createMarker();
        createMarker2();

        when().
                get(host + port + "/api/markers").
        then()
                .statusCode(200).
                body("titulo[0]", is("Grafiti dragon")).
                body("titulo[1]", is("Arco de triumfo"));
    }

    @DirtiesContext
    @Test
    public void updateMarkerSuccess() throws JSONReaderException {
        createMarker();
        given().
                param("titulo", "edited").
                param("descripcion", "edited").
        when().
                put(host + port + "/api/markers/1").
        then().
                statusCode(201).
                body("titulo", is("edited")).
                body("descripcion", is("edited"));

        when().
                get(host + port + "/api/markers/1").
        then().
                statusCode(200).
                assertThat().body("id", Matchers.is(1)).
                assertThat().body("titulo", Matchers.is("edited")).
                assertThat().body("descripcion", Matchers.is("edited"));

    }

    @DirtiesContext
    @Test
    public void deleteMarkerByIdSuccess() throws JSONReaderException {
        createMarker();
        when().
                delete(host + port + "/api/markers/1").
        then().
                statusCode(204);
    }

    @DirtiesContext
    @Test
    public void deleteMarkers() throws JSONReaderException {
        createMarker();
        createMarker2();
        when().
                delete(host + port + "/api/markers").
        then().
                statusCode(204);
    }

    @DirtiesContext
    @Test
    public void countAllMarkers() throws JSONReaderException {
        createMarker();
        createMarker2();
        when().
                get(host + port + "/api/markers/count").
        then()
                .body(containsString("2"));
    }

    @DirtiesContext
    @Test
    public void findMarkersByType() throws JSONReaderException {
        createMarker();
        when().
                get(host + port + "/api/markers/find?tipo=Arte urbano").
        then()
                .statusCode(200)
                .body("titulo[0]", Matchers.is("Grafiti dragon"));
    }

    private void createMarker2() throws JSONReaderException {
        JSONObject jsonObject= Utils.getJSONfromFile(pathToResoruces + "\\create\\Arco de triumfo\\arco_triumfo.json");
        File imagen = new File(pathToResoruces + "\\create\\Arco de triumfo\\arco_triumfo.jpg");
        given().
                multiPart("imagen", imagen).
                multiPart("stringMarker", jsonObject.toJSONString()).
        when().
                post(host + port + "/api/markers").
        then().
                statusCode(201);
    }

}
