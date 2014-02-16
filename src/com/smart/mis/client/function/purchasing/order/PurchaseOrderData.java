package com.smart.mis.client.function.purchasing.order;

import java.util.Date;

import com.smart.mis.shared.DateTimeMapping;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PurchaseOrderData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String order_id,String request_id,String sid, String sup_name, String quote_id, String payment_model, Integer credit, Date received_date, Date returned_date, Date delivery ,Double total_weight,Double total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status, String payment_status, String received_status) {  
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
        record.setAttribute("received_status", received_status);
        
        return record;  
    }
    
    public static ListGridRecord createUpdateRecord(String order_id, String request_id,String sid, String quote_id, String sup_name, String payment_model, Integer credit, Date received_date, Date returned_date, Date delivery ,Double total_weight,Double total_amount,Double netExclusive,Date modified_date,String modified_by, String comment, String status, String payment_status, String received_status) {  
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
        record.setAttribute("received_status", received_status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String order_id, String status, String payment_status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("order_id", order_id);
        record.setAttribute("status", status);
        record.setAttribute("payment_status", payment_status);
        return record;  
    }
    
    public static ListGridRecord updatePaidRecord(ListGridRecord record, String user) {
        record.setAttribute("paid_date", new Date());
        record.setAttribute("paid_by", user);
        return record;  
    }
    
    public static ListGridRecord updateReceivedRecord(ListGridRecord record, Double total_received_weight, Double total_received_amount, String user, String received_status) {
    	record.setAttribute("total_received_weight", total_received_weight);
    	record.setAttribute("total_received_amount", total_received_amount);
        record.setAttribute("received_date", new Date());
        record.setAttribute("received_by", user);
        record.setAttribute("received_status", received_status);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	
		Date received =	DateTimeMapping.getDate("02/16/2014");
		Date delivery =	DateTimeMapping.getDate("03/02/2014");
		Date created =	DateTimeMapping.getDate("02/15/2014");
		Date paid = DateTimeMapping.getDate("02/15/2014");
		
    	ListGridRecord paid_1 = createRecord("PO10002","PR10002","SU10002","ห้างทอง น่ำเชียง","PO0102", "เงินสด", 30 , received, null, delivery , 3000.0, 3000.0, 32500.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "2_recevied_product", "2_paid", "2_received");
    	paid_1.setAttribute("paid_date", paid);
    	paid_1.setAttribute("paid_by", "สมปรีดี เงินสด");
    	paid_1.setAttribute("paidInclusive", 32500.0);
    	paid_1.setAttribute("total_received_weight", 3000.0);
    	paid_1.setAttribute("total_received_amount", 3000.0);
    	paid_1.setAttribute("received_date", paid);
    	paid_1.setAttribute("received_by", "ภักดิ์ทูล ใจทอง");
    	ListGridRecord paid_2 = createRecord("PO10004","PR10004","SU10002","ห้างทอง น่ำเชียง","PO0104", "เงินสด", 10 , received, null, delivery , 3000.0, 3000.0, 32500.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_created_order", "2_paid", "1_waiting_for_received");
    	paid_2.setAttribute("paid_date", paid);
    	paid_2.setAttribute("paid_by", "ภักดิ์ทูล ใจทอง");
    	paid_2.setAttribute("paidInclusive", 32500.0);
    	ListGridRecord paid_3 = createRecord("PO10006","PR10006","SU10003","ร้าน วีวี่เจมส์","PO0106", "เงินสด", 20 , received, null, delivery , 3000.0, 25000.0, 18000.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_created_order", "2_paid", "1_waiting_for_received");
    	paid_3.setAttribute("paid_date", paid);
    	paid_3.setAttribute("paid_by", "สมปรีดี เงินสด");
    	paid_3.setAttribute("paidInclusive", 18000.0);
    	ListGridRecord paid_4 = createRecord("PO10008","PR10008","SU10005","บริษัท เครื่องประดับอิรอส จำกัด","PO0108", "เงินสด", 10 , received, null, delivery , 3000.0, 25000.0, 18000.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "2_recevied_product", "2_paid", "2_received");
    	paid_4.setAttribute("paid_date", paid);
    	paid_4.setAttribute("paid_by", "ภักดิ์ทูล ใจทอง");
    	paid_4.setAttribute("paidInclusive", 18000.0);
    	paid_4.setAttribute("total_received_weight", 3000.0);
    	paid_4.setAttribute("total_received_amount", 25000.0);
    	paid_4.setAttribute("received_date", paid);
    	paid_4.setAttribute("received_by", "ภักดิ์ทูล ใจทอง");
    	
    	ListGridRecord received_1 = createRecord("PO10003","PR10003","SU10001","บริษัท 99GOLDS","PO0103", "เงินสด", 20 , received, null, delivery , 2000.0, 2000.0, 22500.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "2_recevied_product", "1_waiting_for_payment", "2_received");
    	received_1.setAttribute("total_received_weight", 2000.0);
    	received_1.setAttribute("total_received_amount", 2000.0);
    	received_1.setAttribute("received_date", new Date());
    	received_1.setAttribute("received_by", "สมศักดิ์ ดูแลคลัง");
    	
    	return new ListGridRecord[]{ 
    			createRecord("PO10001","PR10001","SU10001","บริษัท 99GOLDS", "PO0101", "เงินสด", 10 , received, null, delivery , 2000.0, 2000.0, 22500.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_created_order", "1_waiting_for_payment", "1_waiting_for_received"),
    			paid_1,
    			received_1,
    			paid_2,
    			
    			createRecord("PO10005","PR10005","SU10003","ร้าน วีวี่เจมส์","PO0105", "เงินสด", 30 , received, null, delivery , 2400.0, 20000.0, 15000.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_created_order", "1_waiting_for_payment", "1_waiting_for_received"),
    			paid_3,
    			createRecord("PO10007","PR10007","SU10005","บริษัท เครื่องประดับอิรอส จำกัด","PO0107", "เงินสด", 10 , received, null, delivery , 2400.0, 20000.0, 15000.0, created, created, "สมหมาย ซื้อของ", "ภักดิ์ทูล ใจทอง", "", "1_created_order", "1_waiting_for_payment", "1_waiting_for_received"),
    			paid_4
    	};
    	//return new ListGridRecord[]{};
    }
}
