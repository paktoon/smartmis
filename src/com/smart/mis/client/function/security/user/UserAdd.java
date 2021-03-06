package com.smart.mis.client.function.security.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserAdd {
	
    private final DataSource userDataSource, permissionDataSource;
    private final UserListGrid userListGrid;
    private final UserDetailTabPane userTabPane;
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    
	public UserAdd(DataSource userDS, UserListGrid userListGrid, UserDetailTabPane userTabPane, String user){
		this.userDataSource = userDS;
    	this.permissionDataSource = PermissionDS.getInstance();
    	this.userListGrid = userListGrid;
    	this.userTabPane = userTabPane;
    	this.user = user;
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มผู้ใช้ระบบ");
		//winModel.setAutoSize(true);	
		winModel.setWidth(720);
		winModel.setHeight(220);
		winModel.setHeaderIcon("[SKIN]actions/add.png");
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
		
		HLayout outlineForm = new HLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(10);
        
        final DynamicForm editorForm = new DynamicForm();
        
        editorForm.setWidth(270);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(true);
        editorForm.setSelectOnFocus(true);
        editorForm.setDataSource(this.userDataSource);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลผู้ใช้ระบบ");
        
        final DynamicForm normalForm = new DynamicForm(); 
        normalForm.setAutoFocus(false);
        normalForm.setCellPadding(2);
        normalForm.setMargin(5); 
        normalForm.setNumCols(2); 
        normalForm.setWidth(270); 
        normalForm.setDataSource(this.userDataSource);  
        normalForm.setUseAllDataSourceFields(false); 
        normalForm.setIsGroup(true);
        normalForm.setGroupTitle("ข้อมูลทั่วไป");
        
        //editorForm
        TextItem uname = new TextItem("uname", "ชื่อผู้ใช้");
        FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("ชื่อผู้ใช้ต้องไม่ซ้ำ และเมื่อบันทึกแล้วไม่สามารถแก้ไขได้");
        uname.setIcons(icon);
        uname.setRequired(true);
        uname.setHint("*");
        SelectItem profile = new SelectItem("name", "สิทธิการใช้งาน");
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
		
        IButton saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	SC.confirm("ยืนยันการเพิ่มผู้ใช้", "ท่านต้องการเพิ่มผู้ใช้ " + (String) editorForm.getValue("uname") + "หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {	
				    		String uname = (String) editorForm.getValue("uname");
							String pwd = (String) editorForm.getValue("pwd");
							String title = (String) normalForm.getValue("title");
					    	String fname = (String) normalForm.getValue("fname");
					    	String lname = (String) normalForm.getValue("lname");
					    	String email = (String) normalForm.getValue("email");
					    	String position = (String) normalForm.getValue("position");
					    	String pname = (String) editorForm.getValue("name");
					    	boolean status = (Boolean) editorForm.getValue("status");
					    	
					    	User createdUser = new User(uname, pwd, fname, lname, email, position, title, status);
					    	securityService.createUserOnServer(createdUser, pname, user, new AsyncCallback<String>() {
								@Override
								public void onFailure(Throwable caught) {
									SC.warn("Updating permission Fails - please contact administrator");
								}
								@Override
								public void onSuccess(String result) {
									if (result != null)
									{
										Record newRecord = UserData.createRecord(
												result,
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
								    	userDataSource.addData(newRecord, new DSCallback() {

											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การเพิ่มผู้ใช้ล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
													} else { 
														SC.say("เพิ่มข้อมูลผู้ใช้เรียบร้อยแล้ว");
														winModel.destroy();
														userListGrid.fetchData();
														userListGrid.selectSingleRecord(dsResponse.getData()[0]);
														userTabPane.updateDetails(dsResponse.getData()[0]);
													}
											}
								    	});
									} else {
										SC.warn("Updating user Fails - please contact administrator");
									}
								}
							});
					    }
					}
            		
            	});
            }  
        }); 
        
        IButton cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setMargin(10);
        cancelButton.setWidth(150);  
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	winModel.destroy();
            }  
        }); 

        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(uname, profile, status, pwd, npwd);
        editorForm.setColWidths(80, 150); 
        normalForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        normalForm.setFields(title,fname,lname,email, position );
        normalForm.setColWidths(80, 150);
        
        //Default selected 
        editorForm.setValue("uname", "[ชื่อผู้ใช้ระบบ]"); 
        editorForm.setValue("name", "ADMIN");
        editorForm.setValue("status", true);
        editorForm.setValue("pwd", "*****");
        editorForm.setValue("npwd", "*****");
        normalForm.setValue("title", "นาย");
        normalForm.setValue("fname", "[ชื่อจริง]");
        normalForm.setValue("lname", "[นามสกุล]");
        normalForm.setValue("email", "");
        normalForm.setValue("position", "");
    	
    	VLayout temp = new VLayout();
    	temp.addMembers(saveButton, cancelButton);
    	temp.setMargin(3);
        outlineForm.addMembers(editorForm, normalForm, temp);
        winModel.addItem(outlineForm);
        winModel.show();
	}
}
