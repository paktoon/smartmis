package com.smart.mis.client.function.purchasing.material;

import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierData;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialDS extends DataSource  {

		 private static MaterialDS instance = null;
		 
		 public static MaterialDS getInstance() {
			 if (instance == null) {
				 instance = new MaterialDS();
			 }
			 
			 return instance;
		 }
		 
		 public static MaterialDS getCustomInstance(String[] list) {
			 return new MaterialDS(list);
		 }
		 
		 public static MaterialDS resetInstance() {
			 instance = new MaterialDS();
			 return instance;
		 }
		 
		 public MaterialDS(){
			 setID("MaterialDS");
			 DataSourceTextField Field_1 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_name", "ชื่อวัตถุดิบ");
			 DataSourceTextField Field_3 = new DataSourceTextField("desc", "คำอธิบาย");
			 DataSourceEnumField Field_4 = new DataSourceEnumField("type", "ชนิด");
			 Field_4.setValueMap("แร่เงิน", "แมกกาไซต์", "พลอยประดับ", "อื่นๆ");
			 DataSourceFloatField Field_4_1 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("safety", "จำนวนสำรองขั้นต่ำ");
			 DataSourceFloatField Field_6 = new DataSourceFloatField("remain", "จำนวนคงเหลือ");
			 Field_5.setDecimalPad(2);
			 Field_6.setDecimalPad(2);
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 //DataSourceTextField Field_8 = new DataSourceTextField("sup_list");
			 //Field_8.setHidden(true);
			 
			 //setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6, Field_7, Field_8);
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_4_1, Field_5, Field_6, Field_7);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(MaterialData.getNewRecords()); // For Test
			 setClientOnly(true);
		 }
		 
		 public MaterialDS(String[] midList){
			 DataSourceTextField Field_1 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_name", "ชื่อวัตถุดิบ");
			 
			 setFields(Field_1, Field_2);
			 //setDataURL("smartmis/security/userData");
			 ListGridRecord[] temp = MaterialData.getNewRecords(midList);
			 setTestData(MaterialData.getNewRecords(midList)); // For Test
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
