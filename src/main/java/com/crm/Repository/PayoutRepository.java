package com.crm.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.model.MerchantList;

public interface PayoutRepository extends JpaRepository<MerchantList, Long> {
	
	@Query(value = "select * from tbl_reconexception where Is_ForceRecon= ? and Uploaded_On like concat(DATE_FORMAT(now(),'%Y-%m-%d'))", nativeQuery = true)
	String ExceptionList(String Is_ForceRecon);

	@Query(value = "select * from PayoutStatus where date_Time= ? and payout_escrow= ?", nativeQuery = true)

	List<Object> findByProperty(Date date, String payoutEscrow);

	@Query(value = "call pro_insertPayoutStatusValue(:date, :payoutEscrow, :payoutStatus);", nativeQuery = true)

	List<Object[]> saveData(java.util.Date date, String payoutEscrow, int payoutStatus);

	@Query(value = "call PPpro_PayoutGenerate_pa_v1(:type, :transDate, :username, :payoutBy);", nativeQuery = true)

	List<Tuple> getTranctionwisePayout(int type, String transDate, String username, String payoutBy);

	@Query(value = "call pro_updatePayoutStatus_pa_1(:type, :IdVal, :Escrow, :FileName, :fileLocation);", nativeQuery = true)

	List<Map<String, Object>> updatePayoutStatus(int type, int IdVal, String Escrow, String FileName,
			String fileLocation);

	@Query(value = "select * from payoutstatus where date_Time = ? and payout_escrow = ? ;", nativeQuery = true)
	List<Map<String, Object>> selectDataPayoutSatus(java.util.Date date, String payoutEscrow);

	@Query(value = "select * from payoutstatus where date_Time = ? and id = ? and payout_escrow = ? ;", nativeQuery = true)
	List<Map<String, Object>> selectDataIfTranExist(String transactionDate, int iD, String payoutEscrow);

	
	@Query(value = "select status from payoutstatus where date_Time = ? and payout_escrow = ?", nativeQuery = true)
	String checkStatusVal(java.util.Date transactionDate, String payoutEscrow);
	
	
	@Query(value = "select merchantwise_payout_path from payoutstatus where date_Time = ? and payout_escrow = ?", nativeQuery = true)
	String merchantPathName(java.util.Date date, String payoutEscrow);
	
	
	
	
	@Query(value = "select transactionwise_payout_path from payoutstatus where date_Time = ? and payout_escrow = ?", nativeQuery = true)
	String transPathName(java.util.Date date, String payoutEscrow);
	
	
	//V1 data
	
	@Query(value = "call PPpro_PayoutGenerate_pa_v2(:type, :transDate, :username, :payoutBy);", nativeQuery = true)

	List<Tuple> getTranctionwisePayoutV1(int type, String transDate, String username, String payoutBy);

	
}
