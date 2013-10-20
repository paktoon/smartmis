package com.smart.mis.client.function.purchasing.material;

import com.google.gwt.i18n.client.NumberFormat;
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

public class MaterialListGrid extends ListGrid {

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
	
	public MaterialListGrid() {
		setWidth100();  
        setHeight(200);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(MaterialDS.getInstance());
        setUseAllDataSourceFields(true);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        
        ListGridField field_1 = new ListGridField("mid", 120);
        ListGridField field_2 = new ListGridField("mat_name",200);
        field_2.setShowHover(true);
        ListGridField field_3 = new ListGridField("desc");
        ListGridField field_4 = new ListGridField("type",100);
        ListGridField field_5 = new ListGridField("safety",150);
        ListGridField field_6 = new ListGridField("remain",150);
        ListGridField field_7 = new ListGridField("unit",100);
        ListGridField field_8 = new ListGridField("sup_list");
        
        //Cell Format
        field_5.setAlign(Alignment.RIGHT);
        field_5.setCellFormatter(getNumberFormat());
        field_6.setAlign(Alignment.RIGHT);
        field_6.setCellFormatter(getNumberFormat());
        field_7.setAlign(Alignment.CENTER);
        
        setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setHoverWidth(200);  
        setHoverHeight(20);
        //hideFields("address");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final MaterialDetailTabPane itemDetailTabPane){
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
	
	public CellFormatter getNumberFormat(){
		return new CellFormatter() {  
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
                if (value == null) return null;  
                try {  
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");  
                    return nf.format(((Number) value).doubleValue());  
                } catch (Exception e) {  
                    return value.toString();  
                }  
            }  
        };
	}
}
