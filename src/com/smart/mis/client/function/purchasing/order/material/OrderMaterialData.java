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
        record.setAttribute("sum_price", price * order_amount);
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_request_id, String order_id, String mid, String name, Double weight, Double price, String type, String unit, Double order_amount, Boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", sub_request_id);
        record.setAttribute("order_id", order_id);
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",name); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("request_amount", order_amount);
        record.setAttribute("status", status);
        record.setAttribute("sum_price", price * order_amount);
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
        record.setAttribute("sum_price", item.material_price * item.order_amount);
        return record;   
    }
    
    public static ListGridRecord createReceivedRecord(ListGridRecord record, Double received_weight, Double received_amount) {
        record.setAttribute("received_weight", received_weight);
        record.setAttribute("received_amount", received_amount);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
    	
    public static ListGridRecord[] getRecords(String sale_id) {
    	if (sale_id != null && ( sale_id.equals( "PO10001") || sale_id.equals( "PO10003"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPO10001",sale_id, "MA10001","แร่เงิน 100%", 1000.0, 12.5, "แร่เงิน","กรัม", 1000.0, true),
        			createRecord("SPO10002",sale_id, "MA10002","แร่เงิน 92.5%", 1000.0, 10.0, "แร่เงิน","กรัม", 1000.0, true)
        	};
        } else if (sale_id != null && ( sale_id.equals( "PO10002") || sale_id.equals( "PO10004"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPO10003",sale_id, "MA10001", "แร่เงิน 100%", 1000.0, 12.5, "แร่เงิน","กรัม", 1000.0, true),
        			createRecord("SPO10004",sale_id, "MA10002", "แร่เงิน 92.5%", 2000.0, 10.0, "แร่เงิน","กรัม", 2000.0, true)
        	};
        } else if (sale_id != null && (sale_id.equals( "PO10005") || sale_id.equals( "PO10007"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPO10005",sale_id, "MA20002","แมกกาไซต์ PP7", 1200.0, 0.7, "แมกกาไซต์","เม็ด", 10000.0, true),
        			createRecord("SPO10006",sale_id, "MA20003","แมกกาไซต์ PP8", 1200.0, 0.8, "แมกกาไซต์","เม็ด", 10000.0, true)
        	};
        } else if (sale_id != null && (sale_id.equals( "PO10006") || sale_id.equals( "PO10008"))) {
        	return new ListGridRecord[]{ 
        			createRecord("SPO10007",sale_id, "MA20001","แมกกาไซต์ PP6", 600.0, 0.6, "แมกกาไซต์","เม็ด", 5000.0, true),
        			createRecord("SPO10008",sale_id, "MA20002","แมกกาไซต์ PP7", 1200.0, 0.7, "แมกกาไซต์","เม็ด", 10000.0, true),
        			createRecord("SPO10009",sale_id, "MA20003","แมกกาไซต์ PP8", 1200.0, 0.8, "แมกกาไซต์","เม็ด", 10000.0, true)
        	};
        }
        else return new ListGridRecord[]{};
    }
}
