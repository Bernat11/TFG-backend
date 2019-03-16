package com.upf.etic.documentwithqr.dao;

import com.upf.etic.documentwithqr.model.entity.Marker;
import org.springframework.data.repository.CrudRepository;

public interface ICrudRepository extends CrudRepository<Marker, Long> {
}
