package com.smart.mis.client.function.production.plan.product;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PlanProductData {

    public static ListGridRecord createRecord(String pid, String name, Double weight, String type, String unit, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Integer plan_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("weight", weight * plan_amount);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("size", size);
        record.setAttribute("width", width);  
        record.setAttribute("length", length);
        record.setAttribute("height", height); 
        record.setAttribute("diameter", diameter);  
        record.setAttribute("thickness", thickness);
		
        record.setAttribute("details", PlanProductDetails.getProductDetails(size, width, length, height, diameter, thickness));
        
        record.setAttribute("plan_amount", plan_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_plan_id, String plan_id, String pid, String name, Double weight, String type, String unit, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Integer plan_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_plan_id", sub_plan_id);
        record.setAttribute("plan_id", plan_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name); 
        record.setAttribute("weight", weight * plan_amount);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("size", size);
        record.setAttribute("width", width);  
        record.setAttribute("length", length);
        record.setAttribute("height", height); 
        record.setAttribute("diameter", diameter);  
        record.setAttribute("thickness", thickness);
        
        record.setAttribute("details", PlanProductDetails.getProductDetails(size, width, length, height, diameter, thickness));
        
        record.setAttribute("plan_amount", plan_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(PlanProductDetails item) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_plan_id", item.sub_plan_id);
        record.setAttribute("plan_id", item.plan_id);
        record.setAttribute("pid", item.product_id);
        record.setAttribute("name",item.product_name); 
        record.setAttribute("weight", item.product_weight * item.plan_amount);
        record.setAttribute("type", item.product_type);  
        record.setAttribute("unit", item.product_unit);
        
        record.setAttribute("size", item.product_size);
        record.setAttribute("width", item.product_width);  
        record.setAttribute("length", item.product_length);
        record.setAttribute("height", item.product_height); 
        record.setAttribute("diameter", item.product_diameter);  
        record.setAttribute("thickness", item.product_thickness);
        
        record.setAttribute("details", item.getProductDetails());
        
        record.setAttribute("plan_amount", item.plan_amount);
        record.setAttribute("status", item.status);
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
