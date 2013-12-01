package com.smart.mis.client.function.inventory.material.returns;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ReturnData {
	
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
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{};
    }
}
