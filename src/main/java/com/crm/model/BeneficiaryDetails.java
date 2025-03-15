package com.crm.model;



public class BeneficiaryDetails {

   private String  name;
    private String mobile_number;
    private String email_address;
    private String account_number;
    private String ifsc;

    public BeneficiaryDetails() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @Override
    public String toString() {
        return "BeneficiaryDetails{" +
                "name='" + name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", email_address='" + email_address + '\'' +
                ", account_number='" + account_number + '\'' +
                ", ifsc='" + ifsc + '\'' +
                '}';
    }
}
