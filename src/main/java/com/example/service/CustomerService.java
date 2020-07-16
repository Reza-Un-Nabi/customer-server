package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.formModel.CustomerFormModel;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;

import ch.qos.logback.core.status.Status;

@Service
@Transactional
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	public ResponseEntity<?> createCustomer (CustomerFormModel customerFormModel) {
		
		try {
			
			Customer customer = new Customer();
			customer.setName(customerFormModel.getName());
			customer.setCompany(customerFormModel.getCompany());
			//customer.setBirthdate(customerFormModel.getBirthdate());
			customerRepository.save(customer);
			return ResponseEntity.ok("");
			
		}catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Status.ERROR);
        }
	}
	
	public ResponseEntity<?> getCustomerById (Long id) {
		
		Optional<Customer> customerOpt = customerRepository.findById(id);
		
		if (!customerOpt.isPresent())
			return ResponseEntity.ok("Id Not Found.");
		
		List<CustomerFormModel> customerList = new ArrayList();
		CustomerFormModel customerFormModel = new CustomerFormModel();
		customerFormModel.setId(customerOpt.get().getId());
		customerFormModel.setName(customerOpt.get().getName());
		customerFormModel.setCompany(customerOpt.get().getCompany());
		
		customerList.add(customerFormModel);
		return ResponseEntity.ok(customerList);
		
	}
	
public ResponseEntity<?> getAllCustomer () {
		
		List <HashMap<String,Object>> customerList = customerRepository.findAllByOrderByIdDesc().stream().map(customer ->{
			HashMap<String,Object> custMap = new HashMap<>();
			custMap.put("id", customer.getId());
			custMap.put("name", customer.getName());
			custMap.put("company", customer.getCompany());
			return custMap;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(customerList);
		
	}
	
	public ResponseEntity<?> updateCustomer (CustomerFormModel customerFormModel) {
		try {

			Optional<Customer> customerOptional = customerRepository.findById(customerFormModel.getId());
			if (customerOptional.isPresent()) {
				Customer customer = customerOptional.get();
				customer.setName(customerFormModel.getName());
				customer.setCompany(customerFormModel.getCompany());
				customerRepository.save(customer);
				return ResponseEntity.ok("");
			}else {
				return ResponseEntity.ok("Id Not Found");
			}

		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Status.ERROR);
		}
	}

	public ResponseEntity<?> deleteCustomer (Long id) {

			Optional<Customer> customerOptional = customerRepository.findById(id);
			if (customerOptional.isPresent())
				customerRepository.delete(customerOptional.get());
				return ResponseEntity.ok("");
	}

}
