package com.smart.mis.client.function.sale.report;

//import java.util.Calendar;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smart.mis.client.function.sale.quotation.QuotationDS;
import com.smart.mis.shared.DateTimeMapping;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
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

public class ReportQuotationLayout_1 extends VLayout{

	ListGrid reportListGrid;
	Label reportDate;
	DynamicForm fromSearchForm, toSearchForm;
	//DateItem searchItem;
	IButton printButton;
	
	String from_day;
	String from_month;
	String from_year;
	
	String to_day;
	String to_month;
	String to_year;
	
	public ReportQuotationLayout_1(final User currentUser) {
		
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1w"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
        Date start = dateRange.getStartDate();
        Date end = dateRange.getEndDate();
        
		from_day = DateTimeFormat.getFormat( "d-M-yyyy" ).format( start ).split( "-")[0];
		from_month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( start ).split( "-")[1];
		from_year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( start ).split( "-")[2];
		from_year = Integer.toString((Integer.parseInt(from_year) + 543));
		
		to_day = DateTimeFormat.getFormat( "d-M-yyyy" ).format( end ).split( "-")[0];
		to_month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( end ).split( "-")[1];
		to_year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( end ).split( "-")[2];
		to_year = Integer.toString((Integer.parseInt(to_year) + 543));
		
		setWidth(950);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(950);
		search.setHeight(45);
		search.setMargin(5);
		search.setMembersMargin(5);
		
		toSearchForm = new DynamicForm();
		toSearchForm.setWidth(200);
		toSearchForm.setNumCols(3);
		toSearchForm.setTitleOrientation(TitleOrientation.TOP);
		toSearchForm.setIsGroup(true);
		toSearchForm.setGroupTitle("ถึง");
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
		
		final SelectItem toDayItem = new SelectItem("toDayItem", "วันที่");
		toDayItem.setValueMap(DateTimeMapping.getDateValueMap(DateTimeMapping.getNumDay(to_month, to_year)));
		toDayItem.setDefaultValue(to_day);
		toDayItem.setWidth(50);
		final SelectItem toMonthItem = new SelectItem("toMonthItem", "เดือน");
		toMonthItem.setValueMap(DateTimeMapping.getMonthValueMap());
		toMonthItem.setDefaultValue(DateTimeMapping.getDisplay(to_month));
		toMonthItem.setWidth(100);
		final SelectItem toYearItem = new SelectItem("toYearItem", "ปี");
		toYearItem.setValueMap(DateTimeMapping.getYearPast(to_year,5), DateTimeMapping.getYearPast(to_year,4) , DateTimeMapping.getYearPast(to_year,3), DateTimeMapping.getYearPast(to_year,2), DateTimeMapping.getYearPast(to_year,1),to_year);
		toYearItem.setDefaultValue(to_year);
		toYearItem.setWidth(50);
		toYearItem.setPickListWidth(60);
        
//        final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
//        final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
//        toYearItem.setIcons(findIcon, cancelIcon);
//        
//        toYearItem.addIconClickHandler(new IconClickHandler() {
//            public void onIconClick(IconClickEvent event) {
//                FormItemIcon icon = event.getIcon();
//                if(icon.getSrc().equals(cancelIcon.getSrc())) {
//                	toSearchForm.reset();
//            		select_day = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[0];
//            		select_month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[1];
//            		select_year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[2];
//                	resetDetails();
//                } else if(icon.getSrc().equals(findIcon.getSrc())) {
//                	if (select_day != null) updateDetails();
//                	else SC.warn("กรุณาเลือกวันที่");
//                }
//            }
//        });
//        
//        toDayItem.addChangedHandler(new ChangedHandler(){
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				select_day = toDayItem.getValueAsString();
//			}
//        });
//        
//        toMonthItem.addChangedHandler(new ChangedHandler(){
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				int num_1 = DateTimeMapping.getNumDay(select_month, select_year);
//				select_month = toMonthItem.getValueAsString();
//				int num_2 = DateTimeMapping.getNumDay(select_month, select_year);
//				if (num_1 != num_2) {
//					select_day = "1";
//					toDayItem.setValue(select_day);
//					toDayItem.setValueMap(DateTimeMapping.getDateValueMap(num_2));
//				}
//			}
//        	
//        });
//        
//        toYearItem.addChangedHandler(new ChangedHandler(){
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				int num_1 = DateTimeMapping.getNumDay(select_month, select_year);
//				select_year = toYearItem.getValueAsString();
//				int num_2 = DateTimeMapping.getNumDay(select_month, select_year);
//				if (num_1 != num_2) {
//					select_day = "1";
//					toDayItem.setValue(select_day);
//					toDayItem.setValueMap(DateTimeMapping.getDateValueMap(num_2));
//				}
//			}
//        	
//        });
        
        toSearchForm.setFields(toDayItem, toMonthItem, toYearItem);
        search.addMember(toSearchForm);
        
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
        Label text = new Label();
        text.setContents("รายงานการเสนอราคา");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        reportDate.setContents("ตั้งแต่วันที่ " + from_day + DateTimeMapping.getDisplay(from_month) + " พ.ศ. " + from_year + " ถึง " + to_day + DateTimeMapping.getDisplay(to_month) + " พ.ศ. " + to_year );
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
        report.addMember(text);
        report.addMember(reportDate);
        report.addMember(createResult());
        addMember(report);
        
        printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Canvas.showPrintPreview(report);
          }
        });
        
        //addMember(createResult());
	}
	
	private VLayout createResult() {
		
		reportListGrid = new ListGrid();
		 
		reportListGrid.setAutoFetchData(true);  
		reportListGrid.setCanMultiSort(true);
		//reportListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"));
		reportListGrid.setDataSource(QuotationDS.getInstance());
		reportListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
                //new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		reportListGrid.setUseAllDataSourceFields(false);
		
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1w"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		reportListGrid.setCriteria(new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, dateRange.getStartDate(), dateRange.getEndDate()));
		
		reportListGrid.setShowRowNumbers(true);
		reportListGrid.setShowGridSummary(true);
		//reportListGrid.setGroupByField("status");
		//reportListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField quote_id = new ListGridField("quote_id" , 100);
		quote_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		quote_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 80);
		//ListGridField quote_id = new ListGridField("quote_id" , 90);
		ListGridField cid = new ListGridField("cid", 100);
		ListGridField cus_name = new ListGridField("cus_name");
		//ListGridField payment_model = new ListGridField("payment_model", 100);
		//ListGridField credit = new ListGridField("credit", 20);
		//ListGridField from = new ListGridField("from", 100);
		//ListGridField to = new ListGridField("to", 100);
		//ListGridField delivery = new ListGridField("delivery", 100);

		ListGridField total_weight = new ListGridField("total_weight", 120);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		total_weight.setSummaryFunction(SummaryFunctionType.SUM);
		total_weight.setShowGridSummary(true);
        
		ListGridField total_amount = new ListGridField("total_amount", 120);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		total_amount.setSummaryFunction(SummaryFunctionType.SUM);
		total_amount.setShowGridSummary(true);
        
		ListGridField netInclusive = new ListGridField("netInclusive", 100);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		netInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		netInclusive.setShowGridSummary(true);
		
		reportListGrid.setFields(quote_id, status, cid, cus_name, total_weight, total_amount, netInclusive);
		
//		reportListGrid.addRecordClickHandler(new RecordClickHandler() {  
//			@Override
//			public void onRecordClick(RecordClickEvent event) {
//				printButton.show();
//			}  
//        });
		
		//reportListGrid.hideField("status");
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth(950);
		gridLayout.setHeight(450);
		gridLayout.setMargin(5);
		
		gridLayout.addMember(reportListGrid);
		
		return gridLayout;
	}
	
	private void updateDetails() {
//		for(ListGridRecord record : reportListGrid.getRecords()) {
//			reportListGrid.removeData(record);
//		}
//		printButton.hide();
		//reportDate.setContents("ประจำวันที่ " + select_day + " เดือน " + DateTimeMapping.getDisplay(select_month) + " ปี พ.ศ. " + select_year);
		reportDate.setContents("ตั้งแต่วันที่ " + from_day + DateTimeMapping.getDisplay(from_month) + " พ.ศ. " + from_year + " ถึง " + to_day + DateTimeMapping.getDisplay(to_month) + " พ.ศ. " + to_year );
		
		Date fromDate = DateTimeFormat.getFormat("yyyy-M-dd").parse(DateTimeMapping.getRealYear(from_year) + "-" + from_month + "-" + from_day);
		Date toDate = DateTimeFormat.getFormat("yyyy-M-dd").parse(DateTimeMapping.getRealYear(to_year) + "-" + to_month + "-" + to_day);
		
		reportListGrid.setCriteria(new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, fromDate, toDate));
		
		reportListGrid.deselectAllRecords();
	}
	
	private void resetDetails() {
//		for(ListGridRecord record : reportListGrid.getRecords()) {
//			reportListGrid.removeData(record);
//		}
//		printButton.hide();
		//reportDate.setContents("ประจำวันที่ " + select_day + " เดือน " + DateTimeMapping.getDisplay(select_month) + " ปี พ.ศ. " + select_year);
		reportDate.setContents("ตั้งแต่วันที่ " + from_day + DateTimeMapping.getDisplay(from_month) + " พ.ศ. " + from_year + " ถึง " + to_day + DateTimeMapping.getDisplay(to_month) + " พ.ศ. " + to_year );
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1w"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		
		reportListGrid.fetchData(new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, dateRange.getStartDate(), dateRange.getEndDate()));
		reportListGrid.deselectAllRecords();
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
