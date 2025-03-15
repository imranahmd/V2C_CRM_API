package com.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.AuditTail;

@Repository
public interface AuditTailRepository extends JpaRepository<AuditTail, Long> {

//	 @Query("SELECT a FROM AuditTail a WHERE a.updatedDate BETWEEN :fromDate AND :toDate AND (:user IS NULL OR a.userName = :user)")
//    List<AuditTail> findAuditTailRecords(@Param("fromDate") String fromDate, 
//                                          @Param("toDate") String toDate, 
//                                          @Param("user") String user);
//
//	 
	 
//	 @Query("SELECT a FROM payfiaudit.AuditTail a WHERE a.updatedDate BETWEEN :fromDate AND :toDate AND (:user IS NULL OR a.userName = :user)")
//	 List<AuditTail> findAuditTailRecords(@Param("fromDate") String fromDate, 
//	                                       @Param("toDate") String toDate, 
//	                                       @Param("user") String user);
//
//	 
	 @Query(value = "SELECT * FROM AuditTail where uu_id = :tokenValidation", nativeQuery = true)
		List<Object[]> findByuuId(String tokenValidation);
		
		
//		@Query(value = "SELECT * FROM payfiaudit.tbl_audit_tail where updated_date between :fromDate and :toDate and user_name=:user ;", nativeQuery = true)
//		 List<AuditTail> findAuditTailRecords(String fromDate, String toDate, String user);
//
//	 
//		 @Query(value = "SELECT * FROM payfiaudit.tbl_audit_tail WHERE updated_date BETWEEN :fromDate AND :toDate AND user_name = :user", nativeQuery = true)
//		    List<AuditTail> findAuditTailRecords(@Param("fromDate") String fromDate, 
//		                                          @Param("toDate") String toDate, 
//		                                          @Param("user") String user);
//		

//		 @Query(value = "select * FROM  payfiaudit.tbl_audit_tail WHERE  updated_date beetween and fromDate = ? and user = ?", nativeQuery = true)
//		 List<AuditTail>  findAuditTailRecords(String fromDate, String toDate, String user);
//			
//			
		 
//		 @Query(value = "SELECT * FROM payfiaudit.tbl_audit_tail WHERE updated_date BETWEEN :fromDate AND :toDate AND user_name = :user", nativeQuery = true)
//		    List<AuditTail> findAuditTailRecords(@Param("fromDate") String fromDate, 
//		                                          @Param("toDate") String toDate, 
//		                                          @Param("user") String user);
		
		
		
		
		
//		
		
		@Query(value = "SELECT * FROM payfiaudit.tbl_audit_tail WHERE updated_date BETWEEN ?1 AND ?2 AND user_name = ?3 order by id desc", nativeQuery = true)
	    List<AuditTail> findAuditTailRecords(String fromDate, String toDate, String user);
		 
		 
		
		 
		 
		 
		 
		 
}
