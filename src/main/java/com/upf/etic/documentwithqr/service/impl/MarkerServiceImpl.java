package com.upf.etic.documentwithqr.service.impl;

import com.upf.etic.documentwithqr.dao.MarkerDao;
import com.upf.etic.documentwithqr.error.exception.RepositoryException;
import com.upf.etic.documentwithqr.model.entity.Marker;
import com.upf.etic.documentwithqr.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerServiceImpl implements MarkerService {

    @Autowired
    private MarkerDao markerDao;

    public MarkerServiceImpl(MarkerDao markerDao){
        this.markerDao = markerDao;
    }

    @Override
    public List<Marker> findAll() {
        return markerDao.findAll();
    }

    @Override
    public Marker findById(Long id) {
        return markerDao.findById(id);
    }

    @Override
    public void save(Marker marker) {
        markerDao.save(marker);
    }

    @Override
    public void delete(Marker marker) {
        markerDao.delete(marker);
    }

    @Override
    public void deleteAll() {
        markerDao.deleteAll();
    }

    @Override
    public long count() {
        return markerDao.count();
    }

    @Override
    public int countByType(String tipo) throws RepositoryException {
        return markerDao.countByType(tipo);
    }

    @Override
    public List<Marker> findByType(String tipo) throws RepositoryException {
        return markerDao.findByType(tipo);
    }
}
