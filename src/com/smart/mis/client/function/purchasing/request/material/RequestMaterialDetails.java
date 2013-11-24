package com.smart.mis.client.function.purchasing.request.material;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RequestMaterialDetails {
	public String sub_request_id;
	String request_id;
	
	String material_id;
	String material_name;
	//static String material_desc;
	//Contact info
	//String material_size;
	Double material_weight;
	Double material_price;
	String material_type;
	
	//static Double material_remain;
	String material_unit;
	//static Boolean material_inStock;
	Double request_amount;
	Boolean status = true;
	
	public void save(String pid, String name, Double weight, String type, String unit) {
		this.material_id = pid;
		this.material_name = name;
		//this.material_size = size; 
		if (weight == null) weight = 1.0;
		this.material_weight = weight;
		//this.material_price = price;
		this.material_type = type;
		this.material_unit = unit;
	}
	
	public void setID(String sub_request_id, String request_id) {
		this.sub_request_id = sub_request_id;
		this.request_id = request_id;
	}
	
	public void setQuantity(Double request_amount, Double material_price) {
		this.request_amount = request_amount;
		this.material_price = material_price;
	}
	
	public ListGridRecord convertToRecord(Double request_amount, Double material_price){
		return RequestMaterialData.createRecord(material_id, material_name, material_weight * request_amount, material_price, material_type, material_unit, request_amount, status);
	}
	
	public void clear(){
		sub_request_id = null;
		request_id = null;
		
		material_id = null;
		material_name = null;
		
		material_weight = null;
		material_price = null;
		material_type = null;
		
		material_unit = null;
	}
	
	public boolean check() {
		if (material_id == null || material_name == null || material_weight == null || material_type == null || material_unit == null) {
			return false;
		}		
		return true;
	}
}
