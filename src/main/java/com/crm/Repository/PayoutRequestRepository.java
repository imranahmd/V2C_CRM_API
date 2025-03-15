package com.crm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.model.LifeCycleStatus;
import com.crm.model.Payrequest;

public interface PayoutRequestRepository extends JpaRepository<Payrequest, Integer> {

	
	
	
}
