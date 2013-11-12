package com.smart.mis.client.function.production.process;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialProcessData {
	public static ListGridRecord createRecord(String mpid, String psid, String mid, String mat_name, Double req_amount, String unit) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mpid", mpid);
        record.setAttribute("psid", psid);
        record.setAttribute("mid",mid);  
        record.setAttribute("mat_name", mat_name);
        record.setAttribute("req_amount", req_amount);
        record.setAttribute("unit", unit);
        return record;  
    }
	
	public static ListGridRecord createRecord(String mid, String mat_name, Double req_amount, String unit) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid",mid);  
        record.setAttribute("mat_name", mat_name);
        record.setAttribute("req_amount", req_amount);
        record.setAttribute("unit", unit);
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String psid) {
    	
        if (psid != null && psid.equals( "PS10001")) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10001","PS10001", "MA10002", "แร่เงิน 92.5%", 0.0, "กรัม")
        	};
        } else if (psid != null && psid.equals( "PS10004")) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10002","PS10004", "MA20002", "แมกกาไซต์ PP7", 7.0,"เม็ด"),
        			createRecord("MP10003","PS10004", "MA20004", "แมกกาไซต์ PP12", 2.0, "เม็ด")
        	};
        } else if (psid != null && psid.equals( "PS20001")) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10001","PS20001", "MA10002", "แร่เงิน 92.5%", 0.0, "กรัม")
        	};
        } else if (psid != null && psid.equals( "PS20004")) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10002","PS20004", "MA20003", "แมกกาไซต์ PP8", 5.0,"เม็ด")
        	};
        }
        	else return new ListGridRecord[]{};
        }
}
