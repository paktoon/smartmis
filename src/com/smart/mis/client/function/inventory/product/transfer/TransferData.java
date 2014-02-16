package com.smart.mis.client.function.inventory.product.transfer;

import java.util.Date;

import com.smart.mis.shared.DateTimeMapping;
import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TransferData {
	
	public static ListGridRecord createRecord(ListGridRecord refRecord, String transfer_id, Date created_date, String created_by, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("transfer_id", transfer_id);
        record.setAttribute("plan_id", refRecord.getAttributeAsString("plan_id"));
        
        record.setAttribute("transfer_date", refRecord.getAttributeAsDate("transfer_date"));
        record.setAttribute("transfer_by", created_by);
        
        record.setAttribute("total_sent_weight", refRecord.getAttributeAsDouble("total_sent_weight"));  
        record.setAttribute("total_sent_amount", refRecord.getAttributeAsInt("total_sent_amount")); 
        //record.setAttribute("total_recv_weight", total_recv_weight);  
        //record.setAttribute("total_recv_weight", total_recv_weight);
         
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        
        Date modified_date = null;
        String modified_by = null; 		
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status); // sent, received
        return record;  
    }
	
	public static ListGridRecord createRecord(String transfer_id, String plan_id, Date transfer_date, Double total_sent_weight, Integer total_sent_amount, Date created_date, String created_by, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("transfer_id", transfer_id);
        record.setAttribute("plan_id", plan_id);
        
        record.setAttribute("transfer_date", transfer_date);
        record.setAttribute("transfer_by", created_by);
        
        record.setAttribute("total_sent_weight", total_sent_weight);  
        record.setAttribute("total_sent_amount", total_sent_amount); 
         
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        
        Date modified_date = null;
        String modified_by = null; 		
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status); // sent, received
        return record;  
    }
    
    public static ListGridRecord createReceivedRecord(ListGridRecord record, Double total_recv_weight, Double total_recv_amount, Date received_date, String received_by, String status) {  
    	record.setAttribute("total_recv_weight", total_recv_weight);
    	record.setAttribute("total_recv_amount", total_recv_amount);
    	record.setAttribute("received_date", received_date);
        record.setAttribute("received_by", received_by);
        record.setAttribute("status", status);
        return record; 
    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, Date modified_date,String modified_by, String status, ListGridRecord castingRecord) {  
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status);
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
//    			createRecord("QA10009","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , new Date(), new Date(), new Date() , 120.0, 210, 11000.0, new Date(), null, "admin test", null, "", "1_waiting_for_revised"),
//    			createRecord("QA10010","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 20 , new Date(), new Date(), new Date() , 120.0, 70, 14850.0, new Date(), null, "admin test", null, "", "3_approved"),
//    			createRecord("QA10011","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , new Date(), new Date(), new Date() , 120.0, 50, 16500.0, new Date(), null, "admin test", null, "", "1_waiting_for_revised"),
//    			createRecord("QA10012","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 , new Date(), new Date(), new Date() , 120.0, 125, 5000.0, new Date(), null, "admin test", null, "", "3_approved"),
//    	};
    	Date transfer =	DateTimeMapping.getDate("02/15/2014");
    	Date created =	DateTimeMapping.getDate("02/15/2014");
    	return new ListGridRecord[]{ 
    			createRecord("TF10001","PL10005", transfer, 1496.0, 220, created, "สมใจ ผลิตเก่ง", "1_sent")
    	};
//    	return new ListGridRecord[]{};
    }
}
