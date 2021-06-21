package com.petrol.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.petrol.dao.CustomerDao;
import com.petrol.dao.ExpensesDao;
import com.petrol.dao.TranasactionsDao;
import com.petrol.helper.CreatePDF;
import com.petrol.helper.CustomerReport;
import com.petrol.helper.DateUtils;
import com.petrol.model.Customer;
import com.petrol.model.Expenses;
import com.petrol.model.ResponseGen;
import com.petrol.model.Transactions;

@Service
public class TransactionsService {

	@Autowired
	CreatePDF createPDF;
	@Autowired
	TranasactionsDao tranasactionsDao;
	@Autowired 
	CustomerDao customerDao;
	@Autowired
	DateUtils utils;
	@Autowired
	ExpensesDao expensesDao;
	@Autowired
	CustomerReport custReport;
	public String saveTransaction(List<Transactions> transactions) {
		
			tranasactionsDao.saveAll(transactions);
		return "Transactions saved successfully";
	}
	public List<Customer> getAllCustomerTransactions() {
		List<Customer> custList;
		try {
			List<Transactions> transactions = (List<Transactions>) tranasactionsDao.findAll();
			custList = createCustList(transactions);
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}
		return custList;
	}
	public List<Transactions> getOneCustomerTransactions(int custId) {
		List<Transactions> transactions = new ArrayList();
		try {
		    tranasactionsDao.findAllTranCust(String.valueOf(custId)) .forEach(tx -> {
			   
		    	tx.setDate(utils.dateModify(tx.getDate()));
		    	transactions.add(tx);
		   });;
		   
		   
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}
		return transactions;
	}
	private List<Customer> createCustList(List<Transactions> transactions) {
		// TODO Auto-generated method stub
		List<Customer> custList = new ArrayList<>();
		HashMap<String, List<Transactions>> tran = new HashMap<>();
		transactions.forEach(t -> {
			if(tran.containsKey(t.getCustId())){
				 tran.get(t.getCustId()).add(t);
				tran.put(t.getCustId(),tran.get(t.getCustId()));
			} else {
				List<Transactions> tmp = new ArrayList<>();
				tmp.add(t);
				tran.put(t.getCustId(),tmp);
			}
		});
		tran.forEach((key,value) -> {
			Optional<Customer> cst = customerDao.findById(Integer.parseInt(key));
			if(cst.isPresent()){
			Customer c = cst.get();
			c.setTranList(value);
			System.out.println(c.getTranList());
			custList.add(c);
			}
		});
		
		return custList;
	}
	
	
	public ResponseGen generatePDF(String date){
		List<Transactions> transactions;
		ResponseGen gen = new ResponseGen();
		 String response;
		try {
		   transactions = tranasactionsDao.findAllTranCustDate(String.valueOf(date));
		   response = createPDF.createPDF(createCustList(transactions), date);
		   gen.setMessage(response);
		   gen.setRespCode("200");
		} catch (Exception e) {
				e.printStackTrace();
				gen.setMessage("Error occured");
				gen.setRespCode("500");
				return gen;
		}
		return gen;
	}
	
	public List<Transactions> getTranBasedOnDate(int custId, String fromDate, String toDate, String tranType) {
		List<Transactions> transactions;
		try {
		   transactions = tranasactionsDao.findAllTranCust(String.valueOf(custId));
		   tranType = tranType.equals("both") ? "" : tranType; 
		   
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}
		return createTranListBasedDate(transactions, tranType, fromDate, toDate);
	}
	
	public List<Transactions> getSelectedDateTran(String selectedDate) {
		List<Transactions> transactions;
		try {
		   transactions = tranasactionsDao.findAllTranCustDate(selectedDate);
		} catch (Exception e) {
				e.printStackTrace();
				return null;
		}
		return transactions;
	}
	
	
	private List<Transactions> createTranListBasedDate(List<Transactions> transactions, String tranType, String fromDate, String toDate){
		List<Transactions> tmp = new ArrayList<>();
		try {
			
			SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
			Date fd= formatter1.parse(fromDate);
			Date td = formatter1.parse(toDate);
			LocalDateTime ldt = LocalDateTime.ofInstant(fd.toInstant(), ZoneId.systemDefault()).minusDays(1);
			Date fd1 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
			LocalDateTime ldt1 = LocalDateTime.ofInstant(td.toInstant(), ZoneId.systemDefault()).plusDays(1);
			Date td1 = Date.from(ldt1.atZone(ZoneId.systemDefault()).toInstant());
			transactions.forEach(tran -> {
			try {
				if(utils.checkDateINBetween(tran.getDate(), fd1, td1) && (tranType.equals("") || tran.getType().equals(tranType))){
					tran.setDate(utils.dateModify(tran.getDate()));
					tmp.add(tran);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			});
		} catch(Exception e) {
			
		}
		
		return tmp;
	}
	
	public String createPDFCustTran(List<Transactions> tranList, String fromDate, String toDate, int custId) throws URISyntaxException, IOException, DocumentException {
		
		String custName = customerDao.findById(custId).get().getName();
		String fileName = custName +utils.dateModify(fromDate)+"to"+utils.dateModify(toDate);
		String response;
			response  = custReport.createPDF(tranList, fileName);
		
		return response; 
	}
	public String createPDFCustTran(List<Transactions> list, int custId) throws URISyntaxException, IOException, DocumentException {
		// TODO Auto-generated method stub
		String custName = customerDao.findById(custId).get().getName();
		String fileName = custName;
		String response;
			response  = custReport.createPDF(list, fileName);
		
		return response; 
	}
}
