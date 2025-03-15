package com.crm.Repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.crm.model.MerchantList;

public interface ChargebackRepo extends JpaRepository<MerchantList, Long> {
	
	
	
	@Transactional
	@Modifying
	@Query(value = "call pro_chargebackUpdate(:iId, :iMerchantId,:iTxnId,:iRefNo,:iFile,:iRemark);", nativeQuery = true)
	int saveRaisedchargebackMerchantupdateChargeBackData(String iId,String iMerchantId, String iTxnId, String iRefNo, String iFile,String iRemark);
	
}
