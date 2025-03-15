package com.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.TblTmpRecon;

@Repository
public interface TblTmpReconRepository extends JpaRepository<TblTmpRecon, String> {

	@Query(value = "select Column_Name from information_schema.columns where  table_name='tbl_tmprecon' and table_schema='pa_uat' and column_name not in ('id','FileId','IsException','Exception','Counter');", nativeQuery = true)
	List<String> getColumnNames();

	
	
	
}
