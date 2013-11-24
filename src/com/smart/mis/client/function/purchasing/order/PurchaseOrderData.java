package com.smart.mis.client.function.purchasing.order;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PurchaseOrderData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String order_id,String request_id,String sid, String sup_name, String quote_id, String payment_model, Integer credit, Date received_date, Date returned_date, Date delivery ,Double total_weight,Double total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status, String payment_status) {  
        ListGridRecord record = new ListGridRecord();
        
        record.setAttribute("order_id", order_id);
        
        record.setAttribute("request_id", request_id);
        record.setAttribute("sid",sid);  
        record.setAttribute("sup_name",sup_name);
        
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);
        
        record.setAttribute("received_date", received_date); 
        record.setAttribute("returned_date", returned_date);
        
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        record.setAttribute("netExclusive", netExclusive);
        record.setAttribute("tax", netExclusive * 0.07);
        record.setAttribute("netInclusive", netExclusive * 1.07);  
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        record.setAttribute("payment_status", payment_status);
        return record;  
    }
    
    public static ListGridRecord createUpdateRecord(String order_id, String request_id,String sid, String quote_id, String sup_name, String payment_model, Integer credit, Date received_date, Date returned_date, Date delivery ,Double total_weight,Double total_amount,Double netExclusive,Date modified_date,String modified_by, String comment, String status, String payment_status) {  
        ListGridRecord record = new ListGridRecord();
        
        record.setAttribute("order_id", order_id);
        
        record.setAttribute("request_id", request_id);
        record.setAttribute("sid",sid);  
        record.setAttribute("sup_name",sup_name);
        
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);
        
        record.setAttribute("received_date", received_date); 
        record.setAttribute("returned_date", returned_date);
        
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        record.setAttribute("netExclusive", netExclusive);
        record.setAttribute("tax", netExclusive * 0.07);
        record.setAttribute("netInclusive", netExclusive * 1.07);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        record.setAttribute("payment_status", payment_status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String order_id, String status, String payment_status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("order_id", order_id);
        record.setAttribute("status", status);
        record.setAttribute("payment_status", payment_status);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
//    	return new ListGridRecord[]{ 
//    			createRecord("QA10001","CU10017", "Flora Creek", "เงินสด", 10 , new Date(), new Date(), new Date() , 120.0, 200, 11000.0, new Date(), null, "admin test", null, "", "2_waiting_for_approved"),
//    			createRecord("QA10002","CU10008", "ประทีปเจมส์", "เงินสด", 30 , new Date(), new Date(), new Date() , 120.0, 270, 14850.0, new Date(), null, "admin test", null, "", "3_approved"),
//    			createRecord("QA10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , new Date(), new Date(), new Date() , 120.0, 300, 16500.0, new Date(), null, "admin test", null, "", "3_approved"),
//    			createRecord("QA10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 , new Date(), new Date(), new Date() , 120.0, 100, 5000.0, new Date(), null, "admin test", null, "", "4_canceled"),
//    			
//    			createRecord("QA10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , new Date(), new Date(), new Date() , 120.0, 200, 11000.0, new Date(), null, "admin test", null, "", "2_waiting_for_approved"),
//    			createRecord("QA10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 20 , new Date(), new Date(), new Date() , 120.0, 270, 14850.0, new Date(), null, "admin test", null, "", "3_approved"),
//    			createRecord("QA10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , new Date(), new Date(), new Date() , 120.0, 300, 16500.0, new Date(), null, "admin test", null, "", "2_waiting_for_approved"),
//    			createRecord("QA10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 , new Date(), new Date(), new Date() , 120.0, 100, 5000.0, new Date(), null, "admin test", null, "", "3_approved"),
//    			
//    			createRecord("QA10009","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , new Date(), new Date(), new Date() , 120.0, 200, 11000.0, new Date(), null, "admin test", null, "", "1_waiting_for_revised"),
//    			createRecord("QA10010","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 20 , new Date(), new Date(), new Date() , 120.0, 270, 14850.0, new Date(), null, "admin test", null, "", "3_approved"),
//    			createRecord("QA10011","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , new Date(), new Date(), new Date() , 120.0, 300, 16500.0, new Date(), null, "admin test", null, "", "1_waiting_for_revised"),
//    			createRecord("QA10012","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 , new Date(), new Date(), new Date() , 120.0, 100, 5000.0, new Date(), null, "admin test", null, "", "3_approved"),
//    	};
    	return new ListGridRecord[]{};
    }
}
