package com.smart.mis.client.function.sale.customer;

import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class CustomerListGrid extends ListGrid {

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
		if (getFieldName(colNum).equals("cus_type")) {
			if (record.getAttributeAsString("cus_type").equalsIgnoreCase("ลูกค้าทั่วไป")) {
				return "font-weight:bold; color:#287fd6;";
			} else if (record.getAttributeAsString("cus_type").equalsIgnoreCase("ลูกค้าประจำ")) {  
                return "font-weight:bold; color:#d64949;";  
            } else {  
                return super.getCellCSSText(record, rowNum, colNum);  
            }
		} else {  
            return super.getCellCSSText(record, rowNum, colNum);  
        } 
	}
	
	public CustomerListGrid() {
		setWidth100();  
		setHeight("30%");  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(CustomerDS.getInstance());
        setUseAllDataSourceFields(true);
        
        //setGroupStartOpen(GroupStartOpen.ALL);
        //setGroupByField("zone"); 
        
        ListGridField field_1 = new ListGridField("cid", 120);
        ListGridField field_2 = new ListGridField("cus_name");
        field_2.setShowHover(true);
        ListGridField field_3 = new ListGridField("cus_phone");
        ListGridField field_4 = new ListGridField("contact_name",150);
        ListGridField field_5 = new ListGridField("contact_phone");
        ListGridField field_6 = new ListGridField("contact_email",200);

        ListGridField field_7 = new ListGridField("address");
        ListGridField field_7_1 = new ListGridField("street");
        ListGridField field_7_2 = new ListGridField("city");
        ListGridField field_7_3 = new ListGridField("state");
        ListGridField field_7_4 = new ListGridField("country");
        ListGridField field_7_5 = new ListGridField("postal");
        
        ListGridField field_8 = new ListGridField("cus_type", 100);
        ListGridField field_8_1 = new ListGridField("bus_type", 100);
        ListGridField field_8_2 = new ListGridField("cus_group", 100);
        ListGridField field_9 = new ListGridField("url", 100);
        ListGridField field_10 = new ListGridField("zone", 80);
        
        setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_7_1, field_7_2, field_7_3, field_7_4, field_7_5, field_8, field_8_2, field_8_1, field_9, field_10);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("contact_name","contact_email","url","cus_phone", "contact_phone", "address", "street", "city", "state", "country" , "postal");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final CustomerDetailTabPane itemDetailTabPane){
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
