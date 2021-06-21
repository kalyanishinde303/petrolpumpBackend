package com.petrol.controller;

import java.sql.Date;
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

import com.petrol.model.BalanceSheet;
import com.petrol.model.Expenses;
import com.petrol.model.ResponseGen;
import com.petrol.service.ExpensesService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ExpensesController {

	@Autowired
	ExpensesService expService;
	
	@PostMapping
    @RequestMapping("/saveExpenses")
	public ResponseEntity<String> saveExpenses(@RequestBody List<Expenses> expenses) {
    	
		String resp = expService.saveExp(expenses);
		 return	new ResponseEntity<String>(resp,HttpStatus.OK);
    }
	
	@PostMapping
    @RequestMapping("/saveBalanceSheet")
	public ResponseEntity<String> saveBalanceSheet(@RequestBody BalanceSheet balanceSheet) {
    	
		String resp = expService.saveBalanceSheet(balanceSheet);
		 return	new ResponseEntity<String>(resp,HttpStatus.OK);
    }
	
	@GetMapping
    @RequestMapping("/getExpensesReport")
	public ResponseEntity<List<Expenses>>  getTranBasedOnDate(
			@RequestParam String fromDate, @RequestParam String toDate) {
    	
		return new ResponseEntity<List<Expenses>>(expService.getExpensesReport( fromDate, toDate), HttpStatus.OK);
		
    }
	
	@GetMapping
    @RequestMapping("/printExpensesReport")
	public ResponseEntity<ResponseGen>  printTranBasedOnDate(
			@RequestParam String fromDate, @RequestParam String toDate) {
		ResponseGen responseGen =  new ResponseGen();
		try{
			List<Expenses> expensesList = expService.getExpensesReport( fromDate, toDate);
			String res = expService.printExpenseReport(expensesList, fromDate, toDate);
			responseGen.setMessage(res);
			responseGen.setRespCode("200");
			return new ResponseEntity<>(responseGen, HttpStatus.OK);
		} catch(Exception e){
			responseGen.setMessage("Not able to print please check file folder and file is opend");
			responseGen.setRespCode("500");
		}
		return new ResponseEntity<>(responseGen, HttpStatus.INTERNAL_SERVER_ERROR);
		
    }
	
	@GetMapping
    @RequestMapping("/getBalanceSheet")
	public ResponseEntity<List<BalanceSheet>>  getBalanceSheetReport(
			@RequestParam Date fromDate, @RequestParam Date toDate) {
    	
		return new ResponseEntity<List<BalanceSheet>>(expService.getBalanceSheet( fromDate, toDate), HttpStatus.OK);
		
    }
	@GetMapping
    @RequestMapping("/getLastBalance")
	public ResponseEntity<BalanceSheet>  getLastBalance(
			@RequestParam Date fromDate, @RequestParam Date toDate) {
    	
		return new ResponseEntity<BalanceSheet>(expService.getLastBalance( fromDate, toDate), HttpStatus.OK);
		
    }
	
	@GetMapping
    @RequestMapping("/printBalanceSheetReport")
	public ResponseEntity<ResponseGen>  printBalanceSheetReport(
			@RequestParam Date fromDate, @RequestParam Date toDate) {
		ResponseGen responseGen =  new ResponseGen();
		try{
			List<BalanceSheet> sheetList = expService.getBalanceSheet( fromDate, toDate);
			String res = expService.printBalanceSheetReport(sheetList, fromDate.toString(), toDate.toString());
			responseGen.setMessage(res);
			responseGen.setRespCode("200");
			return new ResponseEntity<>(responseGen, HttpStatus.OK);
		} catch(Exception e){
			responseGen.setMessage("Not able to print please check file folder and file is opend");
			responseGen.setRespCode("500");
		}
		return new ResponseEntity<>(responseGen, HttpStatus.INTERNAL_SERVER_ERROR);
		
    }
}
