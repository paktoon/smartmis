package com.smart.mis.client.function.inventory.material.returns;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ReturnData {
	
	public static ListGridRecord createRecord(String return_id,String job_id, String smid, String sm_name, String mid, String mat_name, Double total_return_weight, Double total_received_weight,  Date returned_date, Date received_date,Date created_date,Date modified_date,String created_by,String modified_by, String status) {  
        ListGridRecord record = new ListGridRecord();
        
        record.setAttribute("return_id", return_id);
        record.setAttribute("job_id", job_id);
        
        record.setAttribute("smid",smid);  
        record.setAttribute("sm_name",sm_name);
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",mat_name); 
        
        record.setAttribute("total_return_weight", total_return_weight);  
        //record.setAttribute("total_return_amount", total_return_amount); 
        record.setAttribute("total_received_weight", total_received_weight);  
        //record.setAttribute("total_received_amount", total_received_amount); 
        
        record.setAttribute("received_date", received_date); 
        record.setAttribute("return_date", returned_date);
        
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status);
        return record;  
    }
    
	public static ListGridRecord createReceivedRecord(ListGridRecord record, Double total_received_weight, Date received_date, String received_by, String status) {  
        record.setAttribute("total_received_weight", total_received_weight);
        record.setAttribute("received_date", received_date); 
        record.setAttribute("received_by", received_by);
        record.setAttribute("status", status);
        return record;  
    }
	
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
}
