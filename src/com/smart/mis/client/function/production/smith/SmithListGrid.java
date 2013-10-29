package com.smart.mis.client.function.production.smith;

import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class SmithListGrid extends ListGrid {

//	@Override
//	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
//		if (getFieldName(colNum).equals("cus_type")) {
//			if (record.getAttributeAsString("cus_type").equalsIgnoreCase("ลูกค้าทั่วไป")) {
//				return "font-weight:bold; color:#287fd6;";
//			} else if (record.getAttributeAsString("cus_type").equalsIgnoreCase("ลูกค้าประจำ")) {  
//                return "font-weight:bold; color:#d64949;";  
//            } else {  
//                return super.getCellCSSText(record, rowNum, colNum);  
//            }
//		} else {  
//            return super.getCellCSSText(record, rowNum, colNum);  
//        } 
//	}
	
	public SmithListGrid() {
		setWidth100();  
        //setHeight(200); 
        setHeight("30%");
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(SmithDS.getInstance());
        setUseAllDataSourceFields(true);
        
        ListGridField field_1 = new ListGridField("smid", 120);
        ListGridField field_2 = new ListGridField("name",250);
        field_2.setShowHover(true);
        ListGridField field_3 = new ListGridField("phone1",100);
        ListGridField field_4 = new ListGridField("phone2",100);
        ListGridField field_5 = new ListGridField("email");
        ListGridField field_6 = new ListGridField("address");
        
        setFields(field_1, field_2, field_3, field_4, field_5, field_6);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("address");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final SmithDetailTabPane itemDetailTabPane){
        addRecordClickHandler(new RecordClickHandler() {  
			@Override
			public void onRecordClick(RecordClickEvent event) {
				itemDetailTabPane.updateDetails();  
			}  
        });  
  
        addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				itemDetailTabPane.updateDetails();  
			}  
        }); 
		
	}
}