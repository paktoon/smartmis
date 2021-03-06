package com.smart.mis.client.function.report.inventory;

import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class MaterialReceivedReportListGrid extends ListGrid {

	private Double[][] silverDataTable, matDataTable;
	
	public MaterialReceivedReportListGrid() {
		setWidth(950);  
		setHeight(300);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(MaterialReceivedReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type"); 
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("order_id", 120);
        ListGridField field_2_1 = new ListGridField("mid",150);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("mat_name");
        ListGridField field_2_3 = new ListGridField("type");
        
        ListGridField field_3_1 = new ListGridField("received_weight",150);
        field_3_1.setShowGroupSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("received_amount", 150);
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("received_date", 120);
        //ListGridField field_8 = new ListGridField("sup_list");
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        //setFields(field_1, field_2, field_3, field_4, field_5, field_6, field_7, field_8);
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_1, field_3_2, field_3_3, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
	public Double[][] createSilverDataTable(Criterion criteria){
		
		if (silverDataTable != null) return silverDataTable;
		
		MaterialReceivedReportDS.getInstance().refreshData();
	    Record[] silver100 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 100%")}));
	    Record[] silver925 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 92.5%")}));

	    return new Double[][] {
	    		{getReceviedAmount(silver100), getReceviedAmount(silver925)}
	    };
	}
	
	public Double[][] createMaterialDataTable(Criterion criteria){
		
		if (matDataTable != null) return matDataTable;
		
		MaterialReceivedReportDS.getInstance().refreshData();
		Record[] mat_1 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
	    Record[] mat_2 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
	    Record[] mat_3 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));

	    return new Double[][] {
	    		{getReceviedAmount(mat_1), getReceviedAmount(mat_2), getReceviedAmount(mat_3)}
	    };
	}
	
	public Double getReceviedAmount(Record[] records) {
		Double received_amount = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("received_amount") != null) {
				received_amount += record.getAttributeAsDouble("received_amount");
			}
		}
		return received_amount;
	}
	
	//new
	
	public Double getReceviedWeight(Record[] records) {
		Double recv_weight = 0.0;
		for (Record record : records) {
			if (record.getAttributeAsDouble("received_weight") != null) {
				recv_weight += record.getAttributeAsDouble("received_weight");
			}
		}
		return recv_weight;
	}
	
	public MaterialReceivedReportListGrid(Criterion criteria) {
		setWidth(650);
		setHeight(270);
        setAlign(Alignment.CENTER);
		
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setMargin(10);
        //setShowGridSummary(true);
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("group"); 
        setShowGroupSummary(true);
        
        ListGridField field_0 = new ListGridField("group");
        ListGridField field_1 = new ListGridField("type", "ประเภทวัตถุดิบ");
        field_1.setShowGroupSummary(true);
        field_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        
        ListGridField field_3_1 = new ListGridField("received_weight", "น้ำหนักวัตถุดิบที่รับเข้า (กรัม)",170);
        field_3_1.setShowGroupSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("received_amount", "จำนวนวัตถุดิบที่รับเข้า" , 170);
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", "หน่วย", 50);
        field_3_3.setAlign(Alignment.CENTER);
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_0, field_1, field_3_1, field_3_2, field_3_3);
        
        setRecords(createListGridRecord(criteria));
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("group");
	}
	
	public ListGridRecord[] createListGridRecord(Criterion criteria) {
		MaterialReceivedReportDS.getInstance().refreshData();
	    Record[] silver100 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 100%")}));
	    Record[] silver925 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 92.5%")}));

		Record[] mat_1 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")}));
	    Record[] mat_2 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")}));
	    Record[] mat_3 = MaterialReceivedReportDS.getInstance().applyFilter(MaterialReceivedReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "อื่นๆ")}));

	    silverDataTable = new Double[][] {
	    		{getReceviedAmount(silver100), getReceviedAmount(silver925)}
	    };
	    
	    matDataTable = new Double[][] {
	    		{getReceviedAmount(mat_1), getReceviedAmount(mat_2), getReceviedAmount(mat_3)}
	    };
	    
    	return new ListGridRecord[]{ 
    			createRecord("แร่เงิน 100%", "แร่เงิน",getReceviedAmount(silver100),getReceviedWeight(silver100), "กรัม"),
    			createRecord("แร่เงิน 92.5%", "แร่เงิน",getReceviedAmount(silver925),getReceviedWeight(silver925), "กรัม"),
    			createRecord("พลอยประดับ", "วัตถุดิบ",getReceviedAmount(mat_1),getReceviedWeight(mat_1), "เม็ด"),
    			createRecord("แมกกาไซต์", "วัตถุดิบ",getReceviedAmount(mat_2),getReceviedWeight(mat_2), "เม็ด"),
    			createRecord("อื่นๆ", "วัตถุดิบ",getReceviedAmount(mat_3),getReceviedWeight(mat_3), "ชิ้น")
    	};
	}
	
	public ListGridRecord createRecord(String type, String group, Double received_amount, Double received_weight, String unit){
		ListGridRecord record = new ListGridRecord();
        record.setAttribute("type", type);
        record.setAttribute("group", group);
        record.setAttribute("received_amount", received_amount);
        record.setAttribute("received_weight", received_weight);
        record.setAttribute("unit", unit);
        return record;
	}
}
