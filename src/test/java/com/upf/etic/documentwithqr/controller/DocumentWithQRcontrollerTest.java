package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.exceptions.QRcodeGenerationException;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import com.upf.etic.documentwithqr.service.impl.QRgeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
public class DocumentWithQRcontrollerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DocumentWithQRcontroller documentWithQRcontroller;

    @Test
    public void encodeURLSuccessTest() throws QRcodeGenerationException, MalformedURLException, StorageServiceException {
        ResponseEntity responseEntity = documentWithQRcontroller.encodeURL(new URL("http://www.google.es"), 300, 300, "1000");
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE).contains(MediaType.IMAGE_PNG_VALUE));
    }

    @Test
    public void encodeURLThrowsQRCodeGenerationExceptionTest() throws MalformedURLException, QRcodeGenerationException, StorageServiceException {
        QRgeneratorService mockQRgeneratorService = mock(QRgeneratorServiceImpl.class);
        when(mockQRgeneratorService.generateQRcode(any(URL.class), any(ImageDimension.class), any(Long.class))).thenThrow(new QRcodeGenerationException("An exception occurred"));
        DocumentWithQRcontroller documentWithQRcontroller = new DocumentWithQRcontroller(mockQRgeneratorService);
        ResponseEntity responseEntity = documentWithQRcontroller.encodeURL(new URL("http://www.google.es"), 300, 300, "1000");
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
