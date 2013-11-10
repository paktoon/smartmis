package com.smart.mis.shared.prodution;

import java.util.LinkedHashMap;

public class ProductType {
	static LinkedHashMap<String, String> valueMap, engValueMap, unitValueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("ring", "แหวนนิ้วมือ");  
        valueMap.put("toe ring", "แหวนนิ้วเท้า");  
        valueMap.put("earring", "ต่างหู");  
        valueMap.put("necklace", "สร้อยคอ");
        valueMap.put("pendant", "จี้");
        valueMap.put("bracelet", "กำไลข้อมือ");
        valueMap.put("anklet", "กำไลข้อเท้า");
        valueMap.put("bangle", "สร้อยข้อเท้าหรือข้อมือ");
        
		engValueMap = new LinkedHashMap<String, String>();  
		engValueMap.put("ring", "Ring");  
		engValueMap.put("toe ring", "Toe Ring");  
		engValueMap.put("earring", "Earring");  
		engValueMap.put("necklace", "Necklace");
		engValueMap.put("pendant", "Pendant");
		engValueMap.put("bracelet", "Bracelet");
		engValueMap.put("anklet", "Anklet");
		engValueMap.put("bangle", "Bangle");
		
		unitValueMap = new LinkedHashMap<String, String>();  
		unitValueMap.put("ring", "วง");  
		unitValueMap.put("toe ring", "วง");  
		unitValueMap.put("earring", "คู่");  
		unitValueMap.put("necklace", "เส้น");
		unitValueMap.put("pendant", "ชิ้น");
		unitValueMap.put("bracelet", "วง");
		unitValueMap.put("anklet", "วง");
		unitValueMap.put("bangle", "เส้น");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static LinkedHashMap<String, String> getEngValueMap() {
        return engValueMap;
	}
	
	public static String getDisplay(String type) {
        return valueMap.get(type);
	}
	
	public static String getUnit(String type) {
        return unitValueMap.get(type);
	}
	
	public static String getEngDisplay(String type) {
        return engValueMap.get(type);
	}
	
	public static String getEngUnit(String type) {
        return "ea";
	}
	
}
