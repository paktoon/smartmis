package com.smart.mis.client.function.inventory.product.transfer;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TransferItemData {

    //public static ListGridRecord createSentRecord(String sub_job_id, String job_id, String pid, String name, String type, String unit, String details, Double sent_weight, Integer sent_amount, Boolean status) {  
    public static ListGridRecord createRecord(ListGridRecord ref_record, String sub_transfer_id, String transfer_id, Boolean status) {
        ListGridRecord record = new ListGridRecord();
    	//record.setAttribute("sub_job_id", ref_record.getAttributeAsString("sub_job_id"));
        //record.setAttribute("job_id", ref_record.getAttributeAsString("job_id"));
        record.setAttribute("pid", ref_record.getAttributeAsString("pid"));
        record.setAttribute("name", ref_record.getAttributeAsString("name"));  
        record.setAttribute("type", ref_record.getAttributeAsString("type"));  
        record.setAttribute("unit", ref_record.getAttributeAsString("unit")); 
        
        record.setAttribute("sent_weight",  ref_record.getAttributeAsDouble("sent_weight"));
        record.setAttribute("sent_amount",  ref_record.getAttributeAsInt("sent_amount"));

        //record.setAttribute("wage", ref_record.getAttributeAsDouble("wage"));
        //record.setAttribute("sum_wage", ref_record.getAttributeAsDouble("sum_wage"));
        
        record.setAttribute("sub_transfer_id", sub_transfer_id);
        record.setAttribute("transfer_id", transfer_id);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_transfer_id, String transfer_id, String pid, String name, String type, String unit, Double sent_weight, Integer sent_amount, Boolean status) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name", name);  
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("sent_weight",  sent_weight);
        record.setAttribute("sent_amount",  sent_amount);
        
        record.setAttribute("sub_transfer_id", sub_transfer_id);
        record.setAttribute("transfer_id", transfer_id);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createReceivedRecord(ListGridRecord record, Double recv_weight, Integer recv_amount) {  
        record.setAttribute("recv_weight", recv_weight);
        record.setAttribute("recv_amount", recv_amount);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, String status) {  
        record.setAttribute("status", status);
        return record; 
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
    	if (quote_id != null &&  quote_id.equals( "TF10001")) {
    		return new ListGridRecord[]{
    				createRecord("STFP10001", quote_id, "PD10001", "Diamond cut silver ring", "ring", "วง", 1496.0, 220, true)
    		};
    	} else return new ListGridRecord[]{};
    }
}
