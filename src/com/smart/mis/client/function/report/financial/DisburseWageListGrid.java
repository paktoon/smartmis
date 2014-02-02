package com.smart.mis.client.function.report.financial;

import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
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

public class DisburseWageListGrid extends ListGrid {

	private Double[][] dataTable;
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
	
	public DisburseWageListGrid() {
		setWidth(600);  
		setHeight(350);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(WageDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("stype"); 
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("job_id", 80);
        ListGridField field_2_1 = new ListGridField("smid",80);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("sname");
        ListGridField field_2_3 = new ListGridField("stype");
        
        ListGridField field_3 = new ListGridField("paidInclusive", 120);
        field_3.setShowGroupSummary(true);
        field_3.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField Field_4 = new ListGridField("paid_date", 120);
        ListGridField field_5 = new ListGridField("status");
        
        //Cell Format
        field_3.setAlign(Alignment.RIGHT);
        field_3.setCellFormatter(FieldFormatter.getPriceFormat());
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3, Field_4, field_5);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("stype", "status");
        //fetchData();
	}
	
	public Double[][] createDataTable(Criterion criteria){
		
		if (dataTable !=null) return dataTable;
		
		WageDS.getInstance().refreshData();
	    
	    Record[] mat_1 = WageDS.getInstance().applyFilter(WageDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("stype", OperatorId.EQUALS, "หล่อขึ้นรูป")}));
	    Record[] mat_2 = WageDS.getInstance().applyFilter(WageDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("stype", OperatorId.EQUALS, "แต่งและฝังพลอยประดับ")}));
	    Record[] mat_3 = WageDS.getInstance().applyFilter(WageDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("stype", OperatorId.EQUALS, "ขัดและติดพลอยแมกกาไซต์")}));
	  
		    return new Double[][] {
		    		{getPaidInclusive(mat_1), getPaidInclusive(mat_2), getPaidInclusive(mat_3) }
		    };
	}
	
	public Double getPaidInclusive(Record[] records) {
		Double paidInclusive = 0.0;
		for (Record record : records) {
			paidInclusive += record.getAttributeAsDouble("paidInclusive");
		}
		return paidInclusive;
	}
	
	public DisburseWageListGrid(Criterion criteria) {
		setWidth(400);
		setHeight(150);
		setAlign(Alignment.CENTER);
		
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setMargin(10);
        //setShowGridSummary(true);
        //setGroupStartOpen(GroupStartOpen.ALL);
        //setGroupByField("group"); 
        setShowGridSummary(true);
        
        ListGridField field_1 = new ListGridField("type", "ประเภทงานผลิต");
        field_1.setShowGridSummary(true);
        field_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        
      ListGridField Field_6_1 = new ListGridField("sum_paid", "ยอดชำระค่าจ้างผลิต (บาท)", 170);
      Field_6_1.setShowGridSummary(true);
      Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
        
        //Cell Format
      Field_6_1.setAlign(Alignment.RIGHT);
      Field_6_1.setCellFormatter(FieldFormatter.getPriceFormat());
        
        setFields(field_1, Field_6_1);
        
        setRecords(createListGridRecord(criteria));
        setHoverWidth(200);  
        setHoverHeight(20);
	}
	
	public ListGridRecord[] createListGridRecord(Criterion criteria) {
		
		WageDS.getInstance().refreshData();
	    
	    Record[] mat_1 = WageDS.getInstance().applyFilter(WageDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("stype", OperatorId.EQUALS, "หล่อขึ้นรูป")}));
	    Record[] mat_2 = WageDS.getInstance().applyFilter(WageDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("stype", OperatorId.EQUALS, "แต่งและฝังพลอยประดับ")}));
	    Record[] mat_3 = WageDS.getInstance().applyFilter(WageDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("stype", OperatorId.EQUALS, "ขัดและติดพลอยแมกกาไซต์")}));
	  
		dataTable = new Double[][] {
		    		{getPaidInclusive(mat_1), getPaidInclusive(mat_2), getPaidInclusive(mat_3) }
		    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("หล่อขึ้นรูป",getPaidInclusive(mat_1)),
    			createRecord("แต่งและฝังพลอยประดับ",getPaidInclusive(mat_2)),
    			createRecord("ขัดและติดพลอยแมกกาไซต์",getPaidInclusive(mat_3))
    	};
	}
	
	public ListGridRecord createRecord(String type, Double sum_price){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("sum_paid", sum_price);
        return record;
	}
}
