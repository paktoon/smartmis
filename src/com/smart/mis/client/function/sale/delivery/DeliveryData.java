package com.smart.mis.client.function.sale.delivery;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DeliveryData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String delivery_id, String sale_id, String invoice_id ,String cid, String cus_name, Date delivery ,Double total_weight,Integer total_amount,Date created_date,Date modified_date,String created_by,String modified_by, String status, String issued_status, Date sent, Date received, String receipt_id) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("delivery_id", delivery_id);
        record.setAttribute("sale_id", sale_id);
        record.setAttribute("invoice_id", invoice_id);
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status); // กำลังนำส่ง, นำส่งแล้ว
        record.setAttribute("sent", sent);
        record.setAttribute("issued_status", issued_status);
        record.setAttribute("received", received);
        record.setAttribute("receipt_id", receipt_id);
        return record;  
    }
    
    public static ListGridRecord createIssueRecord(ListGridRecord record, Double total_issued_weight, Integer total_issued_amount, Date issued_date, String issued_by, String issued_status) {
        record.setAttribute("total_issued_weight", total_issued_weight);  
        record.setAttribute("total_issued_amount", total_issued_amount); 
        
        record.setAttribute("issued_date", issued_date);
        record.setAttribute("issued_by", issued_by);
        record.setAttribute("issued_status", issued_status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String delivery_id, String status, Date received, String receipt_id) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("delivery_id", delivery_id);
        record.setAttribute("status", status);
        record.setAttribute("received", received);
        record.setAttribute("receipt_id", receipt_id);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("DL10001","SO10006","IN10006","CU10008", "ประทีปเจมส์", new Date() , 1836.0, 270, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_on_delivery", "0_product_request", new Date(), null, ""),
    			createRecord("DL10002","SO10007","IN10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", new Date() , 1896.0, 300, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_on_delivery", "1_product_issued", new Date(), null, ""),
    			createRecord("DL10003","SO10008","IN10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", new Date() , 632.0, 100, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_delivery_completed", "1_product_issued", new Date(), new Date(), "RC102142")
    			};
//    	return new ListGridRecord[]{};
    }
}
