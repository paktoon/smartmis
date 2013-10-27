package com.smart.mis.client.function.production.product;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductData {

    public static ListGridRecord createRecord(String pid, String name, String desc, String size, Double weight, Double price, String type, Integer remain, Boolean inStock, String imgUrl) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);  
        record.setAttribute("desc", desc);
        record.setAttribute("size", size); 
        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);  
        record.setAttribute("remain", remain); 
        record.setAttribute("inStock", inStock); 
        record.setAttribute("imgUrl", imgUrl); 
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("PD10001","Diamond cut silver ring", "แหวนเงิน คัดลายน้ำ", "3.0 mm.", 6.3, 55.0, "แหวนนิ้วมือ" , 300 , true, ""),
    			createRecord("PD10002","Thin plain silver ring", "แหวนเงินเกลี้ยง แบบบาง", "3.0 mm.", 5.6, 50.0, "แหวนนิ้วมือ", 200 , true, ""),
    			createRecord("PD10003","Dense plain silver ring", "แหวนเงินเกลี้ยง แบบหนา", "4.0 mm.", 6.6, 62.0, "แหวนนิ้วมือ", 0 , false, ""),

    			createRecord("PD10004","Spiral silver earrings", "ต่างหู ตีเกลียวคู่", "0.7x2.6 cm.", 6.3, 55.0, "ต่างหู" , 300 , true, GWT.getHostPageBaseURL() + "_ah/img/_avEWP6JsPhjg9w8eS2rmg"),
    			createRecord("PD10005","Scorpion silver ear cuffs", "ต่างหู ลงดำ ลายแมงป่อง", "3.0 mm.", 5.6, 50.0, "ต่างหู", 200 , true, GWT.getHostPageBaseURL() + "_ah/img/x4oiXGpRJ2w9JHqqBnOInQ"),
    			
    			createRecord("PD10006","Silver necklace with star pendant", "สร้อยคอ พร้อมจี้รูปดาว", "ยาว 45.5 cm. จี้ 1.8x1.7cm.", 6.3, 55.0, "สร้อยคอ" , 100 , true, GWT.getHostPageBaseURL() + "_ah/img/wcPuoWXSMi_K__gH9-5E2w"),
    			createRecord("PD10007","Plain silver necklaces", "สายสร้อย โซ่คร้อง", "ควานหนา 0.7mm. ยาว  16inch.", 5.6, 50.0, "สร้อยคอ", 100 , true, GWT.getHostPageBaseURL() + "_ah/img/SaCp1BkcC059PilPBLxskg"),
    	};
    }
}
