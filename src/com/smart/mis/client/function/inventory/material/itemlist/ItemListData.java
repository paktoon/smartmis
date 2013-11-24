package com.smart.mis.client.function.inventory.material.itemlist;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ItemListData {
	public static ListGridRecord createRecord(String id, String ref_id, String item_id, Double amount, Double weight) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("id", item_id);
        record.setAttribute("ref_id", ref_id);
        record.setAttribute("item_id", item_id);
        record.setAttribute("open_amount", amount);
        record.setAttribute("open_weight", weight);
        return record;  
    }
    
	public static ListGridRecord createUpdatedRecord(ListGridRecord record, Double amount, Double weight) {  
        record.setAttribute("close_amount", amount);
        record.setAttribute("close_weight", weight);
        return record;  
    }
	
    //For test only
    public static ListGridRecord[] getNewRecords(String ref_id) {
    	return new ListGridRecord[]{};
    }
}
