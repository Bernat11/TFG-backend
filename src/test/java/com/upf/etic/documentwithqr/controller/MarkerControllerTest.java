package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.error.exception.RepositoryException;
import com.upf.etic.documentwithqr.service.MarkerService;
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
    private MarkerService markerService;

    @Test
    public void getImageByIdSuccessTest() throws IOException {
        StorageService storageService = Mockito.mock(StorageService.class);
        Mockito.when(storageService.getImage("UUID")).thenReturn("IMAGE".getBytes());
        MarkerController markerController = new MarkerController(markerService, storageService);
        Assert.assertEquals(markerController.getImage("UUID").getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void countMarkersByTypeSuccess() throws RepositoryException {
        MarkerService markerServiceMock = Mockito.mock(MarkerService.class);
        Mockito.when(markerServiceMock.countByType("Monumento")).thenReturn(1);
        MarkerController markerController = new MarkerController(markerServiceMock, storageService);
        Assert.assertEquals(markerController.count("Monumento"),1);
    }

}
