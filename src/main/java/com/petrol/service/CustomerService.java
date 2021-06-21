package com.petrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petrol.dao.CustomerDao;
import com.petrol.dao.TranasactionsDao;
import com.petrol.model.Customer;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDao customerDao;
	@Autowired
	TranasactionsDao tranasactionsDao; 
	public String saveCustomerTran(List<Customer> customer) {
		
			customerDao.saveAll(customer);
			System.out.println("Added Successfully");
			return "Added";
	}
	

	public String saveCustoemrDetails(Customer customer) {
		
			customerDao.save(customer);
			System.out.println("Added Successfully");
			return "Added";
	}
	public String deleteCustomer(Customer customer) {
		
		customerDao.delete(customer);
		return  customer.getName() +" deleted successfully";
}
	public String updateCustBalance(List<Customer> customer) {
		
		customerDao.saveAll(customer);
		return "Balance Updated";
}
	public List<Customer> getCustomers() {
		
		List<Customer> custList = (List<Customer>) customerDao.findAll();
		return custList;
}
}
