package com.crm.Repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.crm.dto.BulkCsvDto;
import com.crm.dto.BulkCsvMerchantDto;
import com.crm.model.BulkCsvMerchant;
import com.crm.model.MerchantList;

public interface BulkCsvMerchantRepo extends JpaRepository <BulkCsvMerchant, Long>{

		
	@Transactional
	@Modifying
	@Query(value = """
			insert into tbl_mstmerchant (`MerchantId`,
			`merchant_name`,
			`business_name`,
			`contact_person`,
			`email_id`,
			`mer_return_url`,
			`isretryAllowed`,
			`is_auto_refund`,
			`hours`,
			`minutes`,
			`is_push_url`,
			`integration_type`,
			`mer_website_url`,
			`merchant_category_code`,
			`merchant_sub_category`,
			`reseller_id`,
			`contact_number`,
			`push_url`,
			`ibps_mail_remainder`,
			`merchant_dashboard_refund`,
			`md_disable_refund_cc`,
			`md_disable_refund_dc`,
			`md_disable_refund_nb`,
			`md_disable_refund_upi`,
			`md_disable_refund_wallet`,
			`refund_api`,
			`refund_api_disable_cc`,
			`refund_api_disable_dc`,
			`refund_api_disable_nb`,
			`refund_api_disable_upi`,
			`refund_api_disable_wallet`,
			`ibps_email_notification`,
			`ibps_sms_notification`,
			`settlement_cycle`,
			`BusinessType`,
			`BusinessModel`,
			`TurnoverinlastFinancialYear`,
			`ExpectedMonthlyIncome`,
			`AverageAmountPerTransaction`,
			`AuthorisedSignatoryPAN`,
			`NameAsPerPAN`,
			`GSTINNo`,
			`IsTestAccess`,
			`CompanyPAN`,
			`Reporting_cycle`,
			`max_token_size`) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43,?44,?45,?46)""", nativeQuery = true)
	void createMerchantByCsv(String merchantId, String merchant_name, String business_name, String contact_person, String email_id, String mer_return_url,
			String isretryAllowed, String is_auto_refund, String hours, String minutes, String is_push_url, String integration_type,
			String mer_website_url, String merchant_category_code, String merchant_sub_category, String reseller_id, String contact_number,
			String push_url, String ibps_mail_remainder,String merchant_dashboard_refund, String md_disable_refund_cc,
			String md_disable_refund_dc, String md_disable_refund_nb, String md_disable_refund_upi,
			String md_disable_refund_wallet, String refund_api, String refund_api_disable_cc,
			String refund_api_disable_dc, String refund_api_disable_nb, String refund_api_disable_upi,
			String refund_api_disable_wallet, String ibps_email_notification, String ibps_sms_notification, String settlement_cycle,
			String businessType,  String businessModel, String turnoverinlastFinancialYear,
			String expectedMonthlyIncome, String averageAmountPerTransaction, String authorisedSignatoryPAN,
			String nameAsPerPAN, String gSTINNo, String isTestAccess, String companyPAN, String reporting_cycle, String maxsize);

	
}
