package com.crm.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.model.LifeCycleStatus;


@Repository
public interface LifeCycleTransactionRepo extends JpaRepository<LifeCycleStatus, Long>{
	
	@Query(value = "call pro_updatetrnsactionlifecycle1(:TransId, :RnStatus, :fileName, :remarks, :Addedby);", nativeQuery = true)
	List<Map<String, Object>> updaterecords(String TransId, String RnStatus, String fileName, String remarks, String Addedby);
	
	@Query(value = "SELECT CONVERT(TransId, char(50)) as TransactionId, transLifeCycle as LifeCycleStatus,FileName, Remarks, case when ISNULL(changeStatus) then '' else changeStatus end as SuccessMessage, case when ISNULL(ErrorMessage) then '' else ErrorMessage end as ErrorMessage, AddedBy, DATE_FORMAT(AddedOn,'%d-%m-%Y %h:%m:%s') as AddedOn  FROM tbl_transactionlifecycle where TransId=:bigIntegerStr ;", nativeQuery = true)
	List<Map<String, Object>> findbyTransId(BigInteger bigIntegerStr);

	@Query(value = "call pro_TSettlement(:type, :spId, :instrument, :cycle, :reconDate, :userId);", nativeQuery = true)
	List<Map<String, Object>> findSettleReport(int type, int spId, String instrument, String cycle, String reconDate,
			String userId);
	
	@Query(value = "call pro_T0Settlement(:type, :spId, :instrument, :cycle, :reconDate, :userId);", nativeQuery = true)
	List<Map<String, Object>> findSettleReportT0(int type, int spId, String instrument, String cycle, String reconDate,
			String userId);
	
	@Query(value = "call Pro_GenerateTSPSettlement(:spId, :instrument, :cycle, :reconDate, :userId);", nativeQuery = true)
	List<Tuple> findSettleReportGenerate( int spId, String instrument, String cycle,
			String reconDate, String userId);
	
	@Query(value = "call Pro_GenerateTSPSettlementT0(:spId, :instrument, :cycle, :reconDate, :userId);", nativeQuery = true)
	List<Tuple> findSettleReportGenerateTo( int spId, String instrument, String cycle,
			String reconDate, String userId);
	
	
	
	@Query(value = "call pro_GenratedFileDetailsInsert (:spId, :instrument, :cycle, :reconDate, :userId, :fileName, :fileLocation) ;", nativeQuery = true)
	List<Map<String, Object>> findSettleReportGenerateUpdate(int spId, String instrument, int cycle, String reconDate, String userId, String fileName, String fileLocation);
	
	@Query(value = "call pro_GenratedFileDetailsInsert (:spId, :instrument, :cycle, :reconDate, :userId, :fileName, :fileLocation) ;", nativeQuery = true)
	List<Map<String, Object>> findSettleReportGenerateUpdateTo(int spId, String instrument, int cycle, String reconDate, String userId, String fileName, String fileLocation);
	
	@Query(value = "select count(*) from tbl_tspsettlementfiledetails where SPId=:spId and InstrumentId=:instrument and CycleId=:cycleId and ReconDate=:reconDate ;", nativeQuery = true)
	String findrecordsduplicate(int spId, String instrument, int cycleId, String reconDate);
	
	@Query(value = "call pro_displayBulktranslifecycle (:bigIntegerStr, :rrnStatus) ;", nativeQuery = true)
	Map<String, Object> findreccords(BigInteger bigIntegerStr, String rrnStatus);
	
	@Query(value = "call pro_displayBulktranslifecycle (:bigIntegerStr, :rrnStatus) ;", nativeQuery = true)
	Map<String, Object> findreccordsRR(String bigIntegerStr, String rrnStatus);
	
	@Query(value = "select TransLifeCycle from tbl_transactionmaster where date_time between :req1 and :req2 and cycleId=:cycleId and merchant_id =:merchantId and process_id=:proccessId and trans_status='Ok'and is_settled_consider ='Y' group by TransLifeCycle,merchant_id ;", nativeQuery = true)
	String getStatus(String cycleId, String proccessId, String merchantId, String req1, String req2);

}
