package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.exceptions.QRcodeGenerationException;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import org.springframework.core.io.ByteArrayResource;

import java.net.URL;

public interface QRgeneratorService {

    /**
     * Generate a QR code image with the desired input URL
     * @param url the input URL to encode in QR
     * @param imageDimension the dimensions (width & height) of the output image
     * @throws QRcodeGenerationException
     * @return Path the generated QR code location
     */
    ByteArrayResource generateQRcode(URL url, ImageDimension imageDimension) throws QRcodeGenerationException, StorageServiceException;

}
