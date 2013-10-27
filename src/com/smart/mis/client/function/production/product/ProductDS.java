package com.smart.mis.client.function.production.product;

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

public class ProductDS extends DataSource  {

		 private static ProductDS instance = null;
		 
		 public static ProductDS getInstance() {
			 if (instance == null) {
				 instance = new ProductDS();
			 }
			 
			 return instance;
		 }
		 
		 public static ProductDS resetInstance() {
			 instance = new ProductDS();
			 return instance;
		 }
		 
		 public ProductDS(){
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "ชื่อสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("size", "ขนาดสินค้า");
			 DataSourceFloatField Field_4 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("price", "ราคา (บาท)");
			 DataSourceTextField Field_6 = new DataSourceTextField("desc", "คำอธิบาย");
			 DataSourceEnumField Field_7 = new DataSourceEnumField("type", "ประเภท");
			 Field_7.setValueMap("ต่างหู", "จี้", "สร้อยคอ", "สร้อยข้อมือ", "สร้อยข้อเท้า", "กำไลข้อมือ", "กำไลข้อเท้า", "แหวนนิ้วมือ", "แหวนนิ้วเท้า");
			 
			 DataSourceIntegerField Field_8 = new DataSourceIntegerField("remain", "คงเหลือ (ชิ้น)");
			 DataSourceBooleanField Field_9 = new DataSourceBooleanField("inStock", "In Stock");
			 
			 DataSourceImageField Field_10 = new DataSourceImageField("imgUrl");
			 Field_10.setHidden(true);
			 
			 setFields(Field_1, Field_2, Field_6, Field_3, Field_4, Field_5 ,Field_7, Field_8, Field_9, Field_10);
			 //setDataURL("smartmis/security/userData");
			 setTestData(ProductData.getNewRecords()); // For Test
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
