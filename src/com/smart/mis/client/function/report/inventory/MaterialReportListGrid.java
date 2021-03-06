package com.smart.mis.client.function.report.inventory;

import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class MaterialReportListGrid extends ListGrid {

	private Double[][] silverDataTable, matDataTable;
	
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
	
//	public MaterialReportListGrid() {
//		setWidth(1000);  
//		setHeight(400);  
//        
//        setAlternateRecordStyles(true);  
//        setShowAllRecords(true);  
//        setAutoFetchData(true);  
//        setSelectionType(SelectionStyle.SINGLE);
//        setCanResizeFields(false);
//        setCanEdit(false);
//        setDataSource(MaterialDS.getInstance());
//        setUseAllDataSourceFields(false);
//        setMargin(10);
//        
//        setGroupStartOpen(GroupStartOpen.ALL);
//        setGroupByField("type"); 
//        setShowGroupSummary(true);  
//        
//        ListGridField field_1 = new ListGridField("mid", 120);
//        ListGridField field_2 = new ListGridField("mat_name",200);
//        field_2.setShowGroupSummary(true);
//        field_2.setSummaryFunction(new SummaryFunction() {  
//            public Object getSummaryValue(Record[] records, ListGridField field) {
//                return records.length + " รายการ";  
//            }  
//        });
//        ListGridField field_3 = new ListGridField("desc");
//        ListGridField field_3_1 = new ListGridField("weight");
//        field_3_1.setShowGroupSummary(false);
//        ListGridField field_4 = new ListGridField("type",100);
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
//        //ListGridField field_8 = new ListGridField("sup_list");
//        
//        //Cell Format
//        Field_6_1.setAlign(Alignment.RIGHT);
//        Field_6_1.setCellFormatter(FieldFormatter.getNumberFormat());
//        Field_6_2.setAlign(Alignment.RIGHT);
//        Field_6_2.setCellFormatter(FieldFormatter.getNumberFormat());
//        Field_6_3.setAlign(Alignment.RIGHT);
//        Field_6_3.setCellFormatter(FieldFormatter.getNumberFormat());
//        field_7.setAlign(Alignment.CENTER);
//        
//        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
//        setFields(field_1, field_2, field_3, field_3_1, field_4, Field_6_1, Field_6_2, Field_6_3, field_7);
//        setHoverWidth(200);  
//        setHoverHeight(20);
//        hideFields("type");
//        //fetchData();
//	}
	
	public Double[][] createSilverDataTable(){
		
		if (silverDataTable != null) return silverDataTable;
		
		MaterialDS.getInstance().refreshData();
	    Record[] silver100 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 100%"));
	    Record[] silver925 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 92.5%"));

	    return new Double[][] {
	    		{getRemaining(silver100), getRemaining(silver925)},
	    		{getReserved(silver100), getReserved(silver925)}
	    };
	}
	
	public Double[][] createMaterialDataTable(){
		
		if (matDataTable != null) return matDataTable;
		
		MaterialDS.getInstance().refreshData();
		Record[] mat_1 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "พลอยประดับ"));
	    Record[] mat_2 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์"));
	    Record[] mat_3 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "อื่นๆ"));

	    return new Double[][] {
	    		{getRemaining(mat_1), getRemaining(mat_2), getRemaining(mat_3)},
	    		{getReserved(mat_1), getReserved(mat_2), getReserved(mat_3)}
	    };
	}
	
	public Double getRemaining(Record[] records) {
		Double remaining = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("remain") != null) {
				remaining += record.getAttributeAsDouble("remain");
			}
		}
		return remaining;
	}
	
	public Double getReserved(Record[] records) {
		Double reserved = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("reserved") != null) {
				reserved += record.getAttributeAsDouble("reserved");
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
	
	public MaterialReportListGrid() {
		setWidth(650);
		setHeight(270);
		setAlign(Alignment.CENTER);
		
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setMargin(10);
        //setShowGridSummary(true);
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("group"); 
        setShowGroupSummary(true);
        
        ListGridField field_0 = new ListGridField("group");
        ListGridField field_1 = new ListGridField("type", "ประเภทวัตถุดิบ");
        field_1.setShowGroupSummary(true);
        field_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        
      ListGridField Field_6_1 = new ListGridField("inStock", "ปริมาณในคลัง", 150);
      Field_6_1.setShowGroupSummary(true);
      Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
      ListGridField Field_6_2 = new ListGridField("reserved","จำนวนที่จอง", 150);
      Field_6_2.setShowGroupSummary(true);
      Field_6_2.setSummaryFunction(SummaryFunctionType.SUM);
      ListGridField Field_6_3 = new ListGridField("remain","จำนวนคงเหลือ", 150);
      Field_6_3.setShowGroupSummary(true);
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
        
        setFields(field_0, field_1, Field_6_1, Field_6_2, Field_6_3, field_3_3);
        
        setRecords(createListGridRecord());
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("group");
	}
	
	public ListGridRecord[] createListGridRecord() {
		
		MaterialDS.getInstance().refreshData();
	    Record[] silver100 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 100%"));
	    Record[] silver925 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 92.5%"));

		Record[] mat_1 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "พลอยประดับ"));
	    Record[] mat_2 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์"));
	    Record[] mat_3 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("type", OperatorId.EQUALS, "อื่นๆ"));

	    silverDataTable = new Double[][] {
	    		{getRemaining(silver100), getRemaining(silver925)},
	    		{getReserved(silver100), getReserved(silver925)}
	    };
	    
	    matDataTable = new Double[][] {
	    		{getRemaining(mat_1), getRemaining(mat_2), getRemaining(mat_3)},
	    		{getReserved(mat_1), getReserved(mat_2), getReserved(mat_3)}
	    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("แร่เงิน 100%", "แร่เงิน",getInStock(silver100), getRemaining(silver100),getReserved(silver100), "กรัม"),
    			createRecord("แร่เงิน 92.5%", "แร่เงิน",getInStock(silver925), getRemaining(silver925),getReserved(silver925), "กรัม"),
    			createRecord("พลอยประดับ", "วัตถุดิบ",getInStock(mat_1), getRemaining(mat_1),getReserved(mat_1), "เม็ด"),
    			createRecord("แมกกาไซต์", "วัตถุดิบ",getInStock(mat_2), getRemaining(mat_2),getReserved(mat_2), "เม็ด"),
    			createRecord("อื่นๆ", "วัตถุดิบ",getInStock(mat_3), getRemaining(mat_3),getReserved(mat_3), "ชิ้น")
    	};
	}
	
	public ListGridRecord createRecord(String type, String group, Double inStock, Double remaining, Double reserved, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("group", group);
        record.setAttribute("inStock", inStock);
        record.setAttribute("remain", remaining);
        record.setAttribute("reserved", reserved);
        record.setAttribute("unit", unit);
        return record;
	}
	
}
