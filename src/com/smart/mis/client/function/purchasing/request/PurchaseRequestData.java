package com.smart.mis.client.function.purchasing.request;

import java.util.Date;

import com.smart.mis.shared.DateTimeMapping;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PurchaseRequestData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String request_id,String sid, String sup_name, String quote_id, String payment_model, Integer credit,Date from, Date to, Date delivery ,Double total_weight,Double total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("request_id", request_id);
        record.setAttribute("sid",sid);  
        record.setAttribute("sup_name",sup_name);
        
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);
        record.setAttribute("from", from); 
        record.setAttribute("to", to);
        
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
        return record;  
    }
    
    public static ListGridRecord createUpdateRecord(String request_id,String sid, String quote_id, String sup_name, String payment_model, Integer credit,Date from, Date to, Date delivery ,Double total_weight,Double total_amount,Double netExclusive,Date modified_date,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("request_id", request_id);
        record.setAttribute("sid",sid);  
        record.setAttribute("sup_name",sup_name);
        
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);
        record.setAttribute("from", from); 
        record.setAttribute("to", to);
        
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
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String request_id, String status, String comment, String user) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("request_id", request_id);
        record.setAttribute("status", status);
        record.setAttribute("comment", comment);
        record.setAttribute("modified_date", new Date());
        record.setAttribute("modified_by", user);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	
		Date from =	DateTimeMapping.getDate("02/15/2014");
		Date to =	DateTimeMapping.getDate("02/22/2014");
		Date delivery =	DateTimeMapping.getDate("03/02/2014");
		Date created =	DateTimeMapping.getDate("02/15/2014");
		
    	return new ListGridRecord[]{ 
    			createRecord("PR10001","SU10001","บริษัท 99GOLDS", "PO0101", "เงินสด", 10 , from, to, delivery , 2000.0, 2000.0, 22500.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "2_waiting_for_approved"),
    			createRecord("PR10002","SU10002","ห้างทอง น่ำเชียง","PO0102", "เงินสด", 30 , from, to, delivery , 3000.0, 3000.0, 32500.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			createRecord("PR10003","SU10001","บริษัท 99GOLDS","PO0103", "เงินสด", 20 , from, to, delivery , 2000.0, 2000.0, 22500.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised"),
    			createRecord("PR10004","SU10002","ห้างทอง น่ำเชียง","PO0104", "เงินสด", 10 , from, to, delivery , 3000.0, 3000.0, 32500.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised"),
    			
    			createRecord("PR10005","SU10003","ร้าน วีวี่เจมส์","PO0105", "เงินสด", 30 , from, to, delivery , 2400.0, 20000.0, 15000.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "2_waiting_for_approved"),
    			createRecord("PR10006","SU10003","ร้าน วีวี่เจมส์","PO0106", "เงินสด", 20 , from, to, delivery , 3000.0, 25000.0, 18000.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			createRecord("PR10007","SU10005","บริษัท เครื่องประดับอิรอส จำกัด","PO0107", "เงินสด", 10 , from, to, delivery , 2400.0, 20000.0, 15000.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "2_waiting_for_approved"),
    			createRecord("PR10008","SU10005","บริษัท เครื่องประดับอิรอส จำกัด","PO0108", "เงินสด", 10 , from, to, delivery , 3000.0, 25000.0, 18000.0, created, null, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised")
    	}; 	
//    	return new ListGridRecord[]{};
    }
}
