package com.crm.Repository;

import jakarta.persistence.Column;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.MerchantMdr;

@Repository
public interface MerchantMdrRepository extends JpaRepository<MerchantMdr, Integer> {
	
	/*@Modifying
	@Transactional
	@Query(value = "CALL pro_updateMerchantMdrDetails(:merchantId, :spId, :bankId, :instrumentId, :minAmt, :maxAmt, :mdrType,"
			+ ":aggrMdr, :resellerMdr, :baseRate, :minBaseRate, :maxBaseRate, :minMdr, :maxMdr, :mid,"
			+ ":tid, :channelId,:startDate, :endDate, :prefrences, :surcharge, :payout, :cardVariantName, :network, :instrumentBrand, :bankMdrType,"
			+ ":minResellerMdr, :maxResellerMdr);", nativeQuery = true)
	int updateMdrlist(@Param("merchantId") String merchantId, @Param("spId") Integer spId, @Param("bankId") String bankId, 
			@Param("instrumentId") String instrumentId, @Param("minAmt") Double minAmt,  @Param("maxAmt") Long maxAmt, 
			@Param("mdrType") String mdrType, @Param("aggrMdr") Double aggrMdr, @Param("resellerMdr") Double resellerMdr,
			@Param("baseRate") Double baseRate, @Param("minBaseRate") Double minBaseRate, @Param("maxBaseRate") Double maxBaseRate , 
			@Param("minMdr") Double minMdr, @Param("maxMdr") Double maxMdr, @Param("mid") String mid,  @Param("tid") String tid,
			@Param("channelId") String channelId, @Param("startDate") String startDate, @Param("endDate") String endDate, 
			@Param("prefrences") String prefrences, @Param("surcharge") Integer surcharge, @Param("payout") Integer payout, 
			@Param("cardVariantName") String cardVariantName, @Param("instrumentBrand") int instrumentBrand, @Param("network") String network, 
			 @Param("bankMdrType") String bankMdrType, 
			@Param("minResellerMdr") String minResellerMdr, @Param("maxResellerMdr") String maxResellerMdr); */

	@Modifying
	@Transactional
	@Query(value = "CALL pro_deleteMerchantMdrDetails(:id);", nativeQuery = true)
	int deleteMdrlist(@Param("id") Integer id);
	
	
	// @Transactional
	@Query(value = "CALL pro_insertOrUpdateMerchantMdrDetails(:merchantId, :spId, :bankId, :instrumentId, :minAmt, :maxAmt, :mdrType,"
			+ ":aggrMdr, :resellerMdr, :baseRate, :minBaseRate, :maxBaseRate, :minMdr, :maxMdr, :mid,"
			+ ":tid, :channelId,:startDate, :endDate, :rodt, :prefrences, :surcharge, :payout, :cardVariantName, :network, :instrumentBrand, :bankMdrType,"
			+ ":minResellerMdr, :maxResellerMdr, :resellerMdrType, :id,:payout_escrow);", nativeQuery = true)
	int createorupdateMerchantMdrMdrlist(@Param("merchantId") String merchantId, @Param("spId") Integer spId, @Param("bankId") String bankId, 
			@Param("instrumentId") String instrumentId, @Param("minAmt") Double minAmt,  @Param("maxAmt") Long maxAmt, 
			@Param("mdrType") String mdrType, @Param("aggrMdr") Double aggrMdr, @Param("resellerMdr") Double resellerMdr,
			@Param("baseRate") Double baseRate, @Param("minBaseRate") Double minBaseRate, @Param("maxBaseRate") Double maxBaseRate , 
			@Param("minMdr") Double minMdr, @Param("maxMdr") Double maxMdr, @Param("mid") String mid,  @Param("tid") String tid,
			@Param("channelId") String channelId, @Param("startDate") String startDate, @Param("endDate") String endDate,  @Param("rodt") String rodt,
			@Param("prefrences") String prefrences, @Param("surcharge") Integer surcharge, @Param("payout") Integer payout, 
			@Param("cardVariantName") String cardVariantName, @Param("instrumentBrand") Integer instrumentBrand, @Param("network") String network, 
			 @Param("bankMdrType") String bankMdrType, 
			@Param("minResellerMdr") String minResellerMdr, @Param("maxResellerMdr") String maxResellerMdr,  @Param("resellerMdrType") String resellerMdrType,@Param("id") Integer id, @Param ("payout_escrow") String payout_escrow);

	@Query(value ="CALL pro_insert_merchant_payout_mdr(:Id,:merchant_id,:sp_id,:mop,:aggr_mdr_Type,:aggr_mdr,:base_mdr_Type,:base_mdr,:start_date,:end_date,:mid,:tid,:min_amt,:max_amt,:MDR_apply_Type);", nativeQuery = true)
	int createpayoutmdr(@Param ("Id") int Id,@Param ("merchant_id") String merchant_id, @Param ("sp_id")String sp_id, @Param ("mop")String mop, @Param ("aggr_mdr_Type") String aggr_mdr_Type, @Param ("aggr_mdr") String aggr_mdr,
			@Param ("base_mdr_Type") String base_mdr_Type, @Param ("base_mdr")String base_mdr, @Param ("start_date") String start_date, @Param ("end_date")String end_date, @Param ("mid") String mid, @Param ("tid") String tid,
			@Param ("min_amt") String  min_amt, @Param ("max_amt") String  max_amt, @Param ("MDR_apply_Type") String  MDR_apply_Type);
	

}


