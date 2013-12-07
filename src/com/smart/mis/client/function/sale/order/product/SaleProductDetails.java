package com.smart.mis.client.function.sale.order.product;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SaleProductDetails {
	public String sub_id;
	//String ref_id;
	
	String product_id;
	String product_name;
	//static String product_desc;
	//Contact info
	//String product_size;
	Double product_weight;
	Double product_price;
	String product_type;
	
	//static Double product_remain;
	String product_unit;
	//static Boolean product_inStock;
	Integer sale_amount;
	Boolean status = true;
	
	public void save(String pid, String name, Double weight, Double price, String type, String unit) {
		this.product_id = pid;
		this.product_name = name;
		//this.product_size = size;
		this.product_weight = weight;
		this.product_price = price;
		this.product_type = type;
		this.product_unit = unit;
	}
	
//	public void setID(String sub_id, String ref_id) {
//		this.sub_id = sub_id;
//		this.ref_id = ref_id;
//	}
	
	public void setID(String sub_id) {
		this.sub_id = sub_id;
	}
	
	public void setQuantity(Integer sale_amount) {
		this.sale_amount = sale_amount;
	}
	
	public ListGridRecord convertToRecord(Integer sale_amount){
		return SaleProductData.createRecord(product_id, product_name, product_weight, product_price, product_type, product_unit, sale_amount, status);
	}
	
	public void clear(){
		sub_id = null;
	//	ref_id = null;
		
		product_id = null;
		product_name = null;
		
		//product_size = null;
		product_weight = null;
		product_price = null;
		product_type = null;
		
		product_unit = null;
	}
	
	public boolean check() {
		if (product_id == null || product_name == null || product_weight == null || product_price == null || product_type == null || product_unit == null) {
			return false;
		}		
		return true;
	}
}
