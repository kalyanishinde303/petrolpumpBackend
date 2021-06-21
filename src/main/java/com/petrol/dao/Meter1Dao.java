package com.petrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.petrol.model.Meter;

@Repository
public interface Meter1Dao extends CrudRepository<Meter, Integer>{

	
}
