package com.smart.mis.client.function.security;
import com.smart.mis.shared.FieldVerifier;
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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
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

public class UserDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm, normalForm;
    private Label editorLabel;  
    private UserListGrid userListGrid; 
    private DataSource userDataSource, permissionDataSource;
    private HLayout outlineForm;
    private IButton saveButton;
    
    public UserDetailTabPane(DataSource userDS , UserListGrid userGrid){
    	this.userListGrid = userGrid;
    	this.userDataSource = userDS;
    	this.permissionDataSource = PermissionDS.getInstance();
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(userDS);  
        itemViewer.setWidth(400);  
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
        outlineForm.setMargin(10);
        
        editorForm = new DynamicForm();  
        editorForm.setWidth(270);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(userDS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลผู้ใช้ระบบ");
//        HLayout inlineForm = new HLayout();
//        inlineForm.setWidth(650);
//        inlineForm.setHeight(50);
        
        normalForm = new DynamicForm(); 
        normalForm.setAutoFocus(false);
        normalForm.setCellPadding(2);
        normalForm.setMargin(5); 
        normalForm.setNumCols(2); 
        normalForm.setWidth(270); 
        normalForm.setDataSource(userDS);  
        normalForm.setUseAllDataSourceFields(false); 
        normalForm.setIsGroup(true);
        normalForm.setGroupTitle("ข้อมูลทั่วไป");
               
//        VLayout subInlineForm = new VLayout();
//        subInlineForm.setWidth(170);
//        //subInlineForm.setHeight(50);
//        
//        reportForm = new DynamicForm(); 
//        reportForm.setAutoFocus(false);
//        reportForm.setCellPadding(5); 
//        reportForm.setMargin(5); 
//        reportForm.setWidth(170);
//        reportForm.setDataSource(permissionDS);  
//        reportForm.setUseAllDataSourceFields(false); 
//        reportForm.setIsGroup(true);
//        reportForm.setGroupTitle("สิทธิเจ้าของกิจการ");
//        //reportForm.setAlign(Alignment.LEFT);
//        
//        adminForm = new DynamicForm(); 
//        adminForm.setAutoFocus(false);
//        adminForm.setMargin(5); 
//        adminForm.setWidth(170);
//        adminForm.setCellPadding(5);
//        adminForm.setDataSource(permissionDS);  
//        adminForm.setUseAllDataSourceFields(false); 
//        adminForm.setIsGroup(true);
//        adminForm.setGroupTitle("สิทธิผู้ดูและระบบ");
//        //adminForm.setAlign(Alignment.LEFT);
//        
        //editorForm
        StaticTextItem uname = new StaticTextItem("uname", "ชื่อผู้ใช้");
        uname.setRequired(true);
        SelectItem profile = new SelectItem("name", "สิทธิการใช้งาน");
        //Setup for fetch update data on Data Source
        profile.setValueField("name");
        profile.setDisplayField("name");
        profile.setOptionDataSource(this.permissionDataSource);
        profile.setFetchMissingValues(true);
        profile.setAlwaysFetchMissingValues(true);
        profile.setRequired(true);
        ListGridField profile_name = new ListGridField("name");  
        ListGridField profile_status = new ListGridField("status"); 
        profile.setPickListFields(profile_name, profile_status);
        //End setup
        CheckboxItem status = new CheckboxItem("status", "สถานะ");
        PasswordItem pwd = new PasswordItem("pwd", "รหัสผ่าน");
		CustomValidator cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				return FieldVerifier.isValidName((String) value);
			}
		};
		pwd.setValidators(cv);
		PasswordItem npwd = new PasswordItem("npwd", "ยืนยันรหัสผ่าน");
		MatchesFieldValidator matchesValidator = new MatchesFieldValidator();
		matchesValidator.setOtherField("pwd");
		matchesValidator.setErrorMessage("ยืนยันรหัสผ่านไม่ถูกต้อง");
		npwd.setValidators(matchesValidator);
		
		pwd.setRequired(true);
		npwd.setRequired(true);
		pwd.setHint("*");
		npwd.setHint("*");
		
        //normalForm
		SelectItem title = new SelectItem("title", "คำนำหน้าชื่อ");
		title.setWidth(75);
		TextItem fname = new TextItem("fname", "ชื่อ");
		TextItem lname = new TextItem("lname", "นามสกุล");
		TextItem email = new TextItem("email", "อีเมล");
		TextItem position = new TextItem("position", "ตำแหน่ง");
		
		title.setRequired(true);
		fname.setRequired(true);
		lname.setRequired(true);
		fname.setHint("*");
		lname.setHint("*");
		
//        final CheckboxItem canSale = new CheckboxItem("cSale");
//        final CheckboxItem canProduct = new CheckboxItem("cProd");
//        final CheckboxItem canInven = new CheckboxItem("cInv");
//        final CheckboxItem canPurchase = new CheckboxItem("cPurc");
//        final CheckboxItem canFinance = new CheckboxItem("cFin");
//        //reportForm
//        final CheckboxItem canReport = new CheckboxItem("cRep");
//        //adminForm
//        final CheckboxItem canAdmin = new CheckboxItem("cAdm");
//
//        role.addChangeHandler(new ChangeHandler() {  
//            public void onChange(ChangeEvent event) {  
//                String selectedItem = (String) event.getValue();  
//                if (selectedItem.equalsIgnoreCase("Staff")){
//                	canReport.setValue(false);
//                	canReport.setDisabled(true);
//                	canAdmin.setValue(false);
//                	canAdmin.setDisabled(true);
//                } else if (selectedItem.equalsIgnoreCase("Manager")) {
//                	canReport.setDisabled(false);
//                	canAdmin.setValue(false);
//                	canAdmin.setDisabled(true);
//                } else if (selectedItem.equalsIgnoreCase("Administrator")) {
//                	canReport.setDisabled(false);
//                	canAdmin.setDisabled(false);
//                }
//            }  
//        });
//        
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขข้อมูลผู้ใช้", "ท่านต้องการแก้ไขข้อมูลผู้ใช้" + (String) editorForm.getValue("uname") + "หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
			            	saveData();
						}
					}
            		
            	});
            }  
        }); 
//
//        saveButton.setDisabled(true);
//        
        editorForm.setFields(uname, profile, status, pwd, npwd);
        editorForm.setColWidths(80, 150); 
        normalForm.setFields(title,fname,lname,email, position );
        normalForm.setColWidths(80, 150);
//        reportForm.setFields(canReport);
//        adminForm.setFields(canAdmin);
//        
//        subInlineForm.addMembers(reportForm, adminForm);
//        inlineForm.addMembers(normalForm, subInlineForm, saveButton);
        outlineForm.addMembers(editorForm, normalForm, saveButton);
       //
        
        Tab viewTab = new Tab("ข้อมูลผู้ใช้ระบบ");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        viewTab.setPane(itemViewer);  
  
        Tab editTab = new Tab("แก้ไข");  
        editTab.setIcon("icons/16/icon_edit.png");  
        editTab.setWidth(70);  
        //editTab.setPane(editorForm);
        editTab.setPane(outlineForm);
        
        setTabs(viewTab, editTab);  
        
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
                normalForm.editNewRecord();
//                reportForm.editNewRecord();
//                adminForm.editNewRecord();
            } else {  
                updateTab(1, editorLabel);  
            }  
        }  
    }  
  
    public void updateDetails() {  
        Record selectedRecord  = userListGrid.getSelectedRecord();  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
        } else {  
            // edit tab : show record editor  
        	if (selectedRecord != null) {
//        		saveButton.setDisabled(false);
        		editorForm.editRecord(selectedRecord);  
        		editorForm.setValue("npwd", editorForm.getValueAsString("pwd"));
        		editorForm.setValue("name", selectedRecord.getAttributeAsString("pname"));
                normalForm.editRecord(selectedRecord);  
//                String role = selectedRecord.getAttribute("role");
//                if (role.equalsIgnoreCase("Staff")){
//                	reportForm.getField("cRep").setDisabled(true);
//                	adminForm.getField("cAdm").setDisabled(true);
//                } else if (role.equalsIgnoreCase("Manager")) {
//                	reportForm.getField("cRep").setDisabled(false);
//                	adminForm.getField("cAdm").setDisabled(true);
//                } else {
//                	reportForm.getField("cRep").setDisabled(false);
//                	adminForm.getField("cAdm").setDisabled(false);
//                }
//                reportForm.editRecord(selectedRecord);  
//                adminForm.editRecord(selectedRecord);  
        	}
        }  
    }  
    
    public void updateDetails(Record selectedRecord) {
    	int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
        } else { 
//        	saveButton.setDisabled(false);
    		editorForm.editRecord(selectedRecord);  
    		editorForm.setValue("npwd", editorForm.getValueAsString("pwd"));
    		editorForm.setValue("name", selectedRecord.getAttributeAsString("pname"));
            normalForm.editRecord(selectedRecord);  
//            String role = selectedRecord.getAttribute("role");
//            if (role.equalsIgnoreCase("Staff")){
//            	reportForm.getField("cRep").setDisabled(true);
//            	adminForm.getField("cAdm").setDisabled(true);
//            } else if (role.equalsIgnoreCase("Manager")) {
//            	reportForm.getField("cRep").setDisabled(false);
//            	adminForm.getField("cAdm").setDisabled(true);
//            } else {
//            	reportForm.getField("cRep").setDisabled(false);
//            	adminForm.getField("cAdm").setDisabled(false);
//            }
//            reportForm.editRecord(selectedRecord);  
//            adminForm.editRecord(selectedRecord); 
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate() && normalForm.validate()) {
	    	Record updateRecord = UserData.createRecord(
	    			(String) editorForm.getValue("uname"),
	    			(String) editorForm.getValue("pwd"),
	    			(String) normalForm.getValue("title"),
	    	    	(String) normalForm.getValue("fname"),
	    	    	(String) normalForm.getValue("lname"),
	    	    	(String) normalForm.getValue("email"),
	    	    	(String) normalForm.getValue("position"),
	    	    	(String) editorForm.getValue("name"),
	    	    	(Boolean) editorForm.getValue("status")
	    			);
	    	
	    	userDataSource.updateData(updateRecord);
    	} else {
    		SC.warn("ข้อมูลผู้ใช้ไม่ถูกต้อง");
    	}
    }
}
