package com.smart.mis.client.function.sale.report.quotation;

//import java.util.Calendar;
import java.util.Date;

import com.google.gwt.user.client.ui.Label;
import com.smart.mis.client.function.sale.quotation.QuotationDS;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
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
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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

public class ReportQuotationLayout extends VLayout{

	ListGrid reportListGrid;
	Label reportDate;
	DynamicForm searchForm;
	DateItem searchItem;
	IButton viewButton;
	
	public ReportQuotationLayout(final User currentUser) {
		setWidth(950);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(650);
		search.setHeight(45);
		search.setMargin(5);
		search.setMembersMargin(5);
		
		searchForm = new DynamicForm();
		searchItem = new DateItem("created_date", "วันที่ออกรายงาน");
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
        searchItem.setDefaultChooserDate(new Date());
        searchItem.setDefaultValue(new Date());
        searchItem.setUseTextField(true);
        
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
        
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.BOTTOM);
//        buttonLayout.setHeight(38);
//        viewButton = new IButton("ตรวจสอบคำสั่งซื้อ");
//        viewButton.setIcon("icons/16/folder_out.png");
//        viewButton.setWidth(150);
//        viewButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SaleViewWindow sale =  new SaleViewWindow();
//            	sale.show(reportListGrid.getSelectedRecord(), false, currentUser, 2);
//          }
//        });
//        
//        viewButton.hide();
//        buttonLayout.addMember(viewButton);
//        
//        search.addMember(buttonLayout);
        addMember(search);
        
        VLayout header = new VLayout();
        header.setAlign(Alignment.CENTER);
        header.setWidth(950);
        header.setHeight(10);
        Label text = new Label();
        text.setText("รายงานการเสนอราคา");
        text.setHeight("10");
        reportDate = new Label();
        reportDate.setText("ประจำวันที่ " + getDate(new Date())+ " เดือน " + getMonth(new Date())+ " ปี พ.ศ. " + getYear(new Date()));
        reportDate.setHeight("10");
        header.addMember(text);
        header.addMember(reportDate);
        
        addMember(header);
        
        addMember(createResult());
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
		reportListGrid.setCriteria(new Criterion("created_date", OperatorId.EQUALS, new Date()) );
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
//				viewButton.show();
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
//		viewButton.hide();
		reportDate.setText("ประจำวันที่ " + getDate(searchItem.getValueAsDate())+ " เดือน " + getMonth(searchItem.getValueAsDate())+ " ปี พ.ศ. " + getYear(searchItem.getValueAsDate()));
        
		reportListGrid.fetchData(searchForm.getValuesAsCriteria());
		reportListGrid.deselectAllRecords();
	}
	
	private void resetDetails() {
//		for(ListGridRecord record : reportListGrid.getRecords()) {
//			reportListGrid.removeData(record);
//		}
//		viewButton.hide();
		reportDate.setText("ประจำวันที่ " + getDate(new Date())+ " เดือน " + getMonth(new Date())+ " ปี พ.ศ. " + getYear(new Date()));
        
		reportListGrid.fetchData(new Criterion("created_date", OperatorId.EQUALS, new Date()) );
		reportListGrid.deselectAllRecords();
	}
	
	private int getDate(Date current){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(current);
//        return cal.get(Calendar.DAY_OF_MONTH);
		return current.getDate();
	}
	
	private String getMonth(Date current){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(current);
//        int month = cal.get(Calendar.MONTH);
        int month = current.getMonth();
        switch (month) {
        	case 0: return "มกราคม";
        	case 1: return "กุมภาพันธ์";
        	case 2: return "มีนาคม";
        	case 3: return "เมษายน";
        	case 4: return "พฤษภาคม";
        	case 5: return "มิถุนายน";
        	case 6: return "กรกฎาคม";
        	case 7: return "สิงหาคม";
        	case 8: return "กันยายน";
        	case 9: return "ตุลาคม";
        	case 10: return "พฤศจิกายน";
        	case 11: return "ธันวาคม";
        	default : return "no defined";
        }
	}
	
	private int getYear(Date current){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(current);
//        return cal.get(Calendar.YEAR) + 543;
		return current.getYear();
	}
	
}
