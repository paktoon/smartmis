package com.smart.mis.client.function.report.inventory;

import com.google.gwt.i18n.client.NumberFormat;
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

public class MaterialReportListGrid extends ListGrid {

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
	
	public MaterialReportListGrid() {
		setWidth(1000);  
		setHeight(300);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(MaterialDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        
        ListGridField field_1 = new ListGridField("mid", 120);
        ListGridField field_2 = new ListGridField("mat_name",200);
        field_2.setShowHover(true);
        ListGridField field_3 = new ListGridField("desc");
        ListGridField field_3_1 = new ListGridField("weight");
        ListGridField field_4 = new ListGridField("type",100);
        
        ListGridField Field_6_1 = new ListGridField("inStock", 100);
        ListGridField Field_6_2 = new ListGridField("remain", 100);
        ListGridField Field_6_3 = new ListGridField("reserved", 100);
        ListGridField field_7 = new ListGridField("unit", 50);
        //ListGridField field_8 = new ListGridField("sup_list");
        
        //Cell Format
        Field_6_1.setAlign(Alignment.RIGHT);
        Field_6_1.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_6_2.setAlign(Alignment.RIGHT);
        Field_6_2.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_6_3.setAlign(Alignment.RIGHT);
        Field_6_3.setCellFormatter(FieldFormatter.getNumberFormat());
        field_7.setAlign(Alignment.CENTER);
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2, field_3, field_3_1, field_4, Field_6_1, Field_6_2, Field_6_3, field_7);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
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
