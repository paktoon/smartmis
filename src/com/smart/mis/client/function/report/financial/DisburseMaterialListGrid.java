package com.smart.mis.client.function.report.financial;

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

public class DisburseMaterialListGrid extends ListGrid {

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
	
	public DisburseMaterialListGrid() {
		setWidth(600);  
		setHeight(350);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(DisburseMaterialDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("order_id", 80);
        ListGridField field_2_1 = new ListGridField("mid",80);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("mat_name");
        ListGridField field_2_3 = new ListGridField("type");
        
        ListGridField field_3 = new ListGridField("sum_price", 120);
        field_3.setShowGroupSummary(true);
        field_3.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField Field_4 = new ListGridField("paid_date", 120);
        //ListGridField field_8 = new ListGridField("sup_list");
        
        //Cell Format
        field_3.setAlign(Alignment.RIGHT);
        field_3.setCellFormatter(FieldFormatter.getPriceFormat());
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
	public Double[][] createDataTable(Criterion criteria){
	    DisburseMaterialDS.getInstance().refreshData();
	    
	    Record[] mat_1 = DisburseMaterialDS.getInstance().applyFilter(DisburseMaterialDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แร่เงิน")}));
	    Record[] mat_2 = DisburseMaterialDS.getInstance().applyFilter(DisburseMaterialDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
	    Record[] mat_3 = DisburseMaterialDS.getInstance().applyFilter(DisburseMaterialDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
	    Record[] mat_4 = DisburseMaterialDS.getInstance().applyFilter(DisburseMaterialDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));

		    return new Double[][] {
		    		{getSumPrice(mat_1), getSumPrice(mat_2), getSumPrice(mat_3), getSumPrice(mat_4) }
		    };
	}
	
	public Double getSumPrice(Record[] records) {
		Double sum_price = 0.0;
		for (Record record : records) {
			sum_price += record.getAttributeAsDouble("sum_price");
		}
		return sum_price;
	}
	
}
