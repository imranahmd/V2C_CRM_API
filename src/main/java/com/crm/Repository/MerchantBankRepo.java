package com.crm.Repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.model.MerchantBank;

public interface MerchantBankRepo extends JpaRepository<MerchantBank, Long>{
	
	//List<Object[]> findByMerchantId(String merchantid); ==> nativeQuery
	@Query(value = "SELECT pid,merchant_id,product_id,account_number,ifsc_code,bank_name,date_format(rodt, '%d-%m-%Y') as rodt,account_holder,is_verified,yes_benificiary_code,mobile_no,email_id from tbl_merchant_product where merchant_id= :merchantid",nativeQuery = true)
	List<MerchantBank> findByMerchantId(String merchantid);
	
	@Query(value = "SELECT *  from tbl_merchant_product where pid= :id",nativeQuery = true)
	List<MerchantBank>  findByPId(Long id);
	

	@Query(value = "SELECT count(*)  from tbl_merchant_product where product_id= :product_id and merchant_id=:Mid",nativeQuery = true)
	int CheckAccount(String product_id,String Mid);

	@Query(value = "SELECT count(*)  from tbl_merchant_product where product_id= :product_id",nativeQuery = true)
	int CheckProductId(String product_id);
	
	
	@Query(value = "SELECT count(*)  from tbl_merchant_product where account_number= :Account and merchant_id=:Mid",nativeQuery = true)
	int CheckSameAccount(String Account,String Mid);
	
	@Query(value = "SELECT count(*)  from tbl_merchant_product where account_number= :Account",nativeQuery = true)
	int CheckGlobalAccount(String Account);
	
	@Query(value = "SELECT is_verified  from tbl_merchant_product where pid= :id",nativeQuery = true)
	String CheckStatus(Long id);

}
