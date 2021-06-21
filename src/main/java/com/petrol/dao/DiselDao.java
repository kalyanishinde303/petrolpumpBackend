package com.petrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.petrol.model.DiselCal;

@Repository
public interface DiselDao extends CrudRepository<DiselCal, String>{

}
