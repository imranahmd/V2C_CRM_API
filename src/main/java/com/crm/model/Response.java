package com.crm.model;

import java.util.Arrays;

import org.json.JSONArray;

public class Response {

	private boolean status;
	

	

	private String msg;
	private JSONArray json;
	
	
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	@Override
	public String toString() {
		return "Response [status=" + status + ", msg=" + msg + ", json=" + json + "]";
	}
	
	public Response(boolean status, String msg, JSONArray json) {
		super();
		this.status = status;
		this.msg = msg;
		this.json = json;
	}




	
	
	
	
}
