package com.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.model.MerchantList;
import com.crm.model.ReconRecord;

@Repository
public interface tblFileUplaodRepo  extends JpaRepository<ReconRecord, Integer>{

	
	
	
	
	@Query(value="select Column_Name from information_schema.columns where  table_name='tbl_tmprecon' and table_schema=? and column_name not in ('id','FileId','IsException','Exception','Counter') order by Column_Name",nativeQuery = true)
	List<String> GetColumns(String dbNmeValue);
	
	
}
