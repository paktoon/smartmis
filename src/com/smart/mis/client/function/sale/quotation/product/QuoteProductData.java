package com.smart.mis.client.function.sale.quotation.product;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class QuoteProductData {

    public static ListGridRecord createRecord(String pid, String name, String size, Double weight, Double price, String type, String unit, Integer quote_amount) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("size", size); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("quote_amount", quote_amount);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_quote_id, String quote_id, String pid, String name, String size, Double weight, Double price, String type, String unit, Integer quote_amount) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_quote_id", sub_quote_id);
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("size", size); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("quote_amount", quote_amount);
        return record;  
    }
    
    public static ListGridRecord createRecord(QuoteProductDetails item) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_quote_id", item.sub_quote_id);
        record.setAttribute("quote_id", item.quote_id);
        record.setAttribute("pid", item.product_id);
        record.setAttribute("name",item.product_name);  
        record.setAttribute("size", item.product_size); 
        record.setAttribute("weight", item.product_weight);
        record.setAttribute("price", item.product_price);
        record.setAttribute("type", item.product_type);  
        record.setAttribute("unit", item.product_unit); 
        record.setAttribute("quote_amount", item.quote_amount);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
    	if (quote_id != null && quote_id.equals( "QA10001")) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10001","QA10001", "PD10002", "Thin plain silver ring", "3.0 mm.",6.3, 55.0, "แหวนนิ้วมือ","วง", 200)
        	};
        } else if (quote_id != null && quote_id.equals( "QA10002")) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10002","QA10002", "PD10001", "Diamond cut silver ring", "3.0 mm.", 6.3, 55.0, "แหวนนิ้วมือ","วง", 70),
        			createRecord("QS10003","QA10002", "PD10002", "Thin plain silver ring", "3.0 mm.",6.3, 55.0, "แหวนนิ้วมือ","วง", 200)
        	};
        } else if (quote_id != null && quote_id.equals( "QA10003")) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10004","QA10003", "PD10004","Spiral silver earrings","0.7x2.6 cm.", 6.3, 55.0, "ต่างหู", "คู่", 300)
        	};
        } else if (quote_id != null && quote_id.equals( "QA10004")) {
        	return new ListGridRecord[]{ 
        			createRecord("QS10005","QA10004", "PD10007","Plain silver necklaces", "50 cm.", 5.6, 50.0, "สร้อยคอ","เส้น",100)
        	};
        }
        	else return new ListGridRecord[]{};
    }
}
