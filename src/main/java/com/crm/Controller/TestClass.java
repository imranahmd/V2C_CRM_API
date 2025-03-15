package com.crm.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClass {
	
	@RequestMapping("/welcome")
	public String welcome()
	{
		String text="Hi This Unauthrised";
		return text;
	}
	

}
