package com.smart.mis.shared.prodution;

import java.util.LinkedHashMap;

public class ProcessType {
	static LinkedHashMap<String, String> valueMap;
	static LinkedHashMap<String, Integer> priorityMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_casting", "หล่อขึ้นรูป");  
        valueMap.put("2_scrape", "แต่งและฝังพลอยประดับ");  
        valueMap.put("3_abrade", "ขัดและติดพลอยแมกกาไซต์");  
        valueMap.put("4_packing", "บรรจุหีบห่อ");
        
        priorityMap = new LinkedHashMap<String, Integer>();  
        priorityMap.put("1_casting", 1);  
        priorityMap.put("2_scrape", 2);  
        priorityMap.put("3_abrade", 3);  
        priorityMap.put("4_packing", 4);
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
	
	public static Integer getPriority(String value) {
        return priorityMap.get(value);
	}
}
