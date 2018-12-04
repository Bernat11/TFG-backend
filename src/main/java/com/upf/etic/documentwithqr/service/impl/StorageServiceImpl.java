package com.upf.etic.documentwithqr.service.impl;

import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.service.StorageService;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.TEMPORARY_DIRECTORY;

@Service
public class StorageServiceImpl implements StorageService {

    Logger logger = LoggerFactory.getLogger(StorageService.class);

    public void createTemporaryFolderIfNotExists() throws StorageServiceException {
        File file = new File(TEMPORARY_DIRECTORY);
        if(!file.exists()){
            try {
                FileUtils.forceMkdir(file);
                logger.info("Created temporary folder in: {}", TEMPORARY_DIRECTORY);
            } catch (IOException e){
                throw new StorageServiceException("An error ocurred creating the temporary folder");
            }
        }
    }

    public void deleteTemporaryFolderIfExists() throws StorageServiceException {
        File file = new File(TEMPORARY_DIRECTORY);
        if(file.exists()){
            try {
                for(File f:file.listFiles()) {
                    FileUtils.deleteDirectory(f);
                }
                logger.info("Deleted temporary folder and all it's content: {}", TEMPORARY_DIRECTORY);
            } catch (IOException e){
                throw new StorageServiceException("An error ocurred during the temporary folder deletion");
            }
        }
    }

}
