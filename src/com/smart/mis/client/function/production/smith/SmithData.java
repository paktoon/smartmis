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
    			createRecord("SM10002","อุดมพร แสงคำ","(02) 258-7777", "", "test_2@smith.co.th", "453", "สุขุมวิท 46","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์"),
    			createRecord("SM10003","โรงหล่อ บุษราคัม จิวเวอรี่","(02) 454-4963", "(081) 843-3075", "test_3@smith.co.th", "777/123", "พระรามสี่","คลองเตย", "กรุงเทพ",10110 , "หล่อขึ้นรูป"),
    			
    			createRecord("SM10004","มณี แสงดาว","(02) 258-9999", "", "test_4@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "แต่งและฝังพลอยประดับ"),
    			createRecord("SM10005","ไพลิน มณีจันทร์","(02) 258-2222", "", "test_5@smith.co.th", "532", "สุขุมวิท 101","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์"),
    			createRecord("SM10006","โรงหล่อ พรหมรังสี","(02) 454-4964", "(081) 843-3076", "test_6@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ",10110 , "หล่อขึ้นรูป"),
    			
    			createRecord("SM10007","มุข พินิจ","(02) 258-4444", "", "test_7@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "แต่งและฝังพลอยประดับ"),
    			createRecord("SM10008","นัท หมูทอง","(02) 258-5555", "", "test_8@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์"),
    			createRecord("SM10009","โรงหล่อ ศิลป์เดชา","(02) 454-4965", "(081) 843-3077", "test_9@smith.co.th", "16/6", "","ปากเกล็ด", "กรุงเทพ",11120 , "หล่อขึ้นรูป")
    	};
    }
}
