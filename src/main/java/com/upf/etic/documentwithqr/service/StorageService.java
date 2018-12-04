package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.exceptions.StorageServiceException;

public interface StorageService {

    /**
     * Create a temporary folder if it doesn't exist
     */
    void createTemporaryFolderIfNotExists() throws StorageServiceException;

    /**
     * Create temporary folder after a QR transformation was performed
     */
    void deleteTemporaryFolderIfExists() throws StorageServiceException;

}
