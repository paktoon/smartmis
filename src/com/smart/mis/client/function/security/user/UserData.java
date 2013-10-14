package com.smart.mis.client.function.security.user;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserData {

    public static ListGridRecord createRecord(String uid, String uname, String pwd, String title, String fname, String lname, String email, String position, String pname, boolean status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("uid", uid);
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
