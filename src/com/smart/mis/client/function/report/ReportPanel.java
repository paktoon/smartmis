package com.smart.mis.client.function.report;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.chart.inventory.ProductColumnChart;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.report.inventory.ProductReportListGrid;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportPanel extends FunctionPanel{
	
	private final FunctionWindow saleCubeReportWindow;
	//private final FunctionWindow summaryReportWindow;
	private final FunctionWindow productionReportWindow;
	private final FunctionWindow inventoryReportWindow;
	private final FunctionWindow purchasingReportWindow;
	private final FunctionWindow incomeExpenseWindow;
	
	private ProductReportListGrid productGrid;
	
	public ReportPanel(MainPage main){
		super(main, "ระบบสารสนเทศเพื่อผู้บริหาร", 6);
		
		saleCubeReportWindow = createFuncWindow();
		
		//summaryReportWindow = createFuncWindow();
		productionReportWindow = createFuncWindow();
		inventoryReportWindow = createFuncWindow();
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
		} else if (nodeId.equals("622")) {
			LoadInventoryReportWindow();
			loadWindow(this._main.getReportPanel(), this.inventoryReportWindow , name, icon);
		} else if (nodeId.equals("623")) {
			loadWindow(this._main.getReportPanel(), this.purchasingReportWindow , name, icon);
		} else if (nodeId.equals("624")) {
			loadWindow(this._main.getReportPanel(), this.incomeExpenseWindow , name, icon);
		} else init();
	}
	
	private void LoadInventoryReportWindow() {
		for (Canvas removed : this.inventoryReportWindow.getItems()) {
			this.inventoryReportWindow.removeItem(removed);
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
		productGrid = new ProductReportListGrid();
		gridLayout.addMember(productGrid);
		
		reportLayout.addMembers(title, chartLayout, gridLayout);
		this.inventoryReportWindow.addItem(reportLayout);
	}
	
	public void updateProductGrid(int type) {
		System.out.println("call updateProductGrid on type " + type);
		switch (type) {
			case 0: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "ring")); break;
			case 1: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "toe ring")); break;
			case 2: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "earring")); break;
			case 3: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "necklace")); break;
			case 4: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "pendant")); break;
			case 5: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "bracelet")); break;
			case 6: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "anklet")); break;
			case 7: productGrid.fetchData(new Criterion("type", OperatorId.EQUALS, "bangle")); break;
		default : //none
		}
	}
}
