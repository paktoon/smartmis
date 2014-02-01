package com.smart.mis.client.function.security.user;
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

public class UserDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm, normalForm;
    private Label editorLabel;  
    private UserListGrid userListGrid; 
    private UserDS userDataSource;
    private PermissionDS permissionDataSource;
    private HLayout outlineForm;
    private IButton saveButton, cancelButton;    
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    private SelectItem profile;
    
    public UserDetailTabPane(UserDS userDS , UserListGrid userGrid, String user){
    	this.userListGrid = userGrid;
    	this.userDataSource = userDS;
    	this.permissionDataSource = PermissionDS.getInstance();
    	this.user = user;
    	setHeight("70%");
    	
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
        //outlineForm.setHeight100();
        outlineForm.setHeight(200);
        outlineForm.setMargin(5);
        
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
        
        //editorForm
        StaticTextItem uname = new StaticTextItem("uname", "ชื่อผู้ใช้");
        uname.setRequired(true);
        profile = new SelectItem("name", "สิทธิการใช้งาน");
        //Setup for fetch update data on Data Source
        profile.setValueField("name");
        profile.setDisplayField("name");
        profile.setOptionDataSource(this.permissionDataSource);
        profile.setFetchMissingValues(true);
        profile.setAlwaysFetchMissingValues(true);
        profile.setRequired(true);
        profile.setPickListCriteria(new Criteria("status", "Active"));
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

        cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setMargin(10);
        cancelButton.setWidth(150);  
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยกเลิกการแก้ไขข้อมูผู้ใช้ระบบ", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูลผู้ใช้ระบบ หรือไม่ ?" , new BooleanCallback() {
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
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(uname, profile, status, pwd, npwd);
        editorForm.setColWidths(80, 150); 
        normalForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        normalForm.setFields(title,fname,lname,email, position );
        normalForm.setColWidths(80, 150);
        VLayout editor_control = new VLayout();
        editor_control.addMembers(saveButton, cancelButton);
        outlineForm.addMembers(editorForm, normalForm, editor_control);
       
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
        		saveButton.setDisabled(false);
        		cancelButton.setDisabled(false);
        		profile.invalidateDisplayValueCache();
        		editorForm.editRecord(selectedRecord);  
        		editorForm.setValue("npwd", editorForm.getValueAsString("pwd"));
        		editorForm.setValue("name", selectedRecord.getAttributeAsString("pname"));
                normalForm.editRecord(selectedRecord);  
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
        	profile.invalidateDisplayValueCache();
    		editorForm.editRecord(selectedRecord);  
    		editorForm.setValue("npwd", editorForm.getValueAsString("pwd"));
    		editorForm.setValue("name", selectedRecord.getAttributeAsString("pname"));
            normalForm.editRecord(selectedRecord);  
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate() && normalForm.validate()) {
    		
    		String uname = (String) editorForm.getValue("uname");
			String pwd = (String) editorForm.getValue("pwd");
			String title = (String) normalForm.getValue("title");
	    	String fname = (String) normalForm.getValue("fname");
	    	String lname = (String) normalForm.getValue("lname");
	    	String email = (String) normalForm.getValue("email");
	    	String position = (String) normalForm.getValue("position");
	    	String pname = (String) editorForm.getValue("name");
	    	boolean status = (Boolean) editorForm.getValue("status");
	    	
	    	User updatedUser = new User(uname, pwd, fname, lname, email, position, title, status);
	    	securityService.updateUserOnServer(updatedUser, pname, this.user, new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.warn("Updating permission Fails - please contact administrator");
				}
				@Override
				public void onSuccess(Boolean result) {
					if (result)
					{
						//System.out.println("*** Update result => " + result);
						Record updateRecord = UserData.createRecord(
								(String) editorForm.getValue("uid"),
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
						SC.say("แก้ไขข้อมูลผู้ใช้ระบบเรียบร้อยแล้ว");
					} else {
						SC.warn("Updating user Fails - please contact administrator");
					}
				}
			});
    	} else {
    		SC.warn("ข้อมูลผู้ใช้ไม่ถูกต้อง");
    	}
    }
    
    public void onRefresh() {
    	userDataSource.refreshData();
    	permissionDataSource.refreshData();
    	userListGrid.invalidateCache();
    	profile.invalidateDisplayValueCache();
    }
}
