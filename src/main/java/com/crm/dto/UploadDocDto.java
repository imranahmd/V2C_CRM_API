package com.crm.dto;

import java.util.List;

public class UploadDocDto {

	private String merchantId;
	private List<UploadDocDetailsDto> docType;

	public UploadDocDto() {
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public List<UploadDocDetailsDto> getDocType() {
		return docType;
	}

	public void setDocType(List<UploadDocDetailsDto> docType) {
		this.docType = docType;
	}
	
	
}
