package com.smart.mis.client.function.purchasing.supplier;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SupplierDS extends DataSource  {

		 private static SupplierDS instance = null;
		 
		 public static SupplierDS getInstance() {
			 if (instance == null) {
				 instance = new SupplierDS();
			 }
			 
			 return instance;
		 }
		 
		 public static SupplierDS getCustomInstance(String[] list) {
			 return new SupplierDS(list);
		 }
		 
		 public static SupplierDS resetInstance() {
			 instance = new SupplierDS();
			 return instance;
		 }
		 
		 public SupplierDS(){
			 DataSourceTextField Field_1 = new DataSourceTextField("sid", "รหัสผู้จำหน่าย");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("sup_name", "ชื่อผู้จำหน่าย");
			 DataSourceTextField Field_3 = new DataSourceTextField("sup_phone1", "หมายเลขโทรศัพท์ 1");
			 DataSourceTextField Field_4 = new DataSourceTextField("sup_phone2", "หมายเลขโทรศัพท์ 2");
			 DataSourceTextField Field_5 = new DataSourceTextField("fax", "หมายเลขโทรสาร");
			 DataSourceTextField Field_6 = new DataSourceTextField("email", "อีเมล");
			 DataSourceTextField Field_7 = new DataSourceTextField("address", "ที่อยู่");
			 DataSourceTextField Field_8 = new DataSourceTextField("leadtime", "ระยะเวลาส่งสินค้า (วัน)");
			 DataSourceTextField Field_9 = new DataSourceTextField("list");
			 Field_9.setHidden(true);
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6, Field_7, Field_8, Field_9);
			 //setDataURL("smartmis/security/userData");
			 setTestData(SupplierData.getNewRecords()); // For Test
			 setClientOnly(true);
		 }
		 
		 public SupplierDS(String[] sidList){
			 DataSourceTextField Field_1 = new DataSourceTextField("sid", "รหัสผู้จำหน่าย");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("sup_name", "ชื่อผู้จำหน่าย");
			 
			 setFields(Field_1, Field_2);
			 //setDataURL("smartmis/security/userData");
			 ListGridRecord[] temp = SupplierData.getNewRecords(sidList);
			 setTestData(SupplierData.getNewRecords(sidList)); // For Test
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
