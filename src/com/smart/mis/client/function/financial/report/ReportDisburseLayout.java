package com.smart.mis.client.function.financial.report;

//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestDS;
import com.smart.mis.client.function.inventory.material.returns.ReturnDS;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.sale.delivery.DeliveryDS;
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

public class ReportDisburseLayout extends VLayout{

	VLayout gridLayout;
	ListGrid productListGrid, materialListGrid;
	Label reportDate;
	Label text;
	DynamicForm searchForm;
	//DateItem searchItem;
	IButton printButton;
	
	String select_day;
	String select_month;
	String select_year;
	
	//String type;
	
	public ReportDisburseLayout(final User currentUser) {
		
		gridLayout = new VLayout();
		gridLayout.setWidth(950);
		gridLayout.setHeight(450);
		gridLayout.setMargin(5);
		
		//type = "product"; // "material"
		//productListGrid = new ListGrid();
		createProductResult();
		createMaterialResult();
		//createReturnResult();
		//materialListGrid = new ListGrid();
		
		select_day = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[0];
		select_month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[1];
		select_year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[2];
		select_year = Integer.toString((Integer.parseInt(select_year) + 543));
		
		setWidth(950);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(950);
		search.setHeight(45);
		search.setMargin(5);
		search.setMembersMargin(5);
		
		searchForm = new DynamicForm();
		searchForm.setWidth(340);
		searchForm.setNumCols(4);
		searchForm.setTitleOrientation(TitleOrientation.TOP);
//		searchItem = new DateItem("created_date", "วันที่ออกรายงาน");
//        searchItem.setTitleOrientation(TitleOrientation.TOP);
//        searchItem.setColSpan(2);
//        searchItem.setTitleAlign(Alignment.LEFT);
//        searchItem.addKeyPressHandler(new KeyPressHandler() {
//            public void onKeyPress(KeyPressEvent event) {
//                if("enter".equalsIgnoreCase(event.getKeyName())) {
//                    updateDetails();
//                }
//            }
//        });
//        searchItem.setDefaultChooserDate(new Date());
//        searchItem.setDefaultValue(new Date());
//        searchItem.setUseTextField(true);
		
		final SelectItem dayItem = new SelectItem("dayItem", "วันที่");
		dayItem.setValueMap(DateTimeMapping.getDateValueMap(DateTimeMapping.getNumDay(select_month, select_year)));
		dayItem.setDefaultValue(select_day);
		dayItem.setWidth(50);
		final SelectItem monthItem = new SelectItem("monthItem", "เดือน");
		monthItem.setValueMap(DateTimeMapping.getMonthValueMap());
		monthItem.setDefaultValue(DateTimeMapping.getDisplay(select_month));
		monthItem.setWidth(100);
		final SelectItem yearItem = new SelectItem("yearItem", "ปี");
		yearItem.setValueMap(DateTimeMapping.getYearPast(select_year,5), DateTimeMapping.getYearPast(select_year,4) , DateTimeMapping.getYearPast(select_year,3), DateTimeMapping.getYearPast(select_year,2), DateTimeMapping.getYearPast(select_year,1),select_year);
		yearItem.setDefaultValue(select_year);
		yearItem.setWidth(50);
		yearItem.setPickListWidth(60);
        
        final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
        final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
        yearItem.setIcons(findIcon, cancelIcon);
        
        yearItem.addIconClickHandler(new IconClickHandler() {
            public void onIconClick(IconClickEvent event) {
                FormItemIcon icon = event.getIcon();
                if(icon.getSrc().equals(cancelIcon.getSrc())) {
                	searchForm.reset();
            		select_day = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[0];
            		select_month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[1];
            		select_year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[2];
                	resetDetails();
                } else if(icon.getSrc().equals(findIcon.getSrc())) {
                	if (select_day != null) updateDetails();
                	else SC.warn("กรุณาเลือกวันที่");
                }
            }
        });
        
        dayItem.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				select_day = dayItem.getValueAsString();
			}
        });
        
        monthItem.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				int num_1 = DateTimeMapping.getNumDay(select_month, select_year);
				select_month = monthItem.getValueAsString();
				int num_2 = DateTimeMapping.getNumDay(select_month, select_year);
				if (num_1 != num_2) {
					select_day = "1";
					dayItem.setValue(select_day);
					dayItem.setValueMap(DateTimeMapping.getDateValueMap(num_2));
				}
			}
        	
        });
        
        yearItem.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				int num_1 = DateTimeMapping.getNumDay(select_month, select_year);
				select_year = yearItem.getValueAsString();
				int num_2 = DateTimeMapping.getNumDay(select_month, select_year);
				if (num_1 != num_2) {
					select_day = "1";
					dayItem.setValue(select_day);
					dayItem.setValueMap(DateTimeMapping.getDateValueMap(num_2));
				}
			}
        	
        });
        
        final SelectItem typeItem = new SelectItem("typeItem", "ประเภทรายงาน");
        LinkedHashMap<String,String> typeMap = new LinkedHashMap<String,String>();
        typeMap.put("product", "รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
        typeMap.put("material", "รายงานการจ่ายชำระหนี้วัตถุดิบ");
        typeItem.setValueMap(typeMap);
        typeItem.setDefaultValue("product");
        typeItem.setWidth(140);
        
        typeItem.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				if (typeItem.getValueAsString().equalsIgnoreCase("product")){
					text.setContents("รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
					gridLayout.setMembers(productListGrid);
				} else {
					text.setContents("รายงานการจ่ายชำระหนี้วัตถุดิบ");
					gridLayout.setMembers(materialListGrid);
				}
			}        	
        });
        
        searchForm.setFields(dayItem, monthItem, yearItem, typeItem);
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
        text.setContents("รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        reportDate.setContents("ประจำวันที่ " + select_day + " เดือน " + DateTimeMapping.getDisplay(select_month) + " ปี พ.ศ. " + select_year);
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
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
		productListGrid.setDataSource(WageDS.getInstance());
		//productListGrid.setCriteria(new Criterion("issued_status", OperatorId.EQUALS, "1_product_issued"));
		productListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
        });
		productListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.EQUALS, new Date())
  		  });
		
		productListGrid.setCriteria(criteria);
		productListGrid.setShowRowNumbers(true);
		productListGrid.setShowGridSummary(true);
		
		ListGridField wage_id = new ListGridField("wage_id" , 100);
		wage_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		wage_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 100);
		ListGridField job_id = new ListGridField("job_id", 100);
		ListGridField smid = new ListGridField("smid");
		ListGridField sname = new ListGridField("sname");
		
		ListGridField paid_by = new ListGridField("paid_by");

		ListGridField total_wage = new ListGridField("total_wage", 200);
		total_wage.setCellFormatter(FieldFormatter.getPriceFormat());
		total_wage.setAlign(Alignment.RIGHT);
		total_wage.setSummaryFunction(SummaryFunctionType.SUM);
		total_wage.setShowGridSummary(true);
        
		ListGridField paidInclusive = new ListGridField("paidInclusive", 150);
		paidInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		paidInclusive.setAlign(Alignment.RIGHT);
		paidInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		paidInclusive.setShowGridSummary(true);
        
		productListGrid.setFields(wage_id, status, job_id, smid, sname, paid_by, total_wage, paidInclusive);

	}
	
	private void createMaterialResult() {
		
		materialListGrid = new ListGrid();
		
		materialListGrid.setAutoFetchData(true);  
		materialListGrid.setCanMultiSort(true);
		materialListGrid.setDataSource(PurchaseOrderDS.getInstance());
		//materialListGrid.setCriteria(new Criterion("status", OperatorId.EQUALS, "2_issued"));
		materialListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("payment_status", SortDirection.DESCENDING),
        });
		materialListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("payment_status", OperatorId.EQUALS, "2_issued"),
				new Criterion("paid_date", OperatorId.EQUALS, new Date())
  		  });
		
		materialListGrid.setCriteria(criteria );
		materialListGrid.setShowRowNumbers(true);
		materialListGrid.setShowGridSummary(true);
		
		ListGridField order_id = new ListGridField("order_id" , 100);
		order_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		order_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("payment_status" , 120);
		ListGridField sid = new ListGridField("sid", 100);
		ListGridField sup_name = new ListGridField("sup_name");

		ListGridField paid_by = new ListGridField("paid_by");

		ListGridField netInclusive = new ListGridField("netInclusive", 200);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		netInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		netInclusive.setShowGridSummary(true);
        
		ListGridField paidInclusive = new ListGridField("paidInclusive", 150);
		paidInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		paidInclusive.setAlign(Alignment.RIGHT);
		paidInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		paidInclusive.setShowGridSummary(true);
        
		materialListGrid.setFields(order_id, status, sid, sup_name, paid_by, netInclusive, paidInclusive);

	}
	
	private void updateDetails() {
		
		//text.setContents("รายงานการรับสินค้า");
		reportDate.setContents("ประจำวันที่ " + select_day + " เดือน " + DateTimeMapping.getDisplay(select_month) + " ปี พ.ศ. " + select_year);
        
		Date date = DateTimeFormat.getFormat("yyyy-M-dd").parse(DateTimeMapping.getRealYear(select_year) + "-" + select_month + "-" + select_day);
		
		AdvancedCriteria criteria_p = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.EQUALS, date)
  		  });
		
		AdvancedCriteria criteria_m = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("payment_status", OperatorId.EQUALS, "2_issued"),
				new Criterion("paid_date", OperatorId.EQUALS, date)
  		  });
		
		productListGrid.fetchData(criteria_p);
		productListGrid.deselectAllRecords();
		materialListGrid.fetchData(criteria_m);
		materialListGrid.deselectAllRecords();
	}
	
	private void resetDetails() {
//		for(ListGridRecord record : productListGrid.getRecords()) {
//			productListGrid.removeData(record);
//		}
//		printButton.hide();
		reportDate.setContents("ประจำวันที่ " + select_day + " เดือน " + DateTimeMapping.getDisplay(select_month) + " ปี พ.ศ. " + select_year);
        
		AdvancedCriteria criteria_p = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.EQUALS, new Date())
  		  });
		
		AdvancedCriteria criteria_m = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("payment_status", OperatorId.EQUALS, "2_issued"),
				new Criterion("paid_date", OperatorId.EQUALS, new Date())
  		  });
		
		productListGrid.fetchData(criteria_p);
		productListGrid.deselectAllRecords();
		materialListGrid.fetchData(criteria_m);
		materialListGrid.deselectAllRecords();
	}
	
//	private int getDate(Date current){
////        Calendar cal = Calendar.getInstance();
////        cal.setTime(current);
////        return cal.get(Calendar.DAY_OF_MONTH);
//		return current.getDate();
//	}
//	
//	private String getMonth(Date current){
////        Calendar cal = Calendar.getInstance();
////        cal.setTime(current);
////        int month = cal.get(Calendar.MONTH);
//        int month = current.getMonth();
//        switch (month) {
//        	case 0: return "มกราคม";
//        	case 1: return "กุมภาพันธ์";
//        	case 2: return "มีนาคม";
//        	case 3: return "เมษายน";
//        	case 4: return "พฤษภาคม";
//        	case 5: return "มิถุนายน";
//        	case 6: return "กรกฎาคม";
//        	case 7: return "สิงหาคม";
//        	case 8: return "กันยายน";
//        	case 9: return "ตุลาคม";
//        	case 10: return "พฤศจิกายน";
//        	case 11: return "ธันวาคม";
//        	default : return "no defined";
//        }
//	}
//	
//	private int getYear(Date current){
////        Calendar cal = Calendar.getInstance();
////        cal.setTime(current);
////        return cal.get(Calendar.YEAR) + 543;
//		return current.getYear() + 543;
//	}
	
}
