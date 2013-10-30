package com.smart.mis.client.function.sale.quotation;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

public class QuoteProductDS extends DataSource  {

		 private static QuoteProductDS instance = null;
		 
		 public static QuoteProductDS getInstance() {
			 if (instance == null) {
				 instance = new QuoteProductDS();
			 }
			 
			 return instance;
		 }
		 
		 public static QuoteProductDS resetInstance() {
			 instance = new QuoteProductDS();
			 return instance;
		 }
		 
		 public QuoteProductDS(){
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "ชื่อสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("size", "ขนาด");
			 DataSourceFloatField Field_4 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("price", "ราคาต่อหน่วย (บาท)");
			 DataSourceTextField Field_6 = new DataSourceTextField("type", "ประเภท");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceIntegerField Field_8 = new DataSourceIntegerField("quote_amount", "จำนวน");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6 ,Field_7, Field_8,  Field_9);
			 //setDataURL("smartmis/security/userData");
			 setTestData(QuoteProductData.getNewRecords()); // For Test
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
