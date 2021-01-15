package com.test.avanti.companyuserregistrationmicroservice.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.avanti.companyuserregistrationmicroservice.configuration.MailHelper;
import com.avanti.companyuserregistrationmicroservice.controller.UserController;
import com.avanti.companyuserregistrationmicroservice.dao.UserDAO;
import com.avanti.companyuserregistrationmicroservice.model.UserDetails;
import com.avanti.companyuserregistrationmicroservice.service.UserService;

/**
 * Test class for RestController to unit test POST/GET endpoints of UserController
 * @author deshmukh avanti, harish
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ComponentScan("com.avanti.companyuserregistrationmicroservice")
public class UserControllerTest {
		
		@Autowired
		private MockMvc mvc;
		
		@MockBean
		private UserService userService;
		
		@MockBean 
		private UserDAO userDAO;
		
		@MockBean
		private MailHelper mailHelper;
		
		@InjectMocks
		private UserController userController;
		
		@Before
		public void setUp() {
			mvc = MockMvcBuilders.standaloneSetup(userController).build();
		}
		
		@SuppressWarnings("deprecation")
		@Before
		public void init() {
			MockitoAnnotations.initMocks(this);
		}
		
		private UserDetails createUserDetails() {
			UserDetails userDetails = new UserDetails();
			userDetails.setCompanyName("IonIdea");
			userDetails.setFirstName("Lily");
			userDetails.setLastName("Johnson");
			userDetails.setEmail("lily23@ionidea.com");
			userDetails.setPassword("lily123");
			userDetails.setUserRole("HR");
			userDetails.setVerificationStatus(false);
			userDetails.setVerificationToken(UUID.randomUUID().toString());
			userDetails.setTokenCreationDate(new Date());
			userDetails.setUserImage("D:\\IonIdea_JnG\\microservices\\Jng_GitHub\\jng.rct\\Coding\\Avanti\\CompanyUserImages\\5ff4c982fc68f46fcc81cae0_image");
			return userDetails;
		}
		
		/**
		 * Test method to verify if the user details are successfully added through POST endpoint
		 * @throws Exception
		 */
		@Test
		public void testAddUser() throws Exception {
			Mockito.when(userService.getUserDetails(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			.thenReturn(createUserDetails());
			Mockito.when(userService.addUserDetails(Mockito.any(UserDetails.class), Mockito.anyString(), Mockito.any()))
			.thenReturn(createUserDetails());
			 MockMultipartFile company = new MockMultipartFile(
		        "companyName", 
		        "IonIdea", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        "IonIdea".getBytes()
		      );
			 MockMultipartFile firstName = new MockMultipartFile(
				        "firstName", 
				        "Lily", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "Lily".getBytes()
			 );
			 MockMultipartFile lastName = new MockMultipartFile(
				        "lastName", 
				        "Johnson", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "Johnson".getBytes()
			 );	 
			 MockMultipartFile email = new MockMultipartFile(
				        "email", 
				        "lily123@ionidea.com", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "lily123@ionidea.com".getBytes()
			 );
			 MockMultipartFile password = new MockMultipartFile(
				        "password", 
				        "lily123", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "lily123".getBytes()
			 );
			 MockMultipartFile userRole = new MockMultipartFile(
				        "userRole", 
				        "HR", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "HR".getBytes()
			 );	
			 MockMultipartFile userImage = new MockMultipartFile(
				        "userImage", 
				        "user_image.jpg", 
				        MediaType.MULTIPART_FORM_DATA_VALUE, 
				        "user_image.jpg".getBytes()
			 );
			mvc.perform(MockMvcRequestBuilders.multipart("/add-users")	
					.file(company)
					.file(firstName)
					.file(lastName)
					.file(email)
					.file(password)
					.file(userRole)
					.file(userImage))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("lily23@ionidea.com"));
		}

	
		
		/**
		 * Test method to verify if the user details are not successfully added through POST endpoint
		 * Method should return a bad request
		 * @throws Exception
		 */
		@Test
		public void testAddUserBadRequest() throws Exception {
			Mockito.when(userService.getUserDetails(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			.thenReturn(createUserDetails());
			Mockito.when(userService.addUserDetails(Mockito.any(UserDetails.class), Mockito.anyString(), Mockito.any()))
			.thenReturn(null);
			 MockMultipartFile company = new MockMultipartFile(
		        "companyName", 
		        "IonIdea", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        "IonIdea".getBytes()
		      );
			 MockMultipartFile firstName = new MockMultipartFile(
				        "firstName", 
				        "Lily", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "Lily".getBytes()
			 );
			 MockMultipartFile lastName = new MockMultipartFile(
				        "lastName", 
				        "Johnson", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "Johnson".getBytes()
			 );	 
			 MockMultipartFile email = new MockMultipartFile(
				        "email", 
				        "lily123@ionidea.com", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "lily123@ionidea.com".getBytes()
			 );
			 MockMultipartFile password = new MockMultipartFile(
				        "password", 
				        "lily123", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "lily123".getBytes()
			 );
			 MockMultipartFile userRole = new MockMultipartFile(
				        "userRole", 
				        "HR", 
				        MediaType.TEXT_PLAIN_VALUE, 
				        "HR".getBytes()
			 );		
			mvc.perform(MockMvcRequestBuilders.multipart("/add-users")	
					.file(company)
					.file(firstName)
					.file(lastName)
					.file(email)
					.file(password)
					.file(userRole))
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.jsonPath("$").value("some error occured"));
		}
		
		/**
		 * Test method to perform user verification based on verification token
		 * Method should return a bad request
		 * @throws Exception
		 */
		@Test
		public void testverifyUser() throws Exception {
			Mockito.when(userService.verifyUserToken(Mockito.anyString()))
			.thenReturn("user verified");
			mvc.perform(MockMvcRequestBuilders.get("/confirm-user")
					.param("token", "af2bd23d-1f8d-46e1-8599-e8ad2bd24c37"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$").value("user verified"));
		}
		
		/**
		 * Test method to verify if GET request returns error message for an invalid token
		 * Method should return a bad request
		 * @throws Exception
		 */
		@Test
		public void testverifyUserInvalidToken() throws Exception {
			Mockito.when(userService.verifyUserToken(Mockito.anyString()))
			.thenReturn("no such token exists");
			mvc.perform(MockMvcRequestBuilders.get("/confirm-user")
					.param("token", "af2bd23d-1f8d-46e1-8599-e8ad2bd24c32"))
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.jsonPath("$").value("no such token exists"));
		}
		
		/**
		 * Test method to verify if GET request returns error message when user is already verified
		 * Method should return a bad request
		 * @throws Exception
		 */
		@Test
		public void testverifyUserAlreadyVerified() throws Exception {
			Mockito.when(userService.verifyUserToken(Mockito.anyString()))
			.thenReturn("user is already verified");
			mvc.perform(MockMvcRequestBuilders.get("/confirm-user")
					.param("token", "af2bd23d-1f8d-46e1-8599-e8ad2bd24c32"))
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.jsonPath("$").value("user is already verified"));
		}
		
		/**
		 * Test method to verify if GET request returns error message when user is already verified
		 * Method should return a bad request
		 * @throws Exception
		 */
		@Test
		public void testverifyUserUpdateTokenStatusError() throws Exception {
			Mockito.when(userService.verifyUserToken(Mockito.anyString()))
			.thenReturn("error in updating confirmation token");
			mvc.perform(MockMvcRequestBuilders.get("/confirm-user")
					.param("token", "af2bd23d-1f8d-46e1-8599-e8ad2bd24c32"))
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.jsonPath("$").value("error in updating confirmation token"));
		}
		
		/**
		 * Test method to verify if GET request returns error message when an issue occurred while sending email to the user
		 * Method should return a bad request
		 * @throws Exception
		 */
		@Test
		public void testverifyUserEmailError() throws Exception {
			Mockito.when(userService.verifyUserToken(Mockito.anyString()))
			.thenReturn("error in sending email to the user");
			mvc.perform(MockMvcRequestBuilders.get("/confirm-user")
					.param("token", "af2bd23d-1f8d-46e1-8599-e8ad2bd24c32"))
					.andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andExpect(MockMvcResultMatchers.jsonPath("$").value("error in sending email to the user"));
		}
		
		/**
		 * Test method to verify if user details are successfully retrieved through GET endpoint
		 * @throws Exception
		 */
		@Test
		public void testGetUser() throws Exception {
			List<UserDetails> userList = Arrays.asList(createUserDetails());
			Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(userList);
			mvc.perform(MockMvcRequestBuilders.get("/list-users")
					.param("companyName", "IonIdea"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("lily23@ionidea.com"));
			}
}
