package com.smart.mis.shared;

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateTimeMapping {
	static LinkedHashMap<String, String> monthMap;
	
	static {
		monthMap = new LinkedHashMap<String, String>();  
		monthMap.put("1", "มกราคม");  
		monthMap.put("2", "กุมภาพันธ์"); 
        monthMap.put("3", "มีนาคม");  
        monthMap.put("4", "เมษายน"); 
        monthMap.put("5", "พฤษภาคม");  
        monthMap.put("6", "มิถุนายน"); 
        monthMap.put("7", "กรกฎาคม");  
        monthMap.put("8", "สิงหาคม"); 
        monthMap.put("9", "กันยายน");  
        monthMap.put("10", "ตุลาคม"); 
        monthMap.put("11", "พฤศจิกายน");  
        monthMap.put("12", "ธันวาคม");
	}
	
	public static LinkedHashMap<String, String> getMonthValueMap() {
        return monthMap;
	}
	
	public static LinkedHashMap<String, String> getDateValueMap(int num) {
		LinkedHashMap<String, String> dateMap = new LinkedHashMap<String, String>();  
		for (int i = 1; i <= num; i++){
			dateMap.put(Integer.toString(i), Integer.toString(i)); 
		}
        return dateMap;
	}
	
	public static String getDisplay(String value) {
        return monthMap.get(value);
	}
	
	public static String getYearPast(String year, int num) {
		int current = Integer.parseInt(year);
		int past = current - num;
		return Integer.toString(past);
	}
	
	public static int getNumDay(String in_month, String in_year){
		int month = Integer.parseInt(in_month);
		int year = Integer.parseInt(in_year) - 543;
		
      switch (month) {
    	case 1: return 31;
    	case 2: {
    		if ((year%4 == 0) && (year%100 != 0) && (year%400 != 0))
    			return 29;
    		else return 28;
    		} 
    	case 3: return 31;
    	case 4: return 30;
    	case 5: return 31;
    	case 6: return 30;
    	case 7: return 31;
    	case 8: return 31;
    	case 9: return 30;
    	case 10: return 31;
    	case 11: return 30;
    	case 12: return 31;
    	default : return 0;
    }
	}
	
	public static String getRealYear(String thai_year){
		int year = Integer.parseInt(thai_year);
		return Integer.toString(year - 543);
	}
	
	public static Date getDate(String text){
		DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy");
		Date parsed = fmt.parse(text);
		return parsed;
	}
}
