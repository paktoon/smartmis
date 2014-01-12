package com.smart.mis.client.function.production.product;

import com.google.gwt.core.client.GWT;
import com.smart.mis.shared.prodution.ProductType;
import com.smart.mis.shared.sale.ExchangeRate;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductData {

    public static ListGridRecord createRecord(String pid, String name, String name_th, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Boolean makeToOrder) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);
        record.setAttribute("name_th",name_th);

        //record.setAttribute("weight", weight);
        
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
        
        record.setAttribute("makeToOrder", makeToOrder); //mm
        
        return record;  
    }
    
    public static ListGridRecord createRecord(String name, String name_th, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Boolean makeToOrder) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("name",name);
        record.setAttribute("name_th",name_th);

        //record.setAttribute("weight", weight);
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
        
        record.setAttribute("makeToOrder", makeToOrder);
        
        return record;  
    }
    
    public static ListGridRecord createUpdatedRecord(String pid, String name, String name_th, Double price, String type, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Boolean makeToOrder) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pid", pid);
        record.setAttribute("name",name);
        record.setAttribute("name_th",name_th);

        //record.setAttribute("weight", weight);
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
        
        record.setAttribute("makeToOrder", makeToOrder);
        
        return record;  
    }
    
    public static ListGridRecord createReservedRecord(String pid, Integer reserved, Integer remain, Record product) {  
        ListGridRecord record = (ListGridRecord) product;
        record.setAttribute("pid", pid);
        record.setAttribute("reserved", reserved);
        record.setAttribute("remain", remain);
//        record.setAttribute("name",product.getAttributeAsString("name"));
//        record.setAttribute("name_th",product.getAttributeAsString("name_th"));
//
//        record.setAttribute("weight", product.getAttributeAsDouble("weight"));
//        record.setAttribute("price", product.getAttributeAsDouble("price"));
//        record.setAttribute("type", product.getAttributeAsString("type"));
//        record.setAttribute("unit", product.getAttributeAsString("unit"));  
//        
//        record.setAttribute("inStock", product.getAttributeAsInt("inStock"));
//        record.setAttribute("imgUrl", product.getAttributeAsString("imgUrl")); 
//        
//        //ring, toe ring
//        record.setAttribute("size", product.getAttributeAsDouble("size")); //USA size 5.0,5.5,6.0,6.5,7.0,7.5,8.0
//        //necklace, bangle
//        record.setAttribute("width", product.getAttributeAsDouble("width")); //cm
//        record.setAttribute("length", product.getAttributeAsDouble("length")); // cm
//        //earring, pendant, anklet, bracelet
//        record.setAttribute("height", product.getAttributeAsDouble("height")); 
//        record.setAttribute("diameter", product.getAttributeAsDouble("diameter")); //mm
//        //All
//        record.setAttribute("thickness", product.getAttributeAsDouble("thickness")); //mm
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createDummyRecord("PD10001","Diamond cut silver ring", "แหวนเงิน คัดลายน้ำ", 6.8, 55.0, "ring" , 300 , 120, "http://lh4.ggpht.com/ktQRyQLXmC37vvDVOs0PedH5fvqQzLHDHzD6rOT7Fwhz-fswBliGY3qP0Ai79h8f_lDBXMrbrMgB5KP2zIrxKLE", 5.0,null,null,null,null,3.0, false),
    			createDummyRecord("PD10002","Thin plain silver ring", "แหวนเงินเกลี้ยง แบบบาง", 6.8, 50.0, "ring", 200, 200, "http://lh5.ggpht.com/OEqPZYGFLhfbKg_TjXXY2G4LlnZgV8Ydasn_xtE2Ag1ve1YTAycIi6lHPBKDmZKOJj0CWBOyb6acxXuJypwHTJp1", 5.0,null,null,null,null,3.0, false),
    			createDummyRecord("PD10003","Dense plain silver ring", "แหวนเงินเกลี้ยง แบบหนา", 6.8, 62.0, "ring", 0 , 0, "http://lh4.ggpht.com/eUlY9K1Zedw858kxmkx6g_Atcx8STAm3RbYLcBHMDIx8KwLRWSlwA0YGvbRsDPoodbP9y0mqe8BpeBgZDlgsilEM", 5.0,null,null,null,null,4.0, true),
    			//GWT.getHostPageBaseURL() + "_ah/img/_avEWP6JsPhjg9w8eS2rmg"
    			createDummyRecord("PD10004","Spiral silver earrings", "ต่างหู ตีเกลียวคู่", 6.32, 55.0, "earring", 250, 0, "http://lh3.ggpht.com/QA3XN4rm-YUJa5cYTDrlSTmltS78CUc-c7bQWxFhiilZu130TXLeT6QtJ6Z_A4HnJmLEZcPEKnuiqDll5fggkwOj", null, 0.7, 2.6, null, null, 3.0, false),
    			createDummyRecord("PD10005","Scorpion silver ear cuffs", "ต่างหู ลงดำ ลายแมงป่อง", 6.32, 50.0, "earring", 200, 50, "http://lh3.ggpht.com/S6dsgFS2BomMuNBIU8DAThTm83-mu3Wr50_EHWbpqxDaEIbXHAvbYS6gAh9S5CxlVdvAsw7pUsciQAh3jPmj2VU", null, null, null, 7.0, 10.0, 3.0, false),
    			
    			createDummyRecord("PD10006","Silver necklace with star pendant", "สร้อยคอ พร้อมจี้รูปดาว", 6.32, 55.0, "necklace" , 100, 0, "http://lh4.ggpht.com/9mW7YapodtKfhcJQwIAXvHRulEmU0LP0CEVsICh8Ykijw-yIV5UvqfHp03zlubSDSUu0K4T7FcPSb5n5C4ggby0", null, null, 45.5, 7.0, 10.0, 3.0, false),
    			createDummyRecord("PD10007","Plain silver necklaces", "สายสร้อย โซ่คร้อง", 6.32, 50.0, "necklace", 100, 0, "http://lh3.ggpht.com/3MowBFjEbea2M1eVBBoM1q1LC_t-mvjmBDcDMDxnMnnOR7PFOvQ4dfLUKPNSYHam9VvwzAhr5ULzjFbKtUMkdQ", null, null, 50.0, null, null, 3.0, true),
    			
    			createDummyRecord("PD10008","Diamond cut toe ring", "แหวนนิิ้วเท้า คัดลายน้ำ", 6.8, 55.0, "toe ring" , 50 , 10, "http://lh3.ggpht.com/R0tT50QBJQ2vORBpBlsUcIw1wjszdCqPL8ASJlb85qknBmU7JFsPi-gUrp745SkvmdU0VM89SOmAfKFeUv9xebY", 5.0,null,null,null,null,3.0, false),
    			createDummyRecord("PD10009","Thin plain toe ring", "แหวนนิิ้วเท้า แบบบาง", 6.8, 50.0, "toe ring", 50, 10, "http://lh3.ggpht.com/mll6ZrBykEcu6ESETgvRbbVMOZaerpxTHTWqEeAhgRDaPjf0SCIPWfSe_4d5k997JvmYlFfAF9cwFrJoDqGEQ9o", 5.0,null,null,null,null,3.0, false),
    			createDummyRecord("PD10010","Dense plain toe ring", "แหวนนิิ้วเท้า แบบหนา", 6.8, 62.0, "toe ring", 50 , 0, "http://lh4.ggpht.com/RIOqiQeSbobLeRCgfqbnych23cRhSldySsd51XdtPl9yeH6DBPitZusqAaUuRDFVeEXEV1g0OSAoFvCQ-keUenE", 5.0,null,null,null,null,4.0, true),
    			
    			createDummyRecord("PD10011","Spiral pendant", "จี้ ตีเกลียวคู่", 6.32, 55.0, "pendant", 100, 0, "http://lh3.ggpht.com/kR6VmXML7n9PZ7PxLw9BJ8FROHjGBGacMvXIu2lBYOIFK5hk4MozyDYGKA_QMF7pJQ0oQQktfflY5QrrIsQaxds", null, 0.7, 2.6, null, null, 3.0, false),
    			createDummyRecord("PD10012","Scorpion pendant", "จี้ ลงดำ ลายแมงป่อง", 6.32, 50.0, "pendant", 100, 50, "http://lh5.ggpht.com/ZRAXpCSF4r34-xksmN1B2TkhFUu11GJjwnDjqHHiuqm00b18VMuH_3reOpbeW19JV-GV539m8CQ_-4Hop3yCQTs", null, null, null, 7.0, 10.0, 3.0, false),
    			
    			createDummyRecord("PD10013","Spiral bracelet", "กำไลข้อมือ ตีเกลียวคู่", 6.32, 55.0, "bracelet", 150, 0, "http://lh4.ggpht.com/uBtxtIxrBpBIm50rf3T30lNDihTwOLnWkCZaCtu1nwQ7tzFGoE3pzyzfEnR4nLVq2cRyBFssmZCA3WC3jKXlD-k", null, 0.7, 2.6, null, null, 3.0, false),
    			createDummyRecord("PD10014","Scorpion anklet", "กำไลข้อเท้า ลงดำ ลายแมงป่อง", 6.32, 50.0, "anklet", 120, 50, "http://lh4.ggpht.com/qWy1lqJeh1YcsRYZzyM-u55z5e1hHtasreeaRvZd8qo7zRrBm65zpZqJCF8-RlpAb9j-Dw2Pyu2i8E8QLJSPjj-2", null, null, null, 7.0, 10.0, 3.0, false),
    			createDummyRecord("PD10015","Plain silver bangle", "สร้อยข้อเท้า ธรรมดา", 6.32, 50.0, "bangle", 75, 0, "http://lh5.ggpht.com/odtKYjc-2oxEzapmNHo9zLEymSr6mzyGYJ-T6uX7oQ0CapusDZg9C2ilX5VlMgzdIFaQf24SJIJa0hzNevljSfo", null, null, 50.0, null, null, 3.0, true),	
    	};
    }
    
    public static ListGridRecord createDummyRecord(String pid, String name, String name_th, Double weight, Double price, String type, Integer inStock, Integer reserved, String imgUrl, Double size, Double width, Double length, Double height, Double diameter, Double thickness, Boolean makeToOrder) {  
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
        record.setAttribute("makeToOrder", makeToOrder);
        
        return record;  
    }
}
