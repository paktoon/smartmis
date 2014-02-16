package com.smart.mis.client.function.production.order.casting;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CastingProductData {

    public static ListGridRecord createSentRecord(String sub_job_id, String job_id, String pid, String name, String type, String unit, String details, String pdetails, Double sent_weight, Integer sent_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("job_id", job_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name", name);  
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("details", details);
        record.setAttribute("pdetails", pdetails);
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
    	if (quote_id != null && quote_id.equals("JOB10001")) {
        	return new ListGridRecord[]{ 
        			createSentRecord("SJ10001",quote_id, "PD10001", "Diamond cut silver ring", "ring","วง", "สั่งเทียน", "ขนาดมาตรฐาน 5.0 หนา 3.0 มม.", 1120.0, 200, true),
        			createSentRecord("SJ10002",quote_id, "PD10003", "Dense plain silver ring", "ring","วง", "สั่งเทียน", "	ขนาดมาตรฐาน 5.0 หนา 4.0 มม.", 1960.0, 350, true)
        	};
        } else if (quote_id != null && quote_id.equals("JOB10002")) {
        	return new ListGridRecord[]{ 
        			createSentRecord("SJ10003",quote_id, "PD10001", "Diamond cut silver ring", "ring","วง", "สั่งเทียน", "ขนาดมาตรฐาน 5.0 หนา 3.0 มม.", 560.0, 100, true)
        	};
        } else if (quote_id != null && quote_id.equals("JOB10003")) {
        	return new ListGridRecord[]{ 
        			createSentRecord("SJ10004",quote_id, "PD10001", "Diamond cut silver ring", "ring","วง", "สั่งเทียน", "ขนาดมาตรฐาน 5.0 หนา 3.0 มม.", 392.0, 70, true),
        			createSentRecord("SJ10005",quote_id, "PD10002", "Thin plain silver ring", "ring","วง", "สั่งเทียน", "ขนาดมาตรฐาน 5.0 หนา 3.0 มม.", 1120.0, 200, true)
        	};
        }
        	else 
        		return new ListGridRecord[]{};
    }
}
