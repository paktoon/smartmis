package com.smart.mis.client.function.purchasing.material;

import java.util.ArrayList;
import java.util.HashMap;

import com.smart.mis.shared.prodution.ProductType;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialData {

    //public static ListGridRecord createRecord(String mid, String mat_name, String desc, String type, Double safety, Double remain, String unit, String sup_list) {  
    public static ListGridRecord createRecord(String mid, String mat_name, String desc, String type, String unit, Double weight, Double safety, Double inStock, Double reserved) {   	    
    	ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",mat_name);  
        record.setAttribute("desc", desc);
        record.setAttribute("type", type); // แร่เงิน, แมกกาไซต์, พลอยประดับ, อื่นๆ
        record.setAttribute("unit", unit);
        
        record.setAttribute("weight", weight);
        //record.setAttribute("sup_list", sup_list);
        
        record.setAttribute("safety", safety);
        //record.setAttribute("remain", remain);
        record.setAttribute("inStock", inStock);
        record.setAttribute("reserved", reserved);
        record.setAttribute("remain", inStock - reserved);
        
        return record;  
    }
    
    public static ListGridRecord createUpdatedRecord(String mid, String mat_name, String desc, String type, String unit, Double weight, Double safety) {  
    	ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",mat_name);  
        record.setAttribute("desc", desc);
        record.setAttribute("type", type); // แร่เงิน, แมกกาไซต์, พลอยประดับ, อื่นๆ
        record.setAttribute("unit", unit);
        
        record.setAttribute("weight", weight);
        //record.setAttribute("sup_list", sup_list);
        
        record.setAttribute("safety", safety);
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
    			createRecord("MA10001","แร่เงิน 100%","แร่เงิน บริสุทธิ์ 100%", "แร่เงิน", "กรัม", null, 20000.0, 28302.0, 5000.0), //required
    			createRecord("MA10002","แร่เงิน 92.5%","แร่เงิน 92.5%", "แร่เงิน", "กรัม", null, 20000.0, 78392.0, 5000.0), //required
    			createRecord("MA20001","แมกกาไซต์ PP6","แมกกาไซต์  เบอร์ 6", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 25700.0, 2000.0),
    			createRecord("MA20002","แมกกาไซต์ PP7","แมกกาไซต์  เบอร์ 7", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 20000.0, 1800.0),
    			createRecord("MA20003","แมกกาไซต์ PP8","แมกกาไซต์  เบอร์ 8", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 25000.0, 7200.0),
    			createRecord("MA20004","แมกกาไซต์ PP12","แมกกาไซต์  เบอร์ 12", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 12500.0, 1000.0),
    			
    			createRecord("MA20005","ถุงพลาสติก 6x6","ถุงพลาสติก ขนาด 6x6 ซม.", "อื่นๆ", "ถุง", 0.12, 1000.0, 20000.0, 3000.0),
    			createRecord("MA20006","ถุงพลาสติก 7x7","ถุงพลาสติก ขนาด 7x7 ซม.", "อื่นๆ", "ถุง", 0.14, 1000.0, 10000.0, 3000.0),
    			createRecord("MA20007","บุษราคัม","Yellow Sapphire", "พลอยประดับ", "เม็ด", 0.12, 0.0, 80.0, 0.0),
    			createRecord("MA20008","โทแพซสีเหลือง","Yellow Topaz", "พลอยประดับ", "เม็ด", 0.12, 0.0, 100.0, 0.0),
    			createRecord("MA20009","โอปอล","Opal", "พลอยประดับ", "เม็ด", 0.12, 0.0, 120.0, 0.0),
    			createRecord("MA20010","เพทายสีเหลือง","Yellow Zircon", "พลอยประดับ", "เม็ด", 0.12, 0.0, 0.0, 0.0),
    			createRecord("MA20011","อำพัน","Amber", "พลอยประดับ", "เม็ด", 0.12, 0.0, 0.0, 0.0),
    			createRecord("MA20012","หยกสีเหลือง","Yellow Jade", "พลอยประดับ", "เม็ด", 0.12, 0.0, 0.0, 0.0)
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
