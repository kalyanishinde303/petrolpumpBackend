package com.petrol.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.petrol.dao.BalanceSheetDao;
import com.petrol.dao.ExpensesDao;
import com.petrol.helper.BalanceSheetReport;
import com.petrol.helper.DateUtils;
import com.petrol.helper.ExpensesReport;
import com.petrol.model.BalanceSheet;
import com.petrol.model.Expenses;

@Service
public class ExpensesService {

	@Autowired
	DateUtils utils;
	@Autowired
	ExpensesDao expensesDao;
	@Autowired
	ExpensesReport report;
	@Autowired
	BalanceSheetDao sheetDao;
	@Autowired
	BalanceSheetReport sheetReport;
	public String saveExp(List<Expenses> expenses) {
		try{
			expensesDao.saveAll(expenses);
		} catch(Exception e) {
			e.printStackTrace();
			return "Expenses not save";
		}
		
		
		return "Expenses saved";
	}
	public String saveBalanceSheet(BalanceSheet balanceSheet) {
		try{
			sheetDao.save(balanceSheet);
		} catch(Exception e) {
			e.printStackTrace();
			return "Balance sheet not save";
		}
		
		return "Balance sheet saved";
	}

	public List<Expenses> getExpensesReport(String fromDate, String toDate) {
		List<Expenses> expenses;
		List<Expenses> tmp = new ArrayList<>();
		try {
			expenses = (List<Expenses>) expensesDao.findAll();
			SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
			Date fd= formatter1.parse(fromDate);
			Date td = formatter1.parse(toDate);
			LocalDateTime ldt = LocalDateTime.ofInstant(fd.toInstant(), ZoneId.systemDefault()).minusDays(1);
			Date fd1 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
			LocalDateTime ldt1 = LocalDateTime.ofInstant(td.toInstant(), ZoneId.systemDefault()).plusDays(1);
			Date td1 = Date.from(ldt1.atZone(ZoneId.systemDefault()).toInstant());
			expenses.forEach(tran -> {
				try {
					if(utils.checkDateINBetween(tran.getDate(), fd1, td1)){
						tran.setDate(utils.dateModify(tran.getDate()));
						tmp.add(tran);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				});
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}
		return tmp;
	}
	
	
	public String printExpenseReport(List<Expenses> expenses, String fromDate, String toDate) {
		
		String fileName = utils.dateModify(fromDate)+"to"+utils.dateModify(toDate);
		String response = null;
		try {
			response = report.createPDF(expenses, fileName);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	public List<BalanceSheet> getBalanceSheet(java.sql.Date fromDate, java.sql.Date toDate) {
		List<BalanceSheet> sheetList = new ArrayList<>();
		try{
			sheetList =  sheetDao.findAllRecords(fromDate,toDate);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sheetList;
	}
	
	public BalanceSheet getLastBalance(java.sql.Date fromDate, java.sql.Date toDate) {
		List<BalanceSheet> sheetList = new ArrayList<>();
		try{
			java.sql.Date da =  java.sql.Date.valueOf(dateMinus(fromDate.toString()));
			sheetList =  sheetDao.findAllRecords(da,da);
			if(sheetList != null && !sheetList.isEmpty()){
				return sheetList.get(0);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
public String printBalanceSheetReport(List<BalanceSheet> sheetList, String fromDate, String toDate) {
		
		String fileName = utils.dateModify(fromDate)+"to"+utils.dateModify(toDate);
		String response = null;
		try {
			response = sheetReport.createPDF(sheetList, fileName);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	private String dateMinus(String date) {
		// TODO Auto-generated method stub
		
		String dateMinus = LocalDate.parse(date).minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		return dateMinus;
	}
}
