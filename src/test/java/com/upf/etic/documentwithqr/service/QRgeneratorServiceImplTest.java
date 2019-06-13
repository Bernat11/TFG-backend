package com.upf.etic.documentwithqr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.upf.etic.documentwithqr.error.exception.QRcodeGenerationException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import com.upf.etic.documentwithqr.service.impl.QRgeneratorServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class QRgeneratorServiceImplTest {

    private QRgeneratorService qRgeneratorService;
    private String url = "http://www.google.es";;

    private void init(){
        this.qRgeneratorService = new QRgeneratorServiceImpl();
    }

    @Test
    public void generateQRcodeSuccess() throws MalformedURLException,
            QRcodeGenerationException, StorageServiceException {
        init();
        String url = "http://www.google.es";
        ByteArrayResource byteArrayResource = qRgeneratorService.generateQRcode(url,
                300, 300, 1000);
        Assert.assertNotNull(byteArrayResource);
    }

    @Test(enabled = false)
    public void generateQRthrowsQRcodeGenerationException() throws MalformedURLException,
            QRcodeGenerationException, StorageServiceException, WriterException {
        init();
        QRCodeWriter mockQrCodeWriter = mock(QRCodeWriter.class);
        when(mockQrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300))
                .thenThrow(new WriterException());
        qRgeneratorService.generateQRcode(url, 300, 300, 1000);
    }

}
