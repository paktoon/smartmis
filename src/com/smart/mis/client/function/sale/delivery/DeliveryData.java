package com.smart.mis.client.function.sale.delivery;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DeliveryData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String delivery_id, String sale_id, String invoice_id ,String cid, String cus_name, Date delivery ,Double total_weight,Integer total_amount,Date created_date,Date modified_date,String created_by,String modified_by, String status, Date sent, Date received, String receipt_id) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("delivery_id", delivery_id);
        record.setAttribute("sale_id", sale_id);
        record.setAttribute("invoice_id", invoice_id);
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
//        record.setAttribute("payment_model",payment_model);
//        record.setAttribute("credit",credit);
        //record.setAttribute("from", from); 
        //record.setAttribute("to", to);
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
//        record.setAttribute("netExclusive", netExclusive);
//        record.setAttribute("tax", netExclusive * 0.07);
//        record.setAttribute("netInclusive", netExclusive * 1.07);  
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", created_by);
        //record.setAttribute("comment", comment);
        record.setAttribute("status", status); // กำลังนำส่ง, นำส่งแล้ว
        record.setAttribute("sent", sent);
        record.setAttribute("received", received);
        record.setAttribute("receipt_id", receipt_id);
        return record;  
    }
    
//    public static ListGridRecord createUpdateRecord(String sale_id, String quote_id,String cid, String cus_name, String payment_model, Integer credit, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date modified_date,String modified_by, String status, String purchase_id) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("sale_id", sale_id);
//        record.setAttribute("quote_id", quote_id);
//        record.setAttribute("cid",cid);  
//        record.setAttribute("cus_name",cus_name);
//        record.setAttribute("payment_model",payment_model);
//        record.setAttribute("credit",credit);
//        //record.setAttribute("from", from); 
//        //record.setAttribute("to", to);
//        record.setAttribute("delivery", delivery);
//        record.setAttribute("total_weight", total_weight);  
//        record.setAttribute("total_amount", total_amount); 
//        record.setAttribute("netExclusive", netExclusive);
//        record.setAttribute("tax", netExclusive * 0.07);
//        record.setAttribute("netInclusive", netExclusive * 1.07);
//        record.setAttribute("modified_date", modified_date);
//        record.setAttribute("modified_by", modified_by);
//        //record.setAttribute("comment", comment);
//        record.setAttribute("status", status);
//        return record;  
//    }
    
    public static ListGridRecord createStatusRecord(String delivery_id, String status, Date received, String receipt_id) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("delivery_id", delivery_id);
        record.setAttribute("status", status);
        record.setAttribute("received", received);
        record.setAttribute("receipt_id", receipt_id);
        return record;  
    }
    
//    public static ListGridRecord getStatusRecords(String invoice_id) {
//    	ListGridRecord[] all = getNewRecords();
//    	for (ListGridRecord record : all) {
//    		if (record.getAttributeAsString("invoice_id").equalsIgnoreCase(invoice_id)) {
//    			return record;
//    		}
//    	}
//    	 return createRecord(invoice_id,"SO10001","CU10017", "Flora Creek", "เงินสด", 10 , new Date() , 120.0, 200, 11000.0, new Date(), null, "admin test", null, "1_waiting_for_payment", "", new Date(), null);
//    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("DL10001","SO10006","IN10006","CU10008", "ประทีปเจมส์", new Date() , 120.0, 270, new Date(), null, "admin test", null, "1_on_delivery", new Date(), null, ""),
    			createRecord("DL10002","SO10007","IN10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", new Date() , 120.0, 300, new Date(), null, "admin test", null, "1_on_delivery", new Date(), null, ""),
    			createRecord("DL10003","SO10008","IN10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", new Date() , 120.0, 100, new Date(), null, "admin test", null, "2_delivery_completed", new Date(), new Date(), "RC102142")
    			};
//    	return new ListGridRecord[]{};
    }
}
