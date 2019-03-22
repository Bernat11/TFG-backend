package com.upf.etic.documentwithqr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upf.etic.documentwithqr.dao.MarkerDao;
import com.upf.etic.documentwithqr.exceptions.StorageServiceException;
import com.upf.etic.documentwithqr.model.entity.Marker;
import com.upf.etic.documentwithqr.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MarkerController {

    private Logger logger = LoggerFactory.getLogger(VersionController.class);

    private MarkerDao markerDao;
    private StorageService storageService;

    @Autowired
    public MarkerController(MarkerDao markerDao, StorageService storageService){
        this.markerDao = markerDao;
        this.storageService = storageService;
    }

    @GetMapping("/markers")
    public List<Marker> index() {
        logger.info("Rest call received to /markers with 'GET' request method");
        return markerDao.findAll();
    }

    @GetMapping("/markers/{id}")
    public Marker show(@PathVariable Long id) {
        logger.info("Rest call received to /markers/{} with 'GET' request method", id);
        return this.markerDao.findById(id);
    }

    @GetMapping("/markers/image/{UUID}")
    public ResponseEntity getImage(@PathVariable String UUID) throws IOException {
        logger.info("Rest call received to /markers/image/{} with 'GET' request method", UUID);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource byteArrayResource = new ByteArrayResource(storageService.getImage(UUID));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/markers", headers = {"content-type=multipart/mixed","content-type=multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public Marker create(@RequestPart String stringMarker, @RequestPart MultipartFile imagen) throws IOException, StorageServiceException {
        logger.info("Rest call received to /markers with 'POST' request method");
        Marker marker = new ObjectMapper().readValue(stringMarker, Marker.class);
        String UUID = generateUUID();
        storageService.storeMultiPartImage(imagen, UUID);
        marker.setImagePath(UUID);
        marker.setCreationDate(new Date());
        this.markerDao.save(marker);
        return marker;
    }

    @PutMapping(value = "/markers/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Marker update(@RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion, @PathVariable Long id) {
        logger.info("Rest call received to /markers/{} with 'PUT' request method", id);
        Marker currentMarker = this.markerDao.findById(id);
        currentMarker.setTitulo(titulo);
        currentMarker.setDescripcion(descripcion);
        this.markerDao.save(currentMarker);
        return currentMarker;
    }

    @DeleteMapping("/markers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        logger.info("Rest call received to /markers/{} with 'DELETE' request method", id);
        Marker currentMarker = this.markerDao.findById(id);
        this.markerDao.delete(currentMarker);
    }

    @DeleteMapping("/markers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarkers() {
        logger.info("Rest call received to /markers with 'DELETE' request method");
        this.markerDao.deleteAll();
    }

    @GetMapping("/markers/count")
    public long count(){
        logger.info("Rest call received to /markers/count with 'GET' request method");
        return this.markerDao.count();
    }

    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
