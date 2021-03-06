package com.avanti.companyuserregistrationmicroservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.avanti.companyuserregistrationmicroservice.model.UserDetails;

/**
 * User repository interface to save user details
 * @author deshmukh avanti, harish
 * @version 1.0
 */
public interface UserRepository extends MongoRepository<UserDetails, String>{

}
