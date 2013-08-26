package com.smart.mis.client.function.security;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class PermissionListGrid extends ListGrid {

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
		if (getFieldName(colNum).equals("status")) {
			if (record.getAttributeAsString("status").equalsIgnoreCase("enable")) {
				return "font-weight:bold; color:#287fd6;";
			} else if (record.getAttributeAsString("status").equalsIgnoreCase("disable")) {  
                return "font-weight:bold; color:#d64949;";  
            } else {  
                return super.getCellCSSText(record, rowNum, colNum);  
            }
		} else {  
            return super.getCellCSSText(record, rowNum, colNum);  
        } 
	}

	public PermissionListGrid() {
		setWidth100();  
        setHeight(200);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(PermissionDS.getInstance());
        setUseAllDataSourceFields(true);
        
        ListGridField pid = new ListGridField("pid");
        ListGridField permName = new ListGridField("name");
        permName.setShowHover(true);
        
        ListGridField creator = new ListGridField("creator");
        ListGridField when = new ListGridField("when");
        
        ListGridField role = new ListGridField("role", 120);
        ListGridField status = new ListGridField("status" , 100);
        
        class ImageListGridField extends ListGridField {
        	public ImageListGridField(String name, int width) {
        		super(name, width);
        		setType(ListGridFieldType.IMAGE);
        		setAlign(Alignment.CENTER);
        		setImageURLPrefix("icons/16/");
        		setImageURLSuffix(".png");
        	}
        }
        
        ImageListGridField saleField = new ImageListGridField("cSale" , 75);
        ImageListGridField productField = new ImageListGridField("cProd", 75);
        ImageListGridField invenField = new ImageListGridField("cInv" , 120);
        ImageListGridField purchaseField = new ImageListGridField("cPurc", 75);
        ImageListGridField financeField = new ImageListGridField("cFin", 75);
        ImageListGridField reportField = new ImageListGridField("cRep", 75);
        ImageListGridField adminField = new ImageListGridField("cAdm", 120);
        
        setFields(pid, permName,creator, when, role, status, saleField, productField, invenField, purchaseField, financeField, reportField, adminField);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("creator","when");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final PermissionDetailTabPane itemDetailTabPane){
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
