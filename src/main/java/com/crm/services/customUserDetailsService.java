package com.crm.services;

import java.awt.Menu;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crm.model.UserRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class customUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRequest user1;

	@Autowired
	private UserRequest user2;
	@Autowired
	private CommonService CommonService;

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			user1.setUsername(username);
			user2 = CommonService.userLoginDetails(user1);

			if (username != null) {
				return new User(username, user2.getPassword(), new ArrayList<>());

			} else {
				throw new UsernameNotFoundException("User not found!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("deprecation")
	public String getMenuHierarchy(String roleId) throws Exception {
		DataSource connection = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		String str = null;
		Connection cn = null;

		try {

			connection = JdbcTemplate.getDataSource();
			cn = connection.getConnection();

			if (cn != null) {
				callableStatement = cn.prepareCall("{call pro_getRoleMenuPermissionV2(?)}");
				callableStatement.setString(1, roleId);
				callableStatement.execute();
				rs = callableStatement.getResultSet();
			}

			// initiaze
			Map<String, List<Map<Object, Object>>> map = new LinkedHashMap<>();// new HashMap<String,
																				// List<Map<Object,Object>>>();
			Map<String, List<String>> menuMap = new HashMap<String, List<String>>();
			Map<String, List<String>> menuMap1 = new HashMap<String, List<String>>();
			int i = 0;
			while (rs.next()) {
				i++;
				List<Map<Object, Object>> list = new ArrayList<>();

				LinkedHashMap<Object, Object> pr = new LinkedHashMap<>(); // initiaze
				LinkedHashMap<Object, Object> lhm = new LinkedHashMap<>();
				String menu = rs.getString("MenuName");
				String subMenu = rs.getString("SubmenuName");
				String link = rs.getString("MenuLink");
				String permission = rs.getString("PermissionAction");
				pr.put("permissions", permission);
				lhm.put("Permission", pr);

				lhm.put("submenu", subMenu);
				lhm.put("link", link);
				lhm.put("Parents_Id", rs.getString("MenuId"));
				lhm.put("subMenu_Id", rs.getString("submenuid"));
				if (map.containsKey(menu)) // check if mainmenu already present
				{
					map.get(menu).add(lhm);
				} else {
					// initialize list here

					list.add(lhm);
					map.put(menu, list);
				}

			}
			Gson gson = new Gson();
			str = gson.toJson(map);

			System.out.print("ParmVlaues::::::::::::::: " + str);
			cn.close();

		} catch (Exception e1) {
			throw new Exception();
		} finally {
			cn.close();

			JdbcTemplate.getDataSource().getConnection().close();

			System.out.println("finally block is always executed");
		}
		return str;

	}

	///////////
	public ArrayList GetMenusHiracy(String roleId) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_getRoleMenuPermissionV3(?)}");
				callableStatement.setString(1, roleId);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Fields does not exist");
				return arrayList;
			}
			/*
			 * Gson gson = new Gson(); String jsonArray = gson.toJson(arrayList);
			 */
			return arrayList;
		} catch (Exception e1) {
			throw new Exception();
		} finally {
			JdbcTemplate.getDataSource().getConnection().close();
			System.out.println("finally block is always executed");
		}
	}

	public int CheckPermission(String roleId, String permission) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_checkPermission(?,?)}");
				callableStatement.setString(1, roleId);
				callableStatement.setString(2, permission);
				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Fields does not exist");
				return 0;
			}

			Gson gson = new Gson();
			String jsonArray = gson.toJson(arrayList);
			JSONArray Jr = new JSONArray(arrayList);
			int i = Jr.getJSONObject(0).getInt("totalcount");

			System.out.print("CheckPermissionStatus::::: {} " + i);

			return i;
		} catch (Exception e1) {
			throw new Exception();
		}

	}


	
	
	
	}
