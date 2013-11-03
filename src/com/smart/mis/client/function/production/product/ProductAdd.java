package com.smart.mis.client.function.production.product;

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
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
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

public class ProductAdd {
	
    private ProductListGrid ListGrid; 
    private final DataSource DS;
    private final ProductDetailTabPane TabPane;
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    private final Img editProductImage;
    
	public ProductAdd(DataSource DS, ProductListGrid ListGrid, ProductDetailTabPane TabPane, String user){
		this.DS = DS;
    	this.ListGrid = ListGrid;
    	this.TabPane = TabPane;
    	this.user = user;
    	this.editProductImage = new Img("/images/icons/photoNotFound.png");
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มสินค้า");
		//winModel.setAutoSize(true);	
		winModel.setWidth(700);
		winModel.setHeight(370);
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
        editorForm.setGroupTitle("ข้อมูลสินค้า");
        
		TextItem name = new TextItem("name", "ชื่อสินค้า");
		FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("ชื่อสินค้าต้องไม่ซ้ำ");
        name.setIcons(icon);
        name.setRequired(true);
        name.setHint("*");
		
		TextItem size = new TextItem("size", "ขนาด");
		FloatItem weight = new FloatItem("weight", "น้ำหนัก");
		FloatItem price = new FloatItem("price", "ราคา");
		
		TextAreaItem desc = new TextAreaItem("desc", "คำอธิบาย");
		desc.setWidth(300);
		desc.setRowSpan(2);
		SelectItem type = new SelectItem("type", "ประเภท");
		TextItem unit = new TextItem("unit", "หน่วย");
		
		name.setRequired(true);
		size.setRequired(true);
		weight.setRequired(true);
		price.setRequired(true);
		type.setRequired(true);
		unit.setRequired(true);
		
		name.setHint("*");
		size.setHint("*");
		weight.setHint("กรัม *");
		price.setHint("บาท *");
		type.setHint("*");
		type.setDefaultValue("แหวนนิ้วมือ");
		unit.setHint("*");
		
        IButton saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	SC.confirm("ยืนยันการเพิ่มข้อมูลสินค้า", "ท่านต้องการเพิ่มสินค้า " + (String) editorForm.getValue("name") + " หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {	
				    		//String pid = editorForm.getValueAsString("pid");
							String name = editorForm.getValueAsString("name");
							String size = editorForm.getValueAsString("size");
					    	Double weight = Double.parseDouble(editorForm.getValueAsString("weight"));
					    	Double price = Double.parseDouble(editorForm.getValueAsString("price"));
					    	String desc = editorForm.getValueAsString("desc");
					    	String type = editorForm.getValueAsString("type");
					    	String unit = editorForm.getValueAsString("unit");
					    	editProductImage.setSrc(TabPane.currentEditImgUrl);
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
										Record newRecord = ProductData.createRecord(
												"PD70" + Math.round((Math.random() * 100)),
												editorForm.getValueAsString("name"),
												editorForm.getValueAsString("desc"),
												editorForm.getValueAsString("size"),
												Double.parseDouble(editorForm.getValueAsString("weight")),
												Double.parseDouble(editorForm.getValueAsString("price")),
												editorForm.getValueAsString("type"),
												0,
												editorForm.getValueAsString("unit"),
												false,
												editProductImage.getSrc()
								    			);
										
										DS.addData(newRecord, new DSCallback() {

											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การเพิ่มข้อมูลสินค้าล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
													} else { 
														SC.warn("เพิ่มข้อมูลสินค้าเรียบร้อยแล้ว");
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
        size.setWidth(250);
        weight.setWidth(250);
        price.setWidth(250);
        type.setWidth(250);
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(name, size, weight, price, desc, type);
        editorForm.setColWidths(200	, 300);
        
    	Window imageWindow = this.TabPane.getImageWindow(editProductImage , 1);
    	
    	VLayout temp = new VLayout();
    	temp.addMembers(imageWindow, saveButton, cancelButton);
    	temp.setMargin(3);
        outlineForm.addMembers(editorForm, temp);
        winModel.addItem(outlineForm);
        winModel.show();
	}
}
