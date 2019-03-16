package com.upf.etic.documentwithqr.dao;

import com.upf.etic.documentwithqr.model.entity.Marker;

import java.util.List;

public interface MarkerDao {

    List<Marker> findAll();

    Marker findById(Long id);

    void save(Marker marker);

    void delete(Marker marker);

    void deleteAll();

}
