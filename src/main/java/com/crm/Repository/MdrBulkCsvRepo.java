package com.crm.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.dto.BulCsvMerchantMdrDto;
import com.crm.model.BulkCsvMdr;
import com.crm.model.MerchantKycDoc;


public interface MdrBulkCsvRepo extends JpaRepository<BulkCsvMdr, Long> {
	

	@Transactional
	@Modifying
	@Query(value = """
			insert into tbl_merchant_mdr (`merchant_id`,
			`sp_id`,
			`bank_id`,
			`instrument_id`,
			`min_amt`,
			`max_amt`,
			`mdr_type`,
			`aggr_mdr`,
			`reseller_mdr`,
			`base_rate`,
			`min_base_rate`,
			`max_base_rate`,
			`min_mdr`,
			`max_mdr`,
			`mid`,
			`tid`,
			`channel_id`,
			`start_date`,
			`end_date`,
			`prefrences`,
			`surcharge`,
			`payout`,
			`card_variant_name`,
			`network`,
			`instrument_brand`,
			`bank_mdr_type`,
			`min_reseller_mdr`,
			`max_reseller_mdr`,
			`reseller_mdr_type`,
			`payout_escrow`) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30)""", nativeQuery = true)
	void createMerchantMdrByCsv(String merchant_id, int sp_id, String bank_id, String instrument_id, Double min_amt,
			Double max_amt, String mdr_type, Double aggr_mdr, Double reseller_mdr, Double base_rate,
			Double min_base_rate, Double max_base_rate, Double min_mdr, Double max_mdr, String mid, String tid,
			String channel_id, String start_date, String end_date, String prefrences, int surcharge, int payout,
			String card_variant_name, String network, int instrument_brand, String bank_mdr_type,
			String min_reseller_mdr, String max_reseller_mdr, String reseller_mdr_type, String payout_escrow );
	
	@Query(value = "select * from tbl_mdrbulkuploadcsv", nativeQuery = true)
	List<Object[]> mdrlistAllRecords ();
	
	@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_merchant_mdr where merchant_id=? AND sp_id=? AND instrument_id=? AND bank_id=? ;", nativeQuery = true)
	boolean findByMercBankInstSpid(String MerchantId, int Spid , String Instrumentid, String BankId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tbl_mdrbulkuploadcsv set remarks = :isverifiy, upload_status = :uploadStatus where Id= :recId", nativeQuery = true)
	void updateMdrVerification(@Param ("isverifiy") String isverifiy , @Param ("uploadStatus") String uploadStatus , @Param ("recId") Long recId  );

	@Query(value = "SELECT sp_id FROM tbl_mstserviceprovider where sp_name=:sp_id ;", nativeQuery = true)
	String findSpId(String sp_id);

//	@Query(value ="select bankId from tbl_mstpgbank T1 inner join tbl_mstserviceprovider T2 on T1.sp_id=T2.sp_id where sp_name=? and bankname=? ;" , nativeQuery = true)
	@Query(value ="Select case when bankId IS NOT NULL then bankId else COUNT(bankId) end as bankId from tbl_mstpgbank where bankName=:bank_id and sp_id=:Sp_name ;", nativeQuery = true)	
	String findBankId(String Sp_name,String bank_id);
	
	@Modifying
	@Transactional
	@Query(value = "SET SQL_SAFE_UPDATES = 0;", nativeQuery = true)
	void setSafeMode();
	
}
