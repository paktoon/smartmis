package com.smart.mis.client.function.purchasing.supplier;
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
import com.smartgwt.client.widgets.Canvas;
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
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;  
import com.smartgwt.client.widgets.tab.TabSet;  
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;  
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;  
import com.smartgwt.client.widgets.viewer.DetailViewer;  

public class SupplierDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm; //, normalForm;
    private ListGrid historyGrid, viewItemGrid, editItemGrid;
    private Label editorLabel;  
    private SupplierListGrid supplierListGrid; 
    private SupplierDS supplierDataSource;
    //private PermissionDS permissionDataSource;
    private HLayout outlineForm;
    private IButton saveButton, cancelButton;
//    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
//    private SelectItem profile;
    
    public SupplierDetailTabPane(SupplierDS DS , SupplierListGrid Grid){
    	this.supplierListGrid = Grid;
    	this.supplierDataSource = DS;
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(DS);  
        itemViewer.setWidth(450);  
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
        editorForm.setHeight(300);
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(DS);  
        editorForm.setUseAllDataSourceFields(true); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลผู้จำหน่าย");
        
        historyGrid = new ListGrid();
        
        //editorForm
        StaticTextItem id = new StaticTextItem("sid", "รหัสผู้จำหน่าย");
        id.setRequired(true);
        
		TextItem sup_name = new TextItem("sup_name", "ชื่อผู้จำหน่าย");
		TextItem sup_phone1 = new TextItem("sup_phone1", "หมายเลขโทรศัพท์ 1");
		TextItem sup_phone2 = new TextItem("sup_phone2", "หมายเลขโทรศัพท์ 2");
		TextItem fax = new TextItem("fax", "หมายเลขโทรสาร");
		TextItem email = new TextItem("email", "อีเมล");

		TextAreaItem address = new TextAreaItem("address", "ที่อยู่");
		address.setWidth(300);
		address.setRowSpan(3);
		TextItem leadtime = new TextItem("leadtime", "ระยะเวลาส่งสินค้า");
		
		sup_name.setRequired(true);
		sup_phone1.setRequired(true);
		address.setRequired(true);
		leadtime.setRequired(true);
		sup_name.setHint("*");
		sup_phone1.setHint("*");
		address.setHint("*");
		leadtime.setHint("วัน *");
		
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขข้อมูล", "ท่านต้องการแก้ไขข้อมูลผู้จำหน่าย " + (String) editorForm.getValue("sid") + " หรือไม่ ?" , new BooleanCallback() {
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
            	SC.confirm("ยกเลิกการแก้ไขข้อมูล", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูล หรือไม่ ?" , new BooleanCallback() {
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
        
        sup_name.setWidth(250);
        sup_phone1.setWidth(250);
        sup_phone2.setWidth(250);
        email.setWidth(250);
        editorForm.setFields(id, sup_name, sup_phone1, sup_phone2, fax, email, address, leadtime);
        editorForm.setColWidths(200	, 300); 
        VLayout editor_control = new VLayout();
        editor_control.addMembers(saveButton, cancelButton);
        editor_control.setWidth(200);
        outlineForm.addMembers(editorForm, getEditItemList(null), editor_control);
        
        Tab viewTab = new Tab("ข้อมูลผู้จำหน่าย");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        HLayout tempLayout = new HLayout();
        tempLayout.addMembers(itemViewer, getViewItemList(null));
        viewTab.setPane(tempLayout);
  
        Tab historyTab = new Tab("ประวัติการจัดซื้อ");
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
        Record selectedRecord  = supplierListGrid.getSelectedRecord();  
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
    		
    		String sid = (String) editorForm.getValue("sid");
			String sup_name = (String) editorForm.getValue("sup_name");
			String sup_phone1 = (String) editorForm.getValue("sup_phone1");
	    	String sup_phone2 = (String) editorForm.getValue("sup_phone2");
	    	String email = (String) editorForm.getValue("email");
	    	String address = (String) editorForm.getValue("address");
	    	Integer leadtime = (Integer) editorForm.getValue("leadtime");
	    	String fax = (String) editorForm.getValue("fax");
	    	
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
						Record updateRecord = SupplierData.createRecord(
								(String) editorForm.getValue("sid"),
				    			(String) editorForm.getValue("sup_name"),
				    			(String) editorForm.getValue("sup_phone1"),
				    			(String) editorForm.getValue("sup_phone2"),
				    	    	(String) editorForm.getValue("email"),
				    	    	(String) editorForm.getValue("address"),
				    	    	(String) editorForm.getValue("fax"),
				    	    	(Integer) editorForm.getValue("leadtime"),
				    	    	new String[] {"TBD"}
				    			);
						supplierDataSource.updateData(updateRecord);
//					} else {
//						SC.warn("Updating user Fails - please contact administrator");
//					}
//				}
//			});
    	} else {
    		SC.warn("ข้อมูลไม่ถูกต้อง");
    	}
    }
    
    private SectionStack getViewItemList(String listId){
    	
    	SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(300);
        String title = Canvas.imgHTML("icons/16/basket-full-icon.png", 15, 15) + " วัตถุดิบที่จำหน่าย";
        SectionStackSection section = new SectionStackSection(title);  
        section.setCanCollapse(false);  
        section.setExpanded(true);
        
        viewItemGrid = new ListGrid();
        viewItemGrid.setEmptyMessage("No Item to show.");
        
        section.setItems(viewItemGrid);  
        sectionStack.setSections(section);  
        return sectionStack;
    }
    
    private SectionStack getEditItemList(String listId){
    	
    	SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(300);
        String title = Canvas.imgHTML("icons/16/basket-full-icon.png", 15, 15) + " วัตถุดิบที่จำหน่าย";
        SectionStackSection section = new SectionStackSection(title);  
        section.setCanCollapse(false);  
        section.setExpanded(true);
        
        editItemGrid = new ListGrid();
        editItemGrid.setEmptyMessage("No Item to show.");
        editItemGrid.setWidth100();
        
        section.setItems(editItemGrid);  
        sectionStack.setSections(section);  
        return sectionStack;
    }
    
    public void onRefresh() {
    	supplierDataSource.refreshData();
    	//permissionDataSource.refreshData();
    	supplierListGrid.invalidateCache();
//    	profile.invalidateDisplayValueCache();
    }
}
