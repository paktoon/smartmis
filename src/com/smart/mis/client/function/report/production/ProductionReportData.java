package com.smart.mis.client.function.report.production;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
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
        
//        record.setAttribute("recv_weight", recv_weight);
//        record.setAttribute("recv_amount", recv_amount);
        
        record.setAttribute("produced_date", produced_date);
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
    		
    		dummyReport.add(createRecord("PDR10"+i , "PL20"+i, "PD10001", "Diamond cut silver ring", "ring","วง", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100) +100, date));
    		dummyReport.add(createRecord("PDR20"+i , "PL23"+i, "PD10007", "Plain silver necklaces", "earring","เส้น",(Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));

    		dummyReport.add(createRecord("PDR30"+i , "PL23"+i, "PD10006","Silver necklace with star pendant", "necklace","เส้น", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PDR40"+i , "PL23"+i, "PD10008","Diamond cut toe ring", "toe ring","เส้น", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PDR50"+i , "PL23"+i, "PD10011","Spiral pendant", "pendant","เส้น", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PDR60"+i , "PL23"+i, "PD10013","Spiral bracelet", "bracelet","เส้น", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PDR70"+i , "PL23"+i, "PD10014","Scorpion anklet", "anklet","เส้น", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    		dummyReport.add(createRecord("PDR80"+i , "PL23"+i, "PD10015","Plain silver bangle", "bangle","เส้น", (Math.random() * 1000.0) + 1000, (int)(Math.random() * 100)+100, date));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
      }
}
