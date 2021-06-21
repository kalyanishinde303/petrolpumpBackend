package com.petrol.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.petrol.model.Expenses;

@Repository
public interface ExpensesDao extends CrudRepository<Expenses, Integer>{

}
