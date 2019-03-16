package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class VersionController {

    private VersionService versionService;

    @Autowired
    public VersionController(VersionService versionService){
        this.versionService  = versionService;
    }

    private Logger logger = LoggerFactory.getLogger(VersionController.class);

    @GetMapping(value = "/api/version", produces = "application/json")
    public Version getVersion(){
        logger.info("Received rest call to /version with 'GET' request method");
        return versionService.getVersion();
    }
}
