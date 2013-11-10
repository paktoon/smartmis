package com.smart.mis.client.function.production.plan.product;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PlanProductDetails {
	public String sub_plan_id;
	String plan_id;
	
	String product_id;
	String product_name;
	Double product_weight;
	String product_type;
	String product_unit;
	
	Double product_size;
	Double product_width;
	Double product_length;
	Double product_height;
	Double product_diameter;
	Double product_thickness;
	
	Integer plan_amount;
	Boolean status = true;
	
	public void save(String pid, String name, Double weight, String type, String unit, Double size, Double width, Double length, Double height, Double diameter, Double thickness) {
		this.product_id = pid;
		this.product_name = name;
		this.product_weight = weight;
		this.product_type = type;
		this.product_unit = unit;
		
		this.product_size = size;
		this.product_width = width;
		this.product_length = length;
		this.product_height= height;
		this.product_diameter = diameter;
		this.product_thickness = thickness;
	}
	
	public void setID(String sub_plan_id, String plan_id) {
		this.sub_plan_id = sub_plan_id;
		this.plan_id = plan_id;
	}
	
	public void setQuantity(Integer plan_amount) {
		this.plan_amount = plan_amount;
	}
	
	public ListGridRecord convertToRecord(Integer plan_amount){
		return PlanProductData.createRecord(product_id, product_name, product_weight, product_type, product_unit, product_size, product_width, product_length, product_height, product_diameter, product_thickness, plan_amount, status);
	}
	
	public void clear(){
		sub_plan_id = null;
		plan_id = null;
		
		product_id = null;
		product_name = null;
		product_weight = null;
		product_type = null;
		product_unit = null;
		
		product_size = null;
		product_width = null;
		product_length = null;
		product_height = null;
		product_diameter = null;
		product_thickness = null;
	}
	
	public boolean check() {
		if (product_id == null || product_name == null || product_weight == null || product_type == null || product_unit == null) {
			return false;
		}		
		return true;
	}
}
