package com.smart.mis.shared.prodution;

public class Smith {

	public String smid;
	public String name;
	public String phone1;
	public String phone2;  
	public String email; 
	public String address;  
	//public String address;   
	//public String street;
	//public String city;
	//public String state;
	//public String postal;
	
	public String type;
    
    public Smith(String smid, String name, String phone1, String phone2, String email, String address, String type) {
    	this.smid = smid;
    	this.name = name;
    	this.phone1 = phone1;
    	this.phone2 = phone2;
    	this.email = email;
    	this.address = address;
    	this.type = type;
    }
    
    public Smith() {
    	
    }
    
    public void setAttributes(String smid, String name, String phone1, String phone2, String email, String address, String type) {
    	this.smid = smid;
    	this.name = name;
    	this.phone1 = phone1;
    	this.phone2 = phone2;
    	this.email = email;
    	this.address = address;
    	this.type = type;
    }
}
