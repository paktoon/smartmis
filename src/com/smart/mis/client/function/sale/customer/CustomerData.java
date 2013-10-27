package com.smart.mis.client.function.sale.customer;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CustomerData {

    public static ListGridRecord createRecord(String cid, String cus_name, String cus_phone, String contact_name, String contact_phone, String contact_email, String address, String cus_type, String zone) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("cid", cid);
        record.setAttribute("cus_name",cus_name);  
        record.setAttribute("cus_phone", cus_phone);
        record.setAttribute("contact_name", contact_name);  
        record.setAttribute("contact_phone", contact_phone); 
        record.setAttribute("contact_email", contact_email);  
        record.setAttribute("address", address);   
        record.setAttribute("cus_type", cus_type);
        record.setAttribute("zone", zone);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("CU10001","บริษัท พี.เจ.เจ.จำกัด","022588888", "มานิส แสงเทพ", "0892222244", "webmaster@woeldmark.co.th", "392/21 สุขุมวิท 20 แขวงคลองเตย เขตคลองเตย กทม 10110", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10002","ธำรง แสงน้อย","0894774443", "ธำรง แสงน้อย", "0894774443", "tamrong@hotmail.com", "56/12-6 อาคารบิสโก้ ซอยทรัพย์ ถนนทรัพย์ แขวงสี่พระยา เขตบางรัก กทม 10500", "ลูกค้าทั่วไป", "เอเซีย"),
    			createRecord("CU10003","ร้าน หลิน เครื่องประดับ","0815582721", "ไกรทอง นุ่มนนต์", "0815582122", "taechiraya@gmail.com", "90/156 หมู่ 14 บางกรวย-ไทรน้อย ตำบลบางบัวทอง อำเภอบางบัวทอง นนทบุรี 11110", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10004","มายด์ แอนด์ ลีน่าเครื่องประดับ","074732040", "Andre R.", "0899777702", "mljewelry_stones@hotmail.com", "2 ซอยบรรจบ สตูลธานี ตำบลพิมาน อำเภอเมืองสตูล สตูล 91000", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10005","บริษัท เครื่องประดับอิรอส จำกัด","027495044", "กนธี วิสุทธิ์", "027495044", "", "67/23 หมู่ 12 ซอยอุดมสุข 23 สุขุมวิท 103 แขวงบางนา เขตบางนา กทม 10260", "ลูกค้าทั่วไป", "เอเซีย"),
    			createRecord("CU10006","ร้าน วรรณเครื่องประดับ","0862200088", "วรรณสา เรืองรอง", "0862200088", "", "456/7-8 มิตรภาพ ตำบลในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10007","ไอ-ดีไซน์ จิวเวลรี่","0852178698", "อิทธิพร เลิศพิพัฒน์", "", "itit@gmail.com", "สหกรณ์พระนคร พหลโยธินซอย 7 แขวงสามเสนใน เขตพญาไท กทม 10400", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10008","ประทีปเจมส์","053278930", "นิพัทธ์ ครองศักดิ์", "0899997159", "nipat@gmail.com", "143/3 ต หายยา อำเภอเมืองเชียงใหม่ เชียงใหม่ 50100", "ลูกค้าทั่วไป", "เอเซีย"),
    			createRecord("CU10009","สงขลาจิวเวลรี่","074313458", "กฤตย์ อดิลักษณ์ ", "0817024757", "krit@hotmail.com", "1 ซอยทรัพย์สิน เพชรคีรี ตำบลบ่อยาง อำเภอเมืองสงขลา สงขลา 90000", "ลูกค้าทั่วไป", "เอเซีย"),
    			createRecord("CU10010","บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด","028980098", "มาโนช แย้มยิ้ม", "", "", "9 ซอยเอกชัย 63 แขวงบางบอน เขตบางบอน กทม 10150", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10012","Ying Ge Hai","0892843499", "ศุภณัฐ กลกานต์", "", "supanut@yahoo.com", "No.2 TuHua Road, Pazhou Exhibition Center Area, Guangzhou, China", "ลูกค้าประจำ", "เอเซีย"),
    			createRecord("CU10013","Husa Paseo Del Arte","023745566", "Mr. A", "", "", "C/Atocha 123, Centro, Madrid, Spain 28012", "ลูกค้าทั่วไป", "ยุโรป"),
    			createRecord("CU10014","Quatro Puerta del Sol","023745566", "Mr. B", "", "", "4, Sevilla Street, Centro, Madrid, Spain 28014", "ลูกค้าประจำ", "ยุโรป"),
    			createRecord("CU10015","Wyndham Santa Monica","023745566", "Mr. C", "", "", "120 Colorado Avenue, Santa Monica / Venice Beach, Los Angeles (CA), United States 90401", "ลูกค้าทั่วไป", "อเมริกา"),
    			createRecord("CU10016","Doubletree","023745566", "Mr. D", "", "", "21333 Hawthorne Blvd., Torrance / Carson, Los Angeles (CA), United States 90503", "ลูกค้าประจำ", "อเมริกา"),
    			createRecord("CU10017","Flora Creek","023745566", "Ms. Marina White", "", "", "Port Saeed Road, Near Deira City Centre, Dubai Creek, Dubai, United Arab Emirates", "ลูกค้าประจำ", "อื่นๆ")
    	};
    }
}
