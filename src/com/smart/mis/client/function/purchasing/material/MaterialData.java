package com.smart.mis.client.function.purchasing.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialData {

    //public static ListGridRecord createRecord(String mid, String mat_name, String desc, String type, Double safety, Double remain, String unit, String sup_list) {  
    public static ListGridRecord createRecord(String mid, String mat_name, String desc, String type, Double safety, Double remain, String unit, Double weight) {   	    
    	ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",mat_name);  
        record.setAttribute("desc", desc);
        record.setAttribute("type", type); // แร่เงิน, แมกกาไซต์, พลอยประดับ, อื่นๆ
        record.setAttribute("safety", safety);
        record.setAttribute("remain", remain);
        record.setAttribute("unit", unit);
        
        record.setAttribute("weight", weight);
        //record.setAttribute("sup_list", sup_list);
        return record;  
    }
    
    public static ListGridRecord createRecord(String mid, String mat_name) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",mat_name); 
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("MA10001","แร่เงิน 100%","แร่เงิน บริสุทธิ์ 100%", "แร่เงิน", 100000.0, 178392.9, "กรัม", null),
    			createRecord("MA10002","แร่เงิน 92.5%","แร่เงิน 92.5%", "แร่เงิน", 100000.0, 154326.6, "กรัม", null),
    			createRecord("MA20001","แมกกาไซต์ PP6","แมกกาไซต์  เบอร์ 6", "แมกกาไซต์", 100000.0, 67444.0, "เม็ด", 0.12),
    			createRecord("MA20002","แมกกาไซต์ PP7","แมกกาไซต์  เบอร์ 7", "แมกกาไซต์", 100000.0, 123213.0, "เม็ด", 0.12),
    			createRecord("MA20003","แมกกาไซต์ PP8","แมกกาไซต์  เบอร์ 8", "แมกกาไซต์", 100000.0, 123213.0, "เม็ด", 0.12),
    			createRecord("MA20004","แมกกาไซต์ PP12","แมกกาไซต์  เบอร์ 12", "แมกกาไซต์", 100000.0, 123213.0, "เม็ด", 0.12),
    			createRecord("MA20005","ถุงพลาสติก 6x6","ถุงพลาสติก ขนาด 6x6 ซม.", "อื่นๆ", 100000.0, 123213.0, "ถุง", 0.12)
    	};
    }
    
    public static ListGridRecord[] getNewRecords(String[] midList) {
    	HashMap<String,String> matList = new HashMap<String,String>();
    	matList.put("MA10001", "แร่เงิน 100%");
    	matList.put("MA10002", "แร่เงิน 92.5%");
    	matList.put("MA20001", "แมกกาไซต์ PP6");
    	matList.put("MA20002", "แมกกาไซต์ PP7");
    	matList.put("MA20003", "แมกกาไซต์ PP8");
    	matList.put("MA20004", "แมกกาไซต์ PP12");
    	
    	ArrayList<ListGridRecord> list = new ArrayList<ListGridRecord>();
    	if (midList != null) {
	    	for (String mid : midList){
	    		String value = matList.get(mid);
	    		if (value != null) list.add(createRecord(mid, value));
	    	}
    	}
    	return list.toArray(new ListGridRecord[0]);
    }
}
