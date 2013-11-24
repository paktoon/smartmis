package com.smart.mis.client.function.purchasing.request.material;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RequestMaterialData {

    public static ListGridRecord createRecord(String mid, String name, Double weight, Double price, String type, String unit, Double request_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",name);  
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("request_amount", request_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_request_id, String request_id, String mid, String name, Double weight, Double price, String type, String unit, Integer request_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", sub_request_id);
        record.setAttribute("request_id", request_id);
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",name); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("request_amount", request_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(RequestMaterialDetails item) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", item.sub_request_id);
        record.setAttribute("request_id", item.request_id);
        record.setAttribute("mid", item.material_id);
        record.setAttribute("mat_name",item.material_name); 
        record.setAttribute("weight", item.material_weight);
        record.setAttribute("price", item.material_price);
        record.setAttribute("type", item.material_type);  
        record.setAttribute("unit", item.material_unit); 
        record.setAttribute("request_amount", item.request_amount);
        record.setAttribute("status", item.status);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
//    	if (quote_id != null && ( quote_id.equals( "QA10001") || quote_id.equals( "QA10005") || quote_id.equals( "QA10009"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10001",quote_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
//        	};
//        } else if (quote_id != null && ( quote_id.equals( "QA10002") || quote_id.equals( "QA10006") || quote_id.equals( "QA10010"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10002",quote_id, "PD10001", "Diamond cut silver ring", 6.3, 55.0, "ring","วง", 70, true),
//        			createRecord("QS10003",quote_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
//        	};
//        } else if (quote_id != null && (quote_id.equals( "QA10003") || quote_id.equals( "QA10007") || quote_id.equals( "QA10011"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10004",quote_id, "PD10004","Spiral silver earrings", 6.3, 55.0, "earring", "คู่", 300, true)
//        	};
//        } else if (quote_id != null && (quote_id.equals( "QA10004") || quote_id.equals( "QA10008") || quote_id.equals( "QA10012"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10005",quote_id, "PD10007","Plain silver necklaces", 5.6, 50.0, "necklace","เส้น",100, true)
//        	};
//        }
//        	else 
        		return new ListGridRecord[]{};
    }
}
