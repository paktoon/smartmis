package com.smart.mis.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;

public class TimeDimension {

	public static Integer[] getTimeDimension(Date input) {
		
		String month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( input ).split( "-")[1];
		String year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( input ).split( "-")[2];
		
		Integer year_int = Integer.parseInt(year);
		Integer quarter = 1;
		Integer month_int = Integer.parseInt(month);
		if (month_int == 1 || month_int == 2 || month_int == 3) {
			quarter = 1;
		} else if (month_int == 4 || month_int == 5 || month_int == 6) {
			quarter = 2;
		} else if (month_int == 7 || month_int == 8 || month_int == 9) {
			quarter = 3;
		} else if (month_int == 10 || month_int == 11 || month_int == 12) {
			quarter = 4;
		}
		
		return new Integer[] {year_int, quarter, month_int};
	}
	
	public static Integer getYear(Date input) {
		String year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( input ).split( "-")[2];
		Integer year_int = Integer.parseInt(year);
		return year_int;
	}
	
	public static Integer getQuarter(Date input) {
		String month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( input ).split( "-")[1];
		Integer quarter = 1;
		Integer month_int = Integer.parseInt(month);
		if (month_int == 1 || month_int == 2 || month_int == 3) {
			quarter = 1;
		} else if (month_int == 4 || month_int == 5 || month_int == 6) {
			quarter = 2;
		} else if (month_int == 7 || month_int == 8 || month_int == 9) {
			quarter = 3;
		} else if (month_int == 10 || month_int == 11 || month_int == 12) {
			quarter = 4;
		}
		
		return quarter;
	}
	
	public static Integer getMonth(Date input) {
		
		String month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( input ).split( "-")[1];
		Integer month_int = Integer.parseInt(month);
		return month_int;
	}
	
	public static void setDimension(Record record, Date setting) { 
		record.setAttribute("year", getYear(setting));
		record.setAttribute("quarter", getQuarter(setting));
		record.setAttribute("month", getMonth(setting));
	}
}
