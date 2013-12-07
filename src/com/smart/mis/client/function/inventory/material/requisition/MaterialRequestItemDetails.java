package com.smart.mis.client.function.inventory.material.requisition;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialRequestItemDetails {
	
	public String material_id;
	public String material_name;
	public String material_unit;
	public Double request_amount;
	public Double remain;
	
	public MaterialRequestItemDetails(Record mat, Integer amount, Double remain) {
		this.material_id = mat.getAttributeAsString("mid");
		this.material_name = mat.getAttributeAsString("mat_name");
		this.material_unit = mat.getAttributeAsString("unit");
		this.request_amount = amount * mat.getAttributeAsDouble("req_amount");
		this.remain = remain;
	}
	
	public MaterialRequestItemDetails(Record mat, Double amount, Double remain) {
		this.material_id = mat.getAttributeAsString("mid");
		this.material_name = mat.getAttributeAsString("mat_name");
		this.material_unit = mat.getAttributeAsString("unit");
		this.request_amount = amount;
		this.remain = remain;
	}
	
	public void addAmount(Record mat, Integer amount) {
		this.request_amount += amount * mat.getAttributeAsDouble("req_amount");
	}
	
	public Double getAmount() {
		return request_amount;
	}
}
