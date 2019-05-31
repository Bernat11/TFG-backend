package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.error.exception.QRcodeGenerationException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import org.springframework.core.io.ByteArrayResource;

import java.net.URL;

public interface QRgeneratorService {

    /**
     * Generate a QR code image with the desired input URL
     * @param url the input URL to encode in QR
     * @param width the first dimensions (width) of the QR image
     * @param height the first dimensions (height) of the QR image
     * @throws QRcodeGenerationException
     * @return Path the generated QR code location
     */
    ByteArrayResource generateQRcode(String url, Integer width, Integer height, long id) throws QRcodeGenerationException, StorageServiceException;

}
