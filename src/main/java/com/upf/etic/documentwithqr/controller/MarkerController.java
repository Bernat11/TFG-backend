package com.upf.etic.documentwithqr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upf.etic.documentwithqr.dao.MarkerDao;
import com.upf.etic.documentwithqr.error.exception.RepositoryException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.model.entity.Marker;
import com.upf.etic.documentwithqr.service.StorageService;
import com.upf.etic.documentwithqr.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(value="markers", description="Operations pertaining to markers CRUD", tags = { "Markers" })
@RestController
@RequestMapping("/api/markers")
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

    @ApiOperation(value = "Retrieve a list of created markers", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved markers list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("")
    public List<Marker> index() {
        logger.info("Rest call received to /markers with 'GET' request method");
        return markerDao.findAll();
    }

    @ApiOperation(value = "Retrieve marker by id", response = Marker.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved marker by id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    public Marker show(@PathVariable Long id) {
        logger.info("Rest call received to /markers/{} with 'GET' request method", Utils.sanitizeLogs(id));
        return this.markerDao.findById(id);
    }

    @ApiOperation(value = "Retrieve image by UUID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved image by UUID"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/image/{UUID}")
    public ResponseEntity getImage(@PathVariable String UUID) throws IOException {
        logger.info("Rest call received to /markers/image/{} with 'GET' request method", Utils.sanitizeLogs(UUID));
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource byteArrayResource = new ByteArrayResource(storageService.getImage(UUID));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Create marker", response = Marker.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created marker"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "", headers = {"content-type=multipart/mixed","content-type=multipart/form-data"})
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

    @ApiOperation(value = "Update marker")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated marker"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Marker update(@RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion, @PathVariable Long id) {
        logger.info("Rest call received to /markers/{} with 'PUT' request method", Utils.sanitizeLogs(id));
        Marker currentMarker = this.markerDao.findById(id);
        currentMarker.setTitulo(titulo);
        currentMarker.setDescripcion(descripcion);
        this.markerDao.save(currentMarker);
        return currentMarker;
    }

    @ApiOperation(value = "Delete marker by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted marker"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        logger.info("Rest call received to /markers/{} with 'DELETE' request method", Utils.sanitizeLogs(id));
        Marker currentMarker = this.markerDao.findById(id);
        this.markerDao.delete(currentMarker);
    }

    @ApiOperation(value = "Delete all markers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted all markers"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarkers() {
        logger.info("Rest call received to /markers with 'DELETE' request method");
        this.markerDao.deleteAll();
    }

    @ApiOperation(value = "Count all markers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved markers count"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/count")
    public long count(@RequestParam(required = false) String tipo) throws RepositoryException {
        logger.info("Rest call received to /markers/count with 'GET' request method");
        if(tipo != null && !"".equals(tipo)){
            return this.markerDao.countByType(tipo);
        }
        return this.markerDao.count();
    }

    @ApiOperation(value = "Find markers by type", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved markers list by id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/find")
    public List<Marker> findByType(@RequestParam String tipo) throws RepositoryException {
        logger.info("Rest call received to /markers/find/{} with 'GET' request method", Utils.sanitizeLogs(tipo));
        return this.markerDao.findByType(tipo);
    }

    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
