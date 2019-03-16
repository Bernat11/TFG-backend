package com.upf.etic.documentwithqr.service.impl;

import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionServiceImpl implements VersionService {

    private Version version;

    @Autowired
    public VersionServiceImpl(Version version){
        this.version = version;
    }

    public Version getVersion(){
        return version;
    }

}
