package com.smart.mis.client.function.financial.disburse.wage;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WageItemData {

    //public static ListGridRecord createSentRecord(String sub_job_id, String job_id, String pid, String name, String type, String unit, String details, Double sent_weight, Integer sent_amount, Boolean status) {  
    public static ListGridRecord createRecord(ListGridRecord ref_record, String sub_wage_id, String wage_id, Boolean status) {
        ListGridRecord record = new ListGridRecord();
    	//record.setAttribute("sub_job_id", ref_record.getAttributeAsString("sub_job_id"));
        //record.setAttribute("job_id", ref_record.getAttributeAsString("job_id"));
        record.setAttribute("pid", ref_record.getAttributeAsString("pid"));
        record.setAttribute("name", ref_record.getAttributeAsString("name"));  
        record.setAttribute("type", ref_record.getAttributeAsString("type"));  
        record.setAttribute("unit", ref_record.getAttributeAsString("unit")); 
        
        record.setAttribute("recv_weight",  ref_record.getAttributeAsDouble("recv_weight"));
        record.setAttribute("recv_amount",  ref_record.getAttributeAsInt("recv_amount"));

        record.setAttribute("wage", ref_record.getAttributeAsDouble("wage"));
        record.setAttribute("sum_wage", ref_record.getAttributeAsDouble("sum_wage"));
        
        record.setAttribute("sub_wage_id", sub_wage_id);
        record.setAttribute("wage_id", wage_id);
        record.setAttribute("status", status);
        return record;  
    }
    
//    public static ListGridRecord createReceivedRecord(ListGridRecord record, Double recv_weight, Integer recv_amount, Double wage) {  
////        ListGridRecord record = new ListGridRecord();
////        record.setAttribute("sub_job_id", sub_job_id);
////        record.setAttribute("job_id", job_id);
////        record.setAttribute("pid", pid);
////        record.setAttribute("name",name);  
////        record.setAttribute("type", type);  
////        record.setAttribute("unit", unit); 
////        
////        record.setAttribute("details", details);
////        record.setAttribute("sent_weight", sent_weight);
////        record.setAttribute("sent_amount", sent_amount);
//        
//        record.setAttribute("recv_weight", recv_weight);
//        record.setAttribute("recv_amount", recv_amount);
//        
//        record.setAttribute("wage", wage);
//        record.setAttribute("sum_wage", wage * recv_amount);
////        record.setAttribute("status", status);
//        return record;  
//    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, String status) {  
        record.setAttribute("status", status);
        return record; 
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
        return new ListGridRecord[]{};
    }
}
