package com.smart.mis.client.function.purchasing.order.material;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class OrderMaterialData {

    public static ListGridRecord createRecord(String mid, String name, Double weight, Double price, String type, String unit, Double order_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",name);  
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("order_amount", order_amount);
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
    
    public static ListGridRecord createRecord(OrderMaterialDetails item) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_order_id", item.sub_order_id);
        record.setAttribute("order_id", item.order_id);
        record.setAttribute("mid", item.material_id);
        record.setAttribute("mat_name",item.material_name); 
        record.setAttribute("weight", item.material_weight);
        record.setAttribute("price", item.material_price);
        record.setAttribute("type", item.material_type);  
        record.setAttribute("unit", item.material_unit); 
        record.setAttribute("request_amount", item.order_amount);
        record.setAttribute("status", item.status);
        return record;   
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String sale_id) {
//    	if (sale_id != null && ( sale_id.equals( "SO10001") || sale_id.equals( "SO10005") || sale_id.equals( "IN10001") || sale_id.equals( "IN10005"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("SS10001",sale_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
//        	};
//        } else if (sale_id != null && ( sale_id.equals( "SO10002") || sale_id.equals( "SO10006") || sale_id.equals( "IN10002") || sale_id.equals( "IN10006") || sale_id.equals( "DL10001"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("SS10002",sale_id, "PD10001", "Diamond cut silver ring", 6.3, 55.0, "ring","วง", 70, true),
//        			createRecord("SS10003",sale_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
//        	};
//        } else if (sale_id != null && (sale_id.equals( "SO10003") || sale_id.equals( "SO10007") || sale_id.equals( "IN10003") || sale_id.equals( "IN10007") || sale_id.equals( "DL10002"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("SS10004",sale_id, "PD10004","Spiral silver earrings", 6.3, 55.0, "earring", "คู่", 300, true)
//        	};
//        } else if (sale_id != null && (sale_id.equals( "SO10004") || sale_id.equals( "SO10008") || sale_id.equals( "IN10004") || sale_id.equals( "IN10008") || sale_id.equals( "DL10003"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("SS10005",sale_id, "PD10007","Plain silver necklaces", 5.6, 50.0, "earring","เส้น",100, true)
//        	};
//        } 
//    	
    	return new ListGridRecord[]{};
    }
}
