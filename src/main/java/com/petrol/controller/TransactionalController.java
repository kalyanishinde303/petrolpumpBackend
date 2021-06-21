package com.petrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petrol.model.Customer;
import com.petrol.model.ResponseGen;
import com.petrol.model.Transactions;
import com.petrol.service.CustomerService;
import com.petrol.service.ExpensesService;
import com.petrol.service.TransactionsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TransactionalController {

	@Autowired
	TransactionsService transService;

	@PostMapping
    @RequestMapping("/saveTransactions")
	public ResponseEntity<ResponseGen> saveTransactions(@RequestBody List<Transactions> transactions) {
    	
		ResponseGen response = new ResponseGen();
		try {
			String resp = transService.saveTransaction(transactions);
			response.setMessage(resp);
			response.setRespCode("200");
			return new ResponseEntity<ResponseGen>(response,HttpStatus.OK);
		} catch(Exception e) {
			response.setMessage("Record not saved try again......");
			response.setRespCode("500");
		}
		
		return new ResponseEntity<ResponseGen>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		
    }
	
	@GetMapping
    @RequestMapping("/getAllCustTransDetails")
	public ResponseEntity<List<Customer>>  getAllCustTransDetails() {
    	
		return new ResponseEntity<List<Customer>>(transService.getAllCustomerTransactions(), HttpStatus.OK);
		
    }
	
	@GetMapping
    @RequestMapping("/getOneCustTransDetails")
	public ResponseEntity<List<Transactions>>  getOneCustTransDetails(@RequestParam int custId) {
    	
		return new ResponseEntity<List<Transactions>>(transService.getOneCustomerTransactions(custId), HttpStatus.OK);
		
    }
	@GetMapping
    @RequestMapping("/printOneCustTransDetails")
	public ResponseEntity<ResponseGen> printOneCustTransDetails(@RequestParam int custId) {
		ResponseGen responseGen = new ResponseGen();
		try{
			List<Transactions> list = transService.getOneCustomerTransactions(custId);
			String res = transService.createPDFCustTran(list,custId);
			responseGen.setMessage(res);
			responseGen.setRespCode("200");
			return new ResponseEntity<ResponseGen>(responseGen, HttpStatus.OK);
		} catch(Exception e){
			responseGen.setMessage("File not able to save or print if already open then please close file first");
			responseGen.setRespCode("500");
		}
		return new ResponseEntity<ResponseGen>(responseGen, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@GetMapping
    @RequestMapping("/getTranBasedOnDate")
	public ResponseEntity<List<Transactions>>  getTranBasedOnDate(@RequestParam int custId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String tranType) {
    	
		return new ResponseEntity<List<Transactions>>(transService.getTranBasedOnDate(custId, fromDate, toDate, tranType), HttpStatus.OK);
		
    }
	
	@GetMapping
    @RequestMapping("/getSelectedDateTran")
	public ResponseEntity<List<Transactions>>  getSelectedDateTran(@RequestParam String selectedDate) {
    	
		return new ResponseEntity<List<Transactions>>(transService.getSelectedDateTran(selectedDate), HttpStatus.OK);
		
    }
	@GetMapping
    @RequestMapping("/printTranBasedOnDate")
	public ResponseEntity<ResponseGen>  printTranBasedOnDate(@RequestParam int custId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String tranType) {
		ResponseGen responseGen = new ResponseGen();
		try{
		List<Transactions> tranList = transService.getTranBasedOnDate(custId, fromDate, toDate, tranType);
		String res = transService.createPDFCustTran(tranList, fromDate, toDate, custId);
		responseGen.setMessage(res);
		responseGen.setRespCode("200");
		return new ResponseEntity<>(responseGen, HttpStatus.OK);
		} catch(Exception e){
			responseGen.setMessage("File not able to save or print if already open then please close file first");
			responseGen.setRespCode("500");
		}
		return new ResponseEntity<ResponseGen>(responseGen, HttpStatus.INTERNAL_SERVER_ERROR);
		
    }
	@GetMapping
    @RequestMapping("/generatePDF")
	public ResponseEntity<ResponseGen> generatePDF(@RequestParam String date) {
		
		return new ResponseEntity<>(transService.generatePDF(date), HttpStatus.OK);
    }
}
