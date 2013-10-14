package com.smart.mis.client.function.security.user;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

public class UserDS extends DataSource  {

		 private static UserDS instance = null;
		 
		 public static UserDS getInstance() {
			 if (instance == null) {
				 instance = new UserDS();
			 }
			 
			 return instance;
		 }
		 
		 public static UserDS resetInstance() {
			 instance = new UserDS();
			 return instance;
		 }
		 
		 public UserDS(){
			 DataSourceTextField uidField = new DataSourceTextField("uid", "รหัสผู้ใช้");
			 DataSourceTextField userNameField = new DataSourceTextField("uname", "ชื่อผู้ใช้");
			 DataSourceTextField passwordField = new DataSourceTextField("pwd", "รหัสผ่าน");
			 userNameField.setPrimaryKey(true);
			 DataSourceEnumField titleField = new DataSourceEnumField("title", "คำนำหน้าชื่อ");
			 titleField.setValueMap("นาย", "นาง", "นางสาว");
			 DataSourceTextField firstNameField = new DataSourceTextField("fname", "ชื่อ");
			 DataSourceTextField lastNameField = new DataSourceTextField("lname", "นามสกุล");
			 DataSourceTextField emailField = new DataSourceTextField("email", "อีเมล");
			 DataSourceTextField positionField = new DataSourceTextField("position", "ตำแหน่ง");
			 DataSourceTextField permissionField = new DataSourceTextField("pname", "สิทธิการใช้งาน");
			 DataSourceBooleanField statusField = new DataSourceBooleanField("status", "สถานะ");
			 
			 DataSourceTextField creatorField = new DataSourceTextField("creator", "แก้ไขโดย");
			 DataSourceTextField whenField = new DataSourceTextField("when", "แก้ไขล่าสุด");
			 
			 //setID("user_ds");
			 setFields(uidField, userNameField, passwordField, creatorField, whenField, titleField, firstNameField, lastNameField, emailField, positionField, permissionField, statusField);
			 setDataURL("smartmis/security/userData");
			 //setCacheAllData(true);
			 //setCacheAllData(true);
			 setClientOnly(true);
		 }
		 
		 public void refreshData() {
			 fetchData(null, new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data,
							DSRequest dsRequest) {
						dsResponse.setInvalidateCache(true);
						//dsRequest.setOperationType(DSOperationType.UPDATE);
						updateCaches(dsResponse, dsRequest);
					}
		    	});
		 }
}
