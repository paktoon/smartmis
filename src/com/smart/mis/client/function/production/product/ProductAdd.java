package com.smart.mis.client.function.production.product;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.production.process.ProcessAdd;
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
import com.smartgwt.client.widgets.form.fields.DoubleItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProductAdd {
	
    private ProductListGrid ListGrid; 
    private final DataSource DS;
    private final ProductDetailTabPane TabPane;
    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    private final ProcessAdd process;
    private final Img editProductImage;
    
    private SelectItem size;
    private DoubleItem width, length, height, diameter, thickness;
    
	public ProductAdd(DataSource DS, ProductListGrid ListGrid, ProductDetailTabPane TabPane, String user){
		this.DS = DS;
    	this.ListGrid = ListGrid;
    	this.TabPane = TabPane;
    	this.user = user;
    	this.editProductImage = new Img("/images/icons/photoNotFound.png");
    	this.process = new ProcessAdd();
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มสินค้า");
		//winModel.setAutoSize(true);	
		winModel.setWidth(700);
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
        editorForm.setGroupTitle("ข้อมูลสินค้า");
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		TextItem name = new TextItem("name", "ชื่อสินค้าภาษาอังกฤษ");
//		FormItemIcon icon = new FormItemIcon();  
//        icon.setSrc("[SKIN]/actions/help.png"); 
//        icon.setPrompt("ชื่อสินค้าต้องไม่ซ้ำ");
//        name.setIcons(icon);
//        name.setRequired(true);
//        name.setHint("*");
        
        TextItem name_th = new TextItem("name_th", "ชื่อสินค้าภาษาไทย");
//		DoubleItem weight = new DoubleItem("weight", "น้ำหนัก");
		DoubleItem price = new DoubleItem("price", "ราคา");
		
//		TextAreaItem desc = new TextAreaItem("desc", "คำอธิบาย");
//		desc.setWidth(300);
//		desc.setRowSpan(2);
		final SelectItem type = new SelectItem("type", "ประเภท");
//		TextItem unit = new TextItem("unit", "หน่วย");
		
		name.setRequired(true);
		name_th.setRequired(true);
//		weight.setRequired(true);
		price.setRequired(true);
		type.setRequired(true);
		
		name.setHint("*");
		name_th.setHint("*");
//		weight.setHint("กรัม *");
		price.setHint("บาท *");
		type.setHint("*");
		type.setEmptyDisplayValue("---โปรดเลือก---");
		
		final DynamicForm sizeForm = new DynamicForm();  
		sizeForm.setWidth(450);  
		sizeForm.setMargin(5);  
		sizeForm.setNumCols(2);  
		sizeForm.setAutoFocus(false);  
		sizeForm.setDataSource(DS);  
		sizeForm.setUseAllDataSourceFields(false); 
		sizeForm.setIsGroup(true);
		sizeForm.setGroupTitle("ลายละเอียดสินค้า");
		sizeForm.hide();
		
		size = new SelectItem("size", "ขนาดสินค้า");
		size.setValueMap("5.0","5.5","6.0","6.5","7.0","7.5","8.0","8.5","9.0");
		width = new DoubleItem("width", "ความกว้าง");
		length = new DoubleItem("length", "ความยาว");
		height = new DoubleItem("height", "ความสูง");
		diameter = new DoubleItem("diameter", "เส้นผ่าศูนย์กลาง");
		thickness = new DoubleItem("thickness", "ความหนา");
		
		size.setHint("[ขนาดมาตรฐาน USA]");
		width.setHint("ซม.");
		length.setHint("ซม.");
		height.setHint("มม.");
		diameter.setHint("มม.");
		thickness.setHint("มม.");
		
		type.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				sizeForm.show();
				String selectType = type.getValueAsString();
				showSizeElement(selectType);
			}
        });
		
        IButton saveButton = new IButton("ต่อไป");  
        saveButton.setAlign(Alignment.CENTER);
        saveButton.setMargin(10); 
        saveButton.setHeight(50);
        saveButton.setWidth(150);
        saveButton.setIcon("icons/16/next.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	if (!editorForm.validate()) {
            		SC.warn("กรูณากรอกข้อมูลให้ครบถ้วน");
            		return;
            	}
            	
            	SC.confirm("ยืนยันการเพิ่มข้อมูลสินค้า", "ท่านต้องการเพิ่มสินค้า หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {	
							String name = editorForm.getValueAsString("name");
					    	String name_th = editorForm.getValueAsString("name_th");
					    	//Double weight = Double.parseDouble(editorForm.getValueAsString("weight"));
					    	Double price = Double.parseDouble(editorForm.getValueAsString("price"));
					    	String type = editorForm.getValueAsString("type");
					    	
//					    	Integer inStock = currentRecord.getAttributeAsInt("inStock");
//					    	Integer reserved = currentRecord.getAttributeAsInt("reserved");
					    	Double size = null;
					    	Double width = null;
					    	Double length = null;
					    	Double height = null;
					    	Double diameter = null;
					    	Double thickness = null;
					    	
					    	if (type.equalsIgnoreCase("ring") || type.equalsIgnoreCase("toe ring")) {
					    		size = Double.parseDouble(sizeForm.getValueAsString("size"));
					    		thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
					    	} else if (type.equalsIgnoreCase("necklace") || type.equalsIgnoreCase("bangle")) {
					    	    width = Double.parseDouble(sizeForm.getValueAsString("width"));
					    	    length = Double.parseDouble(sizeForm.getValueAsString("length"));
					    	    thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
					    	} else if (type.equalsIgnoreCase("earring") || type.equalsIgnoreCase("pendant") || type.equalsIgnoreCase("anklet") || type.equalsIgnoreCase("bracelet")) {
					    		height = Double.parseDouble(sizeForm.getValueAsString("height"));
					    		diameter = Double.parseDouble(sizeForm.getValueAsString("diameter"));
					    		thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
					    	}
					    	
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
												editorForm.getValueAsString("name"),
												editorForm.getValueAsString("name_th"),
												//Double.parseDouble(editorForm.getValueAsString("weight")),
												Double.parseDouble(editorForm.getValueAsString("price")),
												editorForm.getValueAsString("type"),
												0,
												0,
												editProductImage.getSrc(),
												size,
												width,
												length,
												height,
												diameter,
												thickness
								    			);
										ProcessAdd next = new ProcessAdd();
		  					        	next.show("1_casting", newRecord, new ArrayList<ListGridRecord>(), new ArrayList<ListGridRecord>(), new ArrayList<ListGridRecord>(), new ArrayList<ListGridRecord>(), new ArrayList<ListGridRecord>());
		  	  							winModel.destroy();
//										DS.addData(newRecord, new DSCallback() {
//
//											@Override
//											public void execute(DSResponse dsResponse, Object data,
//													DSRequest dsRequest) {
//													if (dsResponse.getStatus() != 0) {
//														SC.warn("การเพิ่มข้อมูลสินค้าล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
//													} else { 
//														SC.say("เพิ่มข้อมูลสินค้าเรียบร้อยแล้ว");
//														winModel.destroy();
//														ListGrid.fetchData();
//														ListGrid.selectSingleRecord(dsResponse.getData()[0]);
//														TabPane.updateDetails(dsResponse.getData()[0]);
//													}
//											}
////								    	});
////									} else {
////										SC.warn("Updating customer Fails - please contact administrator");
////									}
////								}
//							});
					    }
					}
            		
            	});
            }  
        }); 
        
        IButton cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setWidth(150);  
        cancelButton.setMargin(10); 
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	winModel.destroy();
            }  
        }); 

        name.setWidth(250);
        name_th.setWidth(250);
//        weight.setWidth(250);
        price.setWidth(250);
        type.setWidth(250);
//        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
//        editorForm.setFields(name, name_th, weight, price, type);
//        editorForm.setColWidths(200	, 300);
//        
//    	Window imageWindow = this.TabPane.getImageWindow(editProductImage , 1);
//    	
//    	VLayout temp = new VLayout();
//    	temp.addMembers(imageWindow, saveButton, cancelButton);
//    	temp.setMargin(3);
//        outlineForm.addMembers(editorForm, temp);
        
        VLayout leftLayout = new VLayout();
        leftLayout.setMembersMargin(5);
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
//        editorForm.setFields(name, name_th, weight, price, type);
        editorForm.setFields(name, name_th, price, type);
        editorForm.setColWidths(200	, 300);
        sizeForm.setFields(size,width,length,height,diameter,thickness);
        leftLayout.addMembers(editorForm, sizeForm);
        
        VLayout editor_control = new VLayout();
        //editor_control.setMembersMargin(5);
        editor_control.addMembers(saveButton, cancelButton);
        VLayout rightLayout = new VLayout();
        rightLayout.setMembersMargin(5);
        rightLayout.addMembers(this.TabPane.getImageWindow(editProductImage, 1), editor_control);
        
        outlineForm.addMembers(leftLayout , rightLayout);
        
        winModel.addItem(outlineForm);
        winModel.show();
	}
	
    public void showSizeElement(String type){
    	if (type.equalsIgnoreCase("ring") || type.equalsIgnoreCase("toe ring")) {
    	    size.show();
    	    width.hide();
    	    length.hide();
    	    height.hide();
    	    diameter.hide();
    	    thickness.show();
    	    
    	    size.setRequired(true);
    	    width.setRequired(false);
    	    length.setRequired(false);
    	    height.setRequired(false);
    	    diameter.setRequired(false);
    	    thickness.setRequired(true);
    	} else if (type.equalsIgnoreCase("necklace") || type.equalsIgnoreCase("bangle")) {
    		size.hide();
    	    width.show();
    	    length.show();
    	    height.hide();
    	    diameter.hide();
    	    thickness.show();
    	    
    	    size.setRequired(false);
    	    width.setRequired(true);
    	    length.setRequired(true);
    	    height.setRequired(false);
    	    diameter.setRequired(false);
    	    thickness.setRequired(true);
    	} else if (type.equalsIgnoreCase("earring") || type.equalsIgnoreCase("pendant") || type.equalsIgnoreCase("anklet") || type.equalsIgnoreCase("bracelet")) {
    		size.hide();
    	    width.hide();
    	    length.hide();
    	    height.show();
    	    diameter.show();
    	    thickness.show();
    	    
    	    size.setRequired(false);
    	    width.setRequired(false);
    	    length.setRequired(false);
    	    height.setRequired(true);
    	    diameter.setRequired(true);
    	    thickness.setRequired(true);
    	}
    }
}
