package com.smart.mis.client.function.report.purchasing;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class PurchasingListGrid extends ListGrid {

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
	
	public PurchasingListGrid(String type) {
		setWidth(850);  
		setHeight(350);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(PurchasingReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField(type);
        
        ListGridField field_1 = new ListGridField("order_id", 80);
        ListGridField field_2_1 = new ListGridField("mid",80);
        ListGridField field_2_2 = new ListGridField("mat_name");
        ListGridField field_2_3 = new ListGridField("type");
        ListGridField field_2_4 = new ListGridField("unit", 70);
        
        ListGridField field_3_1 = new ListGridField("request_amount", 150);
        ListGridField field_3_2 = new ListGridField("sum_price", 150);
        
        ListGridField Field_4 = new ListGridField("created_date", 150);
        //ListGridField field_8 = new ListGridField("sup_list");
        
        Field_4.setGroupValueFunction(new GroupValueFunction() {
            public Object getGroupValue(Object value, ListGridRecord record, ListGridField field, String fieldName, ListGrid grid) {
            	return DateTimeFormat.getFormat( "MM/dd/yyyy" ).format( (Date) value );
            }
        });
        
        Field_4.setGroupTitleRenderer(new GroupTitleRenderer() {

			@Override
			public String getGroupTitle(Object groupValue, GroupNode groupNode,
					ListGridField field, String fieldName, ListGrid grid) {
				// TODO Auto-generated method stub
				return (String) groupValue;
			}
        });
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getPriceFormat());
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_1, field_2_4, field_3_2, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields(type);
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
