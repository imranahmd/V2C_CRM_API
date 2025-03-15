package com.crm.dto;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.crm.model.MerchantBank;

public class MerchantPaginationDto {
	private List<MerchantDto> merchants;
	private Integer numberOfRecords;
	private Integer pageNumber;
	private Long totalRecords;
	private List<Object> columns;
	
	public List<MerchantDto> getMerchants() {
		return merchants;
	}
	public void setMerchants(List<MerchantDto> merchants) {
		this.merchants = merchants;
	}
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<Object> getColumns() {
		return columns;
	}
	public void setColumns(List<Object> columns) {
		this.columns = columns;
	}
	@Override
	public String toString() {
		return "MerchantPaginationDto [merchants=" + merchants + ", numberOfRecords=" + numberOfRecords
				+ ", pageNumber=" + pageNumber + ", totalRecords=" + totalRecords + ", columns=" + columns + "]";
	}
	
	
		
}
