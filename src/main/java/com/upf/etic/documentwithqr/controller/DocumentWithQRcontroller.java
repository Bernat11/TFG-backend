package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.exceptions.QRcodeGenerationException;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class DocumentWithQRcontroller {

    Logger logger = LoggerFactory.getLogger(DocumentWithQRcontroller.class);

    private QRgeneratorService generatorService;

    public DocumentWithQRcontroller (QRgeneratorService qRgeneratorService){
        this.generatorService = qRgeneratorService;
    }

    @GetMapping(value = "/encodeURL", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity encodeURL(@RequestParam URL url, ImageDimension imageDimension) {
        logger.info("Rest call received to /encodeURL");
        HttpHeaders headers = new HttpHeaders();
        try {
            ByteArrayResource byteArrayResource = generatorService.generateQRcode(url, imageDimension);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
            return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
        } catch (QRcodeGenerationException | StorageServiceException e) {
            HashMap<String, String> map = new HashMap<>();
            map.put("Error cause", e.getMessage());
            map.put("HTTP code", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return new ResponseEntity<>(map, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}