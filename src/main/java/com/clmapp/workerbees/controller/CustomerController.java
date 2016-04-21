package com.clmapp.workerbees.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clmapp.workerbees.custrepository.CustRepository;
import com.clmapp.workerbees.model.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	  @Autowired
	  private CustRepository custRepository;
	  
	  // Define the log object for this class
	  private final Logger log = LoggerFactory.getLogger(this.getClass());
	  
	  @RequestMapping(method = RequestMethod.POST)
	  public Map<String, Object> createCustomer(@RequestBody Map<String, Object> customerMap){
	    Customer customer = new Customer(customerMap.get("name").toString(), 
	    	customerMap.get("policyNbr").toString(),
	    	customerMap.get("distance").toString(),
	    	customerMap.get("latlong").toString(),
	    	customerMap.get("telephoneNbr").toString(),
	    	customerMap.get("city").toString(),
	    	customerMap.get("state").toString(),
	    	customerMap.get("postalCode").toString(),
	    	customerMap.get("countryCode").toString(),
	    	customerMap.get("streetName").toString(),
	    	customerMap.get("structureNum").toString()
	        );
	    
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Customer created successfully");
	    response.put("customer", custRepository.save(customer));
	    return response;
	  }
	  
//	  @RequestMapping(method = RequestMethod.GET, value="/{policyNbr}")
//	  public Customer getCustomerDetails(@PathVariable("policyNbr") String policyNbr){
//	    return custRepository.findOne(policyNbr);
//	  }
	  
	  
	  @RequestMapping(method = RequestMethod.GET, value="/{postalCode}")
	  public Map<String, Object>getCustomersByPostalCode(@PathVariable("postalCode") String postalCode){
		List<Customer> customers = custRepository.findByPostalCode(postalCode);
		log.debug("Custumer:" + customers.toString());
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("customers", customers);
	    return response;
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllCustomers(){
	    List<Customer> customers = custRepository.findAll();
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("customers", customers);
	    return response;
	  }
}
