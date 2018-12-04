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

    private QRgeneratorService mockQRgeneratorService;
    private DocumentWithQRcontroller documentWithQRcontroller;

    @Test
    public void encodeURLwithImageDimensionsInRequestSuccessTest() throws QRcodeGenerationException, MalformedURLException, StorageServiceException {
        this.mockQRgeneratorService = mock(QRgeneratorService.class);
        //TODO: NO PUEDO MOCKEAR LA URL PORQUE ES FINAL
        URL mockUrl = new URL("http://www.google.es");
        ImageDimension mockImageDimension = mock(ImageDimension.class);
        ByteArrayResource mockByteArrayResource = mock(ByteArrayResource.class);
        when(mockQRgeneratorService.generateQRcode(mockUrl, mockImageDimension)).thenReturn(mockByteArrayResource);
        this.documentWithQRcontroller = new DocumentWithQRcontroller(mockQRgeneratorService);
        ResponseEntity responseEntity = documentWithQRcontroller.encodeURL(mockUrl, mockImageDimension);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE).contains(MediaType.IMAGE_PNG_VALUE));
    }

    @Test
    public void encodeURLwithImageDimensionsInRequestFailsTest() throws MalformedURLException, QRcodeGenerationException, StorageServiceException {
        this.mockQRgeneratorService = mock(QRgeneratorService.class);
        //TODO: NO PUEDO MOCKEAR LA URL PORQUE ES FINAL
        URL mockUrl = new URL("http://www.google.es");
        ImageDimension mockImageDimension = mock(ImageDimension.class);
        ByteArrayResource mockByteArrayResource = mock(ByteArrayResource.class);
        when(mockQRgeneratorService.generateQRcode(mockUrl, mockImageDimension)).thenThrow(QRcodeGenerationException.class);
        this.documentWithQRcontroller = new DocumentWithQRcontroller(mockQRgeneratorService);
        ResponseEntity responseEntity = documentWithQRcontroller.encodeURL(mockUrl, mockImageDimension);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
