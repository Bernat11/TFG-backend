package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private VersionService versionService;
    Logger logger = LoggerFactory.getLogger(VersionController.class);

    public VersionController(VersionService versionService){
        this.versionService = versionService;
    }

    @GetMapping(value = "/version", produces = "application/json")
    public Version getVersion(){
        logger.info("Received rest call to /version");
        return versionService.getVersion();
    }
}
