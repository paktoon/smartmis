package com.smart.mis.client.function.production.order.scraping;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ScrapingMaterialData {
	public static ListGridRecord createRecord(String sub_job_id, String cm_id, Integer amount, Record material) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", material.getAttributeAsString("mid"));
        record.setAttribute("mat_name", material.getAttributeAsString("mat_name"));
        record.setAttribute("unit", material.getAttributeAsString("unit"));
        
        record.setAttribute("cm_id", cm_id);
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("produce_amount", amount * material.getAttributeAsDouble("req_amount"));
        return record;  
    }
	
	public static ListGridRecord createRecord(Record material, Integer amount) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", material.getAttributeAsString("mid"));
        record.setAttribute("mat_name", material.getAttributeAsString("mat_name"));
        record.setAttribute("unit", material.getAttributeAsString("unit"));
        
        record.setAttribute("produce_amount", amount * material.getAttributeAsDouble("req_amount"));
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String sub_job_id) {
    	return new ListGridRecord[]{};
    }
}
