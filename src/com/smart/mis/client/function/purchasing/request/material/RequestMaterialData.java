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
        record.setAttribute("sum_price", price * request_amount);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_request_id, String request_id, String mid, String name, Double weight, Double price, String type, String unit, Double request_amount, Boolean status) {  
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
        record.setAttribute("sum_price", price * request_amount);
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
        record.setAttribute("sum_price", item.material_price * item.request_amount);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
    	if (quote_id != null && ( quote_id.equals( "PR10001") || quote_id.equals( "PR10003"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPR10001",quote_id, "MA10001","แร่เงิน 100%", 1000.0, 12.5, "แร่เงิน","กรัม", 1000.0, true),
        			createRecord("SPR10002",quote_id, "MA10002","แร่เงิน 92.5%", 1000.0, 10.0, "แร่เงิน","กรัม", 1000.0, true)
        	};
        } else if (quote_id != null && ( quote_id.equals( "PR10002") || quote_id.equals( "PR10004"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPR10003",quote_id, "MA10001", "แร่เงิน 100%", 1000.0, 12.5, "แร่เงิน","กรัม", 1000.0, true),
        			createRecord("SPR10004",quote_id, "MA10002", "แร่เงิน 92.5%", 2000.0, 10.0, "แร่เงิน","กรัม", 2000.0, true)
        	};
        } else if (quote_id != null && (quote_id.equals( "PR10005") || quote_id.equals( "PR10007"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPR10005",quote_id, "MA20002","แมกกาไซต์ PP7", 1200.0, 0.7, "แมกกาไซต์","เม็ด", 10000.0, true),
        			createRecord("SPR10006",quote_id, "MA20003","แมกกาไซต์ PP8", 1200.0, 0.8, "แมกกาไซต์","เม็ด", 10000.0, true)
        	};
        } else if (quote_id != null && (quote_id.equals( "PR10006") || quote_id.equals( "PR10008"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPR10007",quote_id, "MA20001","แมกกาไซต์ PP6", 600.0, 0.6, "แมกกาไซต์","เม็ด", 5000.0, true),
        			createRecord("SPR10008",quote_id, "MA20002","แมกกาไซต์ PP7", 1200.0, 0.7, "แมกกาไซต์","เม็ด", 10000.0, true),
        			createRecord("SPR10009",quote_id, "MA20003","แมกกาไซต์ PP8", 1200.0, 0.8, "แมกกาไซต์","เม็ด", 10000.0, true)
        	};
        }
        else return new ListGridRecord[]{};
    }
}
