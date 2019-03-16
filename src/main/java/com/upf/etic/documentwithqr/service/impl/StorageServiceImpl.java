package com.upf.etic.documentwithqr.service.impl;

import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.service.StorageService;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.IMAGE_FORMAT_EXTENSION;
import static com.upf.etic.documentwithqr.constants.ApplicationConstants.TEMPORARY_DIRECTORY_IMAGES;

@Service
public class StorageServiceImpl implements StorageService {

    Logger logger = LoggerFactory.getLogger(StorageService.class);

    @Override
    public void createTemporaryFolderIfNotExists(String temporaryFolderPath) throws StorageServiceException {
        File file = new File(temporaryFolderPath);
        if(!file.exists()){
            try {
                FileUtils.forceMkdir(file);
                logger.info("Created temporary folder in: {}", temporaryFolderPath);
            } catch (IOException e){
                throw new StorageServiceException("An error ocurred creating the temporary folder");
            }
        }
    }

    @Override
    public void deleteTemporaryFolderIfExists(String temporaryFolderPath) throws StorageServiceException {
        File file = new File(temporaryFolderPath);
        if(file.exists()){
            try {
                for(File f:file.listFiles()) {
                    FileUtils.deleteDirectory(f);
                }
                file.delete();
                logger.info("Deleted temporary folder and all it's content: {}", temporaryFolderPath);
            } catch (IOException e){
                throw new StorageServiceException("An error ocurred during the temporary folder deletion");
            }
        }
    }

    @Override
    public String storeMultiPartImage(MultipartFile multipartFile, String UUID) throws StorageServiceException, IOException {
        createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES);
        String imagePath = TEMPORARY_DIRECTORY_IMAGES + UUID + IMAGE_FORMAT_EXTENSION;
        multipartFile.transferTo(new File(imagePath));
        return imagePath;
    }


    @Override
    public byte[] getImage(String titulo) throws IOException {
        String imagePath = TEMPORARY_DIRECTORY_IMAGES + titulo + IMAGE_FORMAT_EXTENSION;
        File image = new File(imagePath);
        return Files.readAllBytes(image.toPath());
    }

}
