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

import com.petrol.model.Meter;
import com.petrol.model.MeterDetails;
import com.petrol.model.ResponseGen;
import com.petrol.service.MeterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class MeterController {

	
	@Autowired
	MeterService service;
    @GetMapping
    @RequestMapping("/getOpeningMeter")
	public MeterDetails getOpeningMeter(@RequestParam String date) {
		
		
		return service.getMeter(date);
	}
    @GetMapping
    @RequestMapping("/getBalance")
	public MeterDetails getBalance(@RequestParam String date) {
		
		return service.getBalance(date);
	}
   
    @GetMapping
    @RequestMapping("/deleteAllMeterDetails")
	public ResponseEntity<ResponseGen> deleteMeter() {
		
    	ResponseGen response = new ResponseGen();
		try{
			response.setMessage(service.deleteMeter());
			response.setRespCode("200");
		return new ResponseEntity<ResponseGen>(response,HttpStatus.OK);
    	} catch(Exception e){
    		response.setRespCode("500");
    		response.setMessage("Meter details not saved");
    	}
		return new ResponseEntity<ResponseGen>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
   
	@PostMapping
    @RequestMapping("/saveMeterDetails")
	public ResponseEntity<ResponseGen> saveOpeningMeter(@RequestBody MeterDetails meterDetails) {
		ResponseGen response = new ResponseGen();
		try{
	    	String date = meterDetails.getDate();
	    	meterDetails.getM1().setDate(date);
	    	meterDetails.getM2().setDate(date);
	    	meterDetails.getM3().setDate(date);
	    	meterDetails.getM4().setDate(date);
	    	meterDetails.getPtCal().setDate(date);
	    	meterDetails.getDiselCal().setDate(date);
	    	
			response.setMessage(service.saveMeterDetails(meterDetails));
			response.setRespCode("200");
		return new ResponseEntity<ResponseGen>(response,HttpStatus.OK);
    	} catch(Exception e){
    		response.setRespCode("500");
    		response.setMessage("Meter details not saved");
    	}
		return new ResponseEntity<ResponseGen>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
	

	@GetMapping
    @RequestMapping("/getMeterReport")
	public ResponseEntity<List<Object>>  getMeterReport(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String meterType) {
    	
		return new ResponseEntity<List<Object>>(service.getMeterSaleReport(meterType, fromDate, toDate),HttpStatus.OK);
		
    }
	@GetMapping
    @RequestMapping("/printMeterReport")
	public ResponseEntity<ResponseGen>  printMeterReport(@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String meterType) {
		List<Object> list = service.getMeterSaleReport(meterType, fromDate, toDate);
		
		try {
			String res = service.printMeterReport(list, meterType, fromDate, toDate);
			ResponseGen response = new ResponseGen();
			response.setMessage(res);
			return new ResponseEntity<ResponseGen>(response,HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		ResponseGen response = new ResponseGen();
		response.setMessage("Not able to print report try with different date");
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
