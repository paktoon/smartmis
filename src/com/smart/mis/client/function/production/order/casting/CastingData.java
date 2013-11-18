package com.smart.mis.client.function.production.order.casting;

import java.util.Date;

import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CastingData {
	
	public static ListGridRecord createSentRecord(String job_id,String plan_id,Smith smith, Date sent_date, Date due_date,Double total_sent_weight,Integer total_sent_amount, Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("job_id", job_id);
        record.setAttribute("plan_id", plan_id);
        record.setAttribute("smid", smith.smid);
        
        record.setAttribute("sname", smith.name);
        record.setAttribute("semail", smith.email);
        record.setAttribute("sphone1", smith.phone1);
        record.setAttribute("sphone2", smith.phone2);
        record.setAttribute("saddress", smith.address);
        record.setAttribute("stype", smith.type);
        
        record.setAttribute("sent_date", sent_date);
        record.setAttribute("due_date", due_date); // Calculate from std_time
        
        record.setAttribute("total_sent_weight", total_sent_weight);  
        record.setAttribute("total_sent_amount", total_sent_amount); 
        //record.setAttribute("total_rec_weight", total_rec_weight);  
        //record.setAttribute("total_rec_amount", total_rec_amount); 
        //record.setAttribute("total_wage", total_wage);
         
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status); // created, sent
        return record;  
    }
	
	public static ListGridRecord createReceivedRecord(String job_id,String plan_id,Smith smith, Date sent_date, Date received_date, Date due_date,Double total_sent_weight,Double total_sent_amount, Double total_recv_weight, Double total_recv_amount, Double total_wage, Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("job_id", job_id);
        record.setAttribute("plan_id", plan_id);
        record.setAttribute("smid", smith.smid);
        
        record.setAttribute("sname", smith.name);
        record.setAttribute("semail", smith.email);
        record.setAttribute("sphone1", smith.phone1);
        record.setAttribute("sphone2", smith.phone2);
        record.setAttribute("saddress", smith.address);
        record.setAttribute("stype", smith.type);
        
        record.setAttribute("sent_date", sent_date);
        record.setAttribute("received_date", received_date);
        record.setAttribute("due_date", due_date); // Calculate from std_time
        
        record.setAttribute("total_sent_weight", total_sent_weight);  
        record.setAttribute("total_sent_amount", total_sent_amount); 
        
        record.setAttribute("total_recv_weight", total_recv_weight);  
        record.setAttribute("total_recv_amount", total_recv_amount); 
        record.setAttribute("total_wage", total_wage);
        
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status); // received -> to next step, create wage payment, material requisition
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String job_id,Date modified_date,String modified_by, String comment, String status) {  
    	ListGridRecord record = new ListGridRecord();
        record.setAttribute("job_id", job_id);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status); // overdue, canceled
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
    	return new ListGridRecord[]{};
    }
}
