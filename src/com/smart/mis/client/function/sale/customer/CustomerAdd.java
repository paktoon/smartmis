package com.smart.mis.client.function.sale.customer;

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
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CustomerAdd {
	
    private final DataSource customerDataSource;
    private final CustomerListGrid customerListGrid;
    private final CustomerDetailTabPane customerTabPane;
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    
	public CustomerAdd(DataSource customerDS, CustomerListGrid customerListGrid, CustomerDetailTabPane customerTabPane, String user){
		this.customerDataSource = customerDS;
    	this.customerListGrid = customerListGrid;
    	this.customerTabPane = customerTabPane;
    	this.user = user;
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มลูกค้า");
		//winModel.setAutoSize(true);	
		winModel.setWidth(650);
		winModel.setHeight(350);
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
        
        editorForm.setWidth(450);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(true);
        editorForm.setSelectOnFocus(true);
        editorForm.setDataSource(this.customerDataSource);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลลูกค้า");
        
		TextItem cus_name = new TextItem("cus_name", "ชื่อลูกค้า");
		FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("ชื่อลูกค้าต้องไม่ซ้ำ");
        cus_name.setIcons(icon);
        cus_name.setRequired(true);
        cus_name.setHint("*");
        
		TextItem cus_phone = new TextItem("cus_phone", "หมายเลขโทรศัพท์ลูกค้า");
		TextItem contact_name = new TextItem("contact_name", "ชื่อผู้ติดต่อ");
		TextItem contact_phone = new TextItem("contact_phone", "หมายเลขโทรศัพท์ผู้ติดต่อ");
		TextItem contact_email = new TextItem("contact_email", "อีเมลผู้ติดต่อ");
		
		TextAreaItem address = new TextAreaItem("address", "ที่อยู่");
		address.setWidth(300);
		address.setRowSpan(3);
		
		SelectItem type = new SelectItem("cus_type", "ประเภทลูกค้า");
		SelectItem zone = new SelectItem("zone", "โซน");
		
		cus_name.setRequired(true);
		cus_phone.setRequired(true);
		contact_name.setRequired(true);
		address.setRequired(true);
		type.setRequired(true);
		type.setDefaultValue("ลูกค้าทั่วไป");
		zone.setRequired(true);
		zone.setDefaultValue("เอเซีย");
		
		cus_name.setHint("*");
		cus_phone.setHint("*");
		contact_name.setHint("*");
		address.setHint("*");
		type.setHint("*");
		
        IButton saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	SC.confirm("ยืนยันการเพิ่มข้อมูลลูกค้า", "ท่านต้องการเพิ่มลูกค้า " + (String) editorForm.getValue("cus_name") + " หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {	
				    		String cid = (String) editorForm.getValue("cid");
							String cus_name = (String) editorForm.getValue("cus_name");
							String cus_phone = (String) editorForm.getValue("cus_phone");
					    	String contact_name = (String) editorForm.getValue("contact_name");
					    	String contact_phone = (String) editorForm.getValue("contact_phone");
					    	String contact_email = (String) editorForm.getValue("contact_email");
					    	String address = (String) editorForm.getValue("address");
					    	String type = (String) editorForm.getValue("cus_type");
					    	
//					    	User createdUser = new User(uname, pwd, fname, lname, email, position, title, status);
//					    	securityService.createUserOnServer(createdUser, pname, user, new AsyncCallback<String>() {
//								@Override
//								public void onFailure(Throwable caught) {
//									SC.warn("Updating permission Fails - please contact administrator");
//								}
//								@Override
//								public void onSuccess(String result) {
//									if (result != null)
//									{
										Record newRecord = CustomerData.createRecord(
												(String) editorForm.getValue("cid"),
								    			(String) editorForm.getValue("cus_name"),
								    			(String) editorForm.getValue("cus_phone"),
								    			(String) editorForm.getValue("contact_name"),
								    	    	(String) editorForm.getValue("contact_phone"),
								    	    	(String) editorForm.getValue("contact_email"),
								    	    	(String) editorForm.getValue("address"),
								    	    	(String) editorForm.getValue("cus_type"),
								    	    	(String) editorForm.getValue("zone")
								    			);
										
										customerDataSource.addData(newRecord, new DSCallback() {

											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การเพิ่มข้อมูลลูกค้าล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
													} else { 
														SC.warn("เพิ่มข้อมูลลูกค้าเรียบร้อยแล้ว");
														winModel.destroy();
														customerListGrid.fetchData();
														customerListGrid.selectSingleRecord(dsResponse.getData()[0]);
														customerTabPane.updateDetails(dsResponse.getData()[0]);
													}
											}
//								    	});
//									} else {
//										SC.warn("Updating customer Fails - please contact administrator");
//									}
//								}
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

        cus_name.setWidth(250);
        cus_phone.setWidth(250);
        contact_name.setWidth(250);
        contact_phone.setWidth(250);
        contact_email.setWidth(250);
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(cus_name, cus_phone, contact_name, contact_phone, contact_email , address, type);
        editorForm.setColWidths(200	, 300);
        
        //Default selected 
//        editorForm.setValue("cus_name", "[ชื่อลูกค้า]"); 
//        editorForm.setValue("name", "ADMIN");
//        editorForm.setValue("status", true);
//        editorForm.setValue("pwd", "*****");
//        editorForm.setValue("npwd", "*****");
    	
    	VLayout temp = new VLayout();
    	temp.addMembers(saveButton, cancelButton);
    	temp.setMargin(3);
        outlineForm.addMembers(editorForm, temp);
        winModel.addItem(outlineForm);
        winModel.show();
	}
}
