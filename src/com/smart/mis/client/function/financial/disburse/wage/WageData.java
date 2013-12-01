package com.smart.mis.client.function.financial.disburse.wage;

import java.util.Date;

import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WageData {
	
	//public static ListGridRecord createSentRecord(String job_id,String plan_id,Smith smith, Date sent_date, Date due_date,Double total_sent_weight,Integer total_sent_amount, Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
	public static ListGridRecord createRecord(ListGridRecord ref_record, String wage_id, Date created_date, String created_by, String status) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("job_id", ref_record.getAttributeAsString("job_id"));
        record.setAttribute("smid", ref_record.getAttributeAsString("smid"));
        
        record.setAttribute("sname", ref_record.getAttributeAsString("sname"));
        record.setAttribute("semail", ref_record.getAttributeAsString("semail"));
        record.setAttribute("sphone1", ref_record.getAttributeAsString("sphone1"));
        record.setAttribute("sphone2", ref_record.getAttributeAsString("sphone2"));
        record.setAttribute("saddress", ref_record.getAttributeAsString("saddress"));
        record.setAttribute("stype", ref_record.getAttributeAsString("stype"));
        
        record.setAttribute("total_recv_weight", ref_record.getAttributeAsDouble("total_recv_weight"));  
        record.setAttribute("total_recv_amount", ref_record.getAttributeAsInt("total_recv_amount")); 
        record.setAttribute("total_wage", ref_record.getAttributeAsDouble("total_wage"));
		
		record.setAttribute("wage_id", wage_id);
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        
        Date modified_date = null;
        String modified_by = null;
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        //record.setAttribute("comment", comment);
        record.setAttribute("status", status); // created, sent
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, Date modified_date,String modified_by, String status) {  
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status);
        return record; 
    }
    
    public static ListGridRecord createPaidRecord(ListGridRecord record, Date modified_date,String modified_by, String status) {  
        record.setAttribute("paidInclusive", modified_date);
        record.setAttribute("paid_date", modified_by);
        record.setAttribute("paid_by", status);
        return record; 
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
}
