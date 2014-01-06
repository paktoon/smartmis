package com.smart.mis.client.function.sale.invoice;

import com.smart.mis.shared.sale.InvoiceStatus;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class InvoiceDS extends DataSource {

	 private static InvoiceDS instance = null;
	 
	 public static InvoiceDS getInstance() {
		 if (instance == null) {
			 instance = new InvoiceDS();
		 }
		 
		 return instance;
	 }
	 
	 public static InvoiceDS resetInstance() {
		 instance = new InvoiceDS();
		 return instance;
	 }
	 
	 public InvoiceDS(){
		 setID("InvoiceDS");
		 DataSourceTextField Field_0 = new DataSourceTextField("invoice_id", "รหัสใบแจ้งหนี้");
		 Field_0.setPrimaryKey(true);
		 
		 DataSourceTextField Field_1 = new DataSourceTextField("sale_id", "รหัสรายการขาย");
		 DataSourceTextField Field_1_1 = new DataSourceTextField("purchase_id", "รหัสคำสั่งซื้อ");
		 DataSourceTextField Field_2 = new DataSourceTextField("cid", "รหัสลูกค้า");
		 Field_2.setForeignKey("CustomerDS.cid");
		 DataSourceTextField Field_2_1 = new DataSourceTextField("cus_name", "ชื่อลูกค้า");
		 DataSourceTextField Field_2_2 = new DataSourceTextField("payment_model", "วิธีการชำระเงิน");
		 DataSourceIntegerField Field_2_3 = new DataSourceIntegerField("credit", "เครดิต (วัน)");
		 
		 DataSourceTextField Field_2_4 = new DataSourceTextField("cus_type", "ประเภทลูกค้า");
		 DataSourceTextField Field_2_5 = new DataSourceTextField("bus_type", "กลุ่มธุรกิจลูกค้า");
		 DataSourceTextField Field_2_6 = new DataSourceTextField("cus_group", "ชนิดลูกค้า");
		 DataSourceTextField Field_2_7 = new DataSourceTextField("zone", "โซน");
		 
		 DataSourceDateField Field_5 = new DataSourceDateField("delivery", "กำหนดส่งสินค้า");
		 DataSourceDateField Field_5_1 = new DataSourceDateField("due_date", "กำหนดชำระเงิน");
		 
		 DataSourceFloatField Field_6 = new DataSourceFloatField("total_weight", "น้ำหนักรวม (กรัม)");
		 DataSourceIntegerField Field_7 = new DataSourceIntegerField("total_amount", "จำนวนรวม (ชิ้น)");
		 
		 DataSourceFloatField Field_8 = new DataSourceFloatField("netExclusive", "ยอดรวม (บาท)");
		 DataSourceFloatField Field_9 = new DataSourceFloatField("tax", "ภาษีมูลค่าเพิ่ม (7%)");
		 DataSourceFloatField Field_10 = new DataSourceFloatField("netInclusive", "ยอดสุทธิ (บาท)");
		 
		 DataSourceDateField Field_11 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_12 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_12.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_13 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_14 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_14.setForeignKey("UserDS.uid");
		 
		 //DataSourceTextField Field_15 = new DataSourceTextField("comment", "ความคิดเห็น");
		 //DataSourceTextField Field_16 = new DataSourceTextField("status", "สถานะ");
		 DataSourceEnumField Field_16 = new DataSourceEnumField("status", "สถานะ");
		 DataSourceDateField Field_17 = new DataSourceDateField("paid_date", "วันที่ชำระเงิน");
		 DataSourceTextField Field_18 = new DataSourceTextField("paid_by", "แก้ไขโดย");
		 DataSourceFloatField Field_19 = new DataSourceFloatField("receivedInclusive", "ยอดที่รับชำระ (บาท)");
		 
		 //Field_16.setValueMap("รอชำระเงิน", "ชำระเงินแล้ว", "เกินกำหนดชำระเงิน");
		 Field_16.setValueMap(InvoiceStatus.getValueMap());
		 
		 setFields(Field_0, Field_1, Field_1_1, Field_2, Field_2_1, Field_2_2, Field_2_3, Field_2_4, Field_2_5, Field_2_6, Field_2_7, Field_5, Field_5_1, Field_6 ,Field_7, Field_8,  Field_9, Field_10, Field_11, Field_12, Field_13, Field_14, Field_16, Field_17, Field_18, Field_19);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(InvoiceData.getNewRecords()); // For Test
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
