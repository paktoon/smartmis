package com.smart.mis.client.function.production.smith;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.Record;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;  
import com.smartgwt.client.widgets.tab.TabSet;  
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;  
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;  
import com.smartgwt.client.widgets.viewer.DetailViewer;  

public class SmithDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm; //, normalForm;
    private ListGrid historyGrid;
    private Label editorLabel;  
    private SmithListGrid ListGrid; 
    private SmithDS DataSource;
    private HLayout outlineForm;
    private IButton saveButton, cancelButton;
    
    public SmithDetailTabPane(SmithDS DS , SmithListGrid Grid){
    	this.ListGrid = Grid;
    	this.DataSource = DS;
    	setHeight("70%");
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(DS);  
        itemViewer.setWidth(600);  
        itemViewer.setMargin(5);  
        itemViewer.setEmptyMessage("Select an item to view its details");
        
        editorLabel = new Label();  
        editorLabel.setWidth100();  
        editorLabel.setHeight100();  
        editorLabel.setAlign(Alignment.CENTER);  
        editorLabel.setContents("Select a record to edit its details");  

        outlineForm = new HLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(5);
        
        editorForm = new DynamicForm();  
        editorForm.setWidth(450);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(DS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลช่าง");
        
        historyGrid = new ListGrid();
        
        //editorForm
        StaticTextItem smid = new StaticTextItem("smid", "รหัสช่าง");
        smid.setRequired(true);
		TextItem name = new TextItem("name", "ชื่อช่าง");
		TextItem phone1 = new TextItem("phone1", "หมายเลขโทรศัพท์ 1");
		TextItem phone2 = new TextItem("phone2", "หมายเลขโทรศัพท์ 2");
		TextItem email = new TextItem("email", "อีเมล");

		TextAreaItem address = new TextAreaItem("address", "ที่อยู่");
		address.setWidth(300);
		address.setRowSpan(3);
		
		
		name.setRequired(true);
		phone1.setRequired(true);
		address.setRequired(true);
		name.setHint("*");
		phone1.setHint("*");
		address.setHint("*");
		
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขข้อมูลช่าง", "ท่านต้องการแก้ไขข้อมูลช่าง " + (String) editorForm.getValue("smid") + " หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
			            	saveData();
						}
					}
            		
            	});
            }  
        }); 
        
        cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setMargin(10);
        cancelButton.setWidth(150);  
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยกเลิกการแก้ไขข้อมูลช่าง", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูลช่าง หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							updateDetails();
							selectTab(0); // back to detail tab
						}
					}
            		
            	});
            }  
        });

        saveButton.setDisabled(true);
        cancelButton.setDisabled(true);
        
        name.setWidth(250);
        phone1.setWidth(250);
        phone2.setWidth(250);
        email.setWidth(250);
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(smid, name, phone1, phone2, email, address);
        editorForm.setColWidths(200	, 300); 
        VLayout editor_control = new VLayout();
        editor_control.addMembers(saveButton, cancelButton);
        outlineForm.addMembers(editorForm, editor_control);
        
        Tab viewTab = new Tab("ข้อมูลช่าง");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        viewTab.setPane(itemViewer);
  
        Tab historyTab = new Tab("ประวัติการสั่งผลิต");
        historyTab.setIcon("icons/16/application_form.png");  
        historyTab.setWidth(70);
        historyTab.setPane(historyGrid); 
        
        Tab editTab = new Tab("แก้ไข");  
        editTab.setIcon("icons/16/icon_edit.png");  
        editTab.setWidth(70);  
        editTab.setPane(outlineForm);
        
        setTabs(viewTab, historyTab, editTab);  
        
        addTabSelectedHandler(new TabSelectedHandler() {  
            public void onTabSelected(TabSelectedEvent event) {  
                updateDetails();  
            }  
        });
    }
    
    public void clearDetails(Record selectedRecord) {  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData((Record[]) null);  
        } else {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
                updateTab(1, outlineForm);
                editorForm.editNewRecord();
            } else {  
                updateTab(1, editorLabel);  
            }  
        }  
    }  
  
    public void updateDetails() {  
        Record selectedRecord  = ListGrid.getSelectedRecord();  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});
        } else {  
            // edit tab : show record editor  
        	if (selectedRecord != null) {
        		saveButton.setDisabled(false);
        		cancelButton.setDisabled(false);
//        		profile.invalidateDisplayValueCache();
        		editorForm.editRecord(selectedRecord);
        	}
        }  
    }  
    
    public void updateDetails(Record selectedRecord) {
    	int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
        } else { 
        	saveButton.setDisabled(false);
        	cancelButton.setDisabled(false);
//        	profile.invalidateDisplayValueCache();
    		editorForm.editRecord(selectedRecord);
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate()) {
    		
    		String smid = editorForm.getValueAsString("smid");
			String name = editorForm.getValueAsString("name");
			String phone1 = editorForm.getValueAsString("phone1");
	    	String phone2 = editorForm.getValueAsString("phone2");
	    	String email = editorForm.getValueAsString("email");
	    	String address = editorForm.getValueAsString("address");
	    	
//	    	User updatedUser = new User(uname, pwd, fname, lname, email, position, title, status);
//	    	securityService.updateUserOnServer(updatedUser, pname, this.user, new AsyncCallback<Boolean>() {
//				@Override
//				public void onFailure(Throwable caught) {
//					SC.warn("Updating permission Fails - please contact administrator");
//				}
//				@Override
//				public void onSuccess(Boolean result) {
//					if (result)
//					{
//						//System.out.println("*** Update result => " + result);
						Record updateRecord = SmithData.createRecord(
								editorForm.getValueAsString("smid"),
								editorForm.getValueAsString("name"),
								editorForm.getValueAsString("phone1"),
								editorForm.getValueAsString("phone2"),
								editorForm.getValueAsString("email"),
								editorForm.getValueAsString("address")
				    			);
						DataSource.updateData(updateRecord);
						SC.warn("แก้ไขข้อมูลช่างเรียบร้อยแล้ว");
//					} else {
//						SC.warn("Updating user Fails - please contact administrator");
//					}
//				}
//			});
    	} else {
    		SC.warn("ข้อมูลช่างไม่ถูกต้อง");
    	}
    }
    
    public void onRefresh() {
    	DataSource.refreshData();
    	//permissionDataSource.refreshData();
    	ListGrid.invalidateCache();
//    	profile.invalidateDisplayValueCache();
    }
}
