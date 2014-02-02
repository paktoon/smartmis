package com.smart.mis.client.function.report.inventory;

import java.util.Date;

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

public class ProductReceivedReportListGrid extends ListGrid {
	
	private Double[][] dataTable;
	
	public ProductReceivedReportListGrid() {
		setWidth(950);
		setHeight(300);
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(ProductReceivedReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("transfer_id", 120);
        ListGridField field_2_1 = new ListGridField("pid",150);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("name");
        ListGridField field_2_3 = new ListGridField("type");
        
        ListGridField field_3_1 = new ListGridField("recv_weight",150);
        field_3_1.setShowGroupSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("recv_amount", 150);
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("received_date", 100);
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
		
	    ProductReceivedReportDS.getInstance().refreshData();
	    Record[] ring = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "ring")}));
	    Record[] toe_ring = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "toe ring")}));
	    Record[] earring = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "earring")}));
	    Record[] necklace = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "necklace")}));
	    Record[] pendant = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "pendant")}));
	    Record[] bracelet = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bracelet")}));
	    Record[] anklet = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "anklet")}));
	    Record[] bangle = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bangle")}));

	    return new Double[][] {
	    		{getReceviedAmount(ring), getReceviedAmount(toe_ring), getReceviedAmount(earring), getReceviedAmount(necklace), getReceviedAmount(pendant), getReceviedAmount(bracelet), getReceviedAmount(anklet), getReceviedAmount(bangle)}
	    };
	}
	
	public Double getReceviedAmount(Record[] records) {
		Double recv_amount = 0.0;
		for (Record record : records) {
			recv_amount += record.getAttributeAsDouble("recv_amount");
		}
		return recv_amount;
	}
	
	//new
	
	public Double getReceviedWeight(Record[] records) {
		Double recv_weight = 0.0;
		for (Record record : records) {
			recv_weight += record.getAttributeAsDouble("recv_weight");
		}
		return recv_weight;
	}
	
	public ProductReceivedReportListGrid(Criterion criteria) {
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
        
        ListGridField field_3_1 = new ListGridField("recv_weight", "น้ำหนักสินค้าที่รับเข้า (กรัม)",150);
        field_3_1.setShowGridSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("recv_amount", "จำนวนสินค้าที่รับเข้า" , 150);
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
	    ProductReceivedReportDS.getInstance().refreshData();
	    Record[] ring = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "ring")}));
	    Record[] toe_ring = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "toe ring")}));
	    Record[] earring = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "earring")}));
	    Record[] necklace = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "necklace")}));
	    Record[] pendant = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "pendant")}));
	    Record[] bracelet = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bracelet")}));
	    Record[] anklet = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "anklet")}));
	    Record[] bangle = ProductReceivedReportDS.getInstance().applyFilter(ProductReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bangle")}));

	    dataTable = new Double[][] {
	    		{getReceviedAmount(ring), getReceviedAmount(toe_ring), getReceviedAmount(earring), getReceviedAmount(necklace), getReceviedAmount(pendant), getReceviedAmount(bracelet), getReceviedAmount(anklet), getReceviedAmount(bangle)}
	    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("แหวนนิ้วมือ",getReceviedAmount(ring),getReceviedWeight(ring), "วง"),
    			createRecord("แหวนนิ้วเท้า",getReceviedAmount(toe_ring),getReceviedWeight(toe_ring), "วง"),
    			createRecord("ต่างหู",getReceviedAmount(earring),getReceviedWeight(earring), "คู่"),
    			createRecord("สร้อยคอ",getReceviedAmount(necklace),getReceviedWeight(necklace), "เส้น"),
    			createRecord("จี้",getReceviedAmount(pendant),getReceviedWeight(pendant), "ชิ้น"),
    			createRecord("กำไลข้อมือ",getReceviedAmount(bracelet),getReceviedWeight(bracelet), "วง"),
    			createRecord("กำไลข้อเท้า",getReceviedAmount(anklet),getReceviedWeight(anklet), "วง"),
    			createRecord("สร้อยข้อเท้าหรือข้อมือ",getReceviedAmount(bangle),getReceviedWeight(bangle), "เส้น"),
    	};
	}
	
	public ListGridRecord createRecord(String type, Double received_amount, Double received_weight, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("recv_amount", received_amount);
        record.setAttribute("recv_weight", received_weight);
        record.setAttribute("unit", unit);
        return record;
	}
}
