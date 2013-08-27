package com.smart.mis.client.function.security;

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
	
    private final DataSource permissionDataSource;
    private final PermissionListGrid permissionListGrid;
    private final PermissionDetailTabPane permissionTabPane;
    
	public PermissionAdd(DataSource permissionDS, PermissionListGrid permisssionGrid, PermissionDetailTabPane permissionTabPane){
    	this.permissionDataSource = permissionDS;
    	this.permissionListGrid = permisssionGrid;
    	this.permissionTabPane = permissionTabPane;
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
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
        
        final DynamicForm editorForm = new DynamicForm();  
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
        
        final DynamicForm normalForm = new DynamicForm(); 
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
        
        final DynamicForm reportForm = new DynamicForm(); 
        reportForm.setAutoFocus(false);
        reportForm.setCellPadding(5); 
        reportForm.setMargin(5); 
        reportForm.setWidth(170);
        reportForm.setDataSource(this.permissionDataSource);  
        reportForm.setUseAllDataSourceFields(false); 
        reportForm.setIsGroup(true);
        reportForm.setGroupTitle("สิทธิเจ้าของกิจการ");
        
        final DynamicForm adminForm = new DynamicForm(); 
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
					    	Record newRecord = PermissionData.createRecord(
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
											winModel.destroy();
											permissionListGrid.fetchData();
											permissionListGrid.selectSingleRecord(dsResponse.getData()[0]);
											permissionTabPane.updateDetails(dsResponse.getData()[0]);
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

        editorForm.setFields(name, role, status);
        editorForm.setColWidths(50, 150, 200, 80, 100, 50); 
        normalForm.setFields(canSale,canProduct,canInven,canPurchase, canFinance );
        reportForm.setFields(canReport);
        adminForm.setFields(canAdmin);
        
        //Default selected 
        editorForm.setValue("name", "[ชื่อสิทธิการใช้งาน]");  
        editorForm.setValue("role", "Staff");
        editorForm.setValue("status", "Active");
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
}
