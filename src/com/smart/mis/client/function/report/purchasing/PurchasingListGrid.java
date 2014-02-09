package com.smart.mis.client.function.report.purchasing;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.report.financial.DisburseMaterialDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class PurchasingListGrid extends ListGrid {

	private Double[][] dataTable;
//	@Override
//	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
//		if (getFieldName(colNum).equals("remain")) { 
//			return "font-weight:bold; color:#287fd6;";
//		} else if (getFieldName(colNum).equals("reserved")) {
//			return "font-weight:bold; color:#d64949;"; 
//		}
//		else {  
//            return super.getCellCSSText(record, rowNum, colNum);  
//        } 
//	}
	
	public PurchasingListGrid(String type) {
		setWidth(850);  
		setHeight(350);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(PurchasingReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField(type);
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("order_id", 80);
        ListGridField field_2_1 = new ListGridField("mid",80);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("mat_name");
        ListGridField field_2_3 = new ListGridField("type");
        ListGridField field_2_4 = new ListGridField("unit", 70);
        
        ListGridField field_3_1 = new ListGridField("request_amount", 150);
        field_3_1.setShowGroupSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("sum_price", 150);
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        
        ListGridField Field_4 = new ListGridField("created_date", 150);
        //ListGridField field_8 = new ListGridField("sup_list");
        
        Field_4.setGroupValueFunction(new GroupValueFunction() {
            public Object getGroupValue(Object value, ListGridRecord record, ListGridField field, String fieldName, ListGrid grid) {
            	return DateTimeFormat.getFormat( "MM/dd/yyyy" ).format( (Date) value );
            }
        });
        
        Field_4.setGroupTitleRenderer(new GroupTitleRenderer() {
			@Override
			public String getGroupTitle(Object groupValue, GroupNode groupNode,
					ListGridField field, String fieldName, ListGrid grid) {
				// TODO Auto-generated method stub
				return (String) groupValue;
			}
        });
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getPriceFormat());
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_1, field_2_4, field_3_2, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields(type);
        //fetchData();
	}
	
	public Double[][] createDataTable(Criterion criteria, String type){
		
		PurchasingReportDS.getInstance().refreshData();
	    
	    if (type.equalsIgnoreCase("type")) {
	    	
	    	if (dataTable != null) return dataTable;
	    	
	    	Record[] type_1_1 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แร่เงิน")}));
		    Record[] type_1_2 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
		    Record[] type_2_1 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
		    Record[] type_2_2 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));
		    
		    return new Double[][] {
		    		{getSumPrice(type_1_1), getSumPrice(type_1_2), getSumPrice(type_2_1), getSumPrice(type_2_2)}
		    };
//	    } else if (type.equalsIgnoreCase("bus_type")) {
//	    	Record[] bus_type_1_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านหน้าร้าน") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    Record[] bus_type_1_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านเว็บไซต์") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    Record[] bus_type_1_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านหน้าร้าน") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    Record[] bus_type_1_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านเว็บไซต์") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    
//		    Record[] bus_type_2_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านหน้าร้าน") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    Record[] bus_type_2_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านเว็บไซต์") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    Record[] bus_type_2_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านหน้าร้าน") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    Record[] bus_type_2_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านเว็บไซต์") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    
//		    return new Double[][] {
//		    		{getNetInclusive(bus_type_1_1), getNetInclusive(bus_type_1_2), getNetInclusive(bus_type_1_3), getNetInclusive(bus_type_1_4)},
//		    		{getNetInclusive(bus_type_2_1), getNetInclusive(bus_type_2_2), getNetInclusive(bus_type_2_3), getNetInclusive(bus_type_2_4)}
//		    };
	    } else return null;
	}
	
	public Double getRequestAmount(Record[] records) {
		Double request_amount = 0.0;
		for (Record record : records) {
			request_amount += record.getAttributeAsDouble("request_amount");
		}
		return request_amount;
	}
	
	public Double getSumPrice(Record[] records) {
		Double sum_price = 0.0;
		for (Record record : records) {
			sum_price += record.getAttributeAsDouble("sum_price");
		}
		return sum_price;
	}
	
	public PurchasingListGrid(Criterion criteria, String type) {
		setWidth(650);
		setHeight(170);
		setAlign(Alignment.CENTER);
		
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setMargin(10);
        //setShowGridSummary(true);
        //setGroupStartOpen(GroupStartOpen.ALL);
        //setGroupByField("group"); 
        setShowGridSummary(true);
        
        ListGridField field_1 = new ListGridField("type", "ประเภทวัตถุดิบ");
        field_1.setShowGridSummary(true);
        field_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });

        ListGridField field_2_4 = new ListGridField("unit", "หน่วย", 70);
        field_2_4.setAlign(Alignment.CENTER);
        
      ListGridField Field_6_1 = new ListGridField("sum_price", "ยอดค่าวัตถุดิบ (บาท)", 150);
      Field_6_1.setShowGridSummary(true);
      Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
      ListGridField Field_6_2 = new ListGridField("Amount (ea)", "จำนวนวัตถุดิบ", 150);
      Field_6_2.setShowGridSummary(true);
      Field_6_2.setSummaryFunction(SummaryFunctionType.SUM);
        
        //Cell Format
      Field_6_1.setAlign(Alignment.RIGHT);
      Field_6_1.setCellFormatter(FieldFormatter.getPriceFormat());
      Field_6_2.setAlign(Alignment.RIGHT);
      Field_6_2.setCellFormatter(FieldFormatter.getNumberFormat());
      
        setFields(field_1, Field_6_1, Field_6_2, field_2_4);
        
        setRecords(createListGridRecord(criteria, type));
        setHoverWidth(200);  
        setHoverHeight(20);
	}
	
	public ListGridRecord[] createListGridRecord(Criterion criteria, String type) {
		
		PurchasingReportDS.getInstance().refreshData();
	    
	    if (type.equalsIgnoreCase("type")) {
	    	Record[] type_1_1 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แร่เงิน")}));
		    Record[] type_1_2 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
		    Record[] type_2_1 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
		    Record[] type_2_2 = PurchasingReportDS.getInstance().applyFilter(PurchasingReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));
		    
		    dataTable = new Double[][] {
		    		{getSumPrice(type_1_1), getSumPrice(type_1_2), getSumPrice(type_2_1), getSumPrice(type_2_2)}
		    };
		    
	    	return new ListGridRecord[]{ 
	    			createRecord("แร่เงิน",getSumPrice(type_1_1),getRequestAmount(type_1_1), "กรัม"),
	    			createRecord("พลอยประดับ",getSumPrice(type_1_2),getRequestAmount(type_1_2), "เม็ด"),
	    			createRecord("แมกกาไซต์",getSumPrice(type_2_1),getRequestAmount(type_2_1), "เม็ด"),
	    			createRecord("อื่นๆ",getSumPrice(type_2_2),getRequestAmount(type_2_2), "ชิ้น")
	    	};
	    	
//	    } else if (type.equalsIgnoreCase("bus_type")) {
//	    	Record[] bus_type_1_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านหน้าร้าน") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    Record[] bus_type_1_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านเว็บไซต์") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    Record[] bus_type_1_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านหน้าร้าน") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    Record[] bus_type_1_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านเว็บไซต์") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
//		    
//		    Record[] bus_type_2_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านหน้าร้าน") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    Record[] bus_type_2_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านเว็บไซต์") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    Record[] bus_type_2_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านหน้าร้าน") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    Record[] bus_type_2_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านเว็บไซต์") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
//		    
//		    return new Double[][] {
//		    		{getNetInclusive(bus_type_1_1), getNetInclusive(bus_type_1_2), getNetInclusive(bus_type_1_3), getNetInclusive(bus_type_1_4)},
//		    		{getNetInclusive(bus_type_2_1), getNetInclusive(bus_type_2_2), getNetInclusive(bus_type_2_3), getNetInclusive(bus_type_2_4)}
//		    };
	    } else return null;
	}
	
	public ListGridRecord createRecord(String type, Double sum_price, Double amount, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("sum_price", sum_price);
        record.setAttribute("Amount (ea)", amount);
        record.setAttribute("unit", unit);
        return record;
	}
}
