package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "tbl_transactionlifecycle")
public class LifeCycleStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name= "TransId")
	private Long TransId;
	
	@Column(name= "transLifeCycle")
	private String transLifeCycle;
	
	@Column(name= "FileName")
	private String FileName;
	
	@Column(name= "Remarks")
	private String Remarks;
	
	@Column(name= "AddedBy")
	private String AddedBy;
	
	

}
