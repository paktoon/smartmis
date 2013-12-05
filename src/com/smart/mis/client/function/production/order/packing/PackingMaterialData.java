package com.smart.mis.client.function.production.order.packing;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PackingMaterialData {
	public static ListGridRecord createRecord(String sub_job_id, String cm_id, Integer amount, Record material) {  
        ListGridRecord record = (ListGridRecord) material;
        record.setAttribute("cm_id", cm_id);
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("produce_amount", amount * material.getAttributeAsDouble("req_amount"));
        return record;  
    }
    
	public static ListGridRecord createRecord(Record material, Integer amount) {  
        ListGridRecord record = (ListGridRecord) material;
        record.setAttribute("produce_amount", amount * material.getAttributeAsDouble("req_amount"));
        return record;  
    }
	
    //For test only
    public static ListGridRecord[] getNewRecords(String sub_job_id) {
    	return new ListGridRecord[]{};
    }
}
