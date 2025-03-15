package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_mstpgbank")
public class BankDetails {
	  		@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name = "id") 
		   private int  ID;
	  		
		@Column(name = "bankId")
	   private String  BankID;
	 
	
		@Column(name = "bankName")
		   private String  BankName;
		
		@Column(name = "Created_By")
		   private String  CreatedBy;
		@Column(name = "Modified_By")
		   private String  ModifiedBy;
	  
		public int getID() {
			return ID;
		}
		public void setID(int iD) {
			ID = iD;
		}
		public String getBankID() {
			return BankID;
		}
		public void setBankID(String bankID) {
			BankID = bankID;
		}
		public String getBankName() {
			return BankName;
		}
		public void setBankName(String bankName) {
			BankName = bankName;
		}
		public String getCreatedBy() {
			return CreatedBy;
		}
		public void setCreatedBy(String createdBy) {
			CreatedBy = createdBy;
		}
		public String getModifiedBy() {
			return ModifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			ModifiedBy = modifiedBy;
		}
}
