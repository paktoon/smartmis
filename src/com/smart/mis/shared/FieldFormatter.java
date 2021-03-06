package com.smart.mis.shared;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class FieldFormatter {

	public static CellFormatter getNumberFormat(){
		return new CellFormatter() {  
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
                if (value == null) return null;  
                try {  
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");  
                    return nf.format(((Number) value).doubleValue());  
                } catch (Exception e) {  
                    return value.toString();  
                }  
            }  
        };
	}
	
	public static CellFormatter getIntegerFormat(){
		return new CellFormatter() {  
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
                if (value == null) return null;  
                try {  
                    NumberFormat nf = NumberFormat.getFormat("#,##0");  
                    return nf.format(((Number) value).intValue());  
                } catch (Exception e) {  
                    return value.toString();  
                }  
            }  
        };
	}
	
	public static CellFormatter getPriceFormat(){
		return new CellFormatter() {  
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
                if (value == null) return null;  
                try {  
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");  
                    return "฿" + nf.format(((Number) value).doubleValue());  
                } catch (Exception e) {  
                    return value.toString();  
                }  
            }  
        };
	}
	
	public static CellFormatter getPercentageFormat(){
		return new CellFormatter() {  
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
                if (value == null) return null;  
                try {  
                    NumberFormat nf = NumberFormat.getFormat("#0.0");  
                    return nf.format(((Number) value).doubleValue()) + "%";
                } catch (Exception e) {  
                    return value.toString();  
                }  
            }  
        };
	}
}
