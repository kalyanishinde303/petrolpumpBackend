package com.petrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.petrol.model.Customer;
@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer> {

}
