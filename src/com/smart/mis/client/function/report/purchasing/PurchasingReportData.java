package com.smart.mis.client.function.report.purchasing;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PurchasingReportData {
    
    public static ListGridRecord createRecord(String sub_order_id, String order_id, String mid, String name, String type, String unit, Double price, Double request_amount, Date created_date) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_order_id", sub_order_id);
        record.setAttribute("order_id", order_id);
        record.setAttribute("mid",  mid);
        record.setAttribute("mat_name", name); 
        //record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("unit", unit); 
        record.setAttribute("request_amount", request_amount);
        record.setAttribute("sum_price", price * request_amount);
        
        //record.setAttribute("received_weight", received_weight);
        //record.setAttribute("received_amount", received_amount);
        record.setAttribute("created_date", created_date);
        return record;   
    }
    
    public static ListGridRecord[] getNewRecords() {
    	ArrayList<ListGridRecord> dummyReport = new ArrayList<ListGridRecord>();
    	DateRange dateRange = new DateRange();
        dateRange.setRelativeStartDate(RelativeDate.TODAY);
        //dateRange.setRelativeStartDate(new RelativeDate("-1m"));
    	for (int i = 1; i <= 6 ; i++) {
    		dateRange.setRelativeEndDate(new RelativeDate("-"+ (i * 10) +"d"));
    		Date date = dateRange.getEndDate();
    		Double requested_amount = (Math.random() * 5000.0) + 1000;
    		dummyReport.add(createRecord("MRC10"+i , "PO20"+i, "MA10001","แร่เงิน 100%", "แร่เงิน", "กรัม", 10.25, requested_amount, date));
    		requested_amount += 3562;
    		dummyReport.add(createRecord("MRC20"+i , "PO23"+i, "MA10002","แร่เงิน 92.5%", "แร่เงิน", "กรัม", 9.50, requested_amount, date));

    		dummyReport.add(createRecord("MRC30"+i , "PO23"+i, "MA20001","แมกกาไซต์ PP6", "แมกกาไซต์", "เม็ด", 5.00, Math.round((Math.random() * 2000.0) + 1000) * 1.0, date));
    		dummyReport.add(createRecord("MRC40"+i , "PO23"+i, "MA20002","แมกกาไซต์ PP7", "แมกกาไซต์", "เม็ด", 6.5, Math.round((Math.random() * 2000.0) + 1000) * 1.0, date));
    		dummyReport.add(createRecord("MRC50"+i , "PO23"+i, "MA20005","ถุงพลาสติก 6x6", "อื่นๆ", "ถุง" , 1.2, Math.round((Math.random() * 2000.0) + 1000) * 1.0, date));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
    }
}
