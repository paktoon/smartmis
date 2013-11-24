package com.smart.mis.shared.purchasing;

public class Supplier {

	public String sid;
	public String sup_name;
	public String sup_phone1;  
	public String sup_phone2; 
	public String email;  
	public String address;   
	public String fax;
	public String list;
	public Integer leadtime;
	
	public String payment_model;
	public Integer credit;
    
    public Supplier(String sid, String sup_name, String sup_phone1, String sup_phone2, String email, String fax, String address, String list, Integer leadtime) {
    	this.sid = sid;
    	this.sup_name = sup_name;
    	this.sup_phone1 = sup_phone1;
    	this.sup_phone2 = sup_phone2;
    	this.email = email;
    	this.fax = fax;
    	this.address = address;
    	this.list = list;
    	this.leadtime = leadtime;
    }
    
    public Supplier() {
    	
    }
    
    public void setAttributes(String sid, String sup_name, String sup_phone1, String sup_phone2, String email, String fax, String address, String list, Integer leadtime) {
    	this.sid = sid;
    	this.sup_name = sup_name;
    	this.sup_phone1 = sup_phone1;
    	this.sup_phone2 = sup_phone2;
    	this.email = email;
    	this.fax = fax;
    	this.address = address;
    	this.list = list;
    	this.leadtime = leadtime;
    }
    
    public void setPaymentModel(String payment) {
    	this.payment_model = payment;
    }
    
    public void setCredit(Integer credit) {
    	this.credit = credit;
    }
}
