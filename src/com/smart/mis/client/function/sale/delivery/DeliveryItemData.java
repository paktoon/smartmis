package com.smart.mis.client.function.sale.delivery;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DeliveryItemData {

//    public static ListGridRecord createRecord(String pid, String name, Double sale_weight, Double price, String type, String unit, Integer sale_amount, Boolean status) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("pid", pid);
//        record.setAttribute("name",name);
//        record.setAttribute("price", price);
//        record.setAttribute("type", type);  
//        record.setAttribute("unit", unit); 
//        record.setAttribute("sale_weight", sale_weight);
//        record.setAttribute("sale_amount", sale_amount);
//        record.setAttribute("status", status);
//        return record;  
//    }
    
    public static ListGridRecord createRecord(String sub_sale_id, String sale_id, String pid, String name, Double sale_weight, Double price, String type, String unit, Integer sale_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_delivery_id", sub_sale_id);
        record.setAttribute("delivery_id", sale_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("status", status);
        
        record.setAttribute("sale_weight", sale_weight);
        record.setAttribute("sale_amount", sale_amount);
        return record;  
    }
    
    public static ListGridRecord createIssuedRecord(ListGridRecord record, Double issued_weight, Integer issued_amount) {
        record.setAttribute("issued_weight", issued_weight);
        record.setAttribute("issued_amount", issued_amount);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String sale_id) {
    	if (sale_id != null && ( sale_id.equals( "SO10001") || sale_id.equals( "SO10005") || sale_id.equals( "IN10001") || sale_id.equals( "IN10005"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SS10001",sale_id, "PD10002", "Thin plain silver ring",6.3, 55.0, "ring","วง", 200, true)
        	};
        } else if (sale_id != null && ( sale_id.equals( "SO10002") || sale_id.equals( "SO10006") || sale_id.equals( "IN10002") || sale_id.equals( "IN10006") || sale_id.equals( "DL10001"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SS10002",sale_id, "PD10001", "Diamond cut silver ring", 441.0, 55.0, "ring","วง", 70, true),
        			createRecord("SS10003",sale_id, "PD10002", "Thin plain silver ring",1260.0, 55.0, "ring","วง", 200, true)
        	};
        } else if (sale_id != null && (sale_id.equals( "SO10003") || sale_id.equals( "SO10007") || sale_id.equals( "IN10003") || sale_id.equals( "IN10007") || sale_id.equals( "DL10002"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SS10004",sale_id, "PD10004","Spiral silver earrings", 1260.0, 55.0, "earring", "คู่", 300, true)
        	};
        } else if (sale_id != null && (sale_id.equals( "SO10004") || sale_id.equals( "SO10008") || sale_id.equals( "IN10004") || sale_id.equals( "IN10008") || sale_id.equals( "DL10003"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SS10005",sale_id, "PD10007","Plain silver necklaces", 560.0, 50.0, "earring","เส้น",100, true)
        	};
        } 
    	
    	return new ListGridRecord[]{};
    }
}
