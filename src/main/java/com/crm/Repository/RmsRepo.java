package com.crm.Repository;

import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.model.MerchantList;

@Repository
public interface RmsRepo  extends JpaRepository<MerchantList, Long>{
	List<MerchantList> findByMerchantNameLike(String merchant_Name);
	
	@Query(
			  value = "SELECT T1.MenuId ParentMenuId,T1.MenuName ParentMenuName,T2.* FROM tbl_mstmenu T1 inner join tbl_mstmenu T2 on T1.menuId=T2.pmenuId WHERE T2.IsActive=1 and T2.Is_Deleted='N' and FIND_IN_SET(T2.menuid, (select menuid from tbl_mstrole where roleid= :roleId ));", 
			  nativeQuery = true)
	List<Object[]> getAllMenus(Integer roleId);
	
	
	
	@Query(value = "call pro_RiskTracelog(:merchantId, :AddedBy, :Rms, :JsonOld);", nativeQuery = true)
	public void RiskTraceLogs(String merchantId, String AddedBy,String Rms,String JsonOld);

}
