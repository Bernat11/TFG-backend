package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.error.exception.QRcodeGenerationException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value="QR controller", description="Operations pertaining to QR generation", tags = { "QR code" })
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

    @ApiOperation(value = "Produces QR image with an input URL", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully encoded URL into a QR code"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 500, message = "Error while trying to encode URL into QR code")
    })
    @GetMapping(value = "/encodeurl", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity encodeURL(@RequestParam(name = "url")
                                            @ApiParam(required = true, value = "The URL to encode in QR") String url,
                                    @RequestParam(name = "width", required = false)
                                            @ApiParam(value = "The QR width dimension") Integer width,
                                    @RequestParam(name = "height", required = false)
                                            @ApiParam(value = "The QR height dimension") Integer height,
                                    @RequestParam(name = "id")
                                            @ApiParam(required = true, value = "The QR identifier") String id) {

        logger.info("Rest call received to /api/encodeurl with 'GET' request method");
        HttpHeaders headers = new HttpHeaders();
        try {
            ByteArrayResource byteArrayResource = generatorService.generateQRcode(url, width, height, Long.parseLong(id));
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