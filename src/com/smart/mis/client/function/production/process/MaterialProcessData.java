package com.smart.mis.client.function.production.process;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialProcessData {
	public static ListGridRecord createRecord(String mpid, String psid, String mid, String mat_name, Double req_amount, Double weight, String unit, String type) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mpid", mpid);
        record.setAttribute("psid", psid);
        record.setAttribute("mid",mid);  
        record.setAttribute("mat_name", mat_name);
        record.setAttribute("req_amount", req_amount);
        record.setAttribute("weight", weight);
        record.setAttribute("unit", unit);
        record.setAttribute("type", type);
        return record;  
    }
	
	public static ListGridRecord createRecord(String mid, String mat_name, Double req_amount, Double weight, String unit, String type) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid",mid);  
        record.setAttribute("mat_name", mat_name);
        record.setAttribute("req_amount", req_amount);
        record.setAttribute("weight", weight);
        record.setAttribute("unit", unit);
        record.setAttribute("type", type);
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String psid) {
    	
        if (psid != null && (psid.equals( "PS10001") || psid.equals( "PS40001"))) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10001","PS10001", "MA10002", "แร่เงิน 92.5%", 2.8, 2.8, "กรัม", "แร่เงิน"),
        			createRecord("MP10007","PS10001", "MA10001", "แร่เงิน 100%", 2.8, 2.8, "กรัม", "แร่เงิน")
        	};
        } else if (psid != null && (psid.equals( "PS10003") || psid.equals( "PS40003"))) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10002","PS10003", "MA20002", "แมกกาไซต์ PP7", 7.0, 0.84, "เม็ด", "แมกกาไซต์"),
        			createRecord("MP10003","PS10003", "MA20004", "แมกกาไซต์ PP12", 2.0, 0.24, "เม็ด", "แมกกาไซต์")
        	};
        } else if (psid != null && (psid.equals( "PS20001") || psid.equals( "PS30001"))) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10001","PS20001", "MA10002", "แร่เงิน 92.5%", 2.8, 2.8, "กรัม", "แร่เงิน"),
        			createRecord("MP10009","PS20001", "MA10001", "แร่เงิน 100%", 2.8, 2.8, "กรัม", "แร่เงิน")
        	};
        } else if (psid != null && (psid.equals( "PS20003") || psid.equals( "PS30003"))) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10004","PS20003", "MA20003", "แมกกาไซต์ PP8", 5.0, 0.6, "เม็ด", "แมกกาไซต์")
        	};
        } else if (psid != null && (psid.equals( "PS10004") || psid.equals( "PS40004"))) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10005","PS10004", "MA20005", "ถุงพลาสติก", 1.0, 0.12, "ถุง", "อื่นๆ")
        	};
        } else if (psid != null && (psid.equals( "PS20004") || psid.equals( "PS30004"))) {
        	return new ListGridRecord[]{ 
        			createRecord("MP10006","PS20004", "MA20005", "ถุงพลาสติก", 1.0, 0.12, "ถุง", "อื่นๆ")
        	};
        }
        	else return new ListGridRecord[]{};
        }
}
