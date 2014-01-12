package com.smart.mis.client.function.report.inventory;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductRequestReportData {
	
    public static ListGridRecord createRecord(String sub_delivery_id, String delivery_id, String pid, String name, Double sale_weight, Double price, String type, String unit, Integer sale_amount, Boolean status, Double issued_weight, Integer issued_amount, Date issued_date) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_delivery_id", sub_delivery_id);
        record.setAttribute("delivery_id", delivery_id);
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("status", status);
        
        record.setAttribute("sale_weight", sale_weight);
        record.setAttribute("sale_amount", sale_amount);
        
        record.setAttribute("issued_weight", issued_weight);
        record.setAttribute("issued_amount", issued_amount);

        record.setAttribute("issued_date", issued_date);
        return record;  
    }
    	
    public static ListGridRecord[] getNewRecords() {
    	ArrayList<ListGridRecord> dummyReport = new ArrayList<ListGridRecord>();
    	DateRange dateRange = new DateRange();
        dateRange.setRelativeStartDate(RelativeDate.TODAY);
        //dateRange.setRelativeStartDate(new RelativeDate("-1m"));
    	for (int i = 1; i <= 15 ; i++) {
    		dateRange.setRelativeEndDate(new RelativeDate("-"+ (i * 4) +"d"));
    		Date date = dateRange.getEndDate();
    		
    		dummyReport.add(createRecord("PRR10"+i , "DL20"+i, "PD10001", "Diamond cut silver ring", 476.0, 55.0, "ring","วง", 70, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100) +100, date));
    		dummyReport.add(createRecord("PRR20"+i , "DL23"+i, "PD10007", "Plain silver necklaces", 632.0, 50.0, "earring","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));

    		dummyReport.add(createRecord("PRR30"+i , "DL23"+i, "PD10006","Silver necklace with star pendant", 632.0, 50.0, "necklace","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PRR40"+i , "DL23"+i, "PD10008","Diamond cut toe ring", 632.0, 50.0, "toe ring","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PRR50"+i , "DL23"+i, "PD10011","Spiral pendant", 632.0, 50.0, "pendant","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PRR60"+i , "DL23"+i, "PD10013","Spiral bracelet", 632.0, 50.0, "bracelet","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PRR70"+i , "DL23"+i, "PD10014","Scorpion anklet", 632.0, 50.0, "anklet","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PRR80"+i , "DL23"+i, "PD10015","Plain silver bangle", 632.0, 50.0, "bangle","เส้น",100, true, (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
       }
}
