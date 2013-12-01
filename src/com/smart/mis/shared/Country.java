package com.smart.mis.shared;

import java.util.LinkedHashMap;

public class Country {
	
	static LinkedHashMap<String, String> countryMap;
	static LinkedHashMap<String, String> valueIcons;
	static LinkedHashMap<String, String> nameThaiMap;
	static LinkedHashMap<String, String> nameEnglistMap;
	
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
        
		nameThaiMap = new LinkedHashMap<String, String>();  
		nameThaiMap.put("TH", "ไทย");  
		nameThaiMap.put("US", "อเมริกา");  
		nameThaiMap.put("CH", "จีน");  
		nameThaiMap.put("JA", "ญี่ปุ่น");  
		nameThaiMap.put("IN", "อินเดีย");  
		nameThaiMap.put("GM", "เยอรมัน");  
		nameThaiMap.put("FR", "ฝรั่งเศส");  
		nameThaiMap.put("IT", "อิตาลี");  
		nameThaiMap.put("RS", "รัสเซีย");  
		nameThaiMap.put("BR", "บราซิล");  
		nameThaiMap.put("CA", "แคนนาดา");  
		nameThaiMap.put("MX", "เม็กซิโก");  
		nameThaiMap.put("SP", "สเปน");  
		nameThaiMap.put("TC", "สหรัฐอาหรับเอมิเรตส์");
        
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
        
        nameEnglistMap = new LinkedHashMap<String, String>();  
        nameEnglistMap.put("TH", "Thai");  
        nameEnglistMap.put("US", "United State of America");  
        nameEnglistMap.put("CH", "China");  
        nameEnglistMap.put("JA", "Japan");  
        nameEnglistMap.put("IN", "India");  
        nameEnglistMap.put("GM", "Germany");  
        nameEnglistMap.put("FR", "France");  
        nameEnglistMap.put("IT", "Italy");  
        nameEnglistMap.put("RS", "Russia");  
        nameEnglistMap.put("BR", "Brazil");  
        nameEnglistMap.put("CA", "Canada");  
        nameEnglistMap.put("MX", "Mexico");  
        nameEnglistMap.put("SP", "Spain");  
        nameEnglistMap.put("TC", "United Arab Emirates");
	}
	
	public static String getContinient(String contryName) {
		return countryMap.get(contryName);
	}
	
	public static String getThaiCountryName(String contryName) {
		return nameThaiMap.get(contryName);
	}
	
	public static String getEnglishCountryName(String contryName) {
		return nameEnglistMap.get(contryName);
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return nameThaiMap;
	}
	
	public static LinkedHashMap<String, String> getValueIcons() {
        return valueIcons;
	}
}
