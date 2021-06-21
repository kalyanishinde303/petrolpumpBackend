package com.petrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petrol.model.Customer;
import com.petrol.model.Expenses;
import com.petrol.model.ResponseGen;
import com.petrol.model.Transactions;
import com.petrol.service.CustomerService;
import com.petrol.service.ExpensesService;
import com.petrol.service.TransactionsService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CustomerController {

	@Autowired
	CustomerService custService;
	@PostMapping
    @RequestMapping("/saveCustTransactions")
	public ResponseEntity<String> saveCustTrans(@RequestBody List<Customer> details) {
		String resp =  custService.saveCustomerTran(details);
		return	new ResponseEntity<String>(resp,HttpStatus.OK);
    }
	
	@PostMapping
    @RequestMapping("/addCustomer")
	public ResponseEntity<ResponseGen> saveCustomerDetails(@RequestBody Customer custDetails) {
    	ResponseGen responseGen = new ResponseGen();
    	try{
			String resp = custService.saveCustoemrDetails(custDetails);
			responseGen.setMessage(resp);
			responseGen.setRespCode("200");
			return	new ResponseEntity<ResponseGen>(responseGen,HttpStatus.OK);
    	} catch(Exception e){
    		responseGen.setMessage("Customer not added Please try again..");
    		responseGen.setRespCode("500");
    	}
    	return	new ResponseEntity<ResponseGen>(responseGen,HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@PostMapping
    @RequestMapping("/deleteCustomer")
	public ResponseEntity<ResponseGen> deleteCustomer(@RequestBody Customer customer) {
    	ResponseGen responseGen = new ResponseGen();
    	try{
			String resp = custService.deleteCustomer(customer);
			responseGen.setMessage(resp);
			responseGen.setRespCode("200");
			return	new ResponseEntity<ResponseGen>(responseGen,HttpStatus.OK);
    	} catch(Exception e){
    		responseGen.setMessage("Customer not deleted Please try again..");
    		responseGen.setRespCode("500");
    	}
    	return	new ResponseEntity<ResponseGen>(responseGen,HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@PostMapping
    @RequestMapping("/upodateCustBalance")
	public ResponseEntity<String> updateCustBalance(@RequestBody List<Customer> custDetails) {
    	
		String resp = custService.updateCustBalance(custDetails);
		return	new ResponseEntity<String>(resp,HttpStatus.OK);
    }
	
	@GetMapping
    @RequestMapping("/getCustomers")
	public ResponseEntity<List<Customer>>  getCustomerList() {
    	
		return new ResponseEntity<List<Customer>>(custService.getCustomers(), HttpStatus.OK);
		
    }
	
	
	
}
