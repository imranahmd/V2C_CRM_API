package com.crm.dto;

import java.util.List;

public class lBPSPaginationDto {

	private List<lBPSDto> invoices;
	private Integer numberOfRecords;
	private Integer pageNumber;
	private Long totalRecords;
	
	
	public List<lBPSDto> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<lBPSDto> invoices) {
		this.invoices = invoices;
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
