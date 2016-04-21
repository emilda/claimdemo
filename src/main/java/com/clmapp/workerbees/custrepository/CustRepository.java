package com.clmapp.workerbees.custrepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import com.clmapp.workerbees.model.*;
public interface CustRepository extends MongoRepository<Customer, String>{
	
	public List<Customer>findByPostalCode(String postalCode);

}
