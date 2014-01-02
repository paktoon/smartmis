package com.smart.mis.client.function.inventory.material.requisition;

import java.util.Date;

import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialRequestData {
	
	public static ListGridRecord createRecord(String mat_req_id, String job_id, Smith smith, String req_type, Date req_date, Double total_req_amount, Date created_date,Date modified_date,String created_by,String modified_by, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mat_request_id", mat_req_id);
        record.setAttribute("job_id", job_id);
        
        record.setAttribute("smid", smith.smid);
        record.setAttribute("sname", smith.name);
        record.setAttribute("req_type", req_type);
        record.setAttribute("req_date", req_date);
        
        record.setAttribute("total_request_amount", total_req_amount); 
         
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status); // requested
        return record;  
    }
	
	public static ListGridRecord createReceivedRecord(ListGridRecord record, Date issued_date, Double total_issued_amount,Date modified_date,String modified_by, String status) {  
        record.setAttribute("issued_date", issued_date);
        record.setAttribute("total_issued_amount", total_issued_amount); 
        
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status); // issued
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(Date modified_date,String modified_by, String comment, String status, ListGridRecord castingRecord) {  
    	ListGridRecord record = castingRecord;
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status); // requested, issued
        return record; 
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
}
