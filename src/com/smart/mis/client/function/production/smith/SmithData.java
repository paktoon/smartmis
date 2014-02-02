package com.smart.mis.client.function.production.smith;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SmithData {

    public static ListGridRecord createRecord(String smid, String name, String phone1, String phone2, String email, String address, String street, String city, String state, Integer postal, String type) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("smid", smid);
        record.setAttribute("name",name);  
        record.setAttribute("phone1", phone1);
        record.setAttribute("phone2", phone2);
        record.setAttribute("email", email);  
        record.setAttribute("address", address); 
        record.setAttribute("street", street); 
        record.setAttribute("city", city); 
        record.setAttribute("state", state); 
        record.setAttribute("postal", postal); 
        record.setAttribute("type", type);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("SM10001","จำนงค์ คำเงิน","(02) 258-8888", "", "test_1@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "แต่งและฝังพลอยประดับ"),
    			createRecord("SM10002","อุดมพร แสงคำ","(02) 258-8888", "", "test_2@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์"),
    			createRecord("SM10003","โรงหล่อ บุษราคัม จิวเวอรี่","(02) 454-4963", "(081) 843-3075", "test_3@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ",10110 , "หล่อขึ้นรูป")
    	};
    }
}
