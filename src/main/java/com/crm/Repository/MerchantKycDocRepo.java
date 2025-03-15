package com.crm.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.crm.model.MerchantBank;
import com.crm.model.MerchantKycDoc;

public interface MerchantKycDocRepo extends JpaRepository <MerchantKycDoc, Integer>{
	
	List<MerchantKycDoc> findById(int Id);
	
	@Query(value = "SELECT * FROM kyc_upload_doc where merchant_id= ?;", nativeQuery = true)

	List<Object[]> getAllList(String MerchantId );
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM kyc_upload_doc where id= ?;", nativeQuery = true)

	void deleteById(int id );
	
	@Query(value = "call pro_GetDocumentList(:businesstypeId, :merchantId);", nativeQuery = true)
	List<Object[]> findByBussineTypeId(int businesstypeId, String merchantId );
	
	@Query(value = "select if(COUNT(*)>0,'true','false') AS gstin from tbl_mstmerchant where (isnull(GSTINNo) or GSTINNo='') and MerchantId=?;", nativeQuery = true)
	Boolean findGstin(String merchantId );
	
	@Query(value = "SELECT docid FROM tbl_mstdocumentlist where document like '%GST Registration%';", nativeQuery = true)
	ArrayList gstDocumentId();
	
	@Query(value = "call pro_InsertDocument(:merchantId, :docType);", nativeQuery = true)
	List<MerchantKycDoc> createUpload(String merchantId, String docType); 
	
	@Query(value = "call pro_InsertOtherDocument(:merchantId, :docType);", nativeQuery = true)
	List<MerchantKycDoc> uploadKycOtherDocs(String merchantId, String docType); 
	
	@Transactional
	@Modifying
	@Query(value = """
			INSERT INTO tbl_merchant_attachment_upload
			(MerchantId,
			DocumentName,
			DocumentPath,
			Document_addedBy
			) values (:merchantId, :fileOrgname, :DocRef, :user)""", nativeQuery = true)
	
	void inserttablattachment(String merchantId, String fileOrgname, String DocRef, String user);
	
	@Query(value = "select id, MerchantId, DocumentName,DocumentPath, DATE_FORMAT(Document_date, \"%d-%m-%Y\") as Document_date, Document_addedBy from tbl_merchant_attachment_upload where MerchantId=:merchantId ;", nativeQuery = true)
	List<Map<String, Object>> getalldatabymerchant(String merchantId);
}
