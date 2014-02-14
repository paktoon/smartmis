package com.smart.mis.client.function.report.inventory;

import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;

public class ProductRequestReportListGrid extends ListGrid {

	private Double[][] dataTable;
	
	public ProductRequestReportListGrid() {
		setWidth(950);
		setHeight(300);
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(ProductRequestReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("delivery_id", 120);
        ListGridField field_2_1 = new ListGridField("pid",150);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("name");
        ListGridField field_2_3 = new ListGridField("type");
        
        ListGridField field_3_1 = new ListGridField("issued_weight",100);
        field_3_1.setShowGroupSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("issued_amount", 80); //*
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("issued_date", 100);
        //ListGridField Field_6_2 = new ListGridField("remain", 100);
        //ListGridField Field_6_3 = new ListGridField("reserved", 100);
        //ListGridField field_7 = new ListGridField("unit", 50);
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_1, field_3_2, field_3_3, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
	public Double[][] createDataTable(Criterion criteria){
		
		if (dataTable != null) return dataTable;
		
	    ProductRequestReportDS.getInstance().refreshData();
	    Record[] ring = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "ring")}));
	    Record[] toe_ring = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "toe ring")}));
	    Record[] earring = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "earring")}));
	    Record[] necklace = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "necklace")}));
	    Record[] pendant = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "pendant")}));
	    Record[] bracelet = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bracelet")}));
	    Record[] anklet = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "anklet")}));
	    Record[] bangle = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bangle")}));

	    return new Double[][] {
	    		{getIssuedAmount(ring), getIssuedAmount(toe_ring), getIssuedAmount(earring), getIssuedAmount(necklace), getIssuedAmount(pendant), getIssuedAmount(bracelet), getIssuedAmount(anklet), getIssuedAmount(bangle)}
	    };
	}
	
	public Double getIssuedAmount(Record[] records) {
		Double issued_amount = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("issued_amount") != null) {
				issued_amount += record.getAttributeAsDouble("issued_amount");
			}
		}
		return issued_amount;
	}
	
	//new
	
	public Double getIssuedWeight(Record[] records) {
		Double issued_weight = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("issued_weight") != null) {
				issued_weight += record.getAttributeAsDouble("issued_weight");
			}
		}
		return issued_weight;
	}
	
	public ProductRequestReportListGrid(Criterion criteria) {
		setWidth(650);
		setHeight(270);
		setAlign(Alignment.CENTER);
		
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setMargin(10);
        setShowGridSummary(true);
        
        ListGridField field_1 = new ListGridField("type", "ประเภทสินค้า");
        field_1.setShowGridSummary(true);
        field_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        
        ListGridField field_3_1 = new ListGridField("issued_weight", "น้ำหนักสินค้าที่เบิกจ่าย (กรัม)",170);
        field_3_1.setShowGridSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("issued_amount", "จำนวนสินค้าที่เบิกจ่าย" , 170);
        field_3_2.setShowGridSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", "หน่วย", 50);
        field_3_3.setAlign(Alignment.CENTER);
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_1, field_3_1, field_3_2, field_3_3);
        
        setRecords(createListGridRecord(criteria));
        setHoverWidth(200);  
        setHoverHeight(20);
	}
	
	public ListGridRecord[] createListGridRecord(Criterion criteria) {
	    ProductRequestReportDS.getInstance().refreshData();
	    Record[] ring = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "ring")}));
	    Record[] toe_ring = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "toe ring")}));
	    Record[] earring = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "earring")}));
	    Record[] necklace = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "necklace")}));
	    Record[] pendant = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "pendant")}));
	    Record[] bracelet = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bracelet")}));
	    Record[] anklet = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "anklet")}));
	    Record[] bangle = ProductRequestReportDS.getInstance().applyFilter(ProductRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bangle")}));

	    dataTable = new Double[][] {
	    		{getIssuedAmount(ring), getIssuedAmount(toe_ring), getIssuedAmount(earring), getIssuedAmount(necklace), getIssuedAmount(pendant), getIssuedAmount(bracelet), getIssuedAmount(anklet), getIssuedAmount(bangle)}
	    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("แหวนนิ้วมือ",getIssuedAmount(ring),getIssuedWeight(ring), "วง"),
    			createRecord("แหวนนิ้วเท้า",getIssuedAmount(toe_ring),getIssuedWeight(toe_ring), "วง"),
    			createRecord("ต่างหู",getIssuedAmount(earring),getIssuedWeight(earring), "คู่"),
    			createRecord("สร้อยคอ",getIssuedAmount(necklace),getIssuedWeight(necklace), "เส้น"),
    			createRecord("จี้",getIssuedAmount(pendant),getIssuedWeight(pendant), "ชิ้น"),
    			createRecord("กำไลข้อมือ",getIssuedAmount(bracelet),getIssuedWeight(bracelet), "วง"),
    			createRecord("กำไลข้อเท้า",getIssuedAmount(anklet),getIssuedWeight(anklet), "วง"),
    			createRecord("สร้อยข้อเท้าหรือข้อมือ",getIssuedAmount(bangle),getIssuedWeight(bangle), "เส้น"),
    	};
	}
	
	public ListGridRecord createRecord(String type, Double amount, Double weight, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("issued_amount", amount);
        record.setAttribute("issued_weight", weight);
        record.setAttribute("unit", unit);
        return record;
	}
}
