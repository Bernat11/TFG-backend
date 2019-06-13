package com.upf.etic.documentwithqr.dao.Impl;

import com.upf.etic.documentwithqr.dao.ICrudRepository;
import com.upf.etic.documentwithqr.dao.MarkerDao;
import com.upf.etic.documentwithqr.error.exception.RepositoryException;
import com.upf.etic.documentwithqr.model.entity.Marker;
import com.upf.etic.documentwithqr.model.enums.MarkerTypes;
import com.upf.etic.documentwithqr.util.Utils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;

@Repository
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

    @Override
    @Transactional
    public long count(){
        return markerDao.count();
    }

    @Override
    @Transactional
    public int countByType(String tipo) throws RepositoryException {
        String type2compare = Utils.markerType2enum(tipo);
        if(EnumUtils.isValidEnum(MarkerTypes.class, type2compare)){
            return markerDao.findByType(tipo).size();
        } else {
            throw new RepositoryException("El tipo " + tipo + " no existe");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Marker> findByType(String tipo) throws RepositoryException {
        String type2compare = Utils.markerType2enum(tipo);
        if(EnumUtils.isValidEnum(MarkerTypes.class, type2compare)){
            return markerDao.findByType(tipo);
        } else {
            throw new RepositoryException("El tipo " + tipo + " no existe");
        }
    }

}
