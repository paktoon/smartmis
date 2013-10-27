package com.smart.mis.client.function.production.smith;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SmithData {

    public static ListGridRecord createRecord(String smid, String name, String phone1, String phone2, String email, String address) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("smid", smid);
        record.setAttribute("name",name);  
        record.setAttribute("phone1", phone1);
        record.setAttribute("phone2", phone2);
        record.setAttribute("email", email);  
        record.setAttribute("address", address); 
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("SM10001","จำนงค์ คำเงิน","022588888", "", "test_1@smith.co.th", "392/21 สุขุมวิท 20 แขวงคลองเตย เขตคลองเตย กทม 10110"),
    			createRecord("SM10002","อุดมพร แสงคำ","022588888", "", "test_2@smith.co.th", "392/21 สุขุมวิท 20 แขวงคลองเตย เขตคลองเตย กทม 10110"),
    			createRecord("SM10003","โรงหล่อ บุษราคัม จิวเวอรี่","024544963", "0818433075", "test_3@smith.co.th", "392/21 สุขุมวิท 20 แขวงคลองเตย เขตคลองเตย กทม 10110")
    	};
    }
}
