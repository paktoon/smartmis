package com.smart.mis.client.function.report;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smartgwt.client.types.Alignment;

public class ReportPanel extends FunctionPanel{
	
	private final FunctionWindow saleCubeReportWindow;
	private final FunctionWindow summaryReportWindow;
	private final FunctionWindow productionReportWindow;
	private final FunctionWindow inventoryReportWindow;
	private final FunctionWindow purchasingReportWindow;
	private final FunctionWindow incomeExpenseWindow;
	
	public ReportPanel(MainPage main){
		super(main, "ระบบสารสนเทศเพื่อผู้บริหาร", 6);
		
		saleCubeReportWindow = createFuncWindow();
		
		summaryReportWindow = createFuncWindow();
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
			loadWindow(this._main.getReportPanel(), this.summaryReportWindow , name, icon);
		} else if (nodeId.equals("621")) {
			loadWindow(this._main.getReportPanel(), this.productionReportWindow , name, icon);
		} else if (nodeId.equals("622")) {
			loadWindow(this._main.getReportPanel(), this.inventoryReportWindow , name, icon);
		} else if (nodeId.equals("623")) {
			loadWindow(this._main.getReportPanel(), this.purchasingReportWindow , name, icon);
		} else if (nodeId.equals("624")) {
			loadWindow(this._main.getReportPanel(), this.incomeExpenseWindow , name, icon);
		} else init();
	}
}
