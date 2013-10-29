package com.smart.mis.client.function.production.process;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProcessData {

    //public static ListGridRecord createRecord(String id, String type, String desc, Double std_time, String pid, String mid, String mat_name) { 
    public static ListGridRecord createRecord(String id, String type, String desc, Double std_time, String pid, Integer priority) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("psid", id);
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("pid", pid);
        record.setAttribute("priority", priority);
        //record.setAttribute("mid", mid);
        //record.setAttribute("mat_name", mat_name);
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String pid) {
    
    if (pid != null && pid.equals( "PD10001")) {
    	return new ListGridRecord[]{ 
    			createRecord("PS10001","หล่อและขึ้นรูป", "", 3.0, "PD10001", 1),
    			createRecord("PS10002","แต่ง", "", 3.0, "PD10001", 2),
    			createRecord("PS10003","ฝังพลอย", "", 3.0, "PD10001", 3),
    			createRecord("PS10004","ขัดและติดพลอย", "", 3.0, "PD10001", 4),
    			createRecord("PS10005","บรรจุหีบห่อ", "", 1.0, "PD10001", 5)
    	};
    } else if (pid != null && pid.equals( "PD10004")) {
    	return new ListGridRecord[]{ 
    			createRecord("PS20001","หล่อและขึ้นรูป", "", 3.0, "PD10004", 1),
    			createRecord("PS20002","แต่ง", "", 3.0, "PD10004", 2),
    			createRecord("PS20004","ขัดและติดพลอย", "", 3.0, "PD10004", 4),
    			createRecord("PS20005","บรรจุหีบห่อ", "", 1.0, "PD10004", 5)
    	};
    } 
    	else return new ListGridRecord[]{};
    }
}