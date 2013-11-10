package com.smart.mis.client.function.production.product;

import com.smart.mis.shared.prodution.ProductType;
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
			 DataSourceTextField Field_2_1 = new DataSourceTextField("name", "ชื่อสินค้าภาษาอังกฤษ");
			 DataSourceTextField Field_2_2 = new DataSourceTextField("name_th", "ชื่อสินค้าภาษาไทย");
			 DataSourceFloatField Field_3 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceFloatField Field_4_1 = new DataSourceFloatField("price", "ราคา (บาท)");
			 //DataSourceTextField Field_6 = new DataSourceTextField("desc", "คำอธิบาย");
			 DataSourceEnumField Field_5 = new DataSourceEnumField("type", "ประเภท");
			 //Field_7.setValueMap("ต่างหู", "จี้", "สร้อยคอ", "สร้อยข้อมือ", "สร้อยข้อเท้า", "กำไลข้อมือ", "กำไลข้อเท้า", "แหวนนิ้วมือ", "แหวนนิ้วเท้า");
			 Field_5.setValueMap(ProductType.getValueMap());
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 DataSourceIntegerField Field_7_1 = new DataSourceIntegerField("inStock", "ปริมาณคงคลัง");
			 DataSourceIntegerField Field_7_2 = new DataSourceIntegerField("reserved", "ถูกจอง");
			 DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("remain", "คงเหลือ");
			 
			 DataSourceImageField Field_8 = new DataSourceImageField("imgUrl");
			 Field_8.setHidden(true);
			 
			 DataSourceTextField Field_9_1 = new DataSourceTextField("size", "ขนาดสินค้า");
			 DataSourceTextField Field_9_2 = new DataSourceTextField("width", "ความกว้าง (ซม.)");
			 DataSourceTextField Field_9_3 = new DataSourceTextField("length", "ความยาว (ซม.)");
			 DataSourceTextField Field_9_4 = new DataSourceTextField("height", "ความสูง (มม.)");
			 DataSourceTextField Field_9_5 = new DataSourceTextField("diameter", "เส้นผ่าศูนย์กลาง (มม.)");
			 DataSourceTextField Field_9_6 = new DataSourceTextField("thickness", "ความหนา (มม.)");
			 
			 setFields(Field_1, Field_2_1, Field_2_2, Field_3, Field_4_1, Field_5, Field_7_1, Field_7_2, Field_7_3, Field_6, Field_8, Field_9_1, Field_9_2, Field_9_3,Field_9_4,Field_9_5, Field_9_6);
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
