package com.smart.mis.client.function.production;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smartgwt.client.types.Alignment;

public class ProductionPanel extends FunctionPanel{
	
	private final FunctionWindow smithWindow;
	private final FunctionWindow productWindow;
	private final FunctionWindow planWindow;
	private final FunctionWindow produceWindow;
	private final FunctionWindow reportWindow;
	private final FunctionWindow SubPlanReportWindow;
	private final FunctionWindow SubTransferReportWindow;
	
	public ProductionPanel(MainPage main){
		super(main, "ระบบจัดการงานผลิต", 2);
		smithWindow = createFuncWindow();
		productWindow = createFuncWindow();
		planWindow = createFuncWindow();
		produceWindow = createFuncWindow();
		reportWindow = createFuncWindow();
		
		SubPlanReportWindow = createFuncWindow();
		SubTransferReportWindow = createFuncWindow();
	}

	@Override
	public void init() {
		this._main.getProductionPanel().setAlign(Alignment.CENTER);
		this._main.getProductionPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("21")) {
			loadWindow(this._main.getProductionPanel(), this.smithWindow , name, icon);
		} else if (nodeId.equals("22")) {
			loadWindow(this._main.getProductionPanel(), this.productWindow , name, icon);
		} else if (nodeId.equals("23")) {
			loadWindow(this._main.getProductionPanel(), this.planWindow , name, icon);
		} else if (nodeId.equals("24")) {
			loadWindow(this._main.getProductionPanel(), this.produceWindow , name, icon);
		} else if (nodeId.equals("25")) {
			loadWindow(this._main.getProductionPanel(), this.reportWindow , name, icon);
		} else if (nodeId.equals("251")) {
			loadWindow(this._main.getProductionPanel(), this.SubPlanReportWindow , name, icon);
		} else if (nodeId.equals("252")) {
			loadWindow(this._main.getProductionPanel(), this.SubTransferReportWindow , name, icon);
		} else init();
	}
}
