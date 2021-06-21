package com.petrol.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.petrol.model.BalanceSheet;

public interface BalanceSheetDao extends CrudRepository<BalanceSheet, Date>{

	@Query(value ="select * FROM balance_sheet  where sheet_date between :fromDate and :toDate",nativeQuery = true)
	List<BalanceSheet> findAllRecords(@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

}
