package com.smart.mis.client.function.sale.quotation;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.production.process.ProcessData;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class QuotationTabSet extends TabSet {

	Boolean allowApproved;
	public QuotationTabSet(Boolean allow) {
		this.allowApproved = allow;
		
		setWidth100();
		setHeight100();
		
		addTab(getCreateTab());
		addTab(getReviseTab());
		
		if (this.allowApproved) {
			addTab(getApproveTab());
		}
	}
	
	private Tab getCreateTab(){
		Tab createTab = new Tab("ออกใบเสนอราคา", "icons/16/icon_add_files.png");
		//TBD
		VLayout createLayout = new VLayout();
		createLayout.setWidth(750);
		createLayout.setHeight100();
		
		//******************Header
		HLayout headerLayout = new HLayout();
		headerLayout.setHeight(20);
		Label quoteId = new Label();
		quoteId.setContents("รหัสใบเสนอราคา : ");
		quoteId.setWidth("25%");
		quoteId.setAlign(Alignment.LEFT);
		headerLayout.addMember(quoteId);
		
		Label empty = new Label();
		empty.setWidth("*");
		headerLayout.addMember(empty);
		Label createDate = new Label();
		Date today = new Date();
		DateTimeFormat pattern = DateTimeFormat.getFormat("dd/MM/yyyy");
		createDate.setContents("วันที่สร้าง : " + pattern.format(today));
		createDate.setWidth("15%");
		createDate.setAlign(Alignment.RIGHT);
		headerLayout.addMember(createDate);
		createLayout.addMember(headerLayout);
		//******************End Header
		
		//******************Customer
		DynamicForm customerForm = new DynamicForm();
		customerForm.setWidth100(); 
		customerForm.setHeight(30);
		customerForm.setMargin(5); 
		customerForm.setNumCols(6); 
		customerForm.setCellPadding(2);
		customerForm.setAutoFocus(true);
		customerForm.setSelectOnFocus(true);
		//customerForm.setDataSource(CustomerDS.getInstance());
		//customerForm.setUseAllDataSourceFields(false);
		customerForm.setIsGroup(true);
		customerForm.setGroupTitle("ข้อมูลลูกค้า");
        
		final StaticTextItem cid = new StaticTextItem("cid", "รหัสลูกค้า");
		final SelectItem cus_name = new SelectItem("cus_name", "ชื่อลูกค้า");
		cus_name.setOptionDataSource(CustomerDS.getInstance());
		cus_name.setEmptyDisplayValue("--โปรดเลือกลูกค้า--");
		cus_name.setPickListWidth(350);
		cus_name.setWidth(240);
		ListGridField Field_1 = new ListGridField("cid", 80);  
        ListGridField Field_2 = new ListGridField("cus_name", 200);  
        ListGridField Field_3 = new ListGridField("cus_type", 70);
        cus_name.setPickListFields(Field_1, Field_2, Field_3);
        		
		final StaticTextItem type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
		
		cus_name.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Record selected = cus_name.getSelectedRecord();
				if (selected != null) {
					String customer_id = selected.getAttributeAsString("cid");
					String customer_name = selected.getAttributeAsString("cus_name");
					String customer_type = selected.getAttributeAsString("cus_type");
					//Contact info
					String customer_address = selected.getAttributeAsString("address");
					String customer_phone = selected.getAttributeAsString("cus_phone");
					String contact_name = selected.getAttributeAsString("contact_name");
					String contact_phone = selected.getAttributeAsString("contact_phone");
					
					cid.setValue(customer_id);
					type.setValue(customer_type);
				}
			}
        });
		
		customerForm.setFields(cid, cus_name, type);
		customerForm.setColWidths(100, 80, 80, 240, 100, 100);
		createLayout.addMember(customerForm);
		//******************End Customer
		
		//******************Product Header
		final DynamicForm productForm = new DynamicForm();  
		productForm.setWidth100(); 
		productForm.setMargin(5);  
		productForm.setNumCols(6);
		productForm.setCellPadding(2);
		productForm.setAutoFocus(false);  
        //productForm.setDataSource(ProductDS.getInstance());  
        //productForm.setUseAllDataSourceFields(false); 
		productForm.setIsGroup(true);
		productForm.setGroupTitle("เลือกสินค้า");
        
        final StaticTextItem pid = new StaticTextItem("pid" , "รหัสสินค้า");
        final SelectItem pname = new SelectItem("name", "ชื่อสินค้า");
        pname.setOptionDataSource(ProductDS.getInstance());
        pname.setEmptyDisplayValue("--โปรดเลือกสินค้า--");
        pname.setPickListWidth(420);
        pname.setWidth(240);
		ListGridField Field_M1 = new ListGridField("pid", 80);  
        ListGridField Field_M2 = new ListGridField("name", 200);
        ListGridField Field_M3 = new ListGridField("type", 70);
        ListGridField Field_M4 = new ListGridField("remain", 70);
        Field_M4.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_M4.setAlign(Alignment.LEFT);
        pname.setPickListFields(Field_M1, Field_M2, Field_M3, Field_M4);
        
        final StaticTextItem ptype = new StaticTextItem("type", "ประเภทสินค้า");
        
        final IntegerItem quantity = new IntegerItem("quantity", "จำนวน");
        quantity.setHint("*");
        quantity.disable();
        quantity.setWidth(100);
        
        final StaticTextItem desc = new StaticTextItem("desc" , "คำอธิบาย");
        final StaticTextItem pprice = new StaticTextItem("pprice" , "ราคา");
		
        pname.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Record selected = pname.getSelectedRecord();
				if (selected != null) {
					
					//addButton.enable();
					String product_id = selected.getAttributeAsString("pid");
					String product_name = selected.getAttributeAsString("name");
					String product_desc = selected.getAttributeAsString("desc");
					//Contact info
					String product_size = selected.getAttributeAsString("size");
					String product_weight = selected.getAttributeAsString("weight");
					String product_price = selected.getAttributeAsString("price");
					String product_type = selected.getAttributeAsString("type");
					
					Double product_remain = selected.getAttributeAsDouble("remain");
					String product_unit = selected.getAttributeAsString("unit");
					Boolean product_inStock = selected.getAttributeAsBoolean("inStock");
					
					quantity.setHint(product_unit + "*");
					pprice.setHint("บาท ต่อ "+product_unit);
					//unit.setValue(product_unit + "*");
					quantity.enable();
					pname.setValue(product_name);
					pid.setValue(product_id);
					desc.setValue(product_desc);
					ptype.setValue(product_type);
					pprice.setValue(product_price);
				}
			}
        });
        productForm.setFields(pid, pname, pprice, ptype, desc , quantity);
        productForm.setColWidths(100, 80, 80, 240, 100, 100);
        //productForm.setColWidths(100, 100, 100, 100, 50, 50);
		createLayout.addMember(productForm);
		//******************End Product Header
		
		//******************Add button
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(5);
		
        final IButton addButton = new IButton("เพิ่มรายการสินค้า");  
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.setWidth(120);
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	SC.warn("Test --- Naja");
            	//addButton.disable();
            	productForm.reset();
            	quantity.setHint("*");
				pprice.setHint("");
            }  
        });
		//addButton.disable();
		
		buttonLayout.addMember(addButton);
		createLayout.addMember(buttonLayout);
		//******************End Add
		
		//******************Quote List Grid
		HLayout itemLayout = new HLayout();
		itemLayout.setMargin(5);
		ListGrid quoteListGrid = new ListGrid();
		quoteListGrid.setWidth100();
		quoteListGrid.setHeight(300);
		quoteListGrid.setAlternateRecordStyles(true);  
		quoteListGrid.setShowAllRecords(true);  
		quoteListGrid.setAutoFetchData(true);  
		quoteListGrid.setSelectionType(SelectionStyle.SINGLE);
		quoteListGrid.setCanResizeFields(false);
		quoteListGrid.setCanEdit(false);
		//item removal
		quoteListGrid.setCanRemoveRecords(true);
		quoteListGrid.setWarnOnRemoval(true);
		quoteListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
		
		quoteListGrid.setDataSource(QuoteProductDS.getInstance());
		quoteListGrid.setUseAllDataSourceFields(false);
        
		ListGridField quoteItemCell_1 = new ListGridField("pid", 80);  
        ListGridField quoteItemCell_2 = new ListGridField("name", 140);  
        ListGridField quoteItemCell_3 = new ListGridField("size", 80); 		  
        ListGridField quoteItemCell_4 = new ListGridField("weight", 50); 	  
        ListGridField quoteItemCell_5 = new ListGridField("price", 50);
        ListGridField quoteItemCell_6 = new ListGridField("quote_amount", 50);
        ListGridField quoteItemCell_7 = new ListGridField("quote_amount", 50);
        
		itemLayout.addMember(quoteListGrid);
		createLayout.addMember(itemLayout);
		//******************End Product Grid
		
		//******************Summary
		//******************End Summary
		
		//******************Payment
		//******************End Payment
		
		createTab.setPane(createLayout);
		
		return createTab;
	}
	
	private Tab getReviseTab(){
		Tab reviseTab = new Tab("แก้ไข", "icons/16/comment_edit.png");
		//TBD
		return reviseTab;
	}
	
	private Tab getApproveTab(){
		Tab approveTab = new Tab("อนุมัติ", "icons/16/star_yellow.png");
		//TBD
		return approveTab;
	}
}
