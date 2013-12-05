package com.smart.mis.client.function.inventory.material.requisition;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialRequestItemData {
	public static ListGridRecord createRecord(String sub_request_id, String request_id, MaterialRequestItemDetails material) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", sub_request_id);
        record.setAttribute("mat_request_id", request_id);
        record.setAttribute("mid", material.material_id);
        record.setAttribute("mname", material.material_name);
        record.setAttribute("unit", material.material_unit);
        record.setAttribute("request_amount", material.request_amount);
        return record;  
    }
	
//	public static ListGridRecord createRecord(String sub_request_id, String request_id, String mid, String mname, String unit, Double weight, Double amount) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("sub_request_id", sub_request_id);
//        record.setAttribute("mat_request_id", request_id);
//        record.setAttribute("mid", mid);
//        record.setAttribute("mname", mname);
//        record.setAttribute("unit", unit);
//        record.setAttribute("request_weight", weight);
//        record.setAttribute("request_amount", amount);
//        return record;  
//    }
    
	public static ListGridRecord createUpdatedRecord(ListGridRecord record, Double amount) {  
        record.setAttribute("issue_amount", amount);
        return record;  
    }
	
    //For test only
    public static ListGridRecord[] getNewRecords(String sub_request_id) {
    	return new ListGridRecord[]{};
    }
}
