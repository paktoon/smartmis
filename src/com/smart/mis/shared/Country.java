package com.smart.mis.shared;

import java.util.LinkedHashMap;

public class Country {
	
	static LinkedHashMap<String, String> countryMap;
	static LinkedHashMap<String, String> valueMap;
	static LinkedHashMap<String, String> valueIcons;
	
	static {
		countryMap  = new LinkedHashMap<String, String>(); 
		countryMap.put("TH", "เอเซีย");
		countryMap.put("US", "อเมริกาเหนือ");  
		countryMap.put("CH", "เอเซีย");  
		countryMap.put("JA", "เอเซีย");  
		countryMap.put("IN", "เอเซีย");  
		countryMap.put("GM", "ยุโรป");  
		countryMap.put("FR", "ยุโรป");  
		countryMap.put("IT", "ยุโรป");  
		countryMap.put("RS", "ยุโรป");  
		countryMap.put("BR", "อเมริกาใต้");  
		countryMap.put("CA", "อเมริกาเหนือ");  
		countryMap.put("MX", "อเมริกาใต้");  
		countryMap.put("SP", "ยุโรป");   
		countryMap.put("TC", "เอเซีย"); 
        
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("TH", "ไทย");  
        valueMap.put("US", "อเมริกา");  
        valueMap.put("CH", "จีน");  
        valueMap.put("JA", "ญี่ปุ่น");  
        valueMap.put("IN", "อินเดีย");  
        valueMap.put("GM", "เยอรมัน");  
        valueMap.put("FR", "ฝรั่งเศส");  
        valueMap.put("IT", "อิตาลี");  
        valueMap.put("RS", "รัสเซีย");  
        valueMap.put("BR", "บราซิล");  
        valueMap.put("CA", "แคนนาดา");  
        valueMap.put("MX", "เม็กซิโก");  
        valueMap.put("SP", "สเปน");  
        valueMap.put("TC", "สหรัฐอาหรับเอมิเรตส์");
        
        valueIcons = new LinkedHashMap<String, String>();
        valueIcons.put("TH", "TH");
        valueIcons.put("US", "US");  
        valueIcons.put("CH", "CH");  
        valueIcons.put("JA", "JA");  
        valueIcons.put("IN", "IN");  
        valueIcons.put("GM", "GM");  
        valueIcons.put("FR", "FR");  
        valueIcons.put("IT", "IT");  
        valueIcons.put("RS", "RS");  
        valueIcons.put("BR", "BR");  
        valueIcons.put("CA", "CA");  
        valueIcons.put("MX", "MX");  
        valueIcons.put("SP", "SP"); 
        valueIcons.put("TC", "TC");
	}
	
	public static String getContinient(String contryName) {
		return countryMap.get(contryName);
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static LinkedHashMap<String, String> getValueIcons() {
        return valueIcons;
	}
}
