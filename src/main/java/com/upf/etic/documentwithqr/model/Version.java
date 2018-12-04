package com.upf.etic.documentwithqr.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Version {

    private String webAppVersion;

    private String qrVersion;

    public Version (@Value("${build.version}") String webAppVersion,
                    @Value("${QR.version}") String qrVersion){
        this.webAppVersion = webAppVersion;
        this.qrVersion = qrVersion;
    }

    public String getWebAppVersion(){
        return webAppVersion;
    }

    public String getQRversion(){
        return qrVersion;
    }

}
