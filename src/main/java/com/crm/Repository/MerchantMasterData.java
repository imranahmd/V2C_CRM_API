package com.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.model.MerchantBank;
import com.crm.model.Merchant_MasterData;


public interface MerchantMasterData  extends JpaRepository<Merchant_MasterData, Integer> {

	
	  //List<Merchant_MasterData> findByMid(String merchantid);
	List<Merchant_MasterData> findAllByMid(String mid);
	 	
}
