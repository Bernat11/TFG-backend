package com.upf.etic.documentwithqr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.upf.etic.documentwithqr.exceptions.QRcodeGenerationException;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
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

    private void init(){
        this.qRgeneratorService = new QRgeneratorServiceImpl();
    }

    @Test
    public void generateQRcodeSuccess() throws MalformedURLException, QRcodeGenerationException, StorageServiceException {
        init();
        URL url = new URL("http://www.google.es");
        ImageDimension mockImageDimension = mock(ImageDimension.class);
        ByteArrayResource byteArrayResource = qRgeneratorService.generateQRcode(url, mockImageDimension, 1000);
        Assert.assertNotNull(byteArrayResource);
    }

    @Test(enabled = false)
    public void generateQRthrowsQRcodeGenerationException() throws MalformedURLException, QRcodeGenerationException, StorageServiceException, WriterException {
        init();
        URL url = new URL("http://www.google.es");
        QRCodeWriter mockQrCodeWriter = mock(QRCodeWriter.class);
        ImageDimension mockImageDimension = mock(ImageDimension.class);
        when(mockQrCodeWriter.encode(url.toString(), BarcodeFormat.QR_CODE, 300, 300)).thenThrow(new WriterException());
        qRgeneratorService.generateQRcode(url, mockImageDimension, 1000);
    }

}
