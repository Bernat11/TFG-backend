package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.model.Version;

public interface VersionService {

    /**
     * Return actual version of document with QR webapp
     */
    Version getVersion();

}
