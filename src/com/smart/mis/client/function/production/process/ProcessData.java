package com.smart.mis.client.function.production.process;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProcessData {

    //public static ListGridRecord createRecord(String id, String type, String desc, Double std_time, String pid, String mid, String mat_name) { 
    public static ListGridRecord createRecord(String id, String type, String desc, Double std_time, String pid, Integer priority) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("psid", id);
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("priority", priority);
        //record.setAttribute("mid", mid);
        //record.setAttribute("mat_name", mat_name);
        return record;  
    }
    
    public static ListGridRecord createRecord(String type, String desc, Double std_time, Integer priority) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("priority", priority);
        //record.setAttribute("mid", mid);
        //record.setAttribute("mat_name", mat_name);
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String pid) {
    
    if (pid != null && (pid.equals( "PD10001") || pid.equals( "PD10002") || pid.equals( "PD10003"))) {
    	return new ListGridRecord[]{ 
    			createRecord("PS10001","1_casting", "", 3.0, "PD10001", 1),
    			createRecord("PS10002","2_scrape", "", 3.0, "PD10001", 2),
    			createRecord("PS10003","3_abrade", "", 3.0, "PD10001", 3),
    			createRecord("PS10004","4_packing", "", 1.0, "PD10001", 4)
    	};
    } else if (pid != null && (pid.equals( "PD10004") || pid.equals( "PD10005") || pid.equals( "PD10006") || pid.equals( "PD10007"))) {
    	return new ListGridRecord[]{ 
    			createRecord("PS20001","1_casting", "", 3.0, "PD10004", 1),
    			createRecord("PS20002","2_scrape", "", 3.0, "PD10004", 2),
    			createRecord("PS20003","3_abrade", "", 3.0, "PD10004", 3),
    			createRecord("PS20004","4_packing", "", 1.0, "PD10004", 4)
    	};
    } 
    	else return new ListGridRecord[]{};
    }
}
