package com.smart.mis.client.function.security;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserData {
	
	//private static String YES = "checked.png";
	//private static String NO = "close.png";
	
//	private static ListGridRecord[] records;    
//    
//    public static ListGridRecord[] getRecords() {  
//        if (records == null) {  
//            records = getNewRecords();    
//        }    
//        return records;    
//    }   

    public static ListGridRecord createRecord(String uname, String pwd, String title, String fname, String lname, String email, String position, String pname, boolean status) {  
        ListGridRecord record = new ListGridRecord(); 
        record.setAttribute("uname", uname);  
        record.setAttribute("pwd", pwd); 
        record.setAttribute("title", title); 
        record.setAttribute("fname", fname); 
        record.setAttribute("lname", lname);  
        record.setAttribute("email", email);   
        record.setAttribute("position", position);
        record.setAttribute("pname", pname);
        record.setAttribute("status", status);
        return record;  
    }
}
