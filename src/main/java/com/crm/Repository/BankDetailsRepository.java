package com.crm.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.BankDetails;
@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Integer> {
	@Query(value = "CALL pro_insertOrUpdateBankDetails(:ID,:BankID,:BankName,:CreatedBy,:ModifiedBy);",nativeQuery = true)
	int insertUpdateBank(@Param("BankName") String BankName,@Param("ID") String ID,@Param("BankID") String BankID,@Param("CreatedBy") String CreatedBy,@Param("ModifiedBy") String ModifiedBy);
	/*
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query(value = "CALL pro_insertOrUpdateBankDetails(:id,:bankName);",
	 * nativeQuery = true) int BankDetails(@);
	 */
	@Query(value = "CALL pro_getBankDetails();",nativeQuery = true)
	List<Object> getBankDetails();
	
	@Transactional
	@Modifying
	@Query(value = "CALL deleteBankMasterDetails(:ID);",nativeQuery = true)
	int getBank(@Param("ID") String ID);
	
}
