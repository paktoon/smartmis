package com.smart.mis.client.function.sale.quotation;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class QuotationDS extends DataSource {

	 private static QuotationDS instance = null;
	 
	 public static QuotationDS getInstance() {
		 if (instance == null) {
			 instance = new QuotationDS();
		 }
		 
		 return instance;
	 }
	 
	 public static QuotationDS resetInstance() {
		 instance = new QuotationDS();
		 return instance;
	 }
	 
	 public QuotationDS(){
		 setID("QuotationDS");
		 DataSourceTextField Field_1 = new DataSourceTextField("quote_id", "รหัสใบเสนอราคา");
		 Field_1.setPrimaryKey(true);
		 
		 DataSourceTextField Field_2 = new DataSourceTextField("cid", "รหัสลูกค้า");
		 Field_2.setForeignKey("CustomerDS.cid");
		 DataSourceTextField Field_2_1 = new DataSourceTextField("cus_name", "ชื่อลูกค้า");
		 
		 DataSourceDateField Field_3 = new DataSourceDateField("from", "เริ่ม");
		 DataSourceDateField Field_4 = new DataSourceDateField("to", "สิ้นสุด");
		 DataSourceDateField Field_5 = new DataSourceDateField("delivery", "กำหนดส่งสินค้า");
		 
		 DataSourceFloatField Field_6 = new DataSourceFloatField("total_weight", "น้ำหนักรวม (กรัม)");
		 DataSourceIntegerField Field_7 = new DataSourceIntegerField("total_amount", "จำรวนรวม (ชิ้น)");
		 
		 DataSourceFloatField Field_8 = new DataSourceFloatField("netExclusive", "ราคารวม (บาท)");
		 DataSourceFloatField Field_9 = new DataSourceFloatField("tax", "ภาษีมูลค่าเพิ่ม (7%)");
		 DataSourceFloatField Field_10 = new DataSourceFloatField("netInclusive", "ราคาสุทธิ (บาท)");
		 
		 DataSourceDateField Field_11 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_12 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_12.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_13 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_14 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_14.setForeignKey("UserDS.uid");
		 
		 DataSourceTextField Field_15 = new DataSourceTextField("comment", "ความคิดเห็น");
		 DataSourceTextField Field_16 = new DataSourceTextField("status", "สถานะ");
	        
		 setFields(Field_1, Field_2, Field_2_1, Field_3, Field_4, Field_5, Field_6 ,Field_7, Field_8,  Field_9, Field_10, Field_11, Field_12, Field_13, Field_14, Field_15, Field_16);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(QuotationData.getNewRecords()); // For Test
		 setClientOnly(true);
	 }
	 
	 public void refreshData() {
		 fetchData(null, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
					dsResponse.setInvalidateCache(true);
					updateCaches(dsResponse, dsRequest);
				}
	    	});
	 }

}
