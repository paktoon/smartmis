package com.smart.mis.client.function.report.inventory;

import com.google.gwt.i18n.client.NumberFormat;
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

public class MaterialRequestReportListGrid extends ListGrid {

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
	
	public MaterialRequestReportListGrid() {
		setWidth(950);  
		setHeight(300);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(MaterialRequestReportDS.getInstance());
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
        
        //ListGridField field_3_1 = new ListGridField("issued_weight",150);
        ListGridField field_3_2 = new ListGridField("issued_amount", 150); //*
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("issued_date", 120);
        //ListGridField field_8 = new ListGridField("sup_list");
        
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
		MaterialRequestReportDS.getInstance().refreshData();
	    Record[] silver100 = MaterialRequestReportDS.getInstance().applyFilter(MaterialRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 100%")}));
	    Record[] silver925 = MaterialRequestReportDS.getInstance().applyFilter(MaterialRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 92.5%")}));

	    return new Double[][] {
	    		{getIssueAmount(silver100), getIssueAmount(silver925)}
	    };
	}
	
	public Double[][] createMaterialDataTable(Criterion criteria){
		MaterialRequestReportDS.getInstance().refreshData();
		Record[] mat_1 = MaterialRequestReportDS.getInstance().applyFilter(MaterialRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
	    Record[] mat_2 = MaterialRequestReportDS.getInstance().applyFilter(MaterialRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
	    Record[] mat_3 = MaterialRequestReportDS.getInstance().applyFilter(MaterialRequestReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));

	    return new Double[][] {
	    		{getIssueAmount(mat_1), getIssueAmount(mat_2), getIssueAmount(mat_3)}
	    };
	}
	
	public Double getIssueAmount(Record[] records) {
		Double issued_amount = 0.0;
		for (Record record : records) {
			issued_amount += record.getAttributeAsDouble("issued_amount");
		}
		return issued_amount;
	}
}
