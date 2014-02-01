package com.smart.mis.client.function.inventory.material;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DoubleItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ViewMaterialLaylout extends VLayout{

	ListGrid materialListGrid;
	DynamicForm searchForm;
	IButton viewButton;
	
	public ViewMaterialLaylout(final User currentUser) {
		setWidth(800);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(650);
		search.setHeight(45);
		search.setMargin(5);
		search.setMembersMargin(5);
		
		searchForm = new DynamicForm();
		TextItem searchItem = new TextItem("mid", "ค้นหารหัสวัตถุดิบ");
        searchItem.setTitleOrientation(TitleOrientation.TOP);
        searchItem.setColSpan(2);
        searchItem.setTitleAlign(Alignment.LEFT);
        searchItem.addKeyPressHandler(new KeyPressHandler() {
            public void onKeyPress(KeyPressEvent event) {
                if("enter".equalsIgnoreCase(event.getKeyName())) {
                    updateDetails();
                }
            }
        });
        final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
        final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
        searchItem.setIcons(findIcon, cancelIcon);
        
        searchItem.addIconClickHandler(new IconClickHandler() {
            public void onIconClick(IconClickEvent event) {
                FormItemIcon icon = event.getIcon();
                if(icon.getSrc().equals(cancelIcon.getSrc())) {
                	searchForm.reset();
                	resetDetails();
                } else {
                	updateDetails();
                }
            }
        });
        
        searchForm.setFields(searchItem);
        search.addMember(searchForm);

        VLayout buttonLayout = new VLayout();
        buttonLayout.setAlign(VerticalAlignment.BOTTOM);
        buttonLayout.setHeight(38);
        viewButton = new IButton("แก้ไขปริมาณคงเหลือ");
        viewButton.setIcon("icons/16/folder_out.png");
        viewButton.setWidth(150);
        viewButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	showUpdate();
            }
        });
        
        viewButton.hide();
        buttonLayout.addMember(viewButton);
        
        search.addMember(buttonLayout);
        addMember(search);
        
        addMember(createResult());
	}
	
	private VLayout createResult() {
		
		materialListGrid = new ListGrid();
		 
		materialListGrid.setAutoFetchData(true);  
		materialListGrid.setCanMultiSort(true);
		//materialListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"));
		materialListGrid.setDataSource(MaterialDS.getInstance());
//		materialListGrid.setInitialSort(new SortSpecifier[]{
//				new SortSpecifier("status", SortDirection.DESCENDING),
//                new SortSpecifier("created_date", SortDirection.DESCENDING)  
//        });
		materialListGrid.setUseAllDataSourceFields(false);
		materialListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		materialListGrid.setGroupByField("type"); 
		//materialListGrid.setGroupByField("status");
		//materialListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField mid = new ListGridField("mid" , 100);
		ListGridField name = new ListGridField("mat_name", 180);
		ListGridField unit = new ListGridField("unit", 50);
		
		ListGridField safety = new ListGridField("safety");
		safety.setCellFormatter(FieldFormatter.getNumberFormat());
		safety.setAlign(Alignment.RIGHT);
		ListGridField remain = new ListGridField("remain");
		remain.setCellFormatter(FieldFormatter.getNumberFormat());
		remain.setAlign(Alignment.RIGHT);
		ListGridField reserved = new ListGridField("reserved");
		reserved.setCellFormatter(FieldFormatter.getNumberFormat());
		reserved.setAlign(Alignment.RIGHT);
		ListGridField inStock = new ListGridField("inStock");
		inStock.setCellFormatter(FieldFormatter.getNumberFormat());
		inStock.setAlign(Alignment.RIGHT);
		
		materialListGrid.setFields(mid, name, safety, inStock, remain, reserved, unit);
		
		materialListGrid.addRecordClickHandler(new RecordClickHandler() {  
			@Override
			public void onRecordClick(RecordClickEvent event) {
				viewButton.show();
			}  
        });
		
		materialListGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				showUpdate();
			}
		});
		
		//materialListGrid.hideField("status");
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth(750);
		gridLayout.setHeight(450);
		gridLayout.setMargin(5);
		
		gridLayout.addMember(materialListGrid);
		
		return gridLayout;
	}
	
	private void updateDetails() {
//		for(ListGridRecord record : materialListGrid.getRecords()) {
//			materialListGrid.removeData(record);
//		}
		viewButton.hide();
		materialListGrid.fetchData(searchForm.getValuesAsCriteria());
		materialListGrid.deselectAllRecords();
	}
	
	private void resetDetails() {
//		for(ListGridRecord record : materialListGrid.getRecords()) {
//			materialListGrid.removeData(record);
//		}
		viewButton.hide();
		materialListGrid.fetchData();
		materialListGrid.deselectAllRecords();
	}
	
	private void showUpdate() {
    	
    	final ListGridRecord record = materialListGrid.getSelectedRecord();
    	final String mid = record.getAttributeAsString("mid");
    	String mat_name = record.getAttributeAsString("mat_name");
    	Double safety = record.getAttributeAsDouble("safety");
    	final Double inStock = record.getAttributeAsDouble("inStock");
    	final Double reserved = record.getAttributeAsDouble("reserved");
    	Double remain = record.getAttributeAsDouble("remain");
    	String unit = record.getAttributeAsString("unit");
    	
		final Window confirm = new Window();
		confirm.setTitle("แก้ไขปริมาณวัตถุดิบคงคลัง");
		confirm.setWidth(350);
		confirm.setHeight(250);
		confirm.setShowMinimizeButton(false);
		confirm.setIsModal(true);
		confirm.setShowModalMask(true);
		confirm.setCanDragResize(false); 
		confirm.setCanDragReposition(false);
		confirm.centerInPage();
		VLayout inventoryLayout = new VLayout();
		inventoryLayout.setMargin(10);
		
		final DynamicForm inventoryForm = new DynamicForm();
		inventoryForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		StaticTextItem id = new StaticTextItem("pid", "รหัสวัตถุดิบ");
		StaticTextItem name = new StaticTextItem("name", "ชื่อวัตถุดิบ");
		id.setValue(mid);
		name.setValue(mat_name);
		
		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final DoubleItem pSafety = new DoubleItem("safety", "จำนวนสำรองขั้นต่ำ");
		final DoubleItem pInStock = new DoubleItem("inStock", "ปริมาณคงคลัง");
		final StaticTextItem pReserved = new StaticTextItem("reserved", "ถูกจองแล้ว");
		final StaticTextItem pRemain = new StaticTextItem("remain", "คงเหลือ");
		pSafety.setValue(safety);
		pInStock.setValue(inStock);
		pReserved.setValue(nf.format(reserved));
		pRemain.setValue(nf.format(remain));
		
		pSafety.setHint(unit);
		pInStock.setHint(unit);
		pReserved.setHint(unit);
		pRemain.setHint(unit);
		
		pInStock.addChangedHandler( new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (pInStock.validate()) {
					if (pInStock.getValue() != null && pInStock.getValueAsDouble() > reserved) {
						Double adjusted = pInStock.getValueAsDouble();
						pRemain.setValue(nf.format(adjusted - reserved));
					} else {
						pRemain.setValue(0);
					}
				} 
		}});
		
		pSafety.addChangedHandler( new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				pSafety.validate();
		}});
		
		inventoryForm.setFields(id, name, pSafety, pInStock, pReserved, pRemain);
		
		inventoryLayout.addMember(inventoryForm);
		
		HLayout controlLayout = new HLayout();
        controlLayout.setMargin(10);
        controlLayout.setMembersMargin(10);
        controlLayout.setAlign(Alignment.CENTER);
        IButton confirmButton = new IButton("บันทึก");
        confirmButton.setIcon("icons/16/save.png");
        IButton cancelButton = new IButton("ยกเลิก");
        cancelButton.setIcon("icons/16/close.png");
        controlLayout.addMember(confirmButton);
        controlLayout.addMember(cancelButton);
        confirmButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	if (!inventoryForm.validate() || pInStock.getValueAsDouble() < reserved) {
            		SC.warn("ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูลใหม่อีกครั้ง");
            		pInStock.setValue(inStock);
            		pInStock.focusInItem();
            		return;
            	}
            	
            	SC.confirm("ยืนยันการแก้ไข", "ต้องการบันทึกการแก้ไขหรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
								//ListGridRecord updated = new ListGridRecord();
								ListGridRecord updated = record;
								
								System.out.println("safety " + pSafety.getValueAsDouble());
								System.out.println("inStock " + pInStock.getValueAsDouble());
								System.out.println("reserved " + reserved);
								System.out.println("remain " + (pInStock.getValueAsDouble() - reserved));
								
								//updated.setAttribute("mid", mid);
								updated.setAttribute("safety", pSafety.getValueAsDouble());
								updated.setAttribute("inStock", pInStock.getValueAsDouble());
								updated.setAttribute("reserved", reserved);
								updated.setAttribute("remain", pInStock.getValueAsDouble() - reserved);
			            		MaterialDS.getInstance().updateData(updated, new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data,
											DSRequest dsRequest) {
											if (dsResponse.getStatus() != 0) {
												SC.warn("การบันทึกแก้ไข ล้มเหลว");
											} else { 
												SC.say("การบันทึกแก้ไข เสร็จสมบูรณ์");
												confirm.destroy();
											}
									}
								});
						}
					}
            	});
          }
        });
        
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	confirm.destroy();
          }
        });
        
        inventoryLayout.addMember(controlLayout);
        
        confirm.addItem(inventoryLayout);
        
        confirm.show();
	}
}
