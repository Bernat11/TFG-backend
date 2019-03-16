package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.service.impl.StorageServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.TEMPORARY_DIRECTORY_IMAGES;

public class StorageServiceImplTest {

    StorageService storageService = new StorageServiceImpl();
    File temporaryDirectoryFile = new File(TEMPORARY_DIRECTORY_IMAGES);

    @Test
    public void createTemporaryFolderSuccess() throws StorageServiceException {
        if(temporaryDirectoryFile.exists()) {
            storageService.deleteTemporaryFolderIfExists(TEMPORARY_DIRECTORY_IMAGES);
        }
        storageService.createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES);
        Assert.assertTrue(temporaryDirectoryFile.exists());
    }

    @Test
    public void deleteTemporaryFolderSuccess() throws StorageServiceException {
        storageService.createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES);
        Assert.assertTrue(temporaryDirectoryFile.exists());
    }

}
