package com.smart.mis.client.function.production.product;

import com.google.gwt.core.client.GWT;
import com.smart.mis.shared.prodution.ProductType;
import com.smart.mis.shared.sale.ExchangeRate;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductData {

    public static ListGridRecord createRecord(String pid, String name, String name_th, Double weight, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);
        record.setAttribute("name_th",name_th);

        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);
        record.setAttribute("unit", ProductType.getUnit(type));  
        
        record.setAttribute("inStock", inStock);
        record.setAttribute("reserved", reserved);
        record.setAttribute("remain", inStock - reserved);
        
        record.setAttribute("imgUrl", imgUrl); 
        
        //ring, toe ring
        record.setAttribute("size", size); //USA size 5.0,5.5,6.0,6.5,7.0,7.5,8.0
        //necklace, bangle
        record.setAttribute("width", width); //cm
        record.setAttribute("length", length); // cm
        //earring, pendant, anklet, bracelet
        record.setAttribute("height", height); 
        record.setAttribute("diameter", diameter); //mm
        //All
        record.setAttribute("thickness", thickness); //mm
        
        return record;  
    }
    
    public static ListGridRecord createRecord(String name, String name_th, Double weight, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("name",name);
        record.setAttribute("name_th",name_th);

        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);
        record.setAttribute("unit", ProductType.getUnit(type));  
        
        record.setAttribute("inStock", inStock);
        record.setAttribute("reserved", reserved);
        record.setAttribute("remain", inStock - reserved);
        
        record.setAttribute("imgUrl", imgUrl); 
        
        //ring, toe ring
        record.setAttribute("size", size); //USA size 5.0,5.5,6.0,6.5,7.0,7.5,8.0
        //necklace, bangle
        record.setAttribute("width", width); //cm
        record.setAttribute("length", length); // cm
        //earring, pendant, anklet, bracelet
        record.setAttribute("height", height); 
        record.setAttribute("diameter", diameter); //mm
        //All
        record.setAttribute("thickness", thickness); //mm
        
        return record;  
    }
    
    public static ListGridRecord createUpdatedRecord(String pid, String name, String name_th, Double weight, Double price, String type, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);
        record.setAttribute("name_th",name_th);

        record.setAttribute("weight", weight);
        record.setAttribute("price", price);
        record.setAttribute("type", type);
        record.setAttribute("unit", ProductType.getUnit(type));  
        
        record.setAttribute("imgUrl", imgUrl); 
        
        //Ring, Toe Ring
        record.setAttribute("size", size); //USA size 5.0,5.5,6.0,6.5,7.0,7.5,8.0
        //Necklaces, Pendants, Earing
        record.setAttribute("width", width); //cm
        record.setAttribute("length", length); // cm
        //Earing
        record.setAttribute("height", height); 
        record.setAttribute("diameter", diameter); //mm
        //All
        record.setAttribute("thickness", thickness); //mm
        
        return record;  
    }
    
    public static ListGridRecord createReservedRecord(String pid, Integer reserved, Integer remain, Record product) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("reserved", reserved);
        record.setAttribute("remain", remain);
        
        record.setAttribute("name",product.getAttributeAsString("name"));
        record.setAttribute("name_th",product.getAttributeAsString("name_th"));

        record.setAttribute("weight", product.getAttributeAsDouble("weight"));
        record.setAttribute("price", product.getAttributeAsString("price"));
        record.setAttribute("type", product.getAttributeAsString("type"));
        record.setAttribute("unit", product.getAttributeAsString("unit"));  
        
        record.setAttribute("inStock", product.getAttributeAsString("inStock"));
        record.setAttribute("imgUrl", product.getAttributeAsString("imgUrl")); 
        
        //ring, toe ring
        record.setAttribute("size", product.getAttributeAsDouble("size")); //USA size 5.0,5.5,6.0,6.5,7.0,7.5,8.0
        //necklace, bangle
        record.setAttribute("width", product.getAttributeAsDouble("width")); //cm
        record.setAttribute("length", product.getAttributeAsDouble("length")); // cm
        //earring, pendant, anklet, bracelet
        record.setAttribute("height", product.getAttributeAsDouble("height")); 
        record.setAttribute("diameter", product.getAttributeAsDouble("diameter")); //mm
        //All
        record.setAttribute("thickness", product.getAttributeAsDouble("thickness")); //mm
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("PD10001","Diamond cut silver ring", "แหวนเงิน คัดลายน้ำ", 6.3, 55.0, "ring" , 300 , 120, "", 5.0,null,null,null,null,3.0),
    			createRecord("PD10002","Thin plain silver ring", "แหวนเงินเกลี้ยง แบบบาง", 5.6, 50.0, "ring", 200, 100, "", 5.0,null,null,null,null,3.0),
    			createRecord("PD10003","Dense plain silver ring", "แหวนเงินเกลี้ยง แบบหนา", 6.6, 62.0, "ring", 0 , 0, "", 5.0,null,null,null,null,4.0),

    			createRecord("PD10004","Spiral silver earrings", "ต่างหู ตีเกลียวคู่", 6.3, 55.0, "earring", 250, 0, GWT.getHostPageBaseURL() + "_ah/img/_avEWP6JsPhjg9w8eS2rmg", null, 0.7, 2.6, null, null, 3.0),
    			createRecord("PD10005","Scorpion silver ear cuffs", "ต่างหู ลงดำ ลายแมงป่อง", 5.6, 50.0, "earring", 200, 50, GWT.getHostPageBaseURL() + "_ah/img/x4oiXGpRJ2w9JHqqBnOInQ", null, null, null, 7.0, 10.0, 3.0),
    			
    			createRecord("PD10006","Silver necklace with star pendant", "สร้อยคอ พร้อมจี้รูปดาว", 6.3, 55.0, "necklace" , 100, 0, GWT.getHostPageBaseURL() + "_ah/img/wcPuoWXSMi_K__gH9-5E2w", null, null, 45.5, 7.0, 10.0, 3.0),
    			createRecord("PD10007","Plain silver necklaces", "สายสร้อย โซ่คร้อง", 5.6, 50.0, "necklace", 100, 0, GWT.getHostPageBaseURL() + "_ah/img/SaCp1BkcC059PilPBLxskg", null, null, 50.0, null, null, 3.0),
    	};
    }
}
