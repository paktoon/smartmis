package com.smart.mis.client.cube.advanced;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceSequenceField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ProductRevenueDS extends DataSource {
	 private static ProductRevenueDS instance = null;
	 
	 public static ProductRevenueDS getInstance() {
		 if (instance == null) {
			 instance = new ProductRevenueDS();
		 }
		 
		 return instance;
	 }
	 
	 public ProductRevenueDS() {
		 setID("ProductRevenueDS");
		 DataSourceSequenceField Field_1 = new DataSourceSequenceField("cellID");
		 Field_1.setPrimaryKey(true);
		 Field_1.setHidden(true);
		 
		 DataSourceTextField Field_2 = new DataSourceTextField("Scenarios", "Scenario");
		 DataSourceTextField Field_3 = new DataSourceTextField("Time", "Time");
		 DataSourceTextField Field_4 = new DataSourceTextField("Regions", "Regions");
		 DataSourceTextField Field_5 = new DataSourceTextField("Products", "Products");
		 DataSourceIntegerField Field_6 = new DataSourceIntegerField("value", "Value");
		 DataSourceIntegerField Field_7 = new DataSourceIntegerField("_hilite");
		 
		 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6, Field_7);
		 setTestData(ProductRevenueData.getNewRecords());
		 setClientOnly(true);
	 }
}
