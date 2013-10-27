package com.smart.mis.client.function.production.process;

import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.production.product.ProductData;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProcessOutline extends VLayout {
	
	//public DynamicForm editorForm;
    public ProcessOutline(final ProcessListDS DS) {
        setWidth100();
        setHeight(100);
        setMargin(5);
        
        //Label test = new Label(DS.pid);
        //addMembers(test);
        final ListGrid process = processListGrid(DS);
        
        final DynamicForm editorForm = new DynamicForm();  
        editorForm.setWidth(600); 
        editorForm.setMargin(5);  
        editorForm.setNumCols(4);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(DS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ขั้นตอนการผลิต");
        
        SelectItem name = new SelectItem("type", "ขั้นตอน");
        name.setDefaultValue("---โปรดเลือก---");
        name.setValueMap("หล่อและขึ้นรูป", "แต่ง", "ฝังพลอย", "ขัดและติดพลอย", "บรรจุหีบห่อ");
        
		TextItem time = new TextItem("std_time", "เวลาผลิต");

		SelectItem material = new SelectItem("mid", "วัตถุดิบ");
		material.setOptionDataSource(MaterialDS.getInstance());
		material.setValueField("mid");
		material.setDisplayField("mat_name");
		material.setPickListWidth(300);
		ListGridField Field_1 = new ListGridField("mid", 80);  
        ListGridField Field_2 = new ListGridField("mat_name", 140);  
        ListGridField Field_3 = new ListGridField("remain", 80); 
        Field_3.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_3.setAlign(Alignment.CENTER);
        material.setPickListFields(Field_1, Field_2, Field_3);
        
		TextAreaItem desc = new TextAreaItem("desc", "คำอธิบาย");
		desc.setWidth(220);
		desc.setRowSpan(4);
		
		name.setRequired(true);
		time.setRequired(true);
		desc.setRequired(true);
		
		name.setHint("*");
		time.setHint("วัน *");
		desc.setHint("*");
		
		IButton addButton = new IButton("เพิ่มขั้นตอน");  
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("เพิ่มขั้นตอนการผลิต", "ท่านต้องการ เพิ่มขั้นตอนการผลิตสินค้า หรือไม่ ?" , new BooleanCallback() {
            			@Override
                        public void execute(Boolean value) {  
            				if (value) {
	            				Record newRecord = ProcessData.createRecord(
										"PS70" + Math.round((Math.random() * 100)),
										editorForm.getValueAsString("type"),
										editorForm.getValueAsString("desc"),
										Double.parseDouble(editorForm.getValueAsString("std_time")),
										DS.pid,
										editorForm.getValueAsString("mid"),
										editorForm.getField("mid").getDisplayValue()
						    			);
	            				
	            				DS.addData(newRecord, new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data,
											DSRequest dsRequest) {
											if (dsResponse.getStatus() != 0) {
												SC.warn("การเพิ่มขั้นตอนล้มเหลว มีขั้นตอนนี้อยู่แล้ว");
											} else { 
												process.fetchData();
											}
									}
	            				});
            				}
                        }
                    });  
            	editorForm.reset();  
            }  
        });
        
		IButton delButton = new IButton("ลบขั้นตอน");
		delButton.setIcon("icons/16/delete.png");
		delButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	Record selected = process.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
            	} else {
            		SC.confirm("ยืนยันการลบ ขั้นตอนการผลิต", "ท่านต้องการลบ ขั้นตอนการผลิต หรือไม่ ?" , new BooleanCallback() {
    					@Override
    					public void execute(Boolean value) {
    						if (value) {
    							process.removeSelectedData();
    							editorForm.reset();
    						}
    					}
                	});
            	}
            }  
        });
		
		IButton saveButton = new IButton("บันทึก");  
        //saveButton.setMargin(10);
        //saveButton.setWidth(150);  
        //saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	RecordList selected = process.getRecordList();
            	SC.confirm("ยืนยันการแก้ไขข้อมูล ขั้นตอนการผลิต", "ท่านต้องการแก้ไขข้อมูล ขั้นตอนการผลิต หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
			            	//saveData(selected);
						}
					}
            		
            	});
            }  
        }); 
        
//        cancelButton = new IButton("ยกเลิก");  
//        cancelButton.setAlign(Alignment.CENTER);  
//        cancelButton.setMargin(10);
//        cancelButton.setWidth(150);  
//        cancelButton.setHeight(50);
//        cancelButton.setIcon("icons/16/close.png");
//        cancelButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {  
//            	SC.confirm("ยกเลิกการแก้ไขข้อมูลสินค้า", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูลสินค้า หรือไม่ ?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							updateDetails();
//							selectTab(0); // back to detail tab
//						}
//					}
//            		
//            	});
//            }  
//        });
//
//        saveButton.setDisabled(true);
//        cancelButton.setDisabled(true);
//        
        name.setWidth(150);
        time.setWidth(150);
//        weight.setWidth(250);
//        price.setWidth(250);
//        type.setWidth(250);
        editorForm.setFields(name, desc, material, time);
        editorForm.setColWidths(120, 150, 100, 100); 
        HLayout editor_control = new HLayout();
        editor_control.setMargin(5);
        editor_control.setMembersMargin(10);
        editor_control.setHeight(20);
        editor_control.addMembers(addButton, delButton, saveButton);
        //addMembers(editorForm , editor_control);
        addMembers(editorForm, editor_control, process);
    }
    
    private ListGrid processListGrid(ProcessListDS DS) {
    	
    	final ListGrid pGrid = new ListGrid();
    	pGrid.setWidth(600);  
    	pGrid.setHeight(150); 
    	
    	pGrid.setAlternateRecordStyles(true);  
    	pGrid.setShowAllRecords(true); 
    	pGrid.setSelectionType(SelectionStyle.SINGLE);
    	pGrid.setCanResizeFields(false);
    	pGrid.setCanEdit(true);
    	pGrid.setCanSort(false);
    	pGrid.setShowRowNumbers(true);
    	pGrid.setCanDrag(true);
    	pGrid.setCanReorderRecords(true);
    	
//    	ListGridField rowNumberFieldProperties = new ListGridField();
//    	rowNumberFieldProperties.setTitle("ลำดับที่");
//    	rowNumberFieldProperties.setAlign(Alignment.LEFT);
//    	rowNumberFieldProperties.setWidth(50);
//    	pGrid.setRowNumberFieldProperties(rowNumberFieldProperties);
    	
    	if (DS.pid != null) {
        	pGrid.setAutoFetchData(true); 
	    	pGrid.setDataSource(DS);
	    	pGrid.setUseAllDataSourceFields(true);
	        
	    	ListGridField field_0 = new ListGridField("id");
	        ListGridField field_1 = new ListGridField("type",150);
	        field_1.setTitle("ขั้นตอนการผลิต");
	        ListGridField field_2 = new ListGridField("std_time",100);
	        field_2.setTitle("ระยะเวลา (วัน)");
	        ListGridField field_3 = new ListGridField("desc");
	        ListGridField field_4 = new ListGridField("mat_name", 120);
	        field_2.setTitle("วัตถุดิบ");
	        
	        pGrid.setFields(field_0, field_1, field_2, field_3, field_4);
	        pGrid.hideFields("id");
	        
    	} else {
    		pGrid.setEmptyMessage("Select an item to view its details");
    	}
    	return pGrid;
    }
}
