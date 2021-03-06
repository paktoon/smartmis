package com.smart.mis.client.function.inventory.report;

//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smart.mis.client.function.inventory.material.returns.ReturnDS;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.sale.quotation.QuotationDS;
import com.smart.mis.shared.DateTimeMapping;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportInventoryLayout extends VLayout{

	VLayout gridLayout;
	ListGrid productListGrid, materialListGrid;
	Label reportDate;
	Label text;
	DynamicForm searchForm;
	//DateItem searchItem;
	IButton printButton;
	
//	String select_day;
//	String select_month;
//	String select_year;
	
	//String type;
	
	public ReportInventoryLayout(final User currentUser) {
		
		gridLayout = new VLayout();
		gridLayout.setWidth(950);
		gridLayout.setHeight(450);
		gridLayout.setMargin(10);
		
		createProductResult();
		createMaterialResult();
		
		setWidth(950);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(950);
		search.setHeight(45);
		search.setMargin(10);
		search.setMembersMargin(5);
		
		searchForm = new DynamicForm();
		searchForm.setWidth(340);
		searchForm.setNumCols(4);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
        
        final SelectItem typeItem = new SelectItem("typeItem", "ประเภทรายงาน");
        LinkedHashMap<String,String> typeMap = new LinkedHashMap<String,String>();
        typeMap.put("product", "รายงานสินค้าคงเหลือ");
        typeMap.put("material", "รายงานวัตถุดิบคงเหลือ");
        typeItem.setValueMap(typeMap);
        typeItem.setDefaultValue("product");
        typeItem.setWidth(140);
        
        typeItem.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				if (typeItem.getValueAsString().equalsIgnoreCase("product")){
					text.setContents("รายงานสินค้าคงเหลือ");
					gridLayout.setMembers(productListGrid);
				}  else {
					text.setContents("รายงานวัตถุดิบคงเหลือ");
					gridLayout.setMembers(materialListGrid);
				}
			}        	
        });
        
        //searchForm.setFields(dayItem, monthItem, yearItem, typeItem);
        searchForm.setFields(typeItem);
        search.addMember(searchForm);
        
        HLayout empty = new HLayout();
        empty.setWidth("*");
        search.addMember(empty);
        
        VLayout buttonLayout = new VLayout();
        buttonLayout.setAlign(VerticalAlignment.BOTTOM);
        buttonLayout.setHeight(38);
        buttonLayout.setWidth(120);
        printButton = new IButton("พิมพ์รายงาน");
        printButton.setIcon("icons/16/print.png");
        printButton.setWidth(120);
        buttonLayout.addMember(printButton);
        
        search.addMember(buttonLayout);
        
        addMember(search);
        
        final VLayout report = new VLayout();
        report.setWidth(950);
        report.setHeight(10);
        text = new Label();
        text.setContents("รายงานสินค้าคงเหลือ");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        //reportDate.setContents("ประจำวันที่ " + select_day + " เดือน " + DateTimeMapping.getDisplay(select_month) + " ปี พ.ศ. " + select_year);
        reportDate.setContents("ประจำวันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(new Date()));
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
        
		Label createDate = new Label();
		Date today = new Date();
		DateTimeFormat pattern = DateTimeFormat.getFormat("MM/dd/yyyy");
		createDate.setContents("วันที่ออกรายงาน : " + pattern.format(today));
		createDate.setWidth("20%");
		createDate.setHeight(15);
		createDate.setAlign(Alignment.LEFT);
		createDate.setMargin(10);
        report.addMember(createDate);
        report.addMember(text);
        report.addMember(reportDate);
        //report.addMember(createProductResult());
        //report.addMember(createMaterialResult());
        gridLayout.setMembers(productListGrid);
        report.addMember(gridLayout);
        
        addMember(report);
        
        printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Canvas.showPrintPreview(report);
          }
        });
        
        //addMember(createResult());
	}
	
	private void createProductResult() {
		
		productListGrid = new ListGrid();
		
		productListGrid.setAutoFetchData(true);  
		productListGrid.setCanMultiSort(true);
		productListGrid.setDataSource(ProductDS.getInstance());
		productListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("type", SortDirection.ASCENDING),
        });
		productListGrid.setUseAllDataSourceFields(false);
		productListGrid.setShowRowNumbers(true);
		productListGrid.setShowGridSummary(true);
		
		ListGridField pid = new ListGridField("pid" , 100);
		pid.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		pid.setShowGridSummary(true);
        
		ListGridField name = new ListGridField("name", "ชื่อสินค้า");
		ListGridField type = new ListGridField("type", 120);

		ListGridField inStock = new ListGridField("inStock", 100);
		inStock.setCellFormatter(FieldFormatter.getIntegerFormat());
		inStock.setAlign(Alignment.RIGHT);
		inStock.setSummaryFunction(SummaryFunctionType.SUM);
		inStock.setShowGridSummary(true);
        
		ListGridField reserved = new ListGridField("reserved", 100);
		reserved.setCellFormatter(FieldFormatter.getIntegerFormat());
		reserved.setAlign(Alignment.RIGHT);
		reserved.setSummaryFunction(SummaryFunctionType.SUM);
		reserved.setShowGridSummary(true);
        
		ListGridField remain = new ListGridField("remain", 100);
		remain.setCellFormatter(FieldFormatter.getIntegerFormat());
		remain.setAlign(Alignment.RIGHT);
		remain.setSummaryFunction(SummaryFunctionType.SUM);
		remain.setShowGridSummary(true);
		
		ListGridField unit = new ListGridField("unit",50);
		
		productListGrid.setFields(pid, name, type, inStock, reserved, remain, unit);

	}
	
	private void createMaterialResult() {
		
		materialListGrid = new ListGrid();
		
		materialListGrid.setAutoFetchData(true);  
		materialListGrid.setCanMultiSort(true);
		materialListGrid.setDataSource(MaterialDS.getInstance());
		materialListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("type", SortDirection.ASCENDING),
        });
		materialListGrid.setUseAllDataSourceFields(false);
		materialListGrid.setShowRowNumbers(true);
		//materialListGrid.setShowGridSummary(true);
		materialListGrid.setShowGroupSummary(true);
		materialListGrid.setGroupByField("type");
		materialListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField mid = new ListGridField("mid" , 100);
		mid.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		mid.setShowGroupSummary(true);
        
		ListGridField mat_name = new ListGridField("mat_name");
		ListGridField desc = new ListGridField("desc", 180);
		ListGridField type = new ListGridField("type", 80);
		
		ListGridField inStock = new ListGridField("inStock", 130);
		inStock.setCellFormatter(FieldFormatter.getNumberFormat());
		inStock.setAlign(Alignment.RIGHT);
		inStock.setSummaryFunction(SummaryFunctionType.SUM);
		inStock.setShowGroupSummary(true);
        
		ListGridField reserved = new ListGridField("reserved", 120);
		reserved.setCellFormatter(FieldFormatter.getNumberFormat());
		reserved.setAlign(Alignment.RIGHT);
		reserved.setSummaryFunction(SummaryFunctionType.SUM);
		reserved.setShowGroupSummary(true);
        
		ListGridField remain = new ListGridField("remain", 120);
		remain.setCellFormatter(FieldFormatter.getNumberFormat());
		remain.setAlign(Alignment.RIGHT);
		remain.setSummaryFunction(SummaryFunctionType.SUM);
		remain.setShowGroupSummary(true);
		
		ListGridField unit = new ListGridField("unit",50);
		
		materialListGrid.setFields(mid, mat_name, desc, type, inStock, reserved, remain, unit);
		materialListGrid.hideField("type");
	}
	
}
