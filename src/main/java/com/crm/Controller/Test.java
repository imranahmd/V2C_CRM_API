package com.crm.Controller;
import java.time.LocalDate;
import java.time.LocalDate;

public class Test {
	
	
	  public static void main(String[] args) {
	        // Example date string
	        String dateStr = "2024-06-01";
	        // Convert string to LocalDate object
	        LocalDate date = LocalDate.parse(dateStr);
	        // Decrement the date by one day
	        LocalDate newDate = date.minusDays(1);
	        // Convert LocalDate object back to string
	        String newDateStr = newDate.toString();

	        System.out.println(newDateStr);  // Output: "2024-06-03"
	    }
}
