package com.smart.mis.shared.prodution;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.smart.mis.client.function.production.process.ProcessListDS;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smartgwt.client.data.Record;

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
	
	public static Integer getMaxStdTime(ArrayList<String> pids, String value) {
		Integer max_std_time = 0;
		System.out.println("Getting max std_time for process " + value);
		for (String pid : pids){
			System.out.println("	pid " + pid);
			ProcessListDS.getInstance(pid).refreshData();
			Record[] processList = ProcessListDS.getInstance(pid).getCacheData();
			for (Record process : processList) {
				String type = process.getAttributeAsString("type");
				System.out.println("	type " + type);
				if (type.equalsIgnoreCase(value)) {
					Integer std_time = process.getAttributeAsInt("std_time");
					System.out.println("	std_time " + std_time);
					if (std_time > max_std_time) {
						max_std_time = std_time;
					}
				} else System.out.println("		skipped");
			}
		}
		
		return max_std_time;
	}
}
