package com.smart.mis.client.function.security;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PermissionDS extends DataSource  {

		 private static PermissionDS instance = null;
		 
		 public static PermissionDS getInstance() {
			 if (instance == null) {
				 instance = new PermissionDS();
			 }
			 
			 return instance;
		 }
		 
		 public PermissionDS(){
			 DataSourceTextField pidField = new DataSourceTextField("pid", "รหัสสิทธิการใช้งาน");
			 DataSourceTextField permissinNameField = new DataSourceTextField("name", "ชื่อ", 128 , true);
			 permissinNameField.setPrimaryKey(true);
			 DataSourceTextField creatorField = new DataSourceTextField("creator", "แก้ไขโดย");
			 DataSourceTextField whenField = new DataSourceTextField("when", "แก้ไขล่าสุด");
			 DataSourceEnumField roleField = new DataSourceEnumField("role", "ขอบเขตการใช้งาน");
			 roleField.setValueMap("Staff", "Manager", "Administrator");
			 DataSourceEnumField statusField = new DataSourceEnumField("status", "สถานะ");
			 statusField.setValueMap("Active", "Inactive");
			 DataSourceBooleanField saleField = new DataSourceBooleanField("cSale", "งานขาย");
			 DataSourceBooleanField productField = new DataSourceBooleanField("cProd", "งานผลิต");
			 DataSourceBooleanField invenField = new DataSourceBooleanField("cInv", "คลังสินค้าและวัตถุดิบ");
			 DataSourceBooleanField purchaseField = new DataSourceBooleanField("cPurc", "จัดซื้อวัตถุดิบ");
			 DataSourceBooleanField financeField = new DataSourceBooleanField("cFin", "รายรับรายจ่าย");
			 DataSourceBooleanField reportField = new DataSourceBooleanField("cRep", "ออกรายงาน");
			 DataSourceBooleanField adminField = new DataSourceBooleanField("cAdm", "จัดการข้อมูลผู้ใช้ระบบ");

			 setID("permission_ds");
			 setFields(pidField, permissinNameField, creatorField, whenField, roleField, statusField, saleField, productField, invenField, purchaseField, financeField, reportField, adminField);
			 setDataURL("smartmis/security/permissionData");
			 setClientOnly(true);
		 }
}
