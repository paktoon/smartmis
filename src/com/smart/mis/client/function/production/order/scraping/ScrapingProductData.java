package com.smart.mis.client.function.production.order.scraping;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ScrapingProductData {
    
    public static ListGridRecord createSentRecord(String sub_job_id, String job_id, String pid, String name, String type, String unit, String details, Double sent_weight, Integer sent_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("job_id", job_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name", name);  
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("details", details);
        record.setAttribute("sent_weight", sent_weight);
        record.setAttribute("sent_amount", sent_amount);
        
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createReceivedRecord(ListGridRecord record, Double recv_weight, Integer recv_amount, Double wage) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("sub_job_id", sub_job_id);
//        record.setAttribute("job_id", job_id);
//        record.setAttribute("pid", pid);
//        record.setAttribute("name",name);  
//        record.setAttribute("type", type);  
//        record.setAttribute("unit", unit); 
//        
//        record.setAttribute("details", details);
//        record.setAttribute("sent_weight", sent_weight);
//        record.setAttribute("sent_amount", sent_amount);
        
        record.setAttribute("recv_weight", recv_weight);
        record.setAttribute("recv_amount", recv_amount);
        
        record.setAttribute("wage", wage);
        record.setAttribute("sum_wage", wage * recv_amount);
//        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, String status) {  
//    	ListGridRecord record = new ListGridRecord();
//        record.setAttribute("sub_job_id", sub_job_id);
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
