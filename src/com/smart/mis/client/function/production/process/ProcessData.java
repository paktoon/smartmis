package com.smart.mis.client.function.production.process;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProcessData {

    //public static ListGridRecord createRecord(String id, String type, String desc, Double std_time, String pid, String mid, String mat_name) { 
    public static ListGridRecord createRecord(String id, String type, String desc, Integer std_time, String pid, Integer priority) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("psid", id);
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("priority", priority);
        return record;  
    }
    
    public static ListGridRecord createRecord(String type, String desc, Integer std_time, Integer priority) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("priority", priority);
        return record;  
    }
    
    //For test only
    public static ListGridRecord[] getNewRecords(String pid) {
    
    if (pid != null && (pid.equals( "PD10001") || pid.equals( "PD10002") || pid.equals( "PD10003"))) {
    	return new ListGridRecord[]{ 
    			createDummyRecord("PS10001","1_casting", "สั่งเทียน", 3, pid, 1, 5.6),
    			createDummyRecord("PS10002","2_scrape", "ไม่ฝังพลอย", 3, pid, 2, 0.0),
    			createDummyRecord("PS10003","3_abrade", "ดิน-เงา ลงดำ", 3, pid, 3, 1.08),
    			createDummyRecord("PS10004","4_packing", "บรรจุรวม", 1, pid, 4, 0.12)
    	};
    } else if (pid != null && (pid.equals( "PD10004") || pid.equals( "PD10005") || pid.equals( "PD10006") || pid.equals( "PD10007"))) {
    	return new ListGridRecord[]{ 
    			createDummyRecord("PS20001","1_casting", "ใช้แบบเดิม", 3, pid, 1, 5.6),
    			createDummyRecord("PS20002","2_scrape", "ไม่ฝังพลอย", 3, pid, 2, 0.0),
    			createDummyRecord("PS20003","3_abrade", "ดิน-เงา ไม่ลงดำ", 3, pid, 3, 0.6),
    			createDummyRecord("PS20004","4_packing", "แยกบรรจุ", 1, pid, 4, 0.12)
    	};
    } else if (pid != null && (pid.equals( "PD10008") || pid.equals( "PD10009") || pid.equals( "PD10010") || pid.equals( "PD10011"))) {
    	return new ListGridRecord[]{ 
    			createDummyRecord("PS30001","1_casting", "ใช้แบบเดิม", 3, pid, 1, 5.6),
    			createDummyRecord("PS30002","2_scrape", "ไม่ฝังพลอย", 3, pid, 2, 0.0),
    			createDummyRecord("PS30003","3_abrade", "ดิน-เงา ไม่ลงดำ", 3, pid, 3, 0.6),
    			createDummyRecord("PS30004","4_packing", "แยกบรรจุ", 1, pid, 4, 0.12)
    	};
    }  else if (pid != null && (pid.equals( "PD10012") || pid.equals( "PD10013") || pid.equals( "PD10014") || pid.equals( "PD10015"))) {
    	return new ListGridRecord[]{ 
    			createDummyRecord("PS40001","1_casting", "สั่งเทียน", 3, pid, 1, 5.6),
    			createDummyRecord("PS40002","2_scrape", "ไม่ฝังพลอย", 3, pid, 2, 0.0),
    			createDummyRecord("PS40003","3_abrade", "ดิน-เงา ไม่ลงดำ", 3, pid, 3, 1.08),
    			createDummyRecord("PS40004","4_packing", "แยกบรรจุ", 1, pid, 4, 0.12)
    	};
    }
    	else return new ListGridRecord[]{};
    }
    
    public static ListGridRecord createDummyRecord(String id, String type, String desc, Integer std_time, String pid, Integer priority, Double weight) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("psid", id);
        record.setAttribute("type",type);  
        record.setAttribute("desc", desc);
        record.setAttribute("std_time", std_time);
        record.setAttribute("priority", priority);
        record.setAttribute("weight", weight);
        //record.setAttribute("mat_name", mat_name);
        return record;  
    }
}
