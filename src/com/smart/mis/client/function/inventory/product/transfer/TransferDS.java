package com.smart.mis.client.function.inventory.product.transfer;

import com.smart.mis.shared.inventory.TransferStatus;
import com.smart.mis.shared.prodution.ProcessStatus;
import com.smart.mis.shared.sale.QuotationStatus;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TransferDS extends DataSource {

	 private static TransferDS instance = null;
	 
	 public static TransferDS getInstance() {
		 if (instance == null) {
			 instance = new TransferDS();
		 }
		 
		 return instance;
	 }
	 
	 public static TransferDS resetInstance() {
		 instance = new TransferDS();
		 return instance;
	 }
	 
	 public TransferDS(){
		 setID("TransferDS");
		 DataSourceTextField Field_1 = new DataSourceTextField("transfer_id", "รหัสคำขอโอนสินค้า");
		 Field_1.setPrimaryKey(true);
		 DataSourceTextField Field_2 = new DataSourceTextField("plan_id", "รหัสแผนการผลิต");
//		 
//		 DataSourceTextField Field_3_1 = new DataSourceTextField("smid", "รหัสช่าง");
//		 DataSourceTextField Field_3_2 = new DataSourceTextField("sname", "ชื่อช่าง");
//		 DataSourceTextField Field_3_3 = new DataSourceTextField("semail", "อีเมล");
//		 DataSourceTextField Field_3_4 = new DataSourceTextField("sphone1", "โทรศัทท์ 1");
//		 DataSourceTextField Field_3_5 = new DataSourceTextField("sphone2", "โทรศัทท์ 2");
//		 DataSourceTextField Field_3_6 = new DataSourceTextField("saddress", "ที่อยู่");
//		 DataSourceTextField Field_3_7 = new DataSourceTextField("stype", "ประเภทงาน");
		 
		 DataSourceTextField Field_4_1 = new DataSourceTextField("transfer_date", "วันที่ขอโอนสินค้า");
		 DataSourceTextField Field_4_2 = new DataSourceTextField("transfer_by", "ขอโอนโดย");
		 DataSourceTextField Field_4_3 = new DataSourceTextField("received_date", "วันที่รับโอนสินค้า");
		 DataSourceTextField Field_4_4 = new DataSourceTextField("received_by", "รับโอนโดย");
		 
		 DataSourceFloatField Field_5_1 = new DataSourceFloatField("total_sent_weight", "น้ำหนักสินค้า (กรัม)");
		 DataSourceIntegerField Field_5_2 = new DataSourceIntegerField("total_sent_amount", "จำนวนสินค้า (ชิ้น)");
		 
		 DataSourceFloatField Field_6_1 = new DataSourceFloatField("total_recv_weight", "น้ำหนักสินค้าที่รับโอน (กรัม)");
		 DataSourceIntegerField Field_6_2 = new DataSourceIntegerField("total_recv_amount", "จำนวนสินค้าที่รับโอน (ชิ้น)");
		 
//		 DataSourceFloatField Field_6_1 = new DataSourceFloatField("total_recv_weight", "น้ำหนักที่รับ (กรัม)");
//		 DataSourceIntegerField Field_6_2 = new DataSourceIntegerField("total_recv_amount", "จำนวนที่รับ (ชิ้น)");
//		 DataSourceFloatField Field_6_3 = new DataSourceFloatField("total_wage", "ค่าจ้างรวม (บาท)");
//		 DataSourceFloatField Field_6_4 = new DataSourceFloatField("return_mat", "คืนเนื้อเงิน (กรัม)");
		 
		 DataSourceDateField Field_7 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_8 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_8.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_9 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_10 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_10.setForeignKey("UserDS.uid");
		 
		 DataSourceEnumField Field_12 = new DataSourceEnumField("status", "สถานะ");
		 //Field_16.setValueMap("รอแก้ไข" , "รออนุมัติ" , "อนุมัติแล้ว" , "ยกเลิก");
		 Field_12.setValueMap(TransferStatus.getValueMap());
		 
		 //setFields(Field_1, Field_2, Field_3_1, Field_3_2, Field_3_3, Field_3_4, Field_3_5, Field_3_6, Field_3_7, Field_4_1, Field_4_2, Field_4_3 ,Field_5, Field_6, Field_6_1, Field_6_2, Field_6_3, Field_6_4,  Field_7, Field_8, Field_9, Field_10, Field_11, Field_12);
		 //setFields(Field_1, Field_2, Field_4_1, Field_4_2, Field_4_3 ,Field_5, Field_6, Field_6_1, Field_6_2, Field_6_3, Field_6_4,  Field_7, Field_8, Field_9, Field_10, Field_11, Field_12);
		 setFields(Field_1, Field_2, Field_4_1, Field_4_2, Field_4_3, Field_4_4 ,Field_5_1, Field_5_2, Field_6_1, Field_6_2,  Field_7, Field_8, Field_9, Field_10, Field_12);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(TransferData.getNewRecords()); // For Test
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
