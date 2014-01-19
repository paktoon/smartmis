package com.smart.mis.client.cube;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SaleVolumn extends ListGridRecord {  
	  
    public SaleVolumn(String cellId, String year, int type, String setting, String customer, String product, String metric, int value) {  
    	setCellId(cellId);
    	setYear(year);
        
        if (type == 1) setQuater(setting);  
        else setMonth(setting);
        
        setCustomer(customer);  
        setProduct(product);  
        setMetric(metric);  
        setValue(value);  
        //setPercentNational(percentNational);  
    }  
  
//    public SaleVolumn(String cellId, String quarter, String month, String customer, String product, String metric, int value, String hilite) {  
//    	setCellId(cellId);
//        setQuater(quarter);  
//        setMonth(month);  
//        setCustomer(customer);  
//        setProduct(product);  
//        setMetric(metric);  
//        setValue(value);  
//        //setPercentNational(percentNational);  
//    }  
    
    public SaleVolumn(String cellId, String year, String customer, String product, String metric, int value) {  
    	setCellId(cellId);  
        setYear(year);
        setCustomer(customer);  
        setProduct(product);  
        setMetric(metric);  
        setValue(value);  
        //setPercentNational(percentNational);  
    } 
  
    public void setCellId(String cellID) {  
        setAttribute("cellID", cellID);  
    }  
  
    public String setCellId() {  
        return getAttribute("cellID");  
    } 
    
    public void setYear(String year) {  
        setAttribute("year", year);  
    }  
  
    public String getYear() {  
        return getAttribute("year");  
    } 
    
    public void setQuater(String quarter) {  
        setAttribute("quarter", quarter);  
    }  
  
    public String getQuater() {  
        return getAttribute("quarter");  
    }  
    
    public void setMonth(String month) {  
        setAttribute("month", month);  
    }  
  
    public String getMonth() {  
        return getAttribute("month");  
    } 
  
    public void setCustomer(String customer) {  
        setAttribute("customer", customer);  
    }  
  
    public String getCustomer() {  
        return getAttribute("customer");  
    }  
    
    public void setZone(String zone) {  
        setAttribute("zone", zone);  
    }  
  
    public String getZone() {  
        return getAttribute("zone");  
    }  
  
//    public void setMonth(String month) {  
//        setAttribute("month", month);  
//    }  
//  
//    public String getMonth(String month) {  
//        return getAttribute("month");  
//    }  
  
    public void setProduct(String product) {  
        setAttribute("product", product);  
    }  
  
    public String getProduct() {  
        return getAttribute("product");  
    }  
  
    public void setMetric(String metric) {  
        setAttribute("metric", metric);  
    }  
  
    public String getMetric() {  
        return getAttribute("metric");  
    }  
  
    public void setValue(Integer value) {  
        setAttribute("value", value);   
        //setAttribute("value", value); 
    }  
  
    public Integer getValue() {  
        return getAttributeAsInt("value");  
        //return getAttributeAsInt("value");  
    }  
  
//    public void setPercentNational(Integer percentNational) {  
//        setAttribute("percentNational", percentNational);  
//    }  
//  
//    public Integer getPercentNational(Integer percentNational) {  
//        return getAttributeAsInt("percentNational");  
//    }  
  
    public void setHilite(String hilite) {  
        setAttribute("_hilite", hilite);  
    }  
  
    public String getHilite(String hilite) {  
        return getAttribute("_hilite");  
    }  
} 
