package com.smart.mis.client.function.production.smith;

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

public class SmithAdd {
	
    private SmithListGrid ListGrid; 
    private final DataSource DS;
    private final SmithDetailTabPane TabPane;
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    
	public SmithAdd(DataSource DS, SmithListGrid ListGrid, SmithDetailTabPane TabPane, String user){
		this.DS = DS;
    	this.ListGrid = ListGrid;
    	this.TabPane = TabPane;
    	this.user = user;
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มช่าง");
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
        editorForm.setDataSource(this.DS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลช่าง");
        
		TextItem name = new TextItem("name", "ชื่อช่าง");
		FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("ชื่อช่างต้องไม่ซ้ำ");
        name.setIcons(icon);
        name.setRequired(true);
        name.setHint("*");
        
		TextItem phone1 = new TextItem("phone1", "หมายเลขโทรศัพท์ 1");
		TextItem phone2 = new TextItem("phone2", "ชื่หมายเลขโทรศัพท์ 2");
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
		
        IButton saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	SC.confirm("ยืนยันการเพิ่มข้อมูลช่าง", "ท่านต้องการเพิ่มช่าง " + (String) editorForm.getValue("name") + " หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {	
							//String smid = editorForm.getValueAsString("smid");
							String name = editorForm.getValueAsString("name");
							String phone1 = editorForm.getValueAsString("phone1");
					    	String phone2 = editorForm.getValueAsString("phone2");
					    	String email = editorForm.getValueAsString("email");
					    	String address = editorForm.getValueAsString("address");
					    	
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
										Record newRecord = SmithData.createRecord(
												"SM70" + Math.round((Math.random() * 100)),
												editorForm.getValueAsString("name"),
												editorForm.getValueAsString("phone1"),
												editorForm.getValueAsString("phone2"),
												editorForm.getValueAsString("email"),
												editorForm.getValueAsString("address")
								    			);
										
										DS.addData(newRecord, new DSCallback() {

											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การเพิ่มข้อมูลช่างล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
													} else { 
														SC.warn("เพิ่มข้อมูลช่างเรียบร้อยแล้ว");
														winModel.destroy();
														ListGrid.fetchData();
														ListGrid.selectSingleRecord(dsResponse.getData()[0]);
														TabPane.updateDetails(dsResponse.getData()[0]);
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

        name.setWidth(250);
        phone1.setWidth(250);
        phone2.setWidth(250);
        email.setWidth(250);
        editorForm.setFields(name, phone1, phone2, email , address);
        editorForm.setColWidths(200	, 300);
        
    	VLayout temp = new VLayout();
    	temp.addMembers(saveButton, cancelButton);
    	temp.setMargin(3);
        outlineForm.addMembers(editorForm, temp);
        winModel.addItem(outlineForm);
        winModel.show();
	}
}
