package com.smart.mis.client.function.production.product;

import com.google.gwt.i18n.client.NumberFormat;
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

public class ProductListGrid extends ListGrid {

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
		if (getFieldName(colNum).equals("remain")) {
			if (record.getAttributeAsInt("remain") == 0) {
				return "font-weight:bold; color:#d64949;";
			} else {  
                return super.getCellCSSText(record, rowNum, colNum);  
            }
		} else {  
            return super.getCellCSSText(record, rowNum, colNum);  
        } 
	}
	
	public ProductListGrid() {
		setWidth100();  
		setHeight("30%");  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(ProductDS.getInstance());
        setUseAllDataSourceFields(false);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        
        ListGridField field_1 = new ListGridField("pid", 120);
        ListGridField field_2_1 = new ListGridField("name",150);
        ListGridField field_2_2 = new ListGridField("name_th");

        //ListGridField field_3 = new ListGridField("size");
        ListGridField field_3 = new ListGridField("weight",100);
        ListGridField field_4_1 = new ListGridField("price", 80);
        ListGridField field_5 = new ListGridField("type");
        
        ListGridField Field_6 = new ListGridField("remain", 100);
        ListGridField field_7 = new ListGridField("unit", 50);
        
        //Cell Format
        field_3.setAlign(Alignment.RIGHT);
        field_3.setCellFormatter(FieldFormatter.getNumberFormat());
        field_4_1.setAlign(Alignment.RIGHT);
        field_4_1.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_6.setAlign(Alignment.RIGHT);
        Field_6.setCellFormatter(FieldFormatter.getIntegerFormat());
        field_7.setAlign(Alignment.CENTER);
        
        setFields(field_1, field_2_1, field_2_2, field_3, field_4_1, field_5, Field_6, field_7);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final ProductDetailTabPane itemDetailTabPane){
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
