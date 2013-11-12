package com.smart.mis.client.function.production.process;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.production.product.ProductData;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.prodution.ProcessType;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SelectOtherItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ProcessAdd {
//	
//    private final DataSource DS;
//	
	public void show(final String process, final Record product, final List<ListGridRecord> processList, final List<ListGridRecord> List_1, final List<ListGridRecord> List_2, final List<ListGridRecord> List_3, final List<ListGridRecord> List_4){
		
		final Window winModel = new Window();
		
		winModel.setTitle("ขั้นตอนที่ " + ProcessType.getPriority(process) + " : " + ProcessType.getDisplay(process));
		
		winModel.setWidth(700);
		winModel.setHeight(350);
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
        outlineForm.setMembersMargin(10);
        
        final DynamicForm detailsForm = new DynamicForm();
		final SelectOtherItem selectOtherItem = new SelectOtherItem("desc");  
        selectOtherItem.setOtherTitle("อื่นๆ..");  
        selectOtherItem.setOtherValue("OtherVal");
        selectOtherItem.setTitle("รายละเอียด");  
        if (process.equalsIgnoreCase("1_casting")) {
        	selectOtherItem.setValueMap("สั่งเทียน", "ใช้แบบเดิม");
        } else if (process.equalsIgnoreCase("2_scrape")) {
        	selectOtherItem.setValueMap("ฝังพลอย", "ไม่ฝังพลอย");
        } else if (process.equalsIgnoreCase("3_abrade")) {
        	selectOtherItem.setValueMap("ดิน-เงา ลงดำ", "ดิน-เงา ไม่ลงดำ", "ดิน-เงา ธรรมดา");
        } else if (process.equalsIgnoreCase("4_packing")) {
        	//selectOtherItem.setValueMap("xxx", "yyy", "zzz");
        }
        selectOtherItem.setWidth(250);
		
        final FloatItem std_time = new FloatItem("std_time");
        std_time.setTitle("ระยะเวลา");
        std_time.setHint("วัน *");
        std_time.setRequired(true);
        detailsForm.setFields(selectOtherItem, std_time);
        
        outlineForm.addMember(detailsForm);
        
        SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth(525);
    	sectionStack.setHeight(150);
    	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");
    	section.setCanCollapse(false);
        section.setExpanded(true);
        
        final ListGrid materialGrid = new ListGrid();  
        materialGrid.setWidth(525);  
        materialGrid.setHeight(150);  
        materialGrid.setCellHeight(22);  
        //materialGrid.setSaveLocally(true);
        materialGrid.setCanRemoveRecords(true);
        //materialGrid.setDeferRemoval(true);
        materialGrid.setWarnOnRemoval(true);
        materialGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการวัตถุดิบ หรือไม่?");
        
        final DataSource currentDS = new MaterialProcessDS();
        materialGrid.setDataSource(currentDS);
        
//        materialGrid.setModalEditing(true);  
//        materialGrid.setEditEvent(ListGridEditEvent.CLICK);  
//        materialGrid.setListEndEditAction(RowEndEditAction.NEXT);  
        materialGrid.setAutoSaveEdits(true);  
        
        ListGridField Field_1 = new ListGridField("mid", 150);
        ListGridField Field_2 = new ListGridField("mat_name", 200);
        ListGridField editField = new ListGridField("req_amount", 100);
        editField.setCanEdit(true);
        ListGridField Field_3 = new ListGridField("unit", 50);
        
        materialGrid.setFields(Field_1, Field_2, editField, Field_3);
        
  	      ToolStripButton changeButton = new ToolStripButton();  
	      changeButton.setHeight(18);  
	      changeButton.setWidth(120);
	      changeButton.setIcon("icons/16/comment_edit.png");  
	      changeButton.setTitle("เพิ่มวัตถุดิบ");  
	      changeButton.addClickHandler(new ClickHandler() {  
	          public void onClick(ClickEvent event) {
	        	  
	        	  final Window addMaterial = new Window();
	        	  addMaterial.setTitle("เพิ่มวัตถุดิบในขั้นตอนการผลิต");
	        	  addMaterial.setWidth(300);
	        	  addMaterial.setHeight(200);
	        	  addMaterial.setShowMinimizeButton(false);
	        	  addMaterial.setIsModal(true);
	        	  addMaterial.setShowModalMask(true);
	      		addMaterial.setCanDragResize(false);
	      		addMaterial.setCanDragReposition(false);
	      		addMaterial.centerInPage();
	      		
	        	  VLayout addLayout = new VLayout();
	        	  addLayout.setMembersMargin(5);
	        	  addLayout.setMargin(10);
	        	  addLayout.setAlign(Alignment.CENTER);
	        	  addLayout.setWidth100();
	        	  addLayout.setHeight100();
	        	  final DynamicForm addForm = new DynamicForm();
	        	  addForm.setGroupTitle("เลือกรายการวัตถุดิบ");  
	        	  addForm.setIsGroup(true);
	        	  addForm.setNumCols(1);
	        	  addForm.setTitleOrientation(TitleOrientation.TOP);
	        	  
    	      		final SelectItem material = new SelectItem("mid", "วัตถุดิบที่เลือก");
    	      		material.setOptionDataSource(MaterialDS.getInstance());
    	      		material.setValueField("mid");
    	      		material.setDisplayField("mat_name");
    	      		material.setPickListWidth(350);
    	      		//material.setDefaultValue("---โปรดเลือก---");
    	      		material.setEmptyDisplayValue("---โปรดเลือก---");
    	      		ListGridField Field_1 = new ListGridField("mid", 80);  
    	            ListGridField Field_2 = new ListGridField("mat_name", 140);  
    	            ListGridField Field_3 = new ListGridField("remain", 80); 		  
    	            ListGridField Field_4 = new ListGridField("unit", 50); 
    	            Field_3.setCellFormatter(FieldFormatter.getNumberFormat());
    	            Field_3.setAlign(Alignment.CENTER);
    	            material.setPickListFields(Field_1, Field_2, Field_3, Field_4);
    	            final FloatItem reqAmount = new FloatItem("req_amount", "จำนวนที่ใช้ในการผลิต ต่อชิ้น");
    	            reqAmount.disable();
    	            reqAmount.setDefaultValue(0.0);
    	            reqAmount.setTextAlign(Alignment.RIGHT);
    	            material.addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							Record selected = material.getSelectedRecord();
							if (selected != null) {
								reqAmount.enable();
								reqAmount.setHint(selected.getAttributeAsString("unit") + " *");
							}
						}
    	            	
    	            });
    	            
    	            addForm.setFields(material, reqAmount);
    	            addLayout.addMember(addForm);
    	            
    	            IButton confirmButton = new IButton("เพิ่มรายการ");
    	            confirmButton.setAlign(Alignment.CENTER);
    	            //confirmButton.setMargin(5);
    	            confirmButton.addClickHandler(new ClickHandler() {  
    	                public void onClick(ClickEvent event) { 
    	                  Record selected = material.getSelectedRecord();
    	                  if (selected != null && addForm.validate()) {
    	                	  String mid = selected.getAttributeAsString("mid");
    	                	  String mat_name = selected.getAttributeAsString("mat_name");
    	                	  Double req_amount = reqAmount.getValueAsFloat().doubleValue();
    	                	  String unit = selected.getAttributeAsString("unit");
    	                	  
	  	    	        	  Record newRecord = MaterialProcessData.createRecord(
	  									mid,
	  									mat_name,
	  									req_amount,
	  									unit
	  					    			);
	  	    	        	  currentDS.addData(newRecord, new DSCallback() {
								@Override
								public void execute(DSResponse dsResponse,
										Object data, DSRequest dsRequest) {
			  	    	        	  addMaterial.destroy();
			  	    	        	  materialGrid.fetchData();
								}});
    	                  } else {
    	                	  SC.warn("ข้อมูลไม่ถูกต้อง");
    	                  }
    	                }
    	            });
    	            HLayout temp = new HLayout();
    	            temp.addMember(confirmButton);
    	            temp.setAlign(Alignment.CENTER);
    	            
    	            addLayout.addMember(temp);
    	            addMaterial.addItem(addLayout);
    	            
    	            addMaterial.show();
	          }  
	      });
        		
	      section.setControls(changeButton);
	      section.setItems(materialGrid);
	      sectionStack.setSections(section);
	      
	      outlineForm.addMember(sectionStack); 
	      
	      HLayout hLayout = new HLayout(10);  
          hLayout.setAlign(Alignment.CENTER); 
           
//          IButton backButton = new IButton("ย้อนกลับ");  
//          backButton.addClickHandler(new ClickHandler() {  
//              public void onClick(ClickEvent event) {
//                	SC.confirm("ยกเลิกการทำรายการ", "ท่านต้องการยกเลิกการทำรายการหรือไม่ ?" , new BooleanCallback() {
//    					@Override
//    					public void execute(Boolean value) {
//    						if (value) {
//    							winModel.destroy();
//    							
//    						}
//    					}
//                	});
//              }  
//          });  
//          hLayout.addMember(backButton); 
          
          IButton closeButton = new IButton("ปิด");  
          closeButton.setIcon("icons/16/close.png");
          closeButton.addClickHandler(new ClickHandler() {  
              public void onClick(ClickEvent event) {
                	SC.confirm("ยกเลิกการทำรายการ", "ท่านต้องการยกเลิกการทำรายการหรือไม่ ?" , new BooleanCallback() {
    					@Override
    					public void execute(Boolean value) {
    						if (value) {
    							winModel.destroy();
    						}
    					}
                	});
              }  
          });  
          hLayout.addMember(closeButton);  
          
          IButton discardButton = new IButton("ล้างรายการ");
          discardButton.setIcon("icons/16/delete.png");
          discardButton.addClickHandler(new ClickHandler() {  
              public void onClick(ClickEvent event) {  
                  for (ListGridRecord item : materialGrid.getRecords()) {
                	  materialGrid.removeData(item);
                  }
              }  
          });  
          hLayout.addMember(discardButton);  
                        

          IButton saveButton = new IButton("ต่อไป");
          if (process.equalsIgnoreCase("4_packing")) {	
        	  saveButton.setTitle("บันทึก");
        	  saveButton.setIcon("icons/16/save.png");
          } else {
              saveButton.setIcon("icons/16/next.png");
          }
          saveButton.setTop(250);  
          saveButton.addClickHandler(new ClickHandler() {  
              public void onClick(ClickEvent event) { 
            	//System.out.println("Grid size " + materialGrid.getRecords().length);
            	if (!detailsForm.validate()) {
            		SC.warn("ข้อมูลไม่ครบถ้วน");
            		return;
            	}
//              	SC.confirm("ยืนยันการทำรายการ", "ท่านต้องการยืนยันและทำรายการต่อไปหรือไม่ ?" , new BooleanCallback() {
//  					@Override
//  					public void execute(Boolean value) {
//  						if (value) {
  							if (process.equalsIgnoreCase("1_casting")) {
  								processList.add(ProcessData.createRecord("1_casting", selectOtherItem.getValueAsString(), std_time.getValueAsFloat().doubleValue(), 1));
  								List_1.clear();
  								for (ListGridRecord item : materialGrid.getRecords()) {
  									List_1.add(item);
  								}
  					        	ProcessAdd next = new ProcessAdd();
  					        	//System.out.println("Go to step 2 " + materialGrid.getRecords().length  + "to" + List_1.size());
  					        	next.show("2_scrape", product, processList, List_1, List_2, List_3, List_4);
  	  							winModel.destroy();
  					        } else if (process.equalsIgnoreCase("2_scrape")) {
  					        	processList.add(ProcessData.createRecord("2_scrape", selectOtherItem.getValueAsString(), std_time.getValueAsFloat().doubleValue(), 2));
  					        	List_2.clear();
  					        	for (ListGridRecord item : materialGrid.getRecords()) {
  					        		List_2.add(item);
  								}
  					        	ProcessAdd next = new ProcessAdd();
  					        	//System.out.println("Go to step 3 " + materialGrid.getRecords().length  + "to" + List_2.size());
  					        	next.show("3_abrade", product, processList, List_1, List_2, List_3, List_4);
  	  							winModel.destroy();
  					        } else if (process.equalsIgnoreCase("3_abrade")) {
  					        	processList.add(ProcessData.createRecord("3_abrade", selectOtherItem.getValueAsString(), std_time.getValueAsFloat().doubleValue(), 2));
  					        	List_3.clear();
  					        	for (ListGridRecord item : materialGrid.getRecords()) {
  					        		List_3.add(item);
  								}
  					        	ProcessAdd next = new ProcessAdd();
  					        	//System.out.println("Go to step 4 " + materialGrid.getRecords().length  + "to" + List_3.size());
  					        	next.show("4_packing", product,processList, List_1, List_2, List_3, List_4);
  	  							winModel.destroy();
  					        } else if (process.equalsIgnoreCase("4_packing")) {
  					        	processList.add(ProcessData.createRecord("4_packing", selectOtherItem.getValueAsString(), std_time.getValueAsFloat().doubleValue(), 2));
  					        	List_4.clear();
  					        	for (ListGridRecord item : materialGrid.getRecords()) {
  					        		List_4.add(item);
  								}
	  			              	SC.confirm("ยืนยันการทำรายการ", "ท่านต้องการยืนยันและทำรายการต่อไปหรือไม่ ?" , new BooleanCallback() {
		  		  					@Override
		  		  					public void execute(Boolean value) {
		  		  						if (value) {
			  					        	saveProduct(product,processList, List_1, List_2, List_3, List_4);
											winModel.destroy();
				  						}
				  					}
				              	});
  					        }
//  						}
//  					}
//              	});
              }  
          });  
          hLayout.addMember(saveButton); 
          
          outlineForm.addMember(hLayout); 
          
          winModel.addItem(outlineForm);
          winModel.show();
    }
	
	private void saveProduct(final Record product, final List<ListGridRecord> processList, final List<ListGridRecord> List_1, final List<ListGridRecord> List_2, final List<ListGridRecord> List_3, final List<ListGridRecord> List_4){
		//Save part
		String product_id = "PD70" + Math.round((Math.random() * 100));
		String process_id_1 = "PS70" + Math.round((Math.random() * 100));
		String process_id_2 = "PS70" + Math.round((Math.random() * 100));
		String process_id_3 = "PS70" + Math.round((Math.random() * 100));
		String process_id_4 = "PS70" + Math.round((Math.random() * 100));
		
      	product.setAttribute("pid", "PD70" + Math.round((Math.random() * 100)));
      	
      	ListGridRecord process_1 = processList.get(0);
      	process_1.setAttribute("pid", product_id);
      	process_1.setAttribute("psid", process_id_1);
      	ListGridRecord process_2 = processList.get(1);
      	process_2.setAttribute("pid", product_id);
      	process_2.setAttribute("psid", process_id_2);
      	ListGridRecord process_3 = processList.get(2);
      	process_3.setAttribute("pid", product_id);
      	process_3.setAttribute("psid", process_id_3);
      	ListGridRecord process_4 = processList.get(3);
      	process_4.setAttribute("pid", product_id);
      	process_4.setAttribute("psid", process_id_4);
      	
      	ProcessListDS.getInstance(product_id).addData(process_1);
      	ProcessListDS.getInstance(product_id).addData(process_2);
      	ProcessListDS.getInstance(product_id).addData(process_3);
      	ProcessListDS.getInstance(product_id).addData(process_4);
      	
      	//System.out.println("List_1 " + List_1.size());
      	//System.out.println("List_2 " + List_2.size());
      	//System.out.println("List_3 " + List_3.size());
      	//System.out.println("List_4 " + List_4.size());
      	
      	for (ListGridRecord item : List_1) {
      		String mat_process_id = "MP70" + Math.round((Math.random() * 100));
      		item.setAttribute("mpid", mat_process_id);
      		item.setAttribute("psid", process_id_1);
      		MaterialProcessDS.getInstance(process_id_1, product_id).addData(item);
      	}
      	
      	for (ListGridRecord item : List_2) {
      		String mat_process_id = "MP70" + Math.round((Math.random() * 100));
      		item.setAttribute("mpid", mat_process_id);
      		item.setAttribute("psid", process_id_2);
      		MaterialProcessDS.getInstance(process_id_2, product_id).addData(item);
      	}
      	
      	for (ListGridRecord item : List_3) {
      		String mat_process_id = "MP70" + Math.round((Math.random() * 100));
      		item.setAttribute("mpid", mat_process_id);
      		item.setAttribute("psid", process_id_3);
      		MaterialProcessDS.getInstance(process_id_3, product_id).addData(item);
      	}
      	
      	for (ListGridRecord item : List_4) {
      		String mat_process_id = "MP70" + Math.round((Math.random() * 100));
      		item.setAttribute("mpid", mat_process_id);
      		item.setAttribute("psid", process_id_4);
      		MaterialProcessDS.getInstance(process_id_4, product_id).addData(item);
      	}
      	
      	ProductDS.getInstance().addData(product, new DSCallback() {
      		//
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การเพิ่มข้อมูลสินค้าล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
					} else { 
						SC.say("เพิ่มข้อมูลสินค้าเรียบร้อยแล้ว");
					}
			}
    	});
	}
}
