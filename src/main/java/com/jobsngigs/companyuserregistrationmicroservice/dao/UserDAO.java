package com.jobsngigs.companyuserregistrationmicroservice.dao;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.jobsngigs.companyuserregistrationmicroservice.model.UserDetails;
import com.jobsngigs.companyuserregistrationmicroservice.repository.UserRepository;

/**
* DAO to perform CRUD operations in company_users collection
* @author deshmukh avanti,harish
* @version 1.0
*/
@Repository
public class UserDAO {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * This method is used to add user into the database
	 * @param user
	 * @return UserDetails Object if saved successfully otherwise null
	 */
	public UserDetails addUser(UserDetails user) {
		return userRepository.save(user);
	}
	
	/**
	 * This method is used to find user based on confirmation token 
	 * @param token
	 * @return UserDetails Object if token is available otherwise null
	 */
	public UserDetails findByToken(String token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("verification_token").is(token));
		List<UserDetails> userList = mongoTemplate.find(query, UserDetails.class);
		return (!userList.isEmpty()) ? userList.get(0) : null;
	}

	/**
	 * This method is used to find list of users based on company name 
	 * @param companyName
	 * @return list of UserDetails object if users are present otherwise empty list
	 */
	public List<UserDetails> getUser(String companyName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("company_name").is(companyName));
		List<UserDetails> userList = mongoTemplate.find(query, UserDetails.class);
		return (userList.size() > 0) ? userList : Collections.emptyList();
	}

}
