package com.smart.mis.client.function.sale.customer;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CustomerData {

    public static ListGridRecord createRecord(String cid, String cus_name, String cus_phone, String contact_name, String contact_phone, String contact_email, String address, String cus_type) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("cid", cid);
        record.setAttribute("cus_name",cus_name);  
        record.setAttribute("cus_phone", cus_phone);
        record.setAttribute("contact_name", contact_name);  
        record.setAttribute("contact_phone", contact_phone); 
        record.setAttribute("contact_email", contact_email);  
        record.setAttribute("address", address);   
        record.setAttribute("cus_type", cus_type);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("CU10001","aaaaaaaa","01010101010", "bbbbbbbb", "02020202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0<br>cvdvervvxv<br>vfvder<br>cvdr", "ลูกค้าประจำ"),
    			createRecord("CU10002","xxxxxxx","01010101010", "ggggggg", "02020202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0<br>dcvsev edfvergds<br>cvergergcv", "ลูกค้าทั่วไป"),
    			createRecord("CU10003","ffffffff","01010101010", "bbbbbbbb", "0202256430202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าประจำ"),
    			createRecord("CU10004","aaaaaaaa","44444444", "bbbbbbbb", "25642356", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าประจำ"),
    			createRecord("CU10005","aaahhhhaaaaa","01010101010", "bbbbbbbb", "25623456", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าทั่วไป"),
    			createRecord("CU10006","aaaaaaaa","66666666", "eeeeeee", "2374562456", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าประจำ"),
    			createRecord("CU10007","aaaaaaaa","01010101010", "hhhhhh", "2344656", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าประจำ"),
    			createRecord("CU10008","aaaaaaaa","01010101010", "bbbbbbbb", "02020202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าทั่วไป"),
    			createRecord("CU10009","88cxvsdrg","01010101010", "bbbbbbbb", "02020202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าทั่วไป"),
    			createRecord("CU10010","aaadsvsaaaaa","01010101010", "ccccccccc", "02020202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าประจำ"),
    			createRecord("CU10011","aaasdverfaaaaa","01010101010", "bbbbbbbb", "02020202020", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าประจำ"),
    			createRecord("CU10012","aaaaaaaa","77777777", "bbbbbbbb", "7453437", "aaaaaa@bbbbb", "jwiojfwijdfveiwjfviwjrf0iojsfigvjd0ojfgdinfggodijfg0", "ลูกค้าทั่วไป")
    	};
    }
}
