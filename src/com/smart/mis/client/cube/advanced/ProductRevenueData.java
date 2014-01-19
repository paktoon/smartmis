package com.smart.mis.client.cube.advanced;

import java.util.ArrayList;
import java.util.Date;

import com.smart.mis.client.cube.SaleVolumn;
import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductRevenueData {
	
	public static ListGridRecord createRecord(String cellID, String time, String scenario, String region, String product, Integer value) {
		ListGridRecord record = new ListGridRecord();

        record.setAttribute("cellID", cellID);
        record.setAttribute("Time", time);
		record.setAttribute("Scenarios", scenario);
        
        record.setAttribute("Regions", region);
        record.setAttribute("Products", product);
        record.setAttribute("value", value);
        
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {

    	ArrayList<ListGridRecord> genData = new ArrayList<ListGridRecord>();
    	Integer i = 1;
        for (String time : times) {
			for (String region : regions) {
				for (String product : products) {
					for (String scenario : scenarios) {
						genData.add(createRecord(i.toString(), time, scenario, region, product, (int) Math.round(Math.random() * 1000)));
						i++;
					}
				}
			}
		}
        
        System.out.println("GenDate List = " +genData.size());
    		
//    	return new ListGridRecord[]{
//    			createRecord("101","1/1/2002", "Budget", "North", "Prod01", 5000)
//    	};
        return genData.toArray(new ListGridRecord[]{});
    }
   
    private static String[] scenarios  = new String[] {
    	"Actual", "Budget"
    };
    
    private static String[] times  = new String[] {
    	"sum",
    	
    	"2011",
        "2012",
        "2013",
        
    	"Q1-2011", 
        "Q2-2011", 
        "Q3-2011", 
        "Q4-2011",  
        "Q1-2012", 
        "Q2-2012", 
        "Q3-2012", 
        "Q4-2012", 
        "Q1-2013", 
        "Q2-2013",
        "Q3-2013",
        "Q4-2013",
        
		"1/1/2011",
        "2/1/2011",  
        "3/1/2011",  
        "4/1/2011", 
        "5/1/2011",  
        "6/1/2011",
        "7/1/2011", 
        "8/1/2011", 
        "9/1/2011", 
        "10/1/2011",  
        "11/1/2011",  
        "12/1/2011",
        "1/1/2012",
        "2/1/2012",
        "3/1/2012",
        "4/1/2012", 
        "5/1/2012", 
        "6/1/2012", 
        "7/1/2012",
        "8/1/2012",  
        "9/1/2012", 
        "10/1/2012", 
        "11/1/2012",  
        "12/1/2012",
        "1/1/2013",
        "2/1/2013",
        "3/1/2013",
        "4/1/2013", 
        "5/1/2013", 
        "6/1/2013",  
        "7/1/2013", 
        "8/1/2013", 
        "9/1/2013", 
        "10/1/2013", 
        "11/1/2013", 
        "12/1/2013"
    };
    
    private static String[] regions  = new String[] {
    	"sum", "North","South","East", "West"
    };
    
	private static String[] products = new String[] {
		"sum",
		
		"ProdFamily1",
		"ProdFamily2",
		"ProdFamily3",
		"ProdFamily4",
		
        "ProdGroup1",
        "ProdGroup2", 
        "ProdGroup3", 
        "ProdGroup4", 
        "ProdGroup5", 
        "ProdGroup6"
	};
    
}
