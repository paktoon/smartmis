package com.smart.mis.client.function.sale.quotation;

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
    
    public static ListGridRecord[] getNewRecords() {
//    	return new ListGridRecord[]{ 
//    			createRecord("PD10001","Diamond cut silver ring", "3.0 mm.", 6.3, 55.0, "แหวนนิ้วมือ","วง"),
//    			createRecord("PD10002","Thin plain silver ring", "3.0 mm.", 5.6, 50.0, "แหวนนิ้วมือ","วง"),
//    			createRecord("PD10003","Dense plain silver ring", "4.0 mm.", 6.6, 62.0, "แหวนนิ้วมือ","วง"),
//
//    			createRecord("PD10004","Spiral silver earrings", "0.7x2.6 cm.", 6.3, 55.0, "ต่างหู", "คู่"),
//    			createRecord("PD10005","Scorpion silver ear cuffs", "3.0 mm.", 5.6, 50.0, "ต่างหู","คู่"),
//    			
//    			createRecord("PD10006","Silver necklace with star pendant", "ยาว 45.5 cm. จี้ 1.8x1.7cm.", 6.3, 55.0, "สร้อยคอ" ,"เส้น"),
//    			createRecord("PD10007","Plain silver necklaces", "ควานหนา 0.7mm. ยาว  16inch.", 5.6, 50.0, "สร้อยคอ" ,"เส้น")
//    	};
    	return new ListGridRecord[]{};
    }
}
