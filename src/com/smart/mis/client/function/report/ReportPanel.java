package com.smart.mis.client.function.report;

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.MainPage;
import com.smart.mis.client.chart.financial.CashReceiptColumnChart;
import com.smart.mis.client.chart.financial.DisburseMaterialPieChart;
import com.smart.mis.client.chart.financial.DisburseWagePieChart;
import com.smart.mis.client.chart.inventory.MaterialColumnChart;
import com.smart.mis.client.chart.inventory.MaterialReceivedColumnChart;
import com.smart.mis.client.chart.inventory.MaterialRequestColumnChart;
import com.smart.mis.client.chart.inventory.ProductColumnChart;
import com.smart.mis.client.chart.inventory.ProductReceivedColumnChart;
import com.smart.mis.client.chart.inventory.ProductRequestColumnChart;
import com.smart.mis.client.chart.inventory.SilverColumnChart;
import com.smart.mis.client.chart.inventory.SilverReceivedColumnChart;
import com.smart.mis.client.chart.inventory.SilverRequestColumnChart;
import com.smart.mis.client.chart.production.MaterialUsedColumnChart;
import com.smart.mis.client.chart.production.ProductionColumnChart;
import com.smart.mis.client.chart.production.SilverUsedColumnChart;
import com.smart.mis.client.chart.purchasing.PurchasingColumnChart;
import com.smart.mis.client.cube.SaleCube;
import com.smart.mis.client.cube.advanced.AdvancedSaleCube;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.report.financial.CashReceiptListGrid;
import com.smart.mis.client.function.report.financial.DisburseMaterialListGrid;
import com.smart.mis.client.function.report.financial.DisburseWageListGrid;
import com.smart.mis.client.function.report.inventory.MaterialReceivedReportListGrid;
import com.smart.mis.client.function.report.inventory.MaterialReportListGrid;
import com.smart.mis.client.function.report.inventory.MaterialRequestReportListGrid;
import com.smart.mis.client.function.report.inventory.ProductReceivedReportListGrid;
import com.smart.mis.client.function.report.inventory.ProductReportListGrid;
import com.smart.mis.client.function.report.inventory.ProductRequestReportListGrid;
import com.smart.mis.client.function.report.production.MaterialUsedReportListGrid;
import com.smart.mis.client.function.report.production.ProductionReportListGrid;
import com.smart.mis.client.function.report.purchasing.PurchasingListGrid;
import com.smart.mis.shared.report.CustomerType;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportPanel extends FunctionPanel{
	
	private final FunctionWindow saleCubeReportWindow;
	//private final FunctionWindow summaryReportWindow;
	private final FunctionWindow productionReportWindow;
	private final FunctionWindow materailUsedReportWindow;
	
	private final FunctionWindow inventoryProductReportWindow;
	private final FunctionWindow inventoryMaterialReportWindow;
	private final FunctionWindow inventoryProductRequestReportWindow;
	private final FunctionWindow inventoryMaterialRequestReportWindow;
	private final FunctionWindow inventoryProductReceivedReportWindow;
	private final FunctionWindow inventoryMaterialReceivedReportWindow;
	
	private final FunctionWindow purchasingReportWindow;
	
	//private final FunctionWindow incomeExpenseWindow;
	private final FunctionWindow cashReceiptWindow;
	private final FunctionWindow DisburseMaterialWindow;
	private final FunctionWindow DisburseWageWindow;
	
	private ProductReportListGrid InventoryProductGrid;
	private MaterialReportListGrid InventoryMaterialGrid;
	private ProductRequestReportListGrid InventoryProductRequestGrid;
	private MaterialRequestReportListGrid InventoryMaterialRequestGrid;
	private ProductReceivedReportListGrid InventoryProductReceivedGrid;
	private MaterialReceivedReportListGrid InventoryMaterialReceivedGrid;
	private CashReceiptListGrid cashReceiptListGrid;
	private DisburseMaterialListGrid disburseMaterialListGrid;
	private DisburseWageListGrid disburseWageListGrid;
	private PurchasingListGrid purchasingListGrid;
	private MaterialUsedReportListGrid materialUsedReportListGrid;
	private ProductionReportListGrid productionReportListGrid;
	
	private Date startDate;
	private Date endDate;
	
	public ReportPanel(MainPage main){
		super(main, "ระบบสารสนเทศเพื่อผู้บริหาร", 6);
		
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
        startDate = dateRange.getStartDate();
        endDate = dateRange.getEndDate();
        
		saleCubeReportWindow = createFuncWindow();
		
		//summaryReportWindow = createFuncWindow();
		productionReportWindow = createFuncWindow();
		materailUsedReportWindow = createFuncWindow();
		
		inventoryProductReportWindow = createFuncWindow();
		inventoryMaterialReportWindow = createFuncWindow();
		inventoryProductRequestReportWindow = createFuncWindow();
		inventoryMaterialRequestReportWindow = createFuncWindow();
		inventoryProductReceivedReportWindow = createFuncWindow();
		inventoryMaterialReceivedReportWindow = createFuncWindow();
		
		purchasingReportWindow = createFuncWindow();
		
		cashReceiptWindow = createFuncWindow();
		DisburseMaterialWindow = createFuncWindow(); 
		DisburseWageWindow = createFuncWindow(); 


		
//		AdvancedSaleCube cube = new AdvancedSaleCube();
//		this.saleCubeReportWindow.addItem(cube.createMainLayout());
	}

	@Override
	public void init() {
		this._main.getReportPanel().setAlign(Alignment.CENTER);
		this._main.getReportPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("61")) {
			// Time : yearly, quarterly (fit in 1 year)
			// Customer : Type, name (fit in type : todo), zone (fit in type)
			// Sale : name
			// Product : Type, name (fit in type : todo)
			LoadCubeReportWindow("yearly", "type", "all", "type", "2013");
			loadWindow(this._main.getReportPanel(), this.saleCubeReportWindow , name, icon);
		} else if (nodeId.equals("62")) {
			//loadWindow(this._main.getReportPanel(), this.summaryReportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("6211")) {
			LoadProductionReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.productionReportWindow , name, icon);
		} else if (nodeId.equals("6212")) {
			LoadMaterialUsedReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.materailUsedReportWindow , name, icon);
		} else if (nodeId.equals("6221")) {
			LoadinventoryProductReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryProductReportWindow , name, icon);
		} else if (nodeId.equals("6222")) {
			LoadinventoryMaterialReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryMaterialReportWindow , name, icon);
		} else if (nodeId.equals("6223")) {
			LoadinventoryProductRequestReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.inventoryProductRequestReportWindow , name, icon);
		} else if (nodeId.equals("6224")) {
			LoadinventoryMaterialRequestReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.inventoryMaterialRequestReportWindow , name, icon);
		} else if (nodeId.equals("6225")) {
			LoadinventoryProductReceivedReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.inventoryProductReceivedReportWindow , name, icon);
		} else if (nodeId.equals("6226")) {
			LoadinventoryMaterialReceivedReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.inventoryMaterialReceivedReportWindow , name, icon);
		} else if (nodeId.equals("623")) {
			LoadPurchasingReportWindow("type", startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.purchasingReportWindow , name, icon);
		} else if (nodeId.equals("6241")) {
			LoadCashReceiptReportWindow("cus_type", startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.cashReceiptWindow , name, icon);
		} else if (nodeId.equals("6242")) {
			LoadDisburseMaterialReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.DisburseMaterialWindow , name, icon);
		} else if (nodeId.equals("6243")) {
			LoadDisburseWageReportWindow(startDate, endDate);
			loadWindow(this._main.getReportPanel(), this.DisburseWageWindow , name, icon);
		} else init();
	}
	
	private void LoadCubeReportWindow(String time, final String customer, final String sale, final String product, final String yearSelected) {
		for (Canvas removed : this.saleCubeReportWindow.getItems()) {
			this.saleCubeReportWindow.removeItem(removed);
		}
		
		final DynamicForm filterForm  = new DynamicForm();
		filterForm.setWidth(300); 
		filterForm.setHeight(30);
		filterForm.setMargin(5); 
		filterForm.setNumCols(4);
		filterForm.setCellPadding(2);
		filterForm.setSelectOnFocus(true);
		filterForm.setIsGroup(true);
		filterForm.setGroupTitle("Select Report");
		
		final SelectItem selectTime = new SelectItem();
		selectTime.setTitle("Time");
		selectTime.setValueMap("yearly", "quarterly", "monthly");
		selectTime.setDefaultValue(time);
		
		final SelectItem selectYear = new SelectItem();
		selectYear.setTitle("Year");
		selectYear.setValueMap("2011", "2012", "2013");
		selectYear.setDefaultValue(yearSelected);
		
		if (time.equalsIgnoreCase("yearly")) {
			selectYear.disable();
		} else selectYear.enable();
		filterForm.setItems(selectTime, selectYear);
		
		selectTime.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
//				if (selectTime.getValueAsString().equalsIgnoreCase("yearly")) {
//					selectYear.disable();
//				} else selectYear.enable();
				LoadCubeReportWindow(selectTime.getValueAsString(), customer, sale, product, selectYear.getValueAsString());
		}});
		

		selectYear.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				LoadCubeReportWindow(selectTime.getValueAsString(), customer, sale, product, selectYear.getValueAsString());
		}});
		
        
		VLayout cubeLayout = new VLayout();
		cubeLayout.setMargin(10);
		cubeLayout.addMember(filterForm);
		cubeLayout.addMember(SaleCube.createCubeGrid(time, customer, sale, product, yearSelected));
		
		this.saleCubeReportWindow.addItem(cubeLayout);
	}
	
	private void LoadProductionReportWindow(final Date start, final Date end) {
		for (Canvas removed : this.productionReportWindow.getItems()) {
			this.productionReportWindow.removeItem(removed);
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
		dateForm.setGroupTitle("วันที่ผลิตสินค้า");
		final DateItem from = new DateItem("produced_from" , "ตั้งแต่");
		final DateItem to = new DateItem("produced_to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadProductionReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadProductionReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		//productionReportListGrid = new ProductionReportListGrid();
		//InventoryProductRequestGrid.setCriteria(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
	  		      new Criterion("produced_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
	  		  });
		//productionReportListGrid.setCriteria(criteria);
		productionReportListGrid = new ProductionReportListGrid(criteria);
		gridLayout.addMember(productionReportListGrid);
		
		ProductionColumnChart chart = new ProductionColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปผลการผลิตสินค้าแยกตามประเภท");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this, productionReportListGrid.createDataTable(criteria));
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
//		reportLayout.addMembers(filterLayout, title, chartLayout);
		this.productionReportWindow.addItem(reportLayout);
	}
	
	private void LoadMaterialUsedReportWindow(final Date start, final Date end) {
		for (Canvas removed : this.materailUsedReportWindow.getItems()) {
			this.materailUsedReportWindow.removeItem(removed);
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
		dateForm.setGroupTitle("ช่วงที่ใชัวัตถุดิบ");
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("used_from" , "ตั้งแต่");
		final DateItem to = new DateItem("used_to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadMaterialUsedReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadMaterialUsedReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		//materialUsedReportListGrid = new MaterialUsedReportListGrid();
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
	  		      new Criterion("request_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
	  		  });
		//materialUsedReportListGrid.setCriteria(criteria);
		materialUsedReportListGrid = new MaterialUsedReportListGrid(criteria);
		gridLayout.addMember(materialUsedReportListGrid);
		
		SilverUsedColumnChart chart_1 = new SilverUsedColumnChart();
		MaterialUsedColumnChart chart_2 = new MaterialUsedColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการใช้วัตถุดิบแยกตามประเภท");
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
		chart_1.loadChart(chartLayout_1, this, materialUsedReportListGrid.createSilverDataTable(criteria));
		VLayout chartLayout_2 = new VLayout(); 
		chartLayout_2.setWidth(600);
		chart_2.loadChart(chartLayout_2, this, materialUsedReportListGrid.createMaterialDataTable(criteria));
		
		chartLayout.addMembers(chartLayout_1, chartLayout_2);
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
//		reportLayout.addMembers(filterLayout, title, chartLayout);
		this.materailUsedReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryProductReportWindow() {
		for (Canvas removed : this.inventoryProductReportWindow.getItems()) {
			this.inventoryProductReportWindow.removeItem(removed);
		}
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดสินค้าคงคลัง");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);

		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		InventoryProductGrid = new ProductReportListGrid();
		gridLayout.addMember(InventoryProductGrid);
		
		ProductColumnChart chart = new ProductColumnChart();
		VLayout reportLayout = new VLayout();
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this, InventoryProductGrid.createDataTable());
		
		reportLayout.addMembers(title, chartLayout, gridLayout);
		this.inventoryProductReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryMaterialReportWindow() {
		for (Canvas removed : this.inventoryMaterialReportWindow.getItems()) {
			this.inventoryMaterialReportWindow.removeItem(removed);
		}
		
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

		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		InventoryMaterialGrid = new MaterialReportListGrid();
		gridLayout.addMember(InventoryMaterialGrid);
		
		SilverColumnChart chart_1 = new SilverColumnChart();
		MaterialColumnChart chart_2 = new MaterialColumnChart();
		VLayout reportLayout = new VLayout();
		VLayout chartLayout_1 = new VLayout(); 
		chartLayout_1.setWidth(450);
		chart_1.loadChart(chartLayout_1, this , InventoryMaterialGrid.createSilverDataTable());
		VLayout chartLayout_2 = new VLayout(); 
		chartLayout_2.setWidth(600);
		chart_2.loadChart(chartLayout_2, this, InventoryMaterialGrid.createMaterialDataTable());

		chartLayout.addMembers(chartLayout_1, chartLayout_2);
		
		reportLayout.addMembers(title, chartLayout, gridLayout);
		
		//reportLayout.addMembers(title, chartLayout);
		this.inventoryMaterialReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryProductRequestReportWindow(final Date start, final Date end) {
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
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("issued_from" , "ตั้งแต่");
		final DateItem to = new DateItem("issued_to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadinventoryProductRequestReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadinventoryProductRequestReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
        
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.CENTER);
//        buttonLayout.setHeight(50);
//		IButton searchButton = new IButton("ออกรายงานสรุปการเบิกจ่ายสินค้า");
//		searchButton.setIcon("icons/16/reports-icon.png");
//		searchButton.setWidth(200);
//		searchButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	InventoryProductRequestGrid.fetchData(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
//            	InventoryProductRequestGrid.deselectAllRecords();
//          }
//        });
//		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		Criterion dateCriteria = new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate());
		//InventoryProductRequestGrid = new ProductRequestReportListGrid();
		//InventoryProductRequestGrid.setCriteria(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[] { dateCriteria });
		//InventoryProductRequestGrid.setCriteria(criteria);
		InventoryProductRequestGrid = new ProductRequestReportListGrid(criteria);
		gridLayout.addMember(InventoryProductRequestGrid);
		
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
		chart.loadChart(chartLayout, this, InventoryProductRequestGrid.createDataTable(dateCriteria));
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
		this.inventoryProductRequestReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryMaterialRequestReportWindow(final Date start, final Date end) {
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
		dateForm.setGroupTitle("วันที่เบิกจ่ายวัตถุดิบ");
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("issued_from" , "ตั้งแต่");
		final DateItem to = new DateItem("issued_to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadinventoryMaterialRequestReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadinventoryMaterialRequestReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.CENTER);
//        buttonLayout.setHeight(50);
//		IButton searchButton = new IButton("ออกรายงานสรุปการเบิกจ่ายวัตถุดิบ");
//		searchButton.setIcon("icons/16/reports-icon.png");
//		searchButton.setWidth(200);
//		searchButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	InventoryMaterialRequestGrid.fetchData(new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
//            	InventoryMaterialRequestGrid.deselectAllRecords();
//          }
//        });
//		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm);
		
		SilverRequestColumnChart chart_1 = new SilverRequestColumnChart();
		MaterialRequestColumnChart chart_2 = new MaterialRequestColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการเบิกจ่ายวัตถุดิบ");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		Criterion dateCriteria = new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate());
		//InventoryMaterialRequestGrid = new MaterialRequestReportListGrid();
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{ dateCriteria });
		//InventoryMaterialRequestGrid.setCriteria(criteria);
		InventoryMaterialRequestGrid = new MaterialRequestReportListGrid(criteria);
		gridLayout.addMember(InventoryMaterialRequestGrid);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		//chartLayout.setMargin(5);
		
		VLayout chartLayout_1 = new VLayout(); 
		chartLayout_1.setWidth(450);
		chart_1.loadChart(chartLayout_1, this, InventoryMaterialRequestGrid.createSilverDataTable(criteria));
		VLayout chartLayout_2 = new VLayout(); 
		chartLayout_2.setWidth(600);
		chart_2.loadChart(chartLayout_2, this, InventoryMaterialRequestGrid.createMaterialDataTable(criteria));
		
		chartLayout.addMembers(chartLayout_1, chartLayout_2);
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
		
		//reportLayout.addMembers(title, chartLayout);
		this.inventoryMaterialRequestReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryProductReceivedReportWindow(final Date start, final Date end) {
		for (Canvas removed : this.inventoryProductReceivedReportWindow.getItems()) {
			this.inventoryProductReceivedReportWindow.removeItem(removed);
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
		dateForm.setGroupTitle("วันที่รับสินค้า");
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("received_from" , "ตั้งแต่");
		final DateItem to = new DateItem("recevied_to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadinventoryProductReceivedReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadinventoryProductReceivedReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.CENTER);
//        buttonLayout.setHeight(50);
//		IButton searchButton = new IButton("ออกรายงานสรุปการรับสินค้า");
//		searchButton.setIcon("icons/16/reports-icon.png");
//		searchButton.setWidth(200);
//		searchButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	InventoryProductReceivedGrid.fetchData(new Criterion("received_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
//            	InventoryProductReceivedGrid.deselectAllRecords();
//          }
//        });
//		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		Criterion dateCriteria = new Criterion("received_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate());
		
		//InventoryProductReceivedGrid.setCriteria(new Criterion("received_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{ dateCriteria });
		InventoryProductReceivedGrid = new ProductReceivedReportListGrid(criteria);
		//InventoryProductReceivedGrid.setCriteria(criteria);
		gridLayout.addMember(InventoryProductReceivedGrid);
		
		ProductReceivedColumnChart chart = new ProductReceivedColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการรับสินค้า");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this, InventoryProductReceivedGrid.createDataTable(criteria));
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
		this.inventoryProductReceivedReportWindow.addItem(reportLayout);
	}
	
	private void LoadinventoryMaterialReceivedReportWindow(final Date start, final Date end) {
		for (Canvas removed : this.inventoryMaterialReceivedReportWindow.getItems()) {
			this.inventoryMaterialReceivedReportWindow.removeItem(removed);
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
		dateForm.setGroupTitle("วันที่รับวัตถุดิบ");
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("received_from" , "ตั้งแต่");
		final DateItem to = new DateItem("received_to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadinventoryMaterialReceivedReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadinventoryMaterialReceivedReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
        
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.CENTER);
//        buttonLayout.setHeight(50);
//		IButton searchButton = new IButton("ออกรายงานสรุปการรับวัตถุดิบ");
//		searchButton.setIcon("icons/16/reports-icon.png");
//		searchButton.setWidth(200);
//		searchButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	InventoryMaterialReceivedGrid.fetchData(new Criterion("received_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
//            	InventoryMaterialReceivedGrid.deselectAllRecords();
//          }
//        });
//		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		Criterion dateCriteria = new Criterion("received_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate());
		//InventoryMaterialReceivedGrid = new MaterialReceivedReportListGrid();
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{ dateCriteria });
		//InventoryMaterialReceivedGrid.setCriteria(criteria);
		InventoryMaterialReceivedGrid = new MaterialReceivedReportListGrid(criteria);
		gridLayout.addMember(InventoryMaterialReceivedGrid);
		
		SilverReceivedColumnChart chart_1 = new SilverReceivedColumnChart();
		MaterialReceivedColumnChart chart_2 = new MaterialReceivedColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการรับวัตถุดิบ");
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
		chart_1.loadChart(chartLayout_1, this, InventoryMaterialReceivedGrid.createSilverDataTable(criteria));
		VLayout chartLayout_2 = new VLayout(); 
		chartLayout_2.setWidth(600);
		chart_2.loadChart(chartLayout_2, this, InventoryMaterialReceivedGrid.createMaterialDataTable(criteria));
		
		chartLayout.addMembers(chartLayout_1, chartLayout_2);
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
		
		//reportLayout.addMembers(title, chartLayout);
		this.inventoryMaterialReceivedReportWindow.addItem(reportLayout);
	}
	
	private void LoadCashReceiptReportWindow(final String type, final Date start, final Date end) {
		for (Canvas removed : this.cashReceiptWindow.getItems()) {
			this.cashReceiptWindow.removeItem(removed);
		}
		
		HLayout filterLayout = new HLayout();
		filterLayout.setMargin(10);
		filterLayout.setMembersMargin(5);
		filterLayout.setHeight(30);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(500); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(6);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("ตัวเลือกรายงาน");
		
		final SelectItem reportType = new SelectItem("แยกตาม");
		//reportType.setValueMap("ประเภทลูกค้า", "ลักษณะธุรกิจ", "กลุ่มลูกค้า", "โซน");
		reportType.setValueMap(CustomerType.getValueMap());
		reportType.setDefaultValue(type);
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("from" , "ตั้งแต่");
		final DateItem to = new DateItem("to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(reportType, from, to);
        
        reportType.addChangedHandler(new ChangedHandler(){

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				LoadCashReceiptReportWindow(reportType.getValueAsString(), from.getValueAsDate(), to.getValueAsDate());
			}});
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadCashReceiptReportWindow(reportType.getValueAsString(), from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadCashReceiptReportWindow(reportType.getValueAsString(), from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.CENTER);
//        buttonLayout.setHeight(50);
//		IButton searchButton = new IButton("ออกรายงานสรุปการรับชำระเงิน");
//		searchButton.setIcon("icons/16/reports-icon.png");
//		searchButton.setWidth(200);
//		searchButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	cashReceiptListGrid.fetchData(new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
//            	cashReceiptListGrid.deselectAllRecords();
//          }
//        });
//		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		//cashReceiptListGrid = new CashReceiptListGrid(type);
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
    		      new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
    		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
    		  });
		//cashReceiptListGrid.setCriteria(criteria);
		cashReceiptListGrid = new CashReceiptListGrid(criteria, type);
		gridLayout.addMember(cashReceiptListGrid);
		
		CashReceiptColumnChart chart = new CashReceiptColumnChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการรับชำระเงิน");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this, type, cashReceiptListGrid.createDataTable(criteria, type));
		
//		Record[] filtered = filterDataSource(cashReceiptListGrid.getDataSource(), criteria);
//		System.out.println("Found " + filtered.length);
//		for (Record record : filtered){
//			System.out.println(" --> " + record.getAttributeAsDouble("netInclusive"));
//		}
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
//		reportLayout.addMembers(filterLayout, title, chartLayout);
		this.cashReceiptWindow.addItem(reportLayout);
	}
	
	private void LoadDisburseMaterialReportWindow(final Date start, final Date end) {
		for (Canvas removed : this.DisburseMaterialWindow.getItems()) {
			this.DisburseMaterialWindow.removeItem(removed);
		}
		
		HLayout filterLayout = new HLayout();
		filterLayout.setMargin(10);
		filterLayout.setMembersMargin(5);
		filterLayout.setHeight(30);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(500); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(6);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่ชำระค่าวัตถุดิบ");
		
		final DateItem from = new DateItem("from" , "ตั้งแต่");
		final DateItem to = new DateItem("to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadDisburseMaterialReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadDisburseMaterialReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		//disburseMaterialListGrid = new DisburseMaterialListGrid();
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
    		      new Criterion("paid_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
    		  });
		//disburseMaterialListGrid.setCriteria(criteria);

		disburseMaterialListGrid = new DisburseMaterialListGrid(criteria);
		DisburseMaterialPieChart chart = new DisburseMaterialPieChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปสัดส่วนรายจ่ายวัตถุดิบแยกตามประเภท");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this, disburseMaterialListGrid.createDataTable(criteria));
		
		gridLayout.addMembers(chartLayout, disburseMaterialListGrid);
		
		reportLayout.addMembers(filterLayout, title, gridLayout);
		this.DisburseMaterialWindow.addItem(reportLayout);
	}
	
	private void LoadDisburseWageReportWindow(final Date start, final Date end) {
		for (Canvas removed : this.DisburseWageWindow.getItems()) {
			this.DisburseWageWindow.removeItem(removed);
		}
		
		HLayout filterLayout = new HLayout();
		filterLayout.setMargin(10);
		filterLayout.setMembersMargin(5);
		filterLayout.setHeight(30);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(500); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(6);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่ชำระค่าจ้างผลิต");
		
		final DateItem from = new DateItem("from" , "ตั้งแต่");
		final DateItem to = new DateItem("to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        dateForm.setItems(from, to);
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					LoadDisburseMaterialReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					LoadDisburseMaterialReportWindow(from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		//disburseWageListGrid = new DisburseWageListGrid();
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				  new Criterion("status", OperatorId.EQUALS, "2_paid"),
    		      new Criterion("paid_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
    		  });
		//disburseWageListGrid.setCriteria(criteria);
		disburseWageListGrid = new DisburseWageListGrid(criteria);
		DisburseWagePieChart chart = new DisburseWagePieChart();
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปสัดส่วนรายจ่ายค่าจ้างผลิตแยกตามประเภท");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		chart.loadChart(chartLayout, this, disburseWageListGrid.createDataTable(criteria) );
		
		gridLayout.addMembers(chartLayout, disburseWageListGrid);
		
		reportLayout.addMembers(filterLayout, title, gridLayout);
		this.DisburseWageWindow.addItem(reportLayout);
	}
	
	private void LoadPurchasingReportWindow(final String type, final Date start, final Date end) {
		for (Canvas removed : this.purchasingReportWindow.getItems()) {
			this.purchasingReportWindow.removeItem(removed);
		}
		
		HLayout filterLayout = new HLayout();
		filterLayout.setMargin(10);
		filterLayout.setMembersMargin(5);
		filterLayout.setHeight(30);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(500); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(6);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("ตัวเลือกรายงาน");
		
//		final SelectItem reportType = new SelectItem("แยกตาม");
//		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
//		valueMap.put("type", "ประเภทวัตถุดิบ");
//        valueMap.put("created_date", "ช่วงเวลา");
//		reportType.setValueMap(valueMap);
//		//reportType.setValueMap(CustomerType.getValueMap());
//		reportType.setDefaultValue(type);
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
//        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("from" , "ตั้งแต่");
		final DateItem to = new DateItem("to" , "ถึง");
		from.setDefaultChooserDate(start);
		from.setDefaultValue(start);
		from.setUseTextField(true);
        to.setDefaultChooserDate(end);
        to.setDefaultValue(end);
        to.setUseTextField(true);
        //dateForm.setItems(reportType, from, to);
        dateForm.setItems(from, to);
        
//        reportType.addChangedHandler(new ChangedHandler(){
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				// TODO Auto-generated method stub
//				LoadPurchasingReportWindow(reportType.getValueAsString(), from.getValueAsDate(), to.getValueAsDate());
//			}});
        
        from.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (from.getValueAsDate().before(to.getValueAsDate())) {
					//LoadPurchasingReportWindow(reportType.getValueAsString(), from.getValueAsDate(), to.getValueAsDate());
					LoadPurchasingReportWindow("type", from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					from.setValue(start);
				}
		}});
        
        to.addChangedHandler( new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if (to.getValueAsDate().after(from.getValueAsDate())) {
					//LoadPurchasingReportWindow(reportType.getValueAsString(), from.getValueAsDate(), to.getValueAsDate());
					LoadPurchasingReportWindow("type", from.getValueAsDate(), to.getValueAsDate());
				} else {
					SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
					to.setValue(end);
				}
		}});
//        VLayout buttonLayout = new VLayout();
//        buttonLayout.setAlign(VerticalAlignment.CENTER);
//        buttonLayout.setHeight(50);
//		IButton searchButton = new IButton("ออกรายงานสรุปการรับชำระเงิน");
//		searchButton.setIcon("icons/16/reports-icon.png");
//		searchButton.setWidth(200);
//		searchButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	cashReceiptListGrid.fetchData(new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()));  
//            	cashReceiptListGrid.deselectAllRecords();
//          }
//        });
//		buttonLayout.addMember(searchButton);
		
		filterLayout.addMembers(dateForm);
		
		HLayout gridLayout = new HLayout();
		gridLayout.setMargin(5);
		gridLayout.setAlign(Alignment.CENTER);
		//purchasingListGrid = new PurchasingListGrid(type);
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
    		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
    		  });
		//purchasingListGrid.setCriteria(criteria);

		purchasingListGrid = new PurchasingListGrid(criteria, type);
		gridLayout.addMember(purchasingListGrid);
		
		VLayout reportLayout = new VLayout();
		
		Label title = new Label();
		title.setContents("รายงานสรุปยอดการสั่งซื้อวัตถุดิบ");
		title.setStyleName("reportTitle");
		title.setAlign(Alignment.CENTER);
		title.setWidth(1000);
		title.setHeight(30);
		
		HLayout chartLayout = new HLayout();
		chartLayout.setAlign(Alignment.CENTER);
		chartLayout.setHeight(350);
		
		if (type.equalsIgnoreCase("type")) {
			PurchasingColumnChart chart = new PurchasingColumnChart();
			chart.loadChart(chartLayout, this, type, purchasingListGrid.createDataTable(criteria, type));
		}

//		Record[] filtered = filterDataSource(cashReceiptListGrid.getDataSource(), criteria);
//		System.out.println("Found " + filtered.length);
//		for (Record record : filtered){
//			System.out.println(" --> " + record.getAttributeAsDouble("netInclusive"));
//		}
		
		reportLayout.addMembers(filterLayout, title, chartLayout, gridLayout);
//		reportLayout.addMembers(filterLayout, title, chartLayout);
		this.purchasingReportWindow.addItem(reportLayout);
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
	
	public Record[] filterDataSource(DataSource ds, AdvancedCriteria criteria){
		return ds.applyFilter(ds.getCacheData(), criteria);
	}
	
}
