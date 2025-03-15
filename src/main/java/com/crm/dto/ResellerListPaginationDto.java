package com.crm.dto;

import java.util.List;

public class ResellerListPaginationDto {
	
	private List<ResellerListDto> resellers;
	private Integer numberOfRecords;
	private Integer pageNumber;
	private Long totalRecords;
	
	public List<ResellerListDto> getResellers() {
		return resellers;
	}
	public void setResellers(List<ResellerListDto> resellers) {
		this.resellers = resellers;
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
}
