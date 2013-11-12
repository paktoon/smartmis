package com.smart.mis.client.function.production.process;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.production.product.ProductData;
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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProcessAdd {
//	
//    private final DataSource DS;
//	
//	public void show(String product_id){
//		
//		final Window winModel = new Window();
//		
//		winModel.setTitle("เพิ่มสินค้า");
//		//winModel.setAutoSize(true);	
//		winModel.setWidth(700);
//		winModel.setHeight(350);
//		winModel.setHeaderIcon("[SKIN]actions/add.png");
//		winModel.setShowMinimizeButton(false);
//		winModel.setIsModal(true);
//		winModel.setShowModalMask(true);
//		winModel.setCanDragResize(false);
//		winModel.setCanDragReposition(false);
//		winModel.centerInPage();
//		
//		HLayout outlineForm = new HLayout();
//        outlineForm.setWidth100();
//        outlineForm.setHeight100();
//        outlineForm.setMargin(10);
//        
//        final DynamicForm editorForm = new DynamicForm();
//        
//        editorForm.setWidth(450);  
//        editorForm.setMargin(5);  
//        editorForm.setNumCols(2);  
//        editorForm.setCellPadding(2);  
//        editorForm.setAutoFocus(true);
//        editorForm.setSelectOnFocus(true);
//        editorForm.setDataSource(this.DS);  
//        editorForm.setUseAllDataSourceFields(false); 
//        editorForm.setIsGroup(true);
//        editorForm.setGroupTitle("ข้อมูลสินค้า");
//        
//		TextItem name = new TextItem("name", "ชื่อสินค้าภาษาอังกฤษ");
////		FormItemIcon icon = new FormItemIcon();  
////        icon.setSrc("[SKIN]/actions/help.png"); 
////        icon.setPrompt("ชื่อสินค้าต้องไม่ซ้ำ");
////        name.setIcons(icon);
////        name.setRequired(true);
////        name.setHint("*");
//        
//        TextItem name_th = new TextItem("name_th", "ชื่อสินค้าภาษาไทย");
//		FloatItem weight = new FloatItem("weight", "น้ำหนัก");
//		FloatItem price = new FloatItem("price", "ราคา");
//		
////		TextAreaItem desc = new TextAreaItem("desc", "คำอธิบาย");
////		desc.setWidth(300);
////		desc.setRowSpan(2);
//		final SelectItem type = new SelectItem("type", "ประเภท");
////		TextItem unit = new TextItem("unit", "หน่วย");
//		
//		name.setRequired(true);
//		name_th.setRequired(true);
//		weight.setRequired(true);
//		price.setRequired(true);
//		type.setRequired(true);
//		
//		name.setHint("*");
//		name_th.setHint("*");
//		weight.setHint("กรัม *");
//		price.setHint("บาท *");
//		type.setHint("*");
//		type.setEmptyDisplayValue("---โปรดเลือก---");
//		
//		final DynamicForm sizeForm = new DynamicForm();  
//		sizeForm.setWidth(450);  
//		sizeForm.setMargin(5);  
//		sizeForm.setNumCols(2);  
//		sizeForm.setAutoFocus(false);  
//		sizeForm.setDataSource(DS);  
//		sizeForm.setUseAllDataSourceFields(false); 
//		sizeForm.setIsGroup(true);
//		sizeForm.setGroupTitle("ลายละเอียดสินค้า");
//		sizeForm.hide();
//		
//		size = new SelectItem("size", "ขนาดสินค้า");
//		size.setValueMap("5.0","5.5","6.0","6.5","7.0","7.5","8.0","8.5","9.0");
//		width = new FloatItem("width", "ความกว้าง");
//		length = new FloatItem("length", "ความยาว");
//		height = new FloatItem("height", "ความสูง");
//		diameter = new FloatItem("diameter", "เส้นผ่าศูนย์กลาง");
//		thickness = new FloatItem("thickness", "ความหนา");
//		
//		size.setHint("[ขนาดมาตรฐาน USA]");
//		width.setHint("ซม.");
//		length.setHint("ซม.");
//		height.setHint("มม.");
//		diameter.setHint("มม.");
//		thickness.setHint("มม.");
//		
//		type.addChangedHandler(new ChangedHandler() {
//			@Override
//			public void onChanged(ChangedEvent event) {
//				sizeForm.show();
//				String selectType = type.getValueAsString();
//				showSizeElement(selectType);
//			}
//        });
//		
//        IButton saveButton = new IButton("บันทึก");  
//        saveButton.setAlign(Alignment.CENTER);
//        saveButton.setWidth(100); 
//        saveButton.setHeight(50);
//        saveButton.setIcon("icons/16/save.png");
//        saveButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {  
//            	            	
//            	SC.confirm("ยืนยันการเพิ่มข้อมูลสินค้า", "ท่านต้องการเพิ่มสินค้า " + (String) editorForm.getValue("name") + " หรือไม่ ?", new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {	
//							String name = editorForm.getValueAsString("name");
//					    	String name_th = editorForm.getValueAsString("name_th");
//					    	Double weight = Double.parseDouble(editorForm.getValueAsString("weight"));
//					    	Double price = Double.parseDouble(editorForm.getValueAsString("price"));
//					    	String type = editorForm.getValueAsString("type");
//					    	
////					    	Integer inStock = currentRecord.getAttributeAsInt("inStock");
////					    	Integer reserved = currentRecord.getAttributeAsInt("reserved");
//					    	Double size = null;
//					    	Double width = null;
//					    	Double length = null;
//					    	Double height = null;
//					    	Double diameter = null;
//					    	Double thickness = null;
//					    	
//					    	if (type.equalsIgnoreCase("ring") || type.equalsIgnoreCase("toe ring")) {
//					    		size = Double.parseDouble(sizeForm.getValueAsString("size"));
//					    		thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
//					    	} else if (type.equalsIgnoreCase("necklace") || type.equalsIgnoreCase("bangle")) {
//					    	    width = Double.parseDouble(sizeForm.getValueAsString("width"));
//					    	    length = Double.parseDouble(sizeForm.getValueAsString("length"));
//					    	    thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
//					    	} else if (type.equalsIgnoreCase("earring") || type.equalsIgnoreCase("pendant") || type.equalsIgnoreCase("anklet") || type.equalsIgnoreCase("bracelet")) {
//					    		height = Double.parseDouble(sizeForm.getValueAsString("height"));
//					    		diameter = Double.parseDouble(sizeForm.getValueAsString("diameter"));
//					    		thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
//					    	}
//					    	
//					    	editProductImage.setSrc(TabPane.currentEditImgUrl);
////					    	User createdUser = new User(uname, pwd, fname, lname, email, position, title, status);
////					    	securityService.createUserOnServer(createdUser, pname, user, new AsyncCallback<String>() {
////								@Override
////								public void onFailure(Throwable caught) {
////									SC.warn("Updating permission Fails - please contact administrator");
////								}
////								@Override
////								public void onSuccess(String result) {
////									if (result != null)
////									{
//										Record newRecord = ProductData.createRecord(
//												"PD70" + Math.round((Math.random() * 100)),
//												editorForm.getValueAsString("name"),
//												editorForm.getValueAsString("name_th"),
//												Double.parseDouble(editorForm.getValueAsString("weight")),
//												Double.parseDouble(editorForm.getValueAsString("price")),
//												editorForm.getValueAsString("type"),
//												0,
//												0,
//												editProductImage.getSrc(),
//												size,
//												width,
//												length,
//												height,
//												diameter,
//												thickness
//								    			);
//										
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
//					    }
//					}
//            		
//            	});
//            }  
//        }); 
//        
//        IButton cancelButton = new IButton("ยกเลิก");  
//        cancelButton.setAlign(Alignment.CENTER);  
//        cancelButton.setWidth(100);  
//        cancelButton.setHeight(50);
//        cancelButton.setIcon("icons/16/close.png");
//        cancelButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {  
//            	winModel.destroy();
//            }  
//        }); 
//
//        name.setWidth(250);
//        name_th.setWidth(250);
//        weight.setWidth(250);
//        price.setWidth(250);
//        type.setWidth(250);
////        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
////        editorForm.setFields(name, name_th, weight, price, type);
////        editorForm.setColWidths(200	, 300);
////        
////    	Window imageWindow = this.TabPane.getImageWindow(editProductImage , 1);
////    	
////    	VLayout temp = new VLayout();
////    	temp.addMembers(imageWindow, saveButton, cancelButton);
////    	temp.setMargin(3);
////        outlineForm.addMembers(editorForm, temp);
//        
//        VLayout leftLayout = new VLayout();
//        leftLayout.setMembersMargin(5);
//        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
//        editorForm.setFields(name, name_th, weight, price, type);
//        editorForm.setColWidths(200	, 300);
//        sizeForm.setFields(size,width,length,height,diameter,thickness);
//        leftLayout.addMembers(editorForm, sizeForm);
//        
//        HLayout editor_control = new HLayout();
//        editor_control.setMembersMargin(5);
//        editor_control.addMembers(saveButton, cancelButton);
//        VLayout rightLayout = new VLayout();
//        rightLayout.addMembers(this.TabPane.getImageWindow(editProductImage, 1), editor_control);
//        
//        outlineForm.addMembers(leftLayout , rightLayout);
//        
//        winModel.addItem(outlineForm);
//        winModel.show();
//	}
//	
//    public void showSizeElement(String type){
//    	if (type.equalsIgnoreCase("ring") || type.equalsIgnoreCase("toe ring")) {
//    	    size.show();
//    	    width.hide();
//    	    length.hide();
//    	    height.hide();
//    	    diameter.hide();
//    	    thickness.show();
//    	    
//    	    size.setRequired(true);
//    	    width.setRequired(false);
//    	    length.setRequired(false);
//    	    height.setRequired(false);
//    	    diameter.setRequired(false);
//    	    thickness.setRequired(true);
//    	} else if (type.equalsIgnoreCase("necklace") || type.equalsIgnoreCase("bangle")) {
//    		size.hide();
//    	    width.show();
//    	    length.show();
//    	    height.hide();
//    	    diameter.hide();
//    	    thickness.show();
//    	    
//    	    size.setRequired(false);
//    	    width.setRequired(true);
//    	    length.setRequired(true);
//    	    height.setRequired(false);
//    	    diameter.setRequired(false);
//    	    thickness.setRequired(true);
//    	} else if (type.equalsIgnoreCase("earring") || type.equalsIgnoreCase("pendant") || type.equalsIgnoreCase("anklet") || type.equalsIgnoreCase("bracelet")) {
//    		size.hide();
//    	    width.hide();
//    	    length.hide();
//    	    height.show();
//    	    diameter.show();
//    	    thickness.show();
//    	    
//    	    size.setRequired(false);
//    	    width.setRequired(false);
//    	    length.setRequired(false);
//    	    height.setRequired(true);
//    	    diameter.setRequired(true);
//    	    thickness.setRequired(true);
//    	}
//    }
}
