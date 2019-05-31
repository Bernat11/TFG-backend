package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.error.exception.JSONReaderException;
import com.upf.etic.documentwithqr.error.exception.QRcodeGenerationException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.VersionService;
import com.upf.etic.documentwithqr.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

@Api(value="markers", description="Operations to retrieve actual application version", tags = { "Version" })
@RestController
@CrossOrigin(origins = "*")
public class VersionController {

    private VersionService versionService;

    @Autowired
    public VersionController(VersionService versionService){
        this.versionService  = versionService;
    }

    private Logger logger = LoggerFactory.getLogger(VersionController.class);

    @ApiOperation(value = "Retrieve the actual application version", response = List.class, position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved application version"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/api/version", produces = "application/json")
    public Version getVersion(){
        logger.info("Received rest call to /version with 'GET' request method");
        return versionService.getVersion();
    }

    @ApiOperation(value = "Initialize database with test markers", response = List.class, position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully initialized database with test markers"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value="/init")
    public void init() throws JSONReaderException, QRcodeGenerationException, StorageServiceException, MalformedURLException {
        Utils.createFullMarkers();
    }
}
