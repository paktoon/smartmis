package com.smart.mis.client.function.production.order.casting;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CastingProductData {

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
    
    public static ListGridRecord createReceivedRecord(String sub_job_id, String job_id, String pid, String name, String type, String unit, String details, Double sent_weight, Double recv_weight, Integer sent_amount, Integer recv_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("job_id", job_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("details", details);
        record.setAttribute("sent_weight", sent_weight);
        record.setAttribute("sent_amount", sent_amount);
        
        record.setAttribute("recv_weight", recv_weight);
        record.setAttribute("recv_amount", recv_amount);
        
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String sub_job_id,Date modified_date,String modified_by, String status) {  
    	ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("status", status);
        return record; 
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
//    	if (quote_id != null && ( quote_id.equals( "QA10001") || quote_id.equals( "QA10005"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10001",quote_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
//        	};
//        } else if (quote_id != null && ( quote_id.equals( "QA10002") || quote_id.equals( "QA10006"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10002",quote_id, "PD10001", "Diamond cut silver ring", 6.3, 55.0, "ring","วง", 70, true),
//        			createRecord("QS10003",quote_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
//        	};
//        } else if (quote_id != null && (quote_id.equals( "QA10003") || quote_id.equals( "QA10007"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10004",quote_id, "PD10004","Spiral silver earrings", 6.3, 55.0, "earring", "คู่", 300, true)
//        	};
//        } else if (quote_id != null && (quote_id.equals( "QA10004") || quote_id.equals( "QA10008"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10005",quote_id, "PD10007","Plain silver necklaces", 5.6, 50.0, "necklace","เส้น",100, true)
//        	};
//        }
//        	else 
        		return new ListGridRecord[]{};
    }
}
