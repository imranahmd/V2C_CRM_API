package com.crm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	
	
	
	@Query(value = "select logoPath from tbl_mstmerchant where MerchantId = ?", nativeQuery = true)
	String findVerificationLogo(String Username);

}
