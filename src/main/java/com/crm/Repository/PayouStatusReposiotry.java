package com.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.dto.PaymentRequestList;
import com.crm.model.BulkRefundSql;

public interface PayouStatusReposiotry  extends JpaRepository<PaymentRequestList, Integer>{

	

	@Query(value = "call pro_payoutStatusCheck(:CustRef);", nativeQuery = true)
   List<PaymentRequestList> getPayoutTransDeatils(String CustRef);

}
