package com.smart.mis.client.function.report.production;

import java.util.ArrayList;
import java.util.Date;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductionReportData {

    //public static ListGridRecord createSentRecord(String sub_job_id, String job_id, String pid, String name, String type, String unit, String details, Double sent_weight, Integer sent_amount, Boolean status) {  
    public static ListGridRecord createRecord(String sub_transfer_id, String plan_id, String pid, String name, String type, String unit, Double sent_weight, Integer sent_amount, Date produced_date) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name", name);  
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("sub_transfer_id", sub_transfer_id);
        record.setAttribute("plan_id", plan_id);

        record.setAttribute("sent_weight", sent_weight);
        record.setAttribute("sent_amount", sent_amount);
        
        record.setAttribute("produced_date", produced_date);
        
        //New
        record.setAttribute("value", sent_amount * (int)(Math.random() * 100));
        return record;  
    }
    
    public static ListGridRecord createRecord(String sub_transfer_id, String plan_id, String pid, String name, String type, String unit, Double sent_weight, Integer sent_amount, Date produced_date, Double value) {
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name", name);  
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        
        record.setAttribute("sub_transfer_id", sub_transfer_id);
        record.setAttribute("plan_id", plan_id);

        record.setAttribute("sent_weight", sent_weight);
        record.setAttribute("sent_amount", sent_amount);
        
        record.setAttribute("produced_date", produced_date);
        
        //New
        record.setAttribute("value", value);
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
    		
    		Integer ring_amount = (int)(Math.random() * 100 * 2.0) + 100;
    		Integer earring_amount = (int)(Math.random() * 100 * 1.8) + 100;
    		Integer necklace_amount = (int)(Math.random() * 100 * 1.2) + 100;
    		Integer toe_ring_amount = (int)(Math.random() * 100 * 0.2) + 100;
    		Integer pendant_amount = (int)(Math.random() * 100 * 0.6) + 100;
    		Integer bracelet_amount = (int)(Math.random() * 100 * 0.5) + 100;
    		Integer anklet_amount = (int)(Math.random() * 100 * 0.3) + 100;
    		Integer bangle_amount = (int)(Math.random() * 100 * 0.4) + 100;
    		
    		dummyReport.add(createRecord("PDR10"+i , "PL20"+i, "PD10001", "Diamond cut silver ring", "ring","วง", (Math.random() * 1000.0) + 1000, ring_amount, date, ring_amount * 33.0));
    		dummyReport.add(createRecord("PDR20"+i , "PL23"+i, "PD10007", "Plain silver necklaces", "earring","เส้น",(Math.random() * 1000.0) + 1000, earring_amount, date, earring_amount * 20.0));

    		dummyReport.add(createRecord("PDR30"+i , "PL23"+i, "PD10006","Silver necklace with star pendant", "necklace","เส้น", (Math.random() * 1000.0) + 1000, necklace_amount, date, necklace_amount * 55.0));
    		dummyReport.add(createRecord("PDR40"+i , "PL23"+i, "PD10008","Diamond cut toe ring", "toe ring","เส้น", (Math.random() * 1000.0) + 1000, toe_ring_amount, date, toe_ring_amount * 32.0));
    		dummyReport.add(createRecord("PDR50"+i , "PL23"+i, "PD10011","Spiral pendant", "pendant","เส้น", (Math.random() * 1000.0) + 1000, pendant_amount, date, pendant_amount * 52.0));
    		dummyReport.add(createRecord("PDR60"+i , "PL23"+i, "PD10013","Spiral bracelet", "bracelet","เส้น", (Math.random() * 1000.0) + 1000, bracelet_amount, date, bracelet_amount * 38.0));
    		dummyReport.add(createRecord("PDR70"+i , "PL23"+i, "PD10014","Scorpion anklet", "anklet","เส้น", (Math.random() * 1000.0) + 1000, anklet_amount, date, anklet_amount * 35.0));
    		dummyReport.add(createRecord("PDR80"+i , "PL23"+i, "PD10015","Plain silver bangle", "bangle","เส้น", (Math.random() * 1000.0) + 1000, bangle_amount, date, bangle_amount * 35.0));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
      }
}
