package com.upf.etic.documentwithqr.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.upf.etic.documentwithqr.error.exception.QRcodeGenerationException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import com.upf.etic.documentwithqr.service.StorageService;
import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class QRgeneratorServiceImpl implements QRgeneratorService {

    Logger logger = LoggerFactory.getLogger(QRgeneratorServiceImpl.class);

    public ByteArrayResource generateQRcode(String url, Integer width, Integer height, long id) throws
            QRcodeGenerationException, StorageServiceException {

        try{
            createUrlObject(url);
        } catch (MalformedURLException e){
            throw new QRcodeGenerationException("The url does not have a valid format");
        }

        ImageDimension imageDimension = createImageDimensionObject(width, height);
        StorageService storageService = new StorageServiceImpl();
        storageService.createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES_COMPRESSED);
        String qrCompressedImage = TEMPORARY_DIRECTORY_IMAGES_COMPRESSED + id + "_compressed.jpg";
        logger.info("QR destination directory set to: {}", qrCompressedImage);

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url.toString(), BarcodeFormat.QR_CODE, imageDimension.getWidth(),
                    imageDimension.getHeight());
            logger.info("Start writting QR code to disk...");
            MatrixToImageWriter.writeToPath(bitMatrix, IMAGE_FORMAT_NAME, Paths.get(qrCompressedImage));
            logger.info("QR code successfully writted to disk");
        } catch (WriterException | IOException e){
            throw new QRcodeGenerationException("There was an error during QR code generation");
        }

        try (InputStream in = new FileInputStream(qrCompressedImage)){
            return new ByteArrayResource(IOUtils.toByteArray(in));
        } catch (IOException e){
            throw new QRcodeGenerationException("There was an error obtaining QR image from server");
        }
    }

    private ImageDimension createImageDimensionObject(Integer width, Integer height){
        if(width == null || height == null){
            width = 300;
            height = 300;
        }
        return new ImageDimension(width, height);
    }

    private URL createUrlObject(String url) throws MalformedURLException {
        return new URL(url);
    }

}
