package com.smart.mis.client.function.security.permission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.shared.security.Function;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.Role;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PermissionAdd {
	
    private final PermissionDS permissionDataSource;
    private final PermissionListGrid permissionListGrid;
    private DynamicForm editorForm, normalForm, reportForm, adminForm; 
    private final PermissionDetailTabPane permissionTabPane;
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private final String user;
    private Window winModel;
    
	public PermissionAdd(PermissionDS permissionDS, PermissionListGrid permisssionGrid, PermissionDetailTabPane permissionTabPane, String user){
    	this.permissionDataSource = permissionDS;
    	this.permissionListGrid = permisssionGrid;
    	this.permissionTabPane = permissionTabPane;
    	this.user = user;
	}
	
	public void show(){
		winModel = new Window();
		winModel.setTitle("เพิ่มสิทธิการใช้งาน");
		//winModel.setAutoSize(true);	
		winModel.setWidth(680);
		winModel.setHeight(230);
		winModel.setHeaderIcon("[SKIN]actions/add.png");
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
		
        VLayout outlineForm = new VLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(10);
        
        editorForm = new DynamicForm();  
        editorForm.setWidth(650);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(6);  
        editorForm.setCellPadding(5);  
        editorForm.setAutoFocus(true); 
        editorForm.setSelectOnFocus(true);
        editorForm.setDataSource(this.permissionDataSource);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลสิทธิการใช้งาน");

        HLayout inlineForm = new HLayout();
        inlineForm.setWidth(650);
        inlineForm.setHeight(50);
        
        normalForm = new DynamicForm(); 
        normalForm.setAutoFocus(false);
        normalForm.setCellPadding(5);
        normalForm.setMargin(5); 
        normalForm.setNumCols(4); 
        normalForm.setWidth(300); 
        normalForm.setDataSource(this.permissionDataSource);  
        normalForm.setUseAllDataSourceFields(false); 
        normalForm.setIsGroup(true);
        normalForm.setGroupTitle("สิทธิการใช้งานทั่วไป");
        
        VLayout subInlineForm = new VLayout();
        subInlineForm.setWidth(170);
        
        reportForm = new DynamicForm(); 
        reportForm.setAutoFocus(false);
        reportForm.setCellPadding(5); 
        reportForm.setMargin(5); 
        reportForm.setWidth(170);
        reportForm.setDataSource(this.permissionDataSource);  
        reportForm.setUseAllDataSourceFields(false); 
        reportForm.setIsGroup(true);
        reportForm.setGroupTitle("สิทธิเจ้าของกิจการ");
        
        adminForm = new DynamicForm(); 
        adminForm.setAutoFocus(false);
        adminForm.setMargin(5); 
        adminForm.setWidth(170);
        adminForm.setCellPadding(5);
        adminForm.setDataSource(this.permissionDataSource);  
        adminForm.setUseAllDataSourceFields(false); 
        adminForm.setIsGroup(true);
        adminForm.setGroupTitle("สิทธิผู้ดูและระบบ");
        
        //editorForm
        TextItem name = new TextItem("name", "ชื่อ");
        FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("ชื่อสิทธิการใช้งานต้องไม่ซ้ำ และเมื่อบันทึกแล้วไม่สามารถแก้ไขได้");
        name.setIcons(icon);
        name.setRequired(true);
        name.setHint("*");
        SelectItem role = new SelectItem("role", "ขอบเขตการใช้งาน");
        role.setRequired(true);
        SelectItem status = new SelectItem("status", "สถานะ");
        
        //normalForm
        final CheckboxItem canSale = new CheckboxItem("cSale");
        final CheckboxItem canProduct = new CheckboxItem("cProd");
        final CheckboxItem canInven = new CheckboxItem("cInv");
        final CheckboxItem canPurchase = new CheckboxItem("cPurc");
        final CheckboxItem canFinance = new CheckboxItem("cFin");
        //reportForm
        final CheckboxItem canReport = new CheckboxItem("cRep");
        //adminForm
        final CheckboxItem canAdmin = new CheckboxItem("cAdm");

        role.addChangeHandler(new ChangeHandler() {  
            public void onChange(ChangeEvent event) {  
                String selectedItem = (String) event.getValue();  
                if (selectedItem.equalsIgnoreCase("Staff")){
                	canReport.setValue(false);
                	canReport.setDisabled(true);
                	canAdmin.setValue(false);
                	canAdmin.setDisabled(true);
                } else if (selectedItem.equalsIgnoreCase("Manager")) {
                	canReport.setDisabled(false);
                	canAdmin.setValue(false);
                	canAdmin.setDisabled(true);
                } else if (selectedItem.equalsIgnoreCase("Administrator")) {
                	canReport.setDisabled(false);
                	canAdmin.setDisabled(false);
                }
            }  
        });
        
        IButton saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	SC.confirm("ยืนยันการเพิ่มสิทธิการใช้", "ท่านต้องการเพิ่มสิทธิการใช้งาน " + (String) editorForm.getValue("name") + "หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//System.out.println("*** Confirm value => " + value);
							saveData();
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
        editorForm.setFields(name, role, status);
        editorForm.setColWidths(50, 150, 200, 80, 100, 50); 
        normalForm.setFields(canSale,canProduct,canInven,canPurchase, canFinance );
        reportForm.setFields(canReport);
        adminForm.setFields(canAdmin);
        
        //Default selected 
        editorForm.setValue("name", "[ชื่อสิทธิการใช้งาน]");  
        editorForm.setValue("role", "Staff");
        editorForm.setValue("status", "Active");
        canSale.setValue(false);
        canProduct.setValue(false);
        canInven.setValue(false);
        canPurchase.setValue(false);
        canFinance.setValue(false);
        canReport.setValue(false);
    	canReport.setDisabled(true);
    	canAdmin.setValue(false);
    	canAdmin.setDisabled(true);
    	
    	subInlineForm.addMembers(reportForm, adminForm);
    	VLayout temp = new VLayout();
    	temp.addMembers(saveButton, cancelButton);
    	temp.setMargin(3);
        inlineForm.addMembers(normalForm, subInlineForm, temp);
        outlineForm.addMembers(editorForm, inlineForm);
        winModel.addItem(outlineForm);
        winModel.show();
	}
	
	public void saveData(){

    	//Save data to data store
    	//Function
    	byte func = Function.NONE;
    	if ((Boolean) normalForm.getValue("cSale")) {
    		func |= Function.SALE;
    	}
    	if ((Boolean) normalForm.getValue("cProd")) {
    		func |= Function.PRODUCTION;
    	}
    	if ((Boolean) normalForm.getValue("cInv")) {
    		func |= Function.INVENTORY;
    	}
    	if ((Boolean) normalForm.getValue("cPurc")) {
    		func |= Function.PURCHASING;
    	}
    	if ((Boolean) normalForm.getValue("cFin")) {
    		func |= Function.FINANCIAL;
    	}
    	if ((Boolean) reportForm.getValue("cRep")) {
    		func |= Function.REPORT;
    	}
    	if ((Boolean) adminForm.getValue("cAdm")) {
    		func |= Function.SECURITY;
    	}
    	//End function
    	//Role
    	byte role = Role.NON_USER;
    	if (((String) editorForm.getValue("role")).equalsIgnoreCase("staff")) {
    		role = Role.STAFF;
    	} else if (((String) editorForm.getValue("role")).equalsIgnoreCase("manager")) {
    		role = Role.OWNER;
    	} else if (((String) editorForm.getValue("role")).equalsIgnoreCase("administrator")) {
    		role = Role.ADMIN;
    	}
    	//End Role
    	PermissionProfile createdPerm = new PermissionProfile((String) editorForm.getValue("name"), func, role, ((String) editorForm.getValue("status")).equalsIgnoreCase("active"));
    	//System.out.println("*** Adding new permission data " + (String) editorForm.getValue("name") + " " + func + " "  + role);
    	securityService.createPermOnServer(createdPerm, this.user, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Adding permission Fails - please contact administrator");
			}
			@Override
			public void onSuccess(String result) {
				if (result != null)
				{
					//System.out.println("*** Add result => " + result);
					Record newRecord = PermissionData.createRecord(
							result,
			    			(String) editorForm.getValue("name"),
			    			(String) editorForm.getValue("role"),
			    			(String) editorForm.getValue("status"),
			    	    	(Boolean) normalForm.getValue("cSale"),
			    	    	(Boolean) normalForm.getValue("cProd"),
			    	    	(Boolean) normalForm.getValue("cInv"),
			    	    	(Boolean) normalForm.getValue("cPurc"),
			    	    	(Boolean) normalForm.getValue("cFin"),
			    	    	(Boolean) reportForm.getValue("cRep"),
			    	    	(Boolean) adminForm.getValue("cAdm")
			    			);
					permissionDataSource.addData(newRecord, new DSCallback() {

						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การเพิ่มสิทธิการใช้งานล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
								} else { 
									SC.say("เพิ่มข้อมูลสิทธิการใช้งานเรียบร้อยแล้ว");
									winModel.destroy();
									permissionListGrid.fetchData();
									permissionListGrid.selectSingleRecord(dsResponse.getData()[0]);
									permissionTabPane.updateDetails(dsResponse.getData()[0]);
								}
						}
			    	});
				} else {
					SC.warn("Adding permission Fails - please contact administrator");
				}
			}
		});
	}
}
