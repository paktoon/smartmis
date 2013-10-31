package com.smart.mis.client.function.sale.quotation;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class QuoteProductDetails {
	String product_id;
	String product_name;
	//static String product_desc;
	//Contact info
	String product_size;
	Double product_weight;
	Double product_price;
	String product_type;
	
	//static Double product_remain;
	String product_unit;
	//static Boolean product_inStock;
	Integer request_amount;
	
	public void save(String pid, String name, String size, Double weight, Double price, String type, String unit) {
		product_id = pid;
		product_name = name;
		product_size = size;
		product_weight = weight;
		product_price = price;
		product_type = type;
		product_unit = unit;
	}
	
	public ListGridRecord convertToRecord(Integer quote_amount){
		return QuoteProductData.createRecord(product_id, product_name, product_size, product_weight, product_price, product_type, product_unit, quote_amount);
	}
	
	public void clear(){
		product_id = null;
		product_name = null;
		
		product_size = null;
		product_weight = null;
		product_price = null;
		product_type = null;
		
		product_unit = null;
	}
	
	public boolean check() {
		if (product_id == null || product_name == null ||  product_size == null || product_weight == null || product_price == null || product_type == null || product_unit == null) {
			return false;
		}		
		return true;
	}
}
