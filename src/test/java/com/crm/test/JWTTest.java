package com.crm.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import com.crm.ServiceDaoImpl.CommonServiceDaoImpl;
import com.crm.helper.JwtHelperUtil;
import com.crm.model.UserRequest;

public class JWTTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

	@InjectMocks
    private CommonServiceDaoImpl common; // Assuming the JdbcTemplate is injected into CommonServiceDaoImpl
	
	@MockBean
	private UserRequest user;
	
    @Mock
	private  JwtHelperUtil jwtHelperUtil;
	
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

  //  @Test
//    public void testUserLoginDetails() throws Exception {
//        // Create the loginRequestDTO object (your input)
//        UserRequest loginRequestDTO = new UserRequest();
//        loginRequestDTO.setUsername("admin");
//
//        // Create mock result data that will be returned from JdbcTemplate.call
//        Map<String, Object> mockResultData = new HashMap<>();
//        
//        // Simulate the structure of your result set.
//        UserRequest userRequest = new UserRequest();
//        userRequest.setUsername("admin");  // Set null or provide an actual username if needed
//        userRequest.setPassword("NZ4a313B3A==");
//        userRequest.setUSERID("admin");
//        userRequest.setGROUPID("1");
//        userRequest.setROLEID("1");
//        userRequest.setFullName("admin");
//        userRequest.setContactNo("8777737333");
//        userRequest.setEmailId("tauqeer.gmail");
//        System.out.println("userRequest ::::::: "+userRequest);
//
//
//        mockResultData.put("#result-set-1", userRequest); // Mock the expected result set
//        System.out.println("mockResultData ::::::: "+mockResultData);
//
//        // Mock the behavior of JdbcTemplate.call (adjust to match actual usage in your code)
//        when(common.userLoginDetails(loginRequestDTO)).thenReturn(userRequest);
//
//        // Now call the method you are testing (userLoginDetails)
//        UserRequest result = common.userLoginDetails(loginRequestDTO);
//        System.out.println("result ::::::: "+result);
//
//        // Validate the result
//        //assertNotNull(result); // Assert that the result is not null
//        assertEquals("admin", result.getUSERID()); // Verify USERID from the result
//        assertEquals("1", result.getGROUPID()); // Verify GROUPID from the result
//        assertEquals("1", result.getROLEID()); // Verify ROLEID from the result
//        assertEquals("admin", result.getFullName()); // Verify FullName from the result
//        assertEquals("8777737333", result.getContactNo()); // Verify ContactNo from the result
//        assertEquals("tauqeer.khan@1pay.in", result.getEmailId()); // Verify EmailId from the result
//
//        // Optionally, print result for debugging purposes
//        System.out.println("Returned result from userLoginDetails: " + result);
//    }
    
    @Test
    public void testGenerateToken() {
        // Prepare the UserRequest object with the test data
    	UserRequest userRequest = new UserRequest();
        userRequest.setUsername("admin");
        userRequest.setPassword("NZ4aa3B3A==");
        userRequest.setUSERID("admin");
        userRequest.setGROUPID("1");
        userRequest.setROLEID("1");
        userRequest.setFullName("admin");
        userRequest.setContactNo("8777737333");
        userRequest.setEmailId("tauqeer.gmail");
        System.out.println("Generated Token: " + userRequest);

        // Define the expected token (this can be a sample token for testing purposes)
        UserDetails userDetails = mock(UserDetails.class);

        // Populate the mock UserDetails object with necessary values from UserRequest
        when(userDetails.getUsername()).thenReturn(userRequest.getUsername());
        when(userDetails.getPassword()).thenReturn(userRequest.getPassword());
        // Populate other necessary fields...

        // Define the expected token (this can be a sample token for testing purposes)
        String expectedToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNDc3MjU2NywiaWF0IjoxNzM0NzcwNzY3fQ.viAAMJAOlKuLeH87fxC2CapNTe-zsM-2sBABB0KFffIcyX3hB66scXAHIVbb27AKFY-efEBFq_0h38sDKHpQ2Q";

        // Mock the behavior of JwtHelperUtil.generateToken()
        when(jwtHelperUtil.generateToken(userDetails)).thenReturn(expectedToken);

        // Call the method that uses generateToken
        String token = jwtHelperUtil.generateToken(userDetails);
        System.out.println("Generated Token: " + token);
        
        verify(jwtHelperUtil, times(1)).generateToken(userDetails);

        
    }
}
