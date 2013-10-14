package com.smart.mis.client.function.security.user;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class UserListGrid extends ListGrid {

	public UserListGrid() {
		setWidth100();  
        setHeight(200);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(UserDS.getInstance());
        setUseAllDataSourceFields(true);
        
        ListGridField uid = new ListGridField("uid", 120);
        ListGridField uname = new ListGridField("uname", 120);
        uname.setShowHover(true);
        ListGridField pwd = new ListGridField("pwd");
        
        ListGridField creator = new ListGridField("creator");
        ListGridField when = new ListGridField("when");

        ListGridField title = new ListGridField("title", 75);
        ListGridField fname = new ListGridField("fname", 120);
        ListGridField lname = new ListGridField("lname", 120);
        ListGridField email = new ListGridField("email");
        ListGridField position = new ListGridField("position", 120);
        ListGridField pname = new ListGridField("pname", 100);
        ListGridField status = new ListGridField("status", 50);
        
        setFields(uid, uname, pwd, creator, when, title, fname, lname, email, position, pname, status);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("pwd", "creator","when");
        //fetchData();
	}
	
	public void addUpdateDetailHandler(final UserDetailTabPane itemDetailTabPane){
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
