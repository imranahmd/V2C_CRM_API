package com.crm.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.MerchantList;
import com.crm.model.ReconRecord;
import com.crm.model.TblFileupload;

@Repository
public interface ReconManagementRepoV1 extends JpaRepository<MerchantList,Long>{
	
	@Query(value = "CALL pro_CheckFileName(:fileName,:serviceId);", nativeQuery = true)
	int checkFileName(@Param("fileName") String fileName,@Param("serviceId") String serviceId);
}

