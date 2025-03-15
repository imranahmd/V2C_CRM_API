package com.crm.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.crm.Repository.BankDetailsRepository;
import com.crm.services.BankDetailsService;

import java.util.*;


class BankDetailsServiceTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	
	 @MockBean
	  private BankDetailsRepository bankDetailsRepository; // Mock the repository (or service) you use in the insertUpdateBank call
	 
	 @Mock
	 private BankDetailsRepository bankDetails;
	
	
	@InjectMocks
	private BankDetailsService bankDetailsService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBankSuccess() throws Exception {
		// Mock Result Data
		Map<String, Object> mockResultData = new HashMap<>();
		List<Map<String, Object>> mockResultSet = new ArrayList<>();
		Map<String, Object> mockRow = new HashMap<>();
		mockRow.put("BankName", "Test Bank");
		mockRow.put("Id", "123");
		mockRow.put("BankId", "B123");
		mockResultSet.add(mockRow);
		mockResultData.put("#result-set-1", mockResultSet);

		// Mock JdbcTemplate Call
		when(jdbcTemplate.call(any(), anyList())).thenReturn(mockResultData);

		// Execute Method
		String result = bankDetailsService.getBank();
		System.out.println("result::::::::"+result);
		// Verify Result
		JSONArray jsonArray = new JSONArray(result);
		assertEquals(1, jsonArray.length());
		assertEquals("Test Bank", jsonArray.getJSONObject(0).getString("BankName"));
		assertEquals("123", jsonArray.getJSONObject(0).getString("Id"));
		assertEquals("B123", jsonArray.getJSONObject(0).getString("BankId"));
	}

	@Test
	void testGetBankException() throws Exception {
		// Mock Exception
		when(jdbcTemplate.call(any(), anyList())).thenThrow(new RuntimeException("Database Error"));

		// Execute Method
		String result = bankDetailsService.getBank();

		// Verify Result
		JSONObject jsonObject = new JSONObject(result);
		assertFalse(jsonObject.getBoolean("Status"));
		assertEquals("Data Not Aavailable", jsonObject.getString("Message"));
	}
	
	
	@Test
    public void testCreateBankInsertSuccess() {
        // Arrange
        when(bankDetails.insertUpdateBank(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(0); // Simulate insert

        // Act
        JSONObject response = bankDetailsService.createBank("Test Bank", "1", "123", "Admin", "Admin");

        // Assert
        assertTrue(response.getBoolean("Status"));
        assertEquals("Inserted Successfully", response.getString("Message"));
    }

    @Test
    public void testCreateBankUpdateSuccess() {
        // Arrange
        when(bankDetails.insertUpdateBank(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(1); // Simulate update

        // Act
        JSONObject response = bankDetailsService.createBank("Test Bank", "1", "123", "Admin", "Admin");

        // Assert
        assertTrue(response.getBoolean("Status"));
        assertEquals("Updated Successfully", response.getString("Message"));
    }
	
   
    @Test
    public void testDeleteBankSuccess() {
        // Arrange: Simulate successful deletion
        when(bankDetails.getBank(anyString())).thenReturn(1);

        // Act: Call the method
        JSONObject response = bankDetailsService.deleteBank("123");

        // Assert: Verify the response
        assertTrue(response.getBoolean("Status"));
        assertEquals("Delete Successfully", response.getString("Message"));
    }
    
    
    @Test
    public void testDeleteBankFailure() {
        // Arrange: Simulate unsuccessful deletion
        when(bankDetails.getBank(anyString())).thenReturn(0);

        // Act: Call the method
        JSONObject response = bankDetailsService.deleteBank("123");

        // Assert: Verify the response
        assertFalse(response.getBoolean("Status"));
        assertEquals("Unable to Delete", response.getString("Message"));
    }
	
	
	
	
	
}
