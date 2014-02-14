package com.smart.mis.client.function.report.inventory;

import com.smart.mis.client.function.production.product.ProductDS;
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

public class ProductReportListGrid extends ListGrid {

	private Double[][] dataTable;
	
	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
		if (getFieldName(colNum).equals("remain")) { 
			return "font-weight:bold; color:#287fd6;";
		} else if (getFieldName(colNum).equals("reserved")) {
			return "font-weight:bold; color:#d64949;"; 
		}
		else {  
            return super.getCellCSSText(record, rowNum, colNum);  
        } 
	}
	
//	public ProductReportListGrid() {
//		setWidth(1000);  
//		setHeight(400);  
//        
//        setAlternateRecordStyles(true);  
//        setShowAllRecords(true);  
//        setAutoFetchData(true);  
//        setSelectionType(SelectionStyle.SINGLE);
//        setCanResizeFields(false);
//        setCanEdit(false);
//        setDataSource(ProductDS.getInstance());
//        setUseAllDataSourceFields(false);
//        setMargin(10);
//        setShowGroupSummary(true);  
//        
//        setGroupStartOpen(GroupStartOpen.ALL);
//        setGroupByField("type"); 
//        
//        ListGridField field_1 = new ListGridField("pid", 120);
//        ListGridField field_2_1 = new ListGridField("name",150);
//        field_2_1.setShowGroupSummary(true);
//        field_2_1.setSummaryFunction(new SummaryFunction() {  
//            public Object getSummaryValue(Record[] records, ListGridField field) {
//                return records.length + " รายการ";  
//            }  
//        });
//        ListGridField field_2_2 = new ListGridField("name_th");
//
//        //ListGridField field_3 = new ListGridField("size");
//        ListGridField field_3 = new ListGridField("weight",100);
//        field_3.setShowGroupSummary(false);
//        ListGridField field_4_1 = new ListGridField("price", 80);
//        field_4_1.setShowGroupSummary(false);
//        ListGridField field_5 = new ListGridField("type");
//        
//        ListGridField Field_6_1 = new ListGridField("inStock", 100);
//        Field_6_1.setShowGroupSummary(true);
//        Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
//        ListGridField Field_6_2 = new ListGridField("remain", 100);
//        Field_6_2.setShowGroupSummary(true);
//        Field_6_2.setSummaryFunction(SummaryFunctionType.SUM);
//        ListGridField Field_6_3 = new ListGridField("reserved", 100);
//        Field_6_3.setShowGroupSummary(true);
//        Field_6_3.setSummaryFunction(SummaryFunctionType.SUM);
//        ListGridField field_7 = new ListGridField("unit", 50);
//        
//        //Cell Format
//        field_3.setAlign(Alignment.RIGHT);
//        field_3.setCellFormatter(FieldFormatter.getNumberFormat());
//        field_4_1.setAlign(Alignment.RIGHT);
//        field_4_1.setCellFormatter(FieldFormatter.getNumberFormat());
//        Field_6_1.setAlign(Alignment.RIGHT);
//        Field_6_1.setCellFormatter(FieldFormatter.getNumberFormat());
//        Field_6_2.setAlign(Alignment.RIGHT);
//        Field_6_2.setCellFormatter(FieldFormatter.getNumberFormat());
//        Field_6_3.setAlign(Alignment.RIGHT);
//        Field_6_3.setCellFormatter(FieldFormatter.getNumberFormat());
//        field_7.setAlign(Alignment.CENTER);
//        
//        setFields(field_1, field_2_1, field_2_2, field_3, field_4_1, field_5, Field_6_1, Field_6_2, Field_6_3, field_7);
//        setHoverWidth(200);  
//        setHoverHeight(20);
//        hideFields("type");
//        //fetchData();
//	}
	
	
	public Double[][] createDataTable(){
		
		if (dataTable != null) return dataTable;
		
	    ProductDS.getInstance().refreshData();
	    Record[] ring = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "ring"));
	    Record[] toe_ring = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "toe ring"));
	    Record[] earring = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "earring"));
	    Record[] necklace = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "necklace"));
	    Record[] pendant = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "pendant"));
	    Record[] bracelet = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "bracelet"));
	    Record[] anklet = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "anklet"));
	    Record[] bangle = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "bangle"));

	    return new Double[][] {
	    		{getRemaining(ring), getRemaining(toe_ring), getRemaining(earring), getRemaining(necklace), getRemaining(pendant), getRemaining(bracelet), getRemaining(anklet), getRemaining(bangle)},
	    		{getReserved(ring), getReserved(toe_ring), getReserved(earring), getReserved(necklace), getReserved(pendant), getReserved(bracelet), getReserved(anklet), getReserved(bangle)}
	    };
	}
	
	public Double getRemaining(Record[] records) {
		Double remaining = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsInt("remain") != null) {
				remaining += record.getAttributeAsInt("remain");
			}
		}
		return remaining;
	}
	
	public Double getReserved(Record[] records) {
		Double reserved = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsInt("reserved") != null) {
				reserved += record.getAttributeAsInt("reserved");
			}
		}	
		return reserved;
	}
	
	public Double getInStock(Record[] records) {
		Double inStock = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("inStock") != null) {
				inStock += record.getAttributeAsDouble("inStock");
			}
		}	
		return inStock;
	}
	
//	public Double getWeight(Record[] records) {
//		Double weight = 0.0;
//		for (Record record : records) {
//			weight += record.getAttributeAsDouble("weight");
//		}	
//		return weight;
//	}
	//new 
	
	public ProductReportListGrid() {
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
        
        ListGridField Field_6_1 = new ListGridField("inStock", "ปริมาณในคลัง", 150);
        Field_6_1.setShowGridSummary(true);
        Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField Field_6_2 = new ListGridField("reserved","จำนวนที่จอง", 100);
        Field_6_2.setShowGridSummary(true);
        Field_6_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField Field_6_3 = new ListGridField("remain","จำนวนคงเหลือ", 100);
        Field_6_3.setShowGridSummary(true);
        Field_6_3.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", "หน่วย", 50);
        field_3_3.setAlign(Alignment.CENTER);
        
        //Cell Format
        Field_6_1.setAlign(Alignment.RIGHT);
        Field_6_1.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_6_2.setAlign(Alignment.RIGHT);
        Field_6_2.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_6_3.setAlign(Alignment.RIGHT);
        Field_6_3.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_1, Field_6_1, Field_6_2, Field_6_3, field_3_3);
        
        setRecords(createListGridRecord());
        setHoverWidth(200);  
        setHoverHeight(20);
	}
	
	public ListGridRecord[] createListGridRecord() {
	    ProductDS.getInstance().refreshData();
	    Record[] ring = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "ring"));
	    Record[] toe_ring = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "toe ring"));
	    Record[] earring = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "earring"));
	    Record[] necklace = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "necklace"));
	    Record[] pendant = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "pendant"));
	    Record[] bracelet = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "bracelet"));
	    Record[] anklet = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "anklet"));
	    Record[] bangle = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "bangle"));

	    dataTable = new Double[][] {
	    		{getRemaining(ring), getRemaining(toe_ring), getRemaining(earring), getRemaining(necklace), getRemaining(pendant), getRemaining(bracelet), getRemaining(anklet), getRemaining(bangle)},
	    		{getReserved(ring), getReserved(toe_ring), getReserved(earring), getReserved(necklace), getReserved(pendant), getReserved(bracelet), getReserved(anklet), getReserved(bangle)}
	    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("แหวนนิ้วมือ",getInStock(ring),getRemaining(ring),getReserved(ring), "วง"),
    			createRecord("แหวนนิ้วเท้า",getInStock(toe_ring),getRemaining(toe_ring),getReserved(toe_ring), "วง"),
    			createRecord("ต่างหู",getInStock(earring),getRemaining(earring),getReserved(earring), "คู่"),
    			createRecord("สร้อยคอ",getInStock(necklace),getRemaining(necklace),getReserved(necklace), "เส้น"),
    			createRecord("จี้",getInStock(pendant),getRemaining(pendant),getReserved(pendant), "ชิ้น"),
    			createRecord("กำไลข้อมือ",getInStock(bracelet),getRemaining(bracelet),getReserved(bracelet), "วง"),
    			createRecord("กำไลข้อเท้า",getInStock(anklet),getRemaining(anklet),getReserved(anklet), "วง"),
    			createRecord("สร้อยข้อเท้าหรือข้อมือ",getInStock(bangle),getRemaining(bangle),getReserved(bangle), "เส้น"),
    	};
	}
	
	public ListGridRecord createRecord(String type, Double inStock, Double remaining, Double reserved, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("inStock", inStock);
        record.setAttribute("remain", remaining);
        record.setAttribute("reserved", reserved);
        record.setAttribute("unit", unit);
        return record;
	}
}
