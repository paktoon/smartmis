package com.smart.mis.client.function.production.order.casting;

import java.util.Date;

import com.smart.mis.shared.DateTimeMapping;
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
	
//	public static ListGridRecord createReceivedRecord(String job_id,String plan_id,Smith smith, Date sent_date, Date received_date, Date due_date,Double total_sent_weight,Double total_sent_amount, Double total_recv_weight, Double total_recv_amount, Double total_wage, Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("job_id", job_id);
//        record.setAttribute("plan_id", plan_id);
//        record.setAttribute("smid", smith.smid);
//        
//        record.setAttribute("sname", smith.name);
//        record.setAttribute("semail", smith.email);
//        record.setAttribute("sphone1", smith.phone1);
//        record.setAttribute("sphone2", smith.phone2);
//        record.setAttribute("saddress", smith.address);
//        record.setAttribute("stype", smith.type);
//        
//        record.setAttribute("sent_date", sent_date);
//        record.setAttribute("received_date", received_date);
//        record.setAttribute("due_date", due_date); // Calculate from std_time
//        
//        record.setAttribute("total_sent_weight", total_sent_weight);  
//        record.setAttribute("total_sent_amount", total_sent_amount); 
//        
//        record.setAttribute("total_recv_weight", total_recv_weight);  
//        record.setAttribute("total_recv_amount", total_recv_amount); 
//        record.setAttribute("total_wage", total_wage);
//        
//        record.setAttribute("created_date", created_date); 
//        record.setAttribute("created_by", created_by);
//        record.setAttribute("modified_date", modified_date);
//        record.setAttribute("modified_by", modified_by);
//        record.setAttribute("comment", comment);
//        record.setAttribute("status", status); // received -> to next step, create wage payment, material requisition
//        return record;  
//    }
	
	public static ListGridRecord createReceivedRecord(ListGridRecord record, Date received_date, Double total_recv_weight, Double total_recv_amount, Double total_wage,Date modified_date,String modified_by, String status) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("job_id", job_id);
//        record.setAttribute("plan_id", plan_id);
//        record.setAttribute("smid", smith.smid);
//        
//        record.setAttribute("sname", smith.name);
//        record.setAttribute("semail", smith.email);
//        record.setAttribute("sphone1", smith.phone1);
//        record.setAttribute("sphone2", smith.phone2);
//        record.setAttribute("saddress", smith.address);
//        record.setAttribute("stype", smith.type);
//        
//        record.setAttribute("sent_date", sent_date);
        record.setAttribute("received_date", received_date);
//        record.setAttribute("due_date", due_date); // Calculate from std_time
        
//        record.setAttribute("total_sent_weight", total_sent_weight);  
//        record.setAttribute("total_sent_amount", total_sent_amount); 
        
        record.setAttribute("total_recv_weight", total_recv_weight);  
        record.setAttribute("total_recv_amount", total_recv_amount); 
        record.setAttribute("total_wage", total_wage);
        
//        record.setAttribute("created_date", created_date); 
//        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
//        record.setAttribute("comment", comment);
        record.setAttribute("status", status); // received -> to next step, create wage payment, material requisition
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(Date modified_date,String modified_by, String comment, String status, ListGridRecord castingRecord) {  
    	ListGridRecord record = castingRecord;
        //record.setAttribute("job_id", job_id);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status); // overdue, canceled
        return record; 
    }
    
    public static ListGridRecord[] getNewRecords() {
    	
    	Date sent_date =	DateTimeMapping.getDate("02/15/2014");
    	Date due_date =	DateTimeMapping.getDate("02/18/2014");
		Date created =	DateTimeMapping.getDate("02/15/2014");
		Date modified =	DateTimeMapping.getDate("02/15/2014");
		
    	Smith sm_1 = new Smith("SM10006", "โรงหล่อ พรหมรังสี", "(02) 454-4964", "(081) 843-3076", "test_6@smith.co.th", "392/21 ถนน สุขุมวิท 20 เขต คลองเตย จังหวัด กรุงเทพ รหัสไปรษณีย์ 10110", "หล่อขึ้นรูป");
    	return new ListGridRecord[]{ 
    			createSentRecord("JOB10001","PL10004", sm_1, sent_date, due_date, 3080.0, 550 , created, modified, "สมใจ ผลิตเก่ง", "ภักดิ์ทูล ใจทอง", "", "1_on_production"),
    			createSentRecord("JOB10002","PL10006", sm_1, sent_date, due_date, 560.0, 100 , created, modified, "สมใจ ผลิตเก่ง", "ภักดิ์ทูล ใจทอง", "", "1_on_production"),
    			createSentRecord("JOB10003","PL10007", sm_1, sent_date, due_date, 1512.0, 270 , created, modified, "สมใจ ผลิตเก่ง", "ภักดิ์ทูล ใจทอง", "", "1_on_production")
    	};
//    	return new ListGridRecord[]{};
    }
}
