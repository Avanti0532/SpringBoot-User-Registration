package com.avanti.companyuserregistrationmicroservice.model;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This is a POJO class for storing user details
 * @author deshmukh avanti, harish
 * @version 1.0
 */
@ApiModel(description="Details of the user registered within a company")
@Document("company_user")
public class UserDetails {
	
	@Id
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	private ObjectId id;
	
	@ApiModelProperty(
			  value = "company name",
			  name = "companyName",
			  example = "IonIdea")
	@NotEmpty(message = "Company Name is required")
	@Field(value = "company_name")
	private String companyName;
	
	@ApiModelProperty(
			  value = "first name of the user",
			  name = "firstName",
			  example = "John")
	@NotEmpty(message = "First Name is required")
	@Field(value = "first_name")
	private String firstName;
	
	@ApiModelProperty(
			  value = "last name of the user",
			  name = "lastName",
			  example = "Hemsworth")
	@NotEmpty(message = "Last Name is required")
	@Field(value = "last_name")
	private String lastName;
	
	@ApiModelProperty(
			  value = "email id of the user",
			  name = "emal",
			  example = "johnhemsworth@ionidea.com")
	@Email(message = "Please provide a valid email id")
	@NotEmpty(message = "Email id is required")
	@Indexed(unique = true)
	private String email;
	
	@ApiModelProperty(
			  value = "password of the user",
			  name = "password",
			  example = "john123#")
	@NotEmpty(message = "Password is required")
	@JsonIgnore
	private String password;
	
	@ApiModelProperty(
			  value = "role of the user in the company",
			  name = "userRole",
			  example = "HR/Admin/Project Manager")
	@Field(value = "user_role")
	private String userRole;
	
	@ApiModelProperty(value="Location of the image of the user",
			name="userImage",
			example="/test/5ff4c982fc68f46fcc81cae0_image.jpg")
	@Field(value = "user_image")
	private String userImage;
	
	@ApiModelProperty(hidden=true)
	@JsonIgnore
	@Field(value = "verification_token")
	private String verificationToken;
	
	@ApiModelProperty(hidden=true)
	@JsonIgnore
	@Field(value = "token_creation_date")
	private Date tokenCreationDate;
	
	@ApiModelProperty(value="Verified status of the company user",
			name="verificationStatus",
			example="true/false")
	@Field(value = "verification_status")
	private boolean verificationStatus;

	public UserDetails() {}
	
	public UserDetails(ObjectId id, String company,String firstName, String lastName, String email, String password,
			String userRole, String userImage, String verificationToken, Date tokenCreationDate, boolean verificationStatus) {
		super();
		this.id = id;
		this.companyName = company;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
		this.userImage = userImage;
		this.verificationToken = verificationToken;
		this.tokenCreationDate = tokenCreationDate;
		this.verificationStatus = verificationStatus;
	}

	
	public ObjectId getId() {
		return id;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public boolean getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public Date getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(Date tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}
	
	
	
}
