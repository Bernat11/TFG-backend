package com.upf.etic.documentwithqr.dao.Impl;

import com.upf.etic.documentwithqr.dao.ICrudRepository;
import com.upf.etic.documentwithqr.dao.MarkerDao;
import com.upf.etic.documentwithqr.model.entity.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;

@Service
public class MarkerDaoImpl implements MarkerDao{

    private ICrudRepository markerDao;

    @Autowired
    public MarkerDaoImpl(ICrudRepository markerDao){
        this.markerDao = markerDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marker> findAll() {
        return (List<Marker>) markerDao.findAll();
    }

    @Override
    @Transactional
    public void save(Marker marker) {
        markerDao.save(marker);
    }

    @Override
    @Transactional(readOnly = true)
    public Marker findById(Long id) {
        return markerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Marker marker) {
        markerDao.delete(marker);
    }

    @Override
    @TransactionScoped
    public void deleteAll() {
        markerDao.deleteAll();
    }

}
