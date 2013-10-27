package com.smart.mis.client.function.production.process;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProcessData {

    public static ListGridRecord createRecord(String id, String type, String desc, Double std_time, String pid, String mid, String mat_name) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("id", id);
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("pid", pid);
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name", mat_name);
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String pid) {
    	return new ListGridRecord[]{ 
    			createRecord("PS10001","หล่อและขึ้นรูป", "", 3.0, pid, "MA10002", "แร่เงิน 92.5%"),
    			createRecord("PS10002","แต่ง", "", 3.0, pid,"",""),
    			createRecord("PS10003","ฝังพลอย", "", 3.0, pid, "",""),
    			createRecord("PS10004","ขัดและติดพลอย", "", 3.0, pid, "MA20001", "แมกกาไซต์ PP6"),
    			createRecord("PS10005","บรรจุหีบห่อ", "", 1.0, pid, "","")
    	};
    }
}
