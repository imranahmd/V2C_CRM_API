package com.crm.services;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.crm.model.UserRequest;

import lombok.extern.slf4j.Slf4j;


public interface CommonService {

	public String  GetMerchantList(String Name) throws Exception;
	public String uploadForm(File file);
	public ArrayList getDropDown(String Type, String Value) throws SQLException;
	public UserRequest userLoginDetails(UserRequest loginRequestDTO) throws Exception;
	public String  getMenuHierarchy(String roleId) throws Exception;

}
