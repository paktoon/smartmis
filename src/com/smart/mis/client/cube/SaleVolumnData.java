package com.smart.mis.client.cube;

import java.util.ArrayList;

import com.smartgwt.client.widgets.cube.FacetValue;

public class SaleVolumnData {
  
    public static SaleVolumn[] getMonthlyData(String year) {
        
        ArrayList<SaleVolumn> dataGen = new ArrayList<SaleVolumn>();
        Integer i = 1;
        for (String month : months) {
			for (String customer : customers) {
				for (String product : products) {
					for (String type : types) {
						//time, zone, product, matric, value
						dataGen.add(new SaleVolumn(i.toString(), year, 2, month, customer, product, type, (int) Math.round(Math.random() * 10000)));
						i++;
					}
				}
			}
		}
        
        return dataGen.toArray(new SaleVolumn[]{}); 
    }  
    
    public static SaleVolumn[] getQuarterlyData(String year) {
        
        ArrayList<SaleVolumn> dataGen = new ArrayList<SaleVolumn>();
        Integer i = 1;
        for (String quater : quaters) {
			for (String customer : customers) {
				for (String product : products) {
					for (String type : types) {
						//time, zone, product, matric, value
						dataGen.add(new SaleVolumn(i.toString(), year, 1, quater, customer, product, type, (int) Math.round(Math.random() * 10000)));
						i++;
					}
				}
			}
		}
        
        return dataGen.toArray(new SaleVolumn[]{}); 
    }  
    
    public static SaleVolumn[] getYearlyData() {
        ArrayList<SaleVolumn> dataGen = new ArrayList<SaleVolumn>();
        Integer i = 1;
        
        for (String year : years) {
				for (String customer : customers) {
					for (String product : products) {
						for (String type : types) {
							//time, zone, product, matric, value
							dataGen.add(new SaleVolumn(i.toString(), year, customer, product, type, (int) Math.round(Math.random() * 10000)));
							i++;
						}
					}
				}
		}
        
        return dataGen.toArray(new SaleVolumn[]{}); 
    }
    
    
    private static String[] types  = new String[] {
    	"Volumn", "Amount"
    };
    
    private static String[] years = new String[] {
    	"2011", "2012", "2013"
    };
    
    private static String[] quaters = new String[] {
    	"Q1", "Q2", "Q3", "Q4"
    };
    
	private static String[] months = new String[] {
		"January",
        "February",  
        "March",
		"April",
        "May",  
        "June",
		"July",
        "August",  
        "September",
		"October",
        "November",  
        "December"
	};
	
    private static String[] zones = new String[] {
		"Asia",
        "Europe",
        "North America"
	};
	
	private static String[] customers = new String[] { 
		"Retails",
		"Wholesales"
	};
	
	private static String[] products = new String[] { 
        "Rings",
        "Earrings", 
        "Necklaces", 
        "Toe Ring", 
        "Pendants", 
        "Bracelets", 
        "Anklets", 
        "Bangles"
	};
} 
