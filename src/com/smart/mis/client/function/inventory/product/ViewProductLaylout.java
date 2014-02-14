package com.smart.mis.client.function.inventory.product;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
import com.smart.mis.client.function.production.product.ProductDS;
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

public class ViewProductLaylout extends VLayout{

	ListGrid productListGrid;
	DynamicForm searchForm;
	IButton viewButton;
	
	public ViewProductLaylout(final User currentUser) {
		setWidth(800);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(650);
		search.setHeight(45);
		search.setMargin(5);
		search.setMembersMargin(5);
		
		searchForm = new DynamicForm();
		TextItem searchItem = new TextItem("pid", "ค้นหารหัสสินค้า");
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
		
		productListGrid = new ListGrid();
		 
		productListGrid.setAutoFetchData(true);  
		productListGrid.setCanMultiSort(true);
		//productListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"));
		productListGrid.setDataSource(ProductDS.getInstance());
//		productListGrid.setInitialSort(new SortSpecifier[]{
//				new SortSpecifier("status", SortDirection.DESCENDING),
//                new SortSpecifier("created_date", SortDirection.DESCENDING)  
//        });
		productListGrid.setUseAllDataSourceFields(false);
		productListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		productListGrid.setGroupByField("type"); 
		//productListGrid.setGroupByField("status");
		//productListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField pid = new ListGridField("pid" , 100);
		ListGridField name = new ListGridField("name", "ชื่อสินค้า" , 200);
		ListGridField unit = new ListGridField("unit", 50);
		
		ListGridField remain = new ListGridField("remain");
		remain.setCellFormatter(FieldFormatter.getIntegerFormat());
		remain.setAlign(Alignment.RIGHT);
		ListGridField reserved = new ListGridField("reserved");
		reserved.setCellFormatter(FieldFormatter.getIntegerFormat());
		reserved.setAlign(Alignment.RIGHT);
		ListGridField inStock = new ListGridField("inStock");
		inStock.setCellFormatter(FieldFormatter.getIntegerFormat());
		inStock.setAlign(Alignment.RIGHT);
		
		productListGrid.setFields(pid, name, inStock, remain, reserved, unit);
		
		productListGrid.addRecordClickHandler(new RecordClickHandler() {  
			@Override
			public void onRecordClick(RecordClickEvent event) {
				viewButton.show();
			}  
        });
		
		productListGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				showUpdate();
			}
		});
		
		//productListGrid.hideField("status");
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth(750);
		gridLayout.setHeight(450);
		gridLayout.setMargin(5);
		
		gridLayout.addMember(productListGrid);
		
		return gridLayout;
	}
	
	private void updateDetails() {
//		for(ListGridRecord record : productListGrid.getRecords()) {
//			productListGrid.removeData(record);
//		}
		viewButton.hide();
		productListGrid.fetchData(searchForm.getValuesAsCriteria());
		productListGrid.deselectAllRecords();
	}
	
	private void resetDetails() {
//		for(ListGridRecord record : productListGrid.getRecords()) {
//			productListGrid.removeData(record);
//		}
		viewButton.hide();
		productListGrid.fetchData();
		productListGrid.deselectAllRecords();
	}
	
	private void showUpdate() {
		 
    	
    	final ListGridRecord record = productListGrid.getSelectedRecord();
    	final String pid = record.getAttributeAsString("pid");
    	String pname = record.getAttributeAsString("name");
    	final Integer inStock = record.getAttributeAsInt("inStock");
    	final Integer reserved = record.getAttributeAsInt("reserved");
    	Integer remain = record.getAttributeAsInt("remain");
    	String unit = record.getAttributeAsString("unit");
    	
		final Window confirm = new Window();
		confirm.setTitle("แก้ไขปริมาณสินค้าคงคลัง");
		confirm.setWidth(350);
		confirm.setHeight(220);
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
		StaticTextItem id = new StaticTextItem("pid", "รหัสสินค้า");
		StaticTextItem name = new StaticTextItem("name", "ชื่อสินค้า");
		id.setValue(pid);
		name.setValue(pname);
		
		//NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final IntegerItem pInStock = new IntegerItem("inStock", "ปริมาณคงคลัง");
		final StaticTextItem pReserved = new StaticTextItem("reserved", "ถูกจองแล้ว");
		final StaticTextItem pRemain = new StaticTextItem("remain", "คงเหลือ");
		pInStock.setValue(inStock);
		pReserved.setValue(reserved);
		pRemain.setValue(remain);
		
		pInStock.setHint(unit);
		pReserved.setHint(unit);
		pRemain.setHint(unit);
		
		pInStock.addChangedHandler( new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (pInStock.validate()) {
					if (pInStock.getValue() != null && pInStock.getValueAsInteger() > reserved) {
						Integer adjusted = pInStock.getValueAsInteger();
						pRemain.setValue(adjusted - reserved);
					} else {
						pRemain.setValue(0);
					}
				} 
				//else {
				//	SC.warn("ข้อมูลไม่ถูกต้อง");
				//}
		}});
		
		inventoryForm.setFields(id, name, pInStock, pReserved, pRemain);
		
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
            	if (!inventoryForm.validate() || pInStock.getValueAsInteger() < reserved) {
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
								updated.setAttribute("pid", pid);
								updated.setAttribute("inStock", pInStock.getValueAsInteger());
								//record.setAttribute("reserved", pReserved.getValueAsInteger()); no changed
								updated.setAttribute("remain", pInStock.getValueAsInteger() - reserved);
			            		ProductDS.getInstance().updateData(updated, new DSCallback() {
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
