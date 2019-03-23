package com.upf.etic.documentwithqr.dao;

import com.upf.etic.documentwithqr.model.entity.Marker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICrudRepository extends CrudRepository<Marker, Long> {

    @Query("SELECT m FROM Marker m WHERE m.tipo=:tipo")
    public List<Marker> findByType(@Param("tipo") String tipo);

}
