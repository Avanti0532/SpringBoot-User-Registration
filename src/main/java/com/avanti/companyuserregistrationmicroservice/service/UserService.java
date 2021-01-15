package com.avanti.companyuserregistrationmicroservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avanti.companyuserregistrationmicroservice.configuration.MailHelper;
import com.avanti.companyuserregistrationmicroservice.dao.UserDAO;
import com.avanti.companyuserregistrationmicroservice.model.UserDetails;

/**
 * The service class is to hold the business logic of the user registration microservice
 * @author deshmukh avanti, harish
 * @version 1.0
 */
@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Value("${uploadImage.path}")
	private String uploadImagePath;
	
	@Autowired
	private MailHelper mailHelper;
	
	@Autowired
	private UserDAO userDao;
	
	/**
	 * This method is used to add user details
	 * @param UserDetails object
	 * @param url
	 * @param userImage
	 * @return Object UserDetails/null based on conditions
	 */
	public UserDetails addUserDetails(UserDetails user, String url, MultipartFile userImage) throws IOException {
		String dbPathToSet = null;
		boolean emailSent = false;
		UserDetails updatedUserDetails = null;
		UserDetails userDetails = userDao.addUser(user);
		if(userDetails != null) {
			if(userImage != null) {
				String fileName = userImage.getOriginalFilename();
				Path userImagePath = Paths.get(uploadImagePath+File.separator+fileName);
				Files.copy(userImage.getInputStream(), userImagePath, StandardCopyOption.REPLACE_EXISTING);
				Files.move(userImagePath, 
						userImagePath.resolveSibling(userDetails.getId() + "_image." + FilenameUtils.getExtension(fileName)), 
						 StandardCopyOption.REPLACE_EXISTING);
				dbPathToSet = uploadImagePath + File.separator + userDetails.getId().toString() + "_image."+
						FilenameUtils.getExtension(fileName);
				userDetails.setUserImage(dbPathToSet);
				updatedUserDetails = userDao.addUser(userDetails);
			}
			String name = userDetails.getFirstName();
			String to = userDetails.getEmail();
			String text = url + "/confirm-user?token="+ userDetails.getVerificationToken();
			emailSent = mailHelper.sendActivationEmail(to, name, text);
			if(emailSent == true) {
				return (updatedUserDetails != null) ? updatedUserDetails : userDetails;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
	
	/**
	 * This method is used to create user details object based on multipart/form-data 
	 * @param companyName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param userRole
	 * @return Object user
	 */
	public UserDetails getUserDetails(String companyName, String firstName, String lastName, String email, String password, String userRole) {
		UserDetails user = new UserDetails();
		String company = companyName!="" ? companyName:null;
		user.setCompanyName(company);
		String fName = firstName!="" ? firstName:null;
		user.setFirstName(fName);
		String lName = lastName!="" ? lastName:null;
		user.setLastName(lName);
		String userEmail = email!="" ? email:null;
		user.setEmail(userEmail);
		String userPassword = password!="" ? password:null;	
		user.setPassword(encoder.encode(userPassword));
		String role = userRole!="" || userRole!=null ? userRole:null;
		user.setUserRole(role);
		user.setVerificationStatus(false);
		user.setVerificationToken(UUID.randomUUID().toString());
		user.setTokenCreationDate(new Date());
		return user;
	}
	
	/**
	 * This method is used to verify user confirmation token
	 * @param token
	 * @return String response based on different conditions
	 */
	public String verifyUserToken(String token) {
		Boolean emailSent = false;
		UserDetails user = userDao.findByToken(token);
		if(user!=null) {
			if(user.getVerificationStatus() == false) {
				user.setVerificationStatus(true);
				UserDetails saveDetails = userDao.addUser(user);
				if(saveDetails!=null) {
					String name = saveDetails.getFirstName();
					String to = saveDetails.getEmail();
					emailSent = mailHelper.sendConfirmationMessage(to, name);
					return (emailSent == true) ? "user verified" : "error in sending email to the user";
				}else {
					return "error in updating confirmation token";
				}
			}	
			else {
				return "user is already verified";
			}
		}
		return "no such token exists";
	}

	/**
	 * This method is used to retrieve users based on company name
	 * @param companyName
	 * @return List UserDetails Object if users are present otherwise empty list
	 */
	public List<UserDetails> getUser(String companyName) {
		List<UserDetails> userList = userDao.getUser(companyName);
		if(userList.size() > 0) {
			return userList;
		}
		return Collections.emptyList();
	}

}
