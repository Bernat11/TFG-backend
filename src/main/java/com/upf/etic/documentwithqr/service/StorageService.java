package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    /**
     * Create a temporary folder if it doesn't exist
     */
    void createTemporaryFolderIfNotExists(String temporaryFolderPath) throws StorageServiceException;

    /**
     * Create temporary folder after a QR transformation was performed
     */
    void deleteTemporaryFolderIfExists(String temporaryFolderPath) throws StorageServiceException;

    /**
     * Store MultipartFile (image) in system
     */
    String storeMultiPartImage(MultipartFile multipartFile, String UUID) throws StorageServiceException, IOException;

    /**
     * Retrieve image file as bytes from file system
     */
    byte[] getImage(String titulo) throws IOException;
}
