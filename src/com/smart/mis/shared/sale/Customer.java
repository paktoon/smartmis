package com.smart.mis.shared.sale;

public class Customer {

	public String cid;
	public String cus_name;
	public String cus_phone;
	public String contact_name;  
	public String contact_phone; 
	public String contact_email;  
	//public String address;   
	public String cus_type;
	
	public String bus_type;
	public String cus_group;
	public String zone;
	public String payment_model;
	public Integer credit;
    
    public Customer(String cid, String cus_name, String cus_phone, String contact_name, String contact_phone, String contact_email, String cus_type, String bus_type, String cus_group, String zone) {
    	this.cid = cid;
    	this.cus_name = cus_name;
    	this.cus_phone = cus_phone;
    	this.contact_name = contact_name;
    	this.contact_phone = contact_phone;
    	this.contact_email = contact_email;
    	//this.address = address;
    	this.cus_type = cus_type;
    	this.bus_type = bus_type;
    	this.cus_group = cus_group;
    	this.zone = zone;
    	
    }
    
    public Customer() {
    	
    }
    
    public void setAttributes(String cid, String cus_name, String cus_phone, String contact_name, String contact_phone, String contact_email, String cus_type, String bus_type, String cus_group, String zone) {
    	this.cid = cid;
    	this.cus_name = cus_name;
    	this.cus_phone = cus_phone;
    	this.contact_name = contact_name;
    	this.contact_phone = contact_phone;
    	this.contact_email = contact_email;
    	//this.address = address;
    	this.bus_type = bus_type;
    	this.cus_group = cus_group;
    	this.cus_type = cus_type;
    	this.zone = zone;
    }
    
    public void setPaymentModel(String payment) {
    	this.payment_model = payment;
    }
    
    public void setCredit(Integer credit) {
    	this.credit = credit;
    }
}
