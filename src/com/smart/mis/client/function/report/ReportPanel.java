package com.smart.mis.client.function.report;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.MainPage;
import com.smart.mis.client.chart.inventory.MaterialColumnChart;
import com.smart.mis.client.chart.inventory.MaterialRequestColumnChart;
import com.smart.mis.client.chart.inventory.ProductColumnChart;
import com.smart.mis.client.chart.inventory.ProductRequestColumnChart;
import com.smart.mis.client.chart.inventory.SilverColumnChart;
import com.smart.mis.client.chart.inventory.SilverRequestColumnChart;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.report.inventory.MaterialReportListGrid;
import com.smart.mis.client.function.report.inventory.MaterialRequestReportListGrid;
import com.smart.mis.client.function.report.inventory.ProductReportListGrid;
import com.smart.mis.client.function.report.inventory.ProductRequestReportListGrid;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportPanel extends FunctionPanel{
	
	private final FunctionWindow saleCubeReportWindow;
	//private final FunctionWindow summaryReportWindow;
	private final FunctionWindow productionReportWindow;
	
	private final FunctionWindow inventoryProductReportWindow;
	private final FunctionWindow inventoryMaterialReportWindow;
	private final FunctionWindow inventoryProductRequestReportWindow;
	private final FunctionWindow inventoryMaterialRequestReportWindow;
	private final FunctionWindow inventoryProductReceivedReportWindow;
	private final FunctionWindow inventoryMaterialReceivedReportWindow;
	
	private final FunctionWindow purchasingReportWindow;
	private final FunctionWindow incomeExpenseWindow;
	
	private ProductReportListGrid InventoryProductGrid;
	private MaterialReportListGrid InventoryMaterialGrid;
	private ProductRequestReportListGrid InventoryProductRequestGrid;
	private MaterialRequestReportListGrid InventoryMaterialRequestGrid;
	
	public ReportPanel(MainPage main){
		super(main, "ระบบสารสนเทศเพื่อผู้บริหาร", 6);
		
		saleCubeReportWindow = createFuncWindow();
		
		//summaryReportWindow = createFuncWindow();
		productionReportWindow = createFuncWindow();
		
		inventoryProductReportWindow = createFuncWindow();
		inventoryMaterialReportWindow = createFuncWindow();
		inventoryProductRequestReportWindow = createFuncWindow();
		inventoryMaterialRequestReportWindow = createFuncWindow();
		inventoryProductReceivedReportWindow = createFuncWindow();
		inventoryMaterialReceivedReportWindow = createFuncWindow();
		
		purchasingReportWindow = createFuncWindow();
		incomeExpenseWindow = createFuncWindow();
	}

	@Override
	public void init() {
		this._main.getReportPanel().setAlign(Alignment.CENTER);
		this._main.getReportPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("61")) {
			loadWindow(this._main.getReportPanel(), this.saleCubeReportWindow , name, icon);
		} else if (nodeId.equals("62")) {
			//loadWindow(this._main.getReportPanel(), this.summaryReportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("621")) {
			loadWindow(this._main.getReportPanel(), this.productionReportWindow , name, icon);
		} else if (nodeId.equals("6221")) {
			LoadinventoryProductReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryProductReportWindow , name, icon);
		} else if (nodeId.equals("6222")) {
			LoadinventoryMaterialReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryMaterialReportWindow , name, icon);
		} else if (nodeId.equals("6223")) {
			LoadinventoryProductRequestReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryProductRequestReportWindow , name, icon);
		} else if (nodeId.equals("6224")) {
			LoadinventoryMaterialRequestReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryMaterialRequestReportWindow , name, icon);
		} else if (nodeId.equals("623")) {
			loadWindow(this._main.getReportPanel(), this.purchasingReportWindow , name, icon);
		} else if (nodeId.equals("624")) {
			loadWindow(this._main.getReportPanel(), this.incomeExpenseWindow , name, icon);
		} else init();
	}
	
	private void LoadinventoryProductReportWindow() {
		for (Canvas removed : this.inventoryProductReportWindow.getItems()) {
			this.inventoryProductReportWindow.removeItem(removed);
		}
		ProductColumnChart chart = new ProductColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดสินค้าคงคลัง");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this);
		
		VLayout gridLayout = new VLayout();
		gridLayout.setMargin(5);
		InventoryProductGrid = new ProductReportListGrid();
		gridLayout.addMember(InventoryProductGrid);
		
		reportLayout.addMembers(title, chartLayout, gridLayout);
		this.inventoryProductReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryMaterialReportWindow() {
		for (Canvas removed : this.inventoryMaterialReportWindow.getItems()) {
			this.inventoryMaterialReportWindow.removeItem(removed);
		}
		SilverColumnChart chart_1 = new SilverColumnChart();
		MaterialColumnChart chart_2 = new MaterialColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดวัตถุดิบคงคลัง");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		//chartLayout.setMargin(5);
		
		VLayout chartLayout_1 = new VLayout(); 
		chartLayout_1.setWidth(450);
		chart_1.loadChart(chartLayout_1, this);
		VLayout chartLayout_2 = new VLayout(); 
		chartLayout_2.setWidth(600);
		chart_2.loadChart(chartLayout_2, this);
		
		chartLayout.addMembers(chartLayout_1, chartLayout_2);
		VLayout gridLayout = new VLayout();
		gridLayout.setMargin(5);
		InventoryMaterialGrid = new MaterialReportListGrid();
		gridLayout.addMember(InventoryMaterialGrid);
		
		reportLayout.addMembers(title, chartLayout, gridLayout);
		
		//reportLayout.addMembers(title, chartLayout);
		this.inventoryMaterialReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryProductRequestReportWindow() {
		for (Canvas removed : this.inventoryProductRequestReportWindow.getItems()) {
			this.inventoryProductRequestReportWindow.removeItem(removed);
		}
		
		HLayout filterLayout = new HLayout();
		filterLayout.setMargin(10);
		filterLayout.setMembersMargin(5);
		filterLayout.setHeight(30);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(4);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่เบิกจ่ายสินค้า");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("issued_from" , "ตั้งแต่");
		final DateItem to = new DateItem("issued_to" , "ถึง");
		from.setDefaultChooserDate(dateRange.getStartDate());
		from.setDefaultValue(dateRange.getStartDate());
		from.setUseTextField(true);
        to.setDefaultChooserDate(dateRange.getEndDate());
        to.setDefaultValue(dateRange.getEndDate());
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        VLayout buttonLayout = new VLayout();
        buttonLayout.setAlign(VerticalAlignment.CENTER);
        buttonLayout.setHeight(50);
		IButton searchButton = new IButton("ออกรายงานสรุปการเบิกจ่ายสินค้า");
		searchButton.setIcon("icons/16/reports-icon.png");
		searchButton.setWidth(200);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	InventoryProductRequestGrid.fetchData(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
            	InventoryProductRequestGrid.deselectAllRecords();
          }
        });
		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm, buttonLayout);
		
		ProductRequestColumnChart chart = new ProductRequestColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการเบิกจ่ายสินค้า");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this);
		
		VLayout gridLayout = new VLayout();
		gridLayout.setMargin(5);
		InventoryProductRequestGrid = new ProductRequestReportListGrid();
		InventoryProductRequestGrid.setCriteria(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));
		gridLayout.addMember(InventoryProductRequestGrid);
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
		this.inventoryProductRequestReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryMaterialRequestReportWindow() {
		for (Canvas removed : this.inventoryMaterialRequestReportWindow.getItems()) {
			this.inventoryMaterialRequestReportWindow.removeItem(removed);
		}
		
		HLayout filterLayout = new HLayout();
		filterLayout.setMargin(10);
		filterLayout.setMembersMargin(5);
		filterLayout.setHeight(30);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(4);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่เบิกจ่ายสินค้า");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("issued_from" , "ตั้งแต่");
		final DateItem to = new DateItem("issued_to" , "ถึง");
		from.setDefaultChooserDate(dateRange.getStartDate());
		from.setDefaultValue(dateRange.getStartDate());
		from.setUseTextField(true);
        to.setDefaultChooserDate(dateRange.getEndDate());
        to.setDefaultValue(dateRange.getEndDate());
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        VLayout buttonLayout = new VLayout();
        buttonLayout.setAlign(VerticalAlignment.CENTER);
        buttonLayout.setHeight(50);
		IButton searchButton = new IButton("ออกรายงานสรุปการเบิกจ่ายวัตถุดิบ");
		searchButton.setIcon("icons/16/reports-icon.png");
		searchButton.setWidth(200);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	InventoryMaterialRequestGrid.fetchData(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
            	InventoryMaterialRequestGrid.deselectAllRecords();
          }
        });
		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm, buttonLayout);
		
		SilverRequestColumnChart chart_1 = new SilverRequestColumnChart();
		MaterialRequestColumnChart chart_2 = new MaterialRequestColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการเบิกจ่ายวัตถุดิบ");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		//chartLayout.setMargin(5);
		
		VLayout chartLayout_1 = new VLayout(); 
		chartLayout_1.setWidth(450);
		chart_1.loadChart(chartLayout_1, this);
		VLayout chartLayout_2 = new VLayout(); 
		chartLayout_2.setWidth(600);
		chart_2.loadChart(chartLayout_2, this);
		
		chartLayout.addMembers(chartLayout_1, chartLayout_2);
		VLayout gridLayout = new VLayout();
		gridLayout.setMargin(5);
		InventoryMaterialRequestGrid = new MaterialRequestReportListGrid();
		gridLayout.addMember(InventoryMaterialRequestGrid);
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
		
		//reportLayout.addMembers(title, chartLayout);
		this.inventoryMaterialRequestReportWindow.addItem(reportLayout);
	}
	
//	public void updateInventoryProductGrid(int type) {
//		System.out.println("call updateInventoryProductGrid on type " + type);
//		switch (type) {
//			case 0: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "ring")); break;
//			case 1: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "toe ring")); break;
//			case 2: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "earring")); break;
//			case 3: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "necklace")); break;
//			case 4: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "pendant")); break;
//			case 5: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "bracelet")); break;
//			case 6: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "anklet")); break;
//			case 7: InventoryProductGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "bangle")); break;
//		default : //none
//		}
//	}
//	
//	public void updateInventoryMaterialGrid(int type, int subtype) {
//		System.out.println("call updateInventoryProductGrid on type " + type);
//		if (type == 1) {
//			InventoryMaterialGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "แร่เงิน"));
//		} else {
//			switch (subtype) {
//				case 0: InventoryMaterialGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "พลอยประดับ")); break;
//				case 1: InventoryMaterialGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "แมกกาไซต์")); break;
//				case 2: InventoryMaterialGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "อื่นๆ")); break;
//			}
//		}
//	}
}
