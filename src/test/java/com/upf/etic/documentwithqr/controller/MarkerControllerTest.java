package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.dao.MarkerDao;
import com.upf.etic.documentwithqr.service.StorageService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MarkerControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private StorageService storageService;

    @Autowired
    private MarkerDao markerDao;

    @Test
    public void getImageByIdSuccessTest() throws IOException {
        StorageService storageService = Mockito.mock(StorageService.class);
        Mockito.when(storageService.getImage("UUID")).thenReturn("IMAGE".getBytes());
        MarkerController markerController = new MarkerController(markerDao, storageService);
        Assert.assertEquals(markerController.getImage("UUID").getStatusCode(), HttpStatus.OK);
    }

}
