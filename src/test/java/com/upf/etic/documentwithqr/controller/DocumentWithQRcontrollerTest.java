package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.exceptions.QRcodeGenerationException;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentWithQRcontrollerTest {

    @Test
    public void encodeURLwithImageDimensionsInRequestSuccessTest() throws QRcodeGenerationException, MalformedURLException, StorageServiceException {
        //TODO: NO PUEDO MOCKEAR LA URL PORQUE ES FINAL
        URL mockUrl = new URL("http://www.google.es");
        QRgeneratorService mockQRgeneratorService = mock(QRgeneratorService.class);
        ImageDimension mockImageDimension = mock(ImageDimension.class);
        ByteArrayResource mockByteArrayResource = mock(ByteArrayResource.class);
        when(mockQRgeneratorService.generateQRcode(mockUrl, mockImageDimension)).thenReturn(mockByteArrayResource);
        DocumentWithQRcontroller documentWithQRcontroller = new DocumentWithQRcontroller(mockQRgeneratorService);
        ResponseEntity responseEntity = documentWithQRcontroller.encodeURL(mockUrl, mockImageDimension);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE).contains(MediaType.IMAGE_PNG_VALUE));
    }

    @Test
    public void encodeURLwithImageDimensionsInRequestFailsTest() throws MalformedURLException, QRcodeGenerationException, StorageServiceException {
        //TODO: NO PUEDO MOCKEAR LA URL PORQUE ES FINAL
        URL url = new URL("http://www.google.es");
        QRgeneratorService mockQRgeneratorService = mock(QRgeneratorService.class);
        ImageDimension mockImageDimension = mock(ImageDimension.class);
        when(mockQRgeneratorService.generateQRcode(url, mockImageDimension)).thenThrow(QRcodeGenerationException.class);
        DocumentWithQRcontroller documentWithQRcontroller = new DocumentWithQRcontroller(mockQRgeneratorService);
        ResponseEntity responseEntity = documentWithQRcontroller.encodeURL(url, mockImageDimension);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
