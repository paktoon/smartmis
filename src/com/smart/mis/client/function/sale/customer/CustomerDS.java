package com.smart.mis.client.function.sale.customer;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

public class CustomerDS extends DataSource  {

		 private static CustomerDS instance = null;
		 
		 public static CustomerDS getInstance() {
			 if (instance == null) {
				 instance = new CustomerDS();
			 }
			 
			 return instance;
		 }
		 
		 public static CustomerDS resetInstance() {
			 instance = new CustomerDS();
			 return instance;
		 }
		 
		 public CustomerDS(){
			 DataSourceTextField Field_1 = new DataSourceTextField("cid", "รหัสลูกค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("cus_name", "ชื่อลูกค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("cus_phone", "หมายเลขโทรศัพท์ลูกค้า");
			 DataSourceTextField Field_4 = new DataSourceTextField("contact_name", "ชื่อผู้ติดต่อ");
			 DataSourceTextField Field_5 = new DataSourceTextField("contact_phone", "หมายเลขโทรศัพท์ผู้ติดต่อ");
			 DataSourceTextField Field_6 = new DataSourceTextField("contact_email", "อีเมลผู้ติดต่อ");
			 DataSourceTextField Field_7 = new DataSourceTextField("address", "ที่อยู่");
			 DataSourceEnumField Field_8 = new DataSourceEnumField("cus_type", "ประเภทลูกค้า");
			 Field_8.setValueMap("ลูกค้าประจำ", "ลูกค้าทั่วไป");
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6, Field_7, Field_8);
			 //setDataURL("smartmis/security/userData");
			 setTestData(CustomerData.getNewRecords()); // For Test
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
