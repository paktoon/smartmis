package com.smart.mis.client.function.sale.order.product;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SaleProductData {

    public static ListGridRecord createRecord(String pid, String name, String size, Double weight, Double price, String type, String unit, Integer sale_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("size", size); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("sale_amount", sale_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_sale_id, String sale_id, String pid, String name, String size, Double weight, Double price, String type, String unit, Integer sale_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_sale_id", sub_sale_id);
        record.setAttribute("sale_id", sale_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("size", size); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("sale_amount", sale_amount);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createRecord(SaleProductDetails item) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_sale_id", item.sub_sale_id);
        record.setAttribute("sale_id", item.sale_id);
        record.setAttribute("pid", item.product_id);
        record.setAttribute("name",item.product_name);  
        record.setAttribute("size", item.product_size); 
        record.setAttribute("weight", item.product_weight);
        record.setAttribute("price", item.product_price);
        record.setAttribute("type", item.product_type);  
        record.setAttribute("unit", item.product_unit); 
        record.setAttribute("sale_amount", item.sale_amount);
        record.setAttribute("status", item.status);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String quote_id) {
//    	if (quote_id != null && ( quote_id.equals( "QA10001") || quote_id.equals( "QA10005"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10001",quote_id, "PD10002", "Thin plain silver ring", "3.0 mm.",6.3, 55.0, "แหวนนิ้วมือ","วง", 200, true)
//        	};
//        } else if (quote_id != null && ( quote_id.equals( "QA10002") || quote_id.equals( "QA10006"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10002",quote_id, "PD10001", "Diamond cut silver ring", "3.0 mm.", 6.3, 55.0, "แหวนนิ้วมือ","วง", 70, true),
//        			createRecord("QS10003",quote_id, "PD10002", "Thin plain silver ring", "3.0 mm.",6.3, 55.0, "แหวนนิ้วมือ","วง", 200, true)
//        	};
//        } else if (quote_id != null && (quote_id.equals( "QA10003") || quote_id.equals( "QA10007"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10004",quote_id, "PD10004","Spiral silver earrings","0.7x2.6 cm.", 6.3, 55.0, "ต่างหู", "คู่", 300, true)
//        	};
//        } else if (quote_id != null && (quote_id.equals( "QA10004") || quote_id.equals( "QA10008"))) {
//        	return new ListGridRecord[]{ 
//        			createRecord("QS10005",quote_id, "PD10007","Plain silver necklaces", "50 cm.", 5.6, 50.0, "สร้อยคอ","เส้น",100, true)
//        	};
//        }
//        	else 
        		return new ListGridRecord[]{};
    }
}
