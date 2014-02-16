package com.smart.mis.client.function.production.order.casting;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CastingMaterialData {
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
	
	public static ListGridRecord createRecord(String cm_id, String sub_job_id, String mid, String mat_name, String unit, Double produce_amount) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name", mat_name);
        record.setAttribute("unit", unit);
        record.setAttribute("cm_id", cm_id);
        record.setAttribute("sub_job_id", sub_job_id);
        record.setAttribute("produce_amount", produce_amount);
        return record;  
    }
	
    //For test only
    public static ListGridRecord[] getNewRecords(String sub_job_id) {
    	if (sub_job_id != null && sub_job_id.equals("SJ10001")) {
        	return new ListGridRecord[]{ 
        			createRecord("CM10001",sub_job_id, "MA10001", "แร่เงิน 92.5%", "กรัม", 560.0),
        			createRecord("CM10002",sub_job_id, "MA10002", "แร่เงิน 100%", "กรัม", 560.0)
        	};
        } else if (sub_job_id != null && sub_job_id.equals("SJ10002")) {
        	return new ListGridRecord[]{ 
        			createRecord("CM10003",sub_job_id, "MA10001", "แร่เงิน 92.5%", "กรัม", 980.0),
        			createRecord("CM10004",sub_job_id, "MA10002", "แร่เงิน 100%", "กรัม", 980.0)
        	};
        } else if (sub_job_id != null && sub_job_id.equals("SJ10003")) {
        	return new ListGridRecord[]{ 
        			createRecord("CM10005",sub_job_id, "MA10001", "แร่เงิน 92.5%", "กรัม", 280.0),
        			createRecord("CM10006",sub_job_id, "MA10002", "แร่เงิน 100%", "กรัม", 280.0)
        	};
        } else if (sub_job_id != null && sub_job_id.equals("SJ10004")) {
        	return new ListGridRecord[]{ 
        			createRecord("CM10007",sub_job_id, "MA10001", "แร่เงิน 92.5%", "กรัม", 196.0),
        			createRecord("CM10008",sub_job_id, "MA10002", "แร่เงิน 100%", "กรัม", 196.0)
        	};
        } else if (sub_job_id != null && sub_job_id.equals("SJ10005")) {
        	return new ListGridRecord[]{ 
        			createRecord("CM10009",sub_job_id, "MA10001", "แร่เงิน 92.5%", "กรัม", 560.0),
        			createRecord("CM10010",sub_job_id, "MA10002", "แร่เงิน 100%", "กรัม", 560.0)
        	};
        } else return new ListGridRecord[]{};
//    	return new ListGridRecord[]{};
    }
}
