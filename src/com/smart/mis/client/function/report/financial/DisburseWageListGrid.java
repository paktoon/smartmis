package com.smart.mis.client.function.report.financial;

import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class DisburseWageListGrid extends ListGrid {

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
        
        ListGridField field_1 = new ListGridField("job_id", 80);
        ListGridField field_2_1 = new ListGridField("smid",80);
        ListGridField field_2_2 = new ListGridField("sname");
        ListGridField field_2_3 = new ListGridField("stype");
        
        ListGridField field_3 = new ListGridField("paidInclusive", 120);
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
	
//	public void addUpdateDetailHandler(final MaterialDetailTabPane itemDetailTabPane){
//        addRecordClickHandler(new RecordClickHandler() {  
//			@Override
//			public void onRecordClick(RecordClickEvent event) {
//				itemDetailTabPane.updateDetails();  
//			}  
//        });  
//  
//        addCellSavedHandler(new CellSavedHandler() {  
//			@Override
//			public void onCellSaved(CellSavedEvent event) {
//				itemDetailTabPane.updateDetails();  
//			}  
//        }); 
//		
//	}
}