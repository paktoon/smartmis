package com.smart.mis.client.function.sale.quotation.product;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class QuoteProductData {

    public static ListGridRecord createRecord(String pid, String name, Double weight, Double price, String type, String unit, Integer quote_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("quote_amount", quote_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_quote_id, String quote_id, String pid, String name, Double weight, Double price, String type, String unit, Integer quote_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_quote_id", sub_quote_id);
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("quote_amount", quote_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(QuoteProductDetails item) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_quote_id", item.sub_quote_id);
        record.setAttribute("quote_id", item.quote_id);
        record.setAttribute("pid", item.product_id);
        record.setAttribute("name",item.product_name); 
        record.setAttribute("weight", item.product_weight);
        record.setAttribute("price", item.product_price);
        record.setAttribute("type", item.product_type);  
        record.setAttribute("unit", item.product_unit); 
        record.setAttribute("quote_amount", item.quote_amount);
        record.setAttribute("status", item.status);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
    	if (quote_id != null && ( quote_id.equals( "QA10001") || quote_id.equals( "QA10005") || quote_id.equals( "QA10009"))) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10001",quote_id, "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true)
        	};
        } else if (quote_id != null && ( quote_id.equals( "QA10002") || quote_id.equals( "QA10006") || quote_id.equals( "QA10010"))) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10002",quote_id, "PD10001", "Diamond cut silver ring", 476.0, 55.0, "ring","วง", 70, true),
        			createRecord("QS10003",quote_id, "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true)
        	};
        } else if (quote_id != null && (quote_id.equals( "QA10003") || quote_id.equals( "QA10007") || quote_id.equals( "QA10011"))) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10004",quote_id, "PD10004","Spiral silver earrings", 1896.0, 55.0, "earring", "คู่", 300, true)
        	};
        } else if (quote_id != null && (quote_id.equals( "QA10004") || quote_id.equals( "QA10008") || quote_id.equals( "QA10012"))) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10005",quote_id, "PD10007","Plain silver necklaces", 632.0, 50.0, "necklace","เส้น",100, true)
        	};
        }
        	else return new ListGridRecord[]{};
    }
}
