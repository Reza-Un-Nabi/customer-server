package com.example.controller;

import org.springframework.web.bind.annotation.*;

import com.example.formModel.CustomerFormModel;
import com.example.model.Customer;
import com.example.service.CustomerService;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;


	@Value("${server.port}") 
	private int port;

	//System.out.println("A request is received: " + port);

	@PostMapping("/add")
	public ResponseEntity<?> add (@RequestBody CustomerFormModel customerFormModel) {

		return customerService.createCustomer(customerFormModel);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getSingleCustomer (@PathVariable("id") Long id) {

		return customerService.getCustomerById(id);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> allCustomer() {
		System.out.println("A request is received: " + port);
		return customerService.getAllCustomer();
	}

	@PostMapping("/update")
	public ResponseEntity<?> update (@RequestBody CustomerFormModel customerFormModel) {

		return customerService.updateCustomer(customerFormModel);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCustomer (@PathVariable("id") Long id) {

		return customerService.deleteCustomer(id);
	}
}
