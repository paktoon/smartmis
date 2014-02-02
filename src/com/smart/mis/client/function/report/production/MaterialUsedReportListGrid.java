package com.smart.mis.client.function.report.production;

import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.report.inventory.MaterialReceivedReportDS;
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

public class MaterialUsedReportListGrid extends ListGrid {

	private Double[][] silverDataTable, matDataTable;
//	@Override
//	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
//		if (getFieldName(colNum).equals("remain")) { 
//			return "font-weight:bold; color:#287fd6;";
//		} else if (getFieldName(colNum).equals("reserved")) {
//			return "font-weight:bold; color:#d64949;"; 
//		}
//		else {  
//            return super.getCellCSSText(record, rowNum, colNum);  
//        } 
//	}
	
	public MaterialUsedReportListGrid() {
		setWidth(950);  
		setHeight(300);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(MaterialUsedReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("mat_request_id", 120);
        ListGridField field_2_1 = new ListGridField("mid",150);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("mname");
        ListGridField field_2_3 = new ListGridField("type");
        
        ListGridField field_3_2 = new ListGridField("request_amount", 170);
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("request_date", 150);
        
        //Cell Format
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_2, field_3_3, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
	public Double[][] createSilverDataTable(Criterion criteria){
		
		if (silverDataTable != null) return silverDataTable;
		
		MaterialUsedReportDS.getInstance().refreshData();
	    Record[] silver100 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mname", OperatorId.EQUALS, "แร่เงิน 100%")}));
	    Record[] silver925 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mname", OperatorId.EQUALS, "แร่เงิน 92.5%")}));

	    return new Double[][] {
	    		{getRequestAmount(silver100), getRequestAmount(silver925)}
	    };
	}
	
	public Double[][] createMaterialDataTable(Criterion criteria){
		
		if (matDataTable != null) return matDataTable;
		
		MaterialUsedReportDS.getInstance().refreshData();
		Record[] mat_1 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
	    Record[] mat_2 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
	    Record[] mat_3 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));

	    return new Double[][] {
	    		{getRequestAmount(mat_1), getRequestAmount(mat_2), getRequestAmount(mat_3)}
	    };
	}
	
	public Double getRequestAmount(Record[] records) {
		Double request_amount = 0.0;
		for (Record record : records) {
			request_amount += record.getAttributeAsDouble("request_amount");
		}
		return request_amount;
	}
	
	//new
	
	public MaterialUsedReportListGrid(Criterion criteria) {
		setWidth(450);
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
        
      ListGridField Field_6_1 = new ListGridField("request_amount", "จำนวนที่ใช้ในการผลิต", 150);
      Field_6_1.setShowGroupSummary(true);
      Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
//      ListGridField Field_6_2 = new ListGridField("reserved","จำนวนที่จอง", 150);
//      Field_6_2.setShowGroupSummary(true);
//      Field_6_2.setSummaryFunction(SummaryFunctionType.SUM);
//      ListGridField Field_6_3 = new ListGridField("remain","จำนวนคงเหลือ", 150);
//      Field_6_3.setShowGroupSummary(true);
//      Field_6_3.setSummaryFunction(SummaryFunctionType.SUM);
      
        ListGridField field_3_3 = new ListGridField("unit", "หน่วย", 50);
        field_3_3.setAlign(Alignment.CENTER);
        
        //Cell Format
      Field_6_1.setAlign(Alignment.RIGHT);
      Field_6_1.setCellFormatter(FieldFormatter.getNumberFormat());
//      Field_6_2.setAlign(Alignment.RIGHT);
//      Field_6_2.setCellFormatter(FieldFormatter.getNumberFormat());
//      Field_6_3.setAlign(Alignment.RIGHT);
//      Field_6_3.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_0, field_1, Field_6_1, field_3_3);
        
        setRecords(createListGridRecord(criteria));
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("group");
	}
	
	public ListGridRecord[] createListGridRecord(Criterion criteria) {
		
		MaterialUsedReportDS.getInstance().refreshData();
	    Record[] silver100 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mname", OperatorId.EQUALS, "แร่เงิน 100%")}));
	    Record[] silver925 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mname", OperatorId.EQUALS, "แร่เงิน 92.5%")}));

		Record[] mat_1 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
	    Record[] mat_2 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
	    Record[] mat_3 = MaterialUsedReportDS.getInstance().applyFilter(MaterialUsedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));

	    silverDataTable =  new Double[][] {
	    		{getRequestAmount(silver100), getRequestAmount(silver925)}
	    };
	    
	    matDataTable = new Double[][] {
	    		{getRequestAmount(mat_1), getRequestAmount(mat_2), getRequestAmount(mat_3)}
	    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("แร่เงิน 100%", "แร่เงิน",getRequestAmount(silver100), "กรัม"),
    			createRecord("แร่เงิน 92.5%", "แร่เงิน",getRequestAmount(silver925), "กรัม"),
    			createRecord("พลอยประดับ", "วัตถุดิบ",getRequestAmount(mat_1), "เม็ด"),
    			createRecord("แมกกาไซต์", "วัตถุดิบ",getRequestAmount(mat_2), "เม็ด"),
    			createRecord("อื่นๆ", "วัตถุดิบ",getRequestAmount(mat_3), "ชิ้น")
    	};
	}
	
	public ListGridRecord createRecord(String type, String group, Double request_amount, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("group", group);
        record.setAttribute("request_amount", request_amount);
        record.setAttribute("unit", unit);
        return record;
	}
}
