package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.service.impl.StorageServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.TEMPORARY_DIRECTORY_IMAGES_COMPRESSED;

public class StorageServiceImplTest {

    StorageService storageService = new StorageServiceImpl();
    File temporaryDirectoryFile = new File(TEMPORARY_DIRECTORY_IMAGES_COMPRESSED);

    @Test
    public void createTemporaryFolderSuccess() throws StorageServiceException {
        if(temporaryDirectoryFile.exists()) {
            storageService.deleteTemporaryFolderIfExists(TEMPORARY_DIRECTORY_IMAGES_COMPRESSED);
        }
        storageService.createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES_COMPRESSED);
        Assert.assertTrue(temporaryDirectoryFile.exists());
    }

    @Test
    public void deleteTemporaryFolderSuccess() throws StorageServiceException {
        storageService.createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES_COMPRESSED);
        Assert.assertTrue(temporaryDirectoryFile.exists());
    }

}
