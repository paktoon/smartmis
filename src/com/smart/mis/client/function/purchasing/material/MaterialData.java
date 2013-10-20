package com.smart.mis.client.function.purchasing.material;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialData {

    public static ListGridRecord createRecord(String mid, String mat_name, String desc, String type, Double safety, Double remain, String unit, String sup_list) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("mid", mid);
        record.setAttribute("mat_name",mat_name);  
        record.setAttribute("desc", desc);
        record.setAttribute("type", type); // แร่เงิน, แมกกาไซต์, พลอยประดับ
        record.setAttribute("safety", safety);
        record.setAttribute("remain", remain);
        record.setAttribute("unit", unit);
        record.setAttribute("sup_list", sup_list);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("MA10001","แร่เงิน 100%","แร่เงิน บริสุทธิ์ 100%", "แร่เงิน", 100000.0, 178392.9, "กรัม", "SU10001|SU10002"),
    			createRecord("MA10002","แร่เงิน 92.5%","แร่เงิน 92.5%", "แร่เงิน", 100000.0, 154326.6, "กรัม", "SU10001|SU10002"),
    			createRecord("MA20001","แมกกาไซต์ PP6","แมกกาไซต์  เบอร์ 6", "แมกกาไซต์", 100000.0, 67444.0, "เม็ด", "SU10003|SU10004|SU10005"),
    			createRecord("MA20002","แมกกาไซต์ PP7","แมกกาไซต์  เบอร์ 7", "แมกกาไซต์", 100000.0, 123213.0, "เม็ด", "SU10003|SU10004|SU10005"),
    			createRecord("MA20003","แมกกาไซต์ PP8","แมกกาไซต์  เบอร์ 8", "แมกกาไซต์", 100000.0, 123213.0, "เม็ด", "SU10003|SU10004|SU10005"),
    			createRecord("MA20004","แมกกาไซต์ PP12","แมกกาไซต์  เบอร์ 12", "แมกกาไซต์", 100000.0, 123213.0, "เม็ด", "SU10003|SU10004|SU10005")
    		};
    }
}
