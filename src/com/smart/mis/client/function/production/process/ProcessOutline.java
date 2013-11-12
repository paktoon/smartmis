package com.smart.mis.client.function.production.process;

import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.production.product.ProductData;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.prodution.ProcessType;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ProcessOutline extends VLayout {
	
	//public DynamicForm editorForm;
    public ProcessOutline(final ProcessListDS DS) {
    	
        final ListGrid process = processListGrid(DS);
        //on refresh
    	DS.refreshData();
        process.invalidateCache(); // fresh data from server
        
        setWidth100();
        setHeight(100);
        setMargin(5);
        
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
        //name.setDefaultValue("---โปรดเลือก---");
        name.setEmptyDisplayValue("---โปรดเลือก---");
        name.setValueMap(ProcessType.getValueMap());
        
		TextItem time = new TextItem("std_time", "เวลาผลิต");

//		SelectItem material = new SelectItem("mid", "วัตถุดิบ");
//		material.setOptionDataSource(MaterialDS.getInstance());
//		material.setValueField("mid");
//		material.setDisplayField("mat_name");
//		material.setPickListWidth(300);
//		ListGridField Field_1 = new ListGridField("mid", 80);  
//        ListGridField Field_2 = new ListGridField("mat_name", 140);  
//        ListGridField Field_3 = new ListGridField("remain", 80); 
//        Field_3.setCellFormatter(FieldFormatter.getNumberFormat());
//        Field_3.setAlign(Alignment.CENTER);
//        material.setPickListFields(Field_1, Field_2, Field_3);
        
		TextAreaItem desc = new TextAreaItem("desc", "คำอธิบาย");
		desc.setWidth(220);
		desc.setRowSpan(4);
		
		name.setRequired(true);
		time.setRequired(true);
		desc.setRequired(true);
		
		name.setHint("*");
		time.setHint("วัน *");
		desc.setHint("*");
		
        HLayout editor_control = new HLayout();
        editor_control.setMargin(5);
        editor_control.setMembersMargin(10);
        editor_control.setHeight(20);
        
		IButton addButton = new IButton("เพิ่มขั้นตอน");  
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("เพิ่มขั้นตอนการผลิต", "ท่านต้องการ เพิ่มขั้นตอนการผลิตสินค้า หรือไม่ ?" , new BooleanCallback() {
            			@Override
                        public void execute(Boolean value) {  
            				if (value) {
            					Integer priority = ProcessType.getPriority(editorForm.getValueAsString("type"));
            					
	            				Record newRecord = ProcessData.createRecord(
										"PS70" + Math.round((Math.random() * 100)),
										editorForm.getValueAsString("type"),
										editorForm.getValueAsString("desc"),
										Double.parseDouble(editorForm.getValueAsString("std_time")),
										DS.pid,
										priority
										//,
										//editorForm.getValueAsString("mid"),
										//editorForm.getField("mid").getDisplayValue()
						    			);
	            				
	            				DS.addData(newRecord, new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data,
											DSRequest dsRequest) {
											if (dsResponse.getStatus() != 0) {
												SC.warn("การเพิ่มขั้นตอนล้มเหลว มีขั้นตอนนี้อยู่แล้ว");
											} else { 
												process.fetchData();
								            	editorForm.reset(); 
											}
									}
	            				});
            				}
                        }
                    }); 
            }  
        });
        
		editor_control.addMember(addButton);
		
//		IButton delButton = new IButton("ลบขั้นตอน");
//		delButton.setIcon("icons/16/delete.png");
//		delButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {
//            	Record selected = process.getSelectedRecord();
//            	if (selected == null) {
//            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
//            	} else {
//            		SC.confirm("ยืนยันการลบ ขั้นตอนการผลิต", "ท่านต้องการลบ ขั้นตอนการผลิต หรือไม่ ?" , new BooleanCallback() {
//    					@Override
//    					public void execute(Boolean value) {
//    						if (value) {
//    							process.removeSelectedData();
//    							editorForm.reset();
//    						}
//    					}
//                	});
//            	}
//            }  
//        });
		IButton discardButton = new IButton("ยกเลิก");  
		discardButton.setIcon("icons/16/delete.png");
        discardButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                process.discardAllEdits();  
            }  
        });  
        editor_control.addMember(discardButton);  
		
		IButton saveButton = new IButton("บันทึกรายการ");  
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
							process.saveAllEdits();
						}
					}
            		
            	});
            }  
        }); 
        editor_control.addMember(saveButton);
        
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
//        editorForm.setFields(name, desc, material, time);
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(name, desc, time);
        editorForm.setColWidths(120, 150, 100, 100); 

        //editor_control.addMembers(addButton, delButton, saveButton);
        //addMembers(editorForm , editor_control);
        addMembers(editorForm, editor_control, process);
    }
    
    private ListGrid processListGrid(final ProcessListDS DS) {
    	
    	final ListGrid pGrid = new ListGrid()  {  
            public DataSource getRelatedDataSource(ListGridRecord record) {  
                //return new MaterialProcessDS(record.getAttributeAsString("psid"), DS.pid);
                return MaterialProcessDS.getInstance(record.getAttributeAsString("psid"), DS.pid);
            }  
  
            @Override  
            protected Canvas getExpansionComponent(final ListGridRecord record) {  
  
                final ListGrid grid = this;  
  
                VLayout layout = new VLayout(5);  
                layout.setPadding(5);  
  
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
                materialGrid.setDeferRemoval(true);
                materialGrid.setWarnOnRemoval(true);
                materialGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการวัตถุดิบ หรือไม่?");
                
                materialGrid.setDataSource(getRelatedDataSource(record));  
                materialGrid.fetchRelatedData(record, DS); 
            	
                materialGrid.setModalEditing(true);  
                materialGrid.setEditEvent(ListGridEditEvent.CLICK);  
                materialGrid.setListEndEditAction(RowEndEditAction.NEXT);  
                materialGrid.setAutoSaveEdits(false);  
  
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
	    	        	  //changeFunc.show(currentMid);
	    	        	  //
	    	        	  final String psid = grid.getSelectedRecord().getAttributeAsString("psid");
	    	        	  final String pid = DS.pid;
	    	        	  
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
			    	                  //SC.warn("psid = " + psid + " pid = " + pid + " mid = " + mid + " mat_name = " + mat_name + " req_amount = " + req_amount);
			    	                  
			  	    	        	  MaterialProcessDS currentDS = MaterialProcessDS.getInstance(psid, pid);
			  	    	        	  Record newRecord = MaterialProcessData.createRecord(
			  									"MP70" + Math.round((Math.random() * 100)),
			  									psid,
			  									mid,
			  									mat_name,
			  									req_amount,
			  									unit
			  					    			);
			  	    	        	  currentDS.addData(newRecord);
			  	    	        	  materialGrid.fetchData();
			  	    	        	  addMaterial.destroy();
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
    	      
                layout.addMember(sectionStack);  
  
                HLayout hLayout = new HLayout(10);  
                hLayout.setAlign(Alignment.CENTER);  
  
//        		IButton delButton = new IButton("ลบรายการ");
//        		//delButton.setIcon("icons/16/delete.png");
//        		delButton.addClickHandler(new ClickHandler() {  
//                    public void onClick(ClickEvent event) {
//            			materialGrid.removeSelectedData();
//                    }  
//                });
//        		hLayout.addMember(delButton); 
        		
                IButton saveButton = new IButton("ยืนยันการทำรายการ");  
                saveButton.setTop(250);  
                saveButton.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) { 
                    	SC.confirm("ยืนยันการทำรายการ", "ท่านต้องการยืนยันการทำรายการ หรือไม่ ?" , new BooleanCallback() {
        					@Override
        					public void execute(Boolean value) {
        						if (value) {
        	                        materialGrid.saveAllEdits();
        						}
        					}
                    	});
                    }  
                });  
                hLayout.addMember(saveButton);  
  
                IButton discardButton = new IButton("ยกเลิก");  
                discardButton.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        materialGrid.discardAllEdits();  
                    }  
                });  
                hLayout.addMember(discardButton);  
  
                IButton closeButton = new IButton("ปิด");  
                closeButton.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        grid.collapseRecord(record);  
                    }  
                });  
                hLayout.addMember(closeButton);  
                                                 
                layout.addMember(hLayout);  
  
                return layout;  
            }  
        };
        
    	pGrid.setWidth(600);  
    	pGrid.setHeight(300); 
        
    	pGrid.setCanRemoveRecords(true);
    	pGrid.setDeferRemoval(true);
    	pGrid.setWarnOnRemoval(true);
    	pGrid.setWarnOnRemovalMessage("คุณต้องการลบ ขั้นตอนการผลิต หรือไม่?");
    	pGrid.setAutoSaveEdits(false); 
    	
    	pGrid.setAlternateRecordStyles(true);  
    	pGrid.setShowAllRecords(true); 
    	pGrid.setSelectionType(SelectionStyle.SINGLE);
    	pGrid.setCanResizeFields(false);
    	pGrid.setCanEdit(true);
    	pGrid.setCanSort(false);
    	pGrid.setShowRowNumbers(true);
    	pGrid.setCanDrag(true);
    	//pGrid.setCanReorderRecords(true);
    	pGrid.setDrawAheadRatio(4);  
    	pGrid.setCanExpandRecords(true);
//    	ListGridField rowNumberFieldProperties = new ListGridField();
//    	rowNumberFieldProperties.setTitle("ลำดับที่");
//    	rowNumberFieldProperties.setAlign(Alignment.LEFT);
//    	rowNumberFieldProperties.setWidth(50);
//    	pGrid.setRowNumberFieldProperties(rowNumberFieldProperties);
    	
    	if (DS.pid != null) {
        	
        	pGrid.setAutoFetchData(true); 
	    	pGrid.setDataSource(DS);
	    	pGrid.setUseAllDataSourceFields(true);
	        
	    	ListGridField field_0 = new ListGridField("psid");
	        ListGridField field_1 = new ListGridField("type",150);
	        field_1.setTitle("ขั้นตอนการผลิต");
	        ListGridField field_2 = new ListGridField("std_time",100);
	        field_2.setTitle("เวลาผลิต (วัน)");
	        ListGridField field_3 = new ListGridField("desc");
	        ListGridField field_4 = new ListGridField("priority");
	        
	        pGrid.setFields(field_0, field_1, field_2, field_3, field_4);
	        
	        pGrid.sort("priority", SortDirection.ASCENDING);
	        pGrid.hideFields("psid", "priority");
	        
    	} else {
    		pGrid.setEmptyMessage("Select an item to view its details");
    	}
    	return pGrid;
    }
    
//    private Integer getPriority(String type){
//    	String[] valueMap = new String[]{"หล่อและขึ้นรูป", "แต่ง", "ฝังพลอย", "ขัดและติดพลอย", "บรรจุหีบห่อ"};
//    	if (type.equalsIgnoreCase(valueMap[0])) return 1;
//    	else if (type.equalsIgnoreCase(valueMap[1])) return 2;
//    	else if (type.equalsIgnoreCase(valueMap[2])) return 3;
//    	else if (type.equalsIgnoreCase(valueMap[3])) return 4;
//    	else if (type.equalsIgnoreCase(valueMap[4])) return 5;
//    	else return 99;
//    }
}
