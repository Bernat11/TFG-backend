package com.upf.etic.documentwithqr.service.impl;

import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.service.StorageService;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Iterator;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.*;

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
        createTemporaryFolderIfNotExists(TEMPORARY_DIRECTORY_IMAGES_NON_COMPRESSED);
        String imagePath = TEMPORARY_DIRECTORY_IMAGES_NON_COMPRESSED + UUID + IMAGE_FORMAT_EXTENSION;
        multipartFile.transferTo(new File(imagePath));
        return compressImage(UUID);
    }


    @Override
    public byte[] getImage(String titulo) throws IOException {
        String imagePath = TEMPORARY_DIRECTORY_IMAGES_COMPRESSED + titulo + "_compressed.jpg";
        File image = new File(imagePath);
        return Files.readAllBytes(image.toPath());
    }

    private String compressImage(String UUID) throws IOException, StorageServiceException {
        File input = new File(TEMPORARY_DIRECTORY_IMAGES_NON_COMPRESSED + UUID + IMAGE_FORMAT_EXTENSION);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(TEMPORARY_DIRECTORY_IMAGES_COMPRESSED + UUID + "_compressed.jpg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.5f);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();

        deleteTemporaryFolderIfExists(TEMPORARY_DIRECTORY_IMAGES_NON_COMPRESSED);

        return compressedImageFile.getAbsolutePath();
    }

}
