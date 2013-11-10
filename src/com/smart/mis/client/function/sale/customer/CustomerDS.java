package com.smart.mis.client.function.sale.customer;

import com.smart.mis.shared.Country;
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
			 setID("CustomerDS");
			 DataSourceTextField Field_1 = new DataSourceTextField("cid", "รหัสลูกค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("cus_name", "ชื่อลูกค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("cus_phone", "หมายเลขโทรศัพท์ลูกค้า");
			 DataSourceTextField Field_4 = new DataSourceTextField("contact_name", "ชื่อผู้ติดต่อ");
			 DataSourceTextField Field_5 = new DataSourceTextField("contact_phone", "หมายเลขโทรศัพท์ผู้ติดต่อ");
			 DataSourceTextField Field_6 = new DataSourceTextField("contact_email", "อีเมลผู้ติดต่อ");
			 //DataSourceTextField Field_7 = new DataSourceTextField("address", "ที่อยู่");
			 DataSourceTextField Field_7_1 = new DataSourceTextField("address", "ที่อยู่");
			 DataSourceTextField Field_7_2 = new DataSourceTextField("street", "ถนน");
			 DataSourceTextField Field_7_3 = new DataSourceTextField("city", "อำเภอ/เมือง");
			 DataSourceTextField Field_7_4 = new DataSourceTextField("state", "จังหวัด/รัฐ");
			 DataSourceEnumField Field_7_5 = new DataSourceEnumField("country", "ประเทศ");
			 Field_7_5.setValueMap(Country.getValueMap());
			 DataSourceTextField Field_7_6 = new DataSourceTextField("postal", "รหัสไปรษณีย์");
			 DataSourceTextField Field_7_7 = new DataSourceTextField("url", "เว็บไซต์");
			 
			 DataSourceEnumField Field_8 = new DataSourceEnumField("cus_type", "ประเภทลูกค้า");
			 Field_8.setValueMap("ลูกค้าประจำ", "ลูกค้าทั่วไป");
			 DataSourceEnumField Field_8_1 = new DataSourceEnumField("bus_type", "ประเภทธุรกิจ");
			 Field_8_1.setValueMap("ค้าส่งผ่านเว็บไซต์", "ค้าส่งผ่านหน้าร้าน", "ค้าปลีกผ่านเว็บไซต์", "ค้าปลีกผ่านหน้าร้าน");
			 DataSourceEnumField Field_8_2 = new DataSourceEnumField("cus_group", "กลุ่มลูกค้า");
			 Field_8_2.setValueMap("ร้านค้า", "บุคคลทั่วไป");
			 DataSourceTextField Field_9 = new DataSourceTextField("zone", "โซน");
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6, Field_7_1, Field_7_2, Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_7_7, Field_8, Field_8_1, Field_8_2, Field_9);
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
