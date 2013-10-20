package com.smart.mis.client.function.purchasing.supplier;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class SupplierListGrid extends ListGrid {

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
	
	public SupplierListGrid() {
		setWidth100();  
        setHeight(150);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(SupplierDS.getInstance());
        setUseAllDataSourceFields(true);
        
        ListGridField field_1 = new ListGridField("sid", 120);
        ListGridField field_2 = new ListGridField("sup_name",250);
        field_2.setShowHover(true);
        ListGridField field_3 = new ListGridField("sup_phone1",120);
        ListGridField field_4 = new ListGridField("sup_phone2",120);
        ListGridField field_5 = new ListGridField("fax", 120);
        ListGridField field_6 = new ListGridField("email");
        ListGridField field_7 = new ListGridField("leadtime", 150);
        field_7.setAlign(Alignment.CENTER);
        ListGridField field_8 = new ListGridField("address");
        
        setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("address");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final SupplierDetailTabPane itemDetailTabPane){
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
