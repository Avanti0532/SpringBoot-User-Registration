package com.jobsngigs.companyuserregistrationmicroservice.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.jobsngigs.companyuserregistrationmicroservice.model.UserDetails;
import com.jobsngigs.companyuserregistrationmicroservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * Rest controller class to perform add/get operations on the user using POST/GET endpoints
 * @author deshmukh avanti, harish
 * @version 1.0
 */
@Api(value="UserController", description= "A Rest controller in use for company admin to add/get user details")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * POST request used to add company user details provided as @RequestPart (multipart/form-data)
	 * @param companyName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param userRole
	 * @param userImage
	 * @return ResponseEntity based on result returned by service layer
	 */
	@ApiOperation(value = "POST Endpoint to add user details",notes = "This endpoint is used to add user details to the database.")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Ok",response = UserDetails.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping(path = "/add-users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addUser(@RequestPart(value="companyName") String companyName, 
			@RequestPart(value="firstName") String firstName,
			@RequestPart(value="lastName") String lastName,
			@RequestPart(value="email") String email,
			@RequestPart(value="password") String password,
			@RequestPart(value="userRole",required=false) String userRole,
			@RequestPart(value="userImage",required=false) MultipartFile userImage,
			HttpServletRequest request) throws Exception{
		UserDetails getDetails = userService.getUserDetails(companyName, firstName, lastName, email, password, userRole);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		UserDetails user = userService.addUserDetails(getDetails, url, userImage);
		return (user != null) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>("some error occured", HttpStatus.BAD_REQUEST) ;
	}
	
	/**
	 * GET request used to perform user verification based on confirmation token
	 * @param token
	 * @return ResponseEntity based on result returned by service layer
	 */
	@ApiOperation(value = "GET Endpoint to verify the user once the user clicks on confirmation link",notes = "This endpoint is used to update verification status"
			+ "of the user to true if not verified")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Ok",response = String.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping(path = "/confirm-user", produces = "text/plain")
	public ResponseEntity<Object> verifyUser(@RequestParam(value="token") String token){
		String response = userService.verifyUserToken(token);
		return (response.equalsIgnoreCase("user verified")) ? new ResponseEntity<>(response, HttpStatus.OK): new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * GET request used to retrieve user details based on company name
	 * @param companyName
	 * @return ResponseEntity based on result returned by service layer
	 */
	@ApiOperation(value = "GET Endpoint to retrieve user details based on comapny name",notes = "This endpoint is used to retrieve "
			+ "job details based on company name from the database.")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Ok", response = UserDetails.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping(path = "/list-users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUser(@RequestParam(value="companyName") String companyName){
		if(companyName!=null) {
			List<UserDetails> userList = userService.getUser(companyName);
			return new ResponseEntity<>(userList, HttpStatus.OK);
		}
			return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
	}
}
