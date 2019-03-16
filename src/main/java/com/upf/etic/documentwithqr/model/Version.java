package com.upf.etic.documentwithqr.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Version {

    @Value("${build.version}")
    private String webAppVersion;

    @Value("${QR.version}")
    private String qrVersion;

    public String getWebAppVersion(){
        return webAppVersion;
    }

    public String getQRversion(){
        return qrVersion;
    }

}
