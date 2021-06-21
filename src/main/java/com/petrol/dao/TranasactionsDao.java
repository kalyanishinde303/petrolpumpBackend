package com.petrol.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petrol.model.Transactions;

@Repository
public interface TranasactionsDao extends CrudRepository<Transactions, Integer>{

	@Query(value ="select * FROM Transactions  where cust_Id= :custId",nativeQuery = true)
	List<Transactions> findAllTranCust(@Param("custId") String custId);
	
	@Query(value ="select * FROM Transactions  where date= :date",nativeQuery = true)
	List<Transactions> findAllTranCustDate(@Param("date") String date);
}
