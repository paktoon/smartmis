package com.smart.mis.client.function.sale.quotation;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class QuotationData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String quote_id,String cid, String cus_name,Date from,Date to, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
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
        record.setAttribute("modified_by", created_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createUpdateRecord(String quote_id,String cid, String cus_name,Date from,Date to, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date modified_date,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
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
    
    public static ListGridRecord createStatusRecord(String quote_id, String status, String comment) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("status", status);
        record.setAttribute("comment", comment);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("QA10001","CU10017", "Flora Creek", new Date(), new Date(), new Date() , 120.0, 200, 11000.0, new Date(), null, "admin test", null, "", "รออนุมัติ"),
    			createRecord("QA10002","CU10008", "ประทีปเจมส์", new Date(), new Date(), new Date() , 120.0, 270, 14850.0, new Date(), null, "admin test", null, "", "รอแก้ไข"),
    			createRecord("QA10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", new Date(), new Date(), new Date() , 120.0, 300, 16500.0, new Date(), null, "admin test", null, "", "ยกเลิก"),
    			createRecord("QA10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", new Date(), new Date(), new Date() , 120.0, 100, 5000.0, new Date(), null, "admin test", null, "", "อนุมัติแล้ว"),
    			
    			createRecord("QA10005","CU10017", "Flora Creek", new Date(), new Date(), new Date() , 120.0, 200, 11000.0, new Date(), null, "admin test", null, "", "รออนุมัติ"),
    			createRecord("QA10006","CU10008", "ประทีปเจมส์", new Date(), new Date(), new Date() , 120.0, 270, 14850.0, new Date(), null, "admin test", null, "", "รอแก้ไข"),
    			createRecord("QA10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", new Date(), new Date(), new Date() , 120.0, 300, 16500.0, new Date(), null, "admin test", null, "", "รอแก้ไข"),
    			createRecord("QA10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", new Date(), new Date(), new Date() , 120.0, 100, 5000.0, new Date(), null, "admin test", null, "", "อนุมัติแล้ว")
    	};
    	//return new ListGridRecord[]{};
    }
}
