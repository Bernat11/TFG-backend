package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.exceptions.QRcodeGenerationException;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.model.ImageDimension;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DocumentWithQRcontroller {

    private Logger logger = LoggerFactory.getLogger(DocumentWithQRcontroller.class);

    private QRgeneratorService generatorService;

    @Autowired
    public DocumentWithQRcontroller(QRgeneratorService generatorService){
        this.generatorService = generatorService;
    }

    @GetMapping(value = "/encodeurl", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity encodeURL(@RequestHeader(name = "url") URL url,
                                    @RequestHeader(name = "width", required = false) int width,
                                    @RequestHeader(name = "height", required = false) int height,
                                    @RequestHeader(name = "id") String id) {
        logger.info("Rest call received to /api/encodeurl with 'GET' request method");
        ImageDimension imageDimension = new ImageDimension(width, height);
        HttpHeaders headers = new HttpHeaders();
        try {
            ByteArrayResource byteArrayResource = generatorService.generateQRcode(url, imageDimension, Long.parseLong(id));
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