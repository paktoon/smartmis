package com.smart.mis.client.function.report.inventory;

import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ProductReceivedReportListGrid extends ListGrid {

//	@Override
//	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
//		if (getFieldName(colNum).equals("remain")) {
//			if (record.getAttributeAsInt("remain") == 0) {
//				return "font-weight:bold; color:#d64949;";
//			} else {  
//                return super.getCellCSSText(record, rowNum, colNum);  
//            }
//		} else {  
//            return super.getCellCSSText(record, rowNum, colNum);  
//        } 
//	}
	
	public ProductReceivedReportListGrid() {
		setWidth(950);
		setHeight(300);
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(ProductReceivedReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        
        ListGridField field_1 = new ListGridField("transfer_id", 120);
        ListGridField field_2_1 = new ListGridField("pid",150);
        ListGridField field_2_2 = new ListGridField("name");
        ListGridField field_2_3 = new ListGridField("type");
        
        ListGridField field_3_1 = new ListGridField("recv_weight",150);
        ListGridField field_3_2 = new ListGridField("recv_amount", 150);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("received_date", 100);
        //ListGridField Field_6_2 = new ListGridField("remain", 100);
        //ListGridField Field_6_3 = new ListGridField("reserved", 100);
        //ListGridField field_7 = new ListGridField("unit", 50);
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_1, field_3_2, field_3_3, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
//	public void addUpdateDetailHandler(final ProductDetailTabPane itemDetailTabPane){
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
