package com.smart.mis.client.function.sale.quotation;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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
		
		//Header
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
		//End Header
		
		//Customer
		Label createDate = new Label();
		Date today = new Date();
		DateTimeFormat pattern = DateTimeFormat.getFormat("dd/MM/yyyy");
		createDate.setContents("วันที่สร้าง : " + pattern.format(today));
		createDate.setWidth("15%");
		createDate.setAlign(Alignment.RIGHT);
		headerLayout.addMember(createDate);
		createLayout.addMember(headerLayout);
		
		DynamicForm userForm = new DynamicForm();
		userForm.setWidth100(); 
		userForm.setHeight(30);
		userForm.setMargin(5); 
		userForm.setNumCols(6); 
		userForm.setCellPadding(2);
		userForm.setAutoFocus(true);
		userForm.setSelectOnFocus(true);
		//userForm.setDataSource(CustomerDS.getInstance());
		//userForm.setUseAllDataSourceFields(false);
		userForm.setIsGroup(true);
		userForm.setGroupTitle("ข้อมูลลูกค้า");
        
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
		
		userForm.setFields(cid, cus_name, type);
		userForm.setColWidths(100, 80, 80, 240, 100, 100);
		createLayout.addMember(userForm);
		
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
