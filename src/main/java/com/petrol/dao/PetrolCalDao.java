package com.petrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.petrol.model.PetrolCal;
@Repository
public interface PetrolCalDao extends CrudRepository<PetrolCal, String>{

}
