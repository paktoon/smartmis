package com.smart.mis.client.function.purchasing.supplier;

import java.util.ArrayList;
import java.util.HashMap;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SupplierData {

    public static ListGridRecord createRecord(String id, String name, String phone1, String phone2, String email, String address, String fax, Integer leadtime, String[] itemList) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sid", id);
        record.setAttribute("sup_name", name);  
        record.setAttribute("sup_phone1", phone1);
        record.setAttribute("sup_phone2", phone2);
        record.setAttribute("email", email);  
        record.setAttribute("address", address);
        record.setAttribute("fax", fax);  
        record.setAttribute("leadtime", leadtime);
        record.setAttribute("list", itemList);
        return record;  
    }
    
    public static ListGridRecord createRecord(String id, String name) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sid", id);
        record.setAttribute("sup_name", name);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("SU10001","บริษัท 99GOLDS","022588888", "0892222244", "webmaster@woeldmark.co.th", "392/21 สุขุมวิท 20 แขวงคลองเตย เขตคลองเตย กทม 10110", "023456124", 7, new String[] {"MA10001","MA10002"}),
    			createRecord("SU10002","ห้างทอง น่ำเชียง","0894774443", "0894774443", "tamrong@hotmail.com", "56/12-6 อาคารบิสโก้ ซอยทรัพย์ ถนนทรัพย์ แขวงสี่พระยา เขตบางรัก กทม 10500", "023456124", 7, new String[] {"MA10001","MA10002"}),
    			createRecord("SU10003","ร้าน วีวี่เจมส์","0815582721", "0815582122", "taechiraya@gmail.com", "90/156 หมู่ 14 บางกรวย-ไทรน้อย ตำบลบางบัวทอง อำเภอบางบัวทอง นนทบุรี 11110", "023456124", 15, new String[] {"MA20001","MA20002","MA20003"}),
    			createRecord("SU10004","มายด์ แอนด์ ลีน่าเครื่องประดับ","074732040", "0899777702", "mljewelry_stones@hotmail.com", "2 ซอยบรรจบ สตูลธานี ตำบลพิมาน อำเภอเมืองสตูล สตูล 91000", "023456124", 15, new String[] {"MA20001","MA20002","MA20003"}),
    			createRecord("SU10005","บริษัท เครื่องประดับอิรอส จำกัด","027495044", "027495044", "", "67/23 หมู่ 12 ซอยอุดมสุข 23 สุขุมวิท 103 แขวงบางนา เขตบางนา กทม 10260", "023456124", 3 , new String[] {"MA20001","MA20002","MA20003"}),
    	};
    }
    
    public static ListGridRecord[] getNewRecords(String[] sidList) {
    	HashMap<String,String> supList = new HashMap<String,String>();
    	supList.put("SU10001", "บริษัท 99GOLDS");
    	supList.put("SU10002", "ห้างทอง น่ำเชียง");
    	supList.put("SU10003", "ร้าน วีวี่เจมส์");
    	supList.put("SU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ");
    	supList.put("SU10005", "บริษัท เครื่องประดับอิรอส จำกัด");
    	
    	ArrayList<ListGridRecord> list = new ArrayList<ListGridRecord>();
    	if (sidList != null) {
	    	for (String sid : sidList){
	    		String value = supList.get(sid);
	    		if (value != null) list.add(createRecord(sid, value));
	    	}
    	}
    	return list.toArray(new ListGridRecord[0]);
    }
}
