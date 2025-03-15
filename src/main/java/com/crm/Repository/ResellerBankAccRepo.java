package com.crm.Repository;


import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.model.ResellerBankAcc;

public interface ResellerBankAccRepo extends JpaRepository<ResellerBankAcc, Long>{
	
	@Query(value = "SELECT id,reseller_id,account_number,account_holder_name,ifsc_code,bank_name,is_active,is_deleted,date_format(created_on,'%d-%m-%Y') as created_on,created_by,approved_on,approved_by, date_format(rodt, '%d-%m-%Y') as rodt FROM tbl_reseller_bank_account_details where reseller_id= ? and is_deleted= ? and is_active= ? ;", nativeQuery = true)
	Optional<ResellerBankAcc> findByResellerId(String resellerId,String isDeleted, String isActive);
	
	
	@Query(value = "SELECT count(1) FROM tbl_reseller_bank_account_details where reseller_id= ? and is_deleted= ? and is_active= ? ;", nativeQuery = true)
	long findByResellerIdIsActiveIsDeleted(String resellerId, String isDeleted, String isActive);
	
	// id for update data
	@Query(value = "SELECT IFNULL(id,0) as 'id' FROM tbl_reseller_bank_account_details where reseller_id= ? and is_deleted= ? and is_active= ? ;", nativeQuery = true)
	long findIdByResellerIdIsActiveIsDeleted(String resellerId, String isDeleted, String isActive);
	
	
	@Query(value = "SELECT * FROM tbl_reseller_bank_account_details where is_deleted= ? and is_active= ? ;", nativeQuery = true)
	List<Object[]> getAllList(String isDeleted,String isActive);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tbl_reseller_bank_account_details SET is_deleted= :isdeleted , is_active= :isactive where id=:pid ;", nativeQuery = true)
	void deleteReselletBank(@Param ("isdeleted") String is_deleted , @Param ("isactive") String uploadStatus , @Param ("pid") BigInteger Pid);
	
	@Query(value = "SELECT * FROM tbl_reseller_bank_account_details where reseller_id= ? and is_deleted= ? and is_active= ? ;", nativeQuery = true)
	List<Object[]> getListByResellerId(String resellerId , String isDeleted,String isActive);
}
