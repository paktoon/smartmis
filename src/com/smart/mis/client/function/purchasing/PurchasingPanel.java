package com.smart.mis.client.function.purchasing;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smartgwt.client.types.Alignment;

public class PurchasingPanel extends FunctionPanel{
	
	private final FunctionWindow materialWindow;
	private final FunctionWindow supplierWindow;
	private final FunctionWindow purchaseOrderWindow;
	private final FunctionWindow saleOrderWindow;
	private final FunctionWindow reportWindow;
	private final FunctionWindow subPurcharseOrderReportWindow;
	private final FunctionWindow subSaleOrderReportWindow;
	
	public PurchasingPanel(MainPage main){
		super(main, "ระบบการจัดซื้อวัตถุดิบ", 4);
		materialWindow = createFuncWindow();
		supplierWindow = createFuncWindow();
		purchaseOrderWindow = createFuncWindow();
		saleOrderWindow = createFuncWindow();
		
		reportWindow = createFuncWindow();
		subPurcharseOrderReportWindow = createFuncWindow();
		subSaleOrderReportWindow = createFuncWindow();
		
	}

	@Override
	public void init() {
		this._main.getPurchasingPanel().setAlign(Alignment.CENTER);
		this._main.getPurchasingPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("41")) {
			loadWindow(this._main.getPurchasingPanel(), this.materialWindow , name, icon);
		} else if (nodeId.equals("42")) {
			loadWindow(this._main.getPurchasingPanel(), this.supplierWindow , name, icon);
		} else if (nodeId.equals("43")) {
			loadWindow(this._main.getPurchasingPanel(), this.purchaseOrderWindow , name, icon);
		} else if (nodeId.equals("44")) {
			loadWindow(this._main.getPurchasingPanel(), this.saleOrderWindow , name, icon);
		} else if (nodeId.equals("45")) {
			loadWindow(this._main.getPurchasingPanel(), this.reportWindow , name, icon);
		} else if (nodeId.equals("451")) {
			loadWindow(this._main.getPurchasingPanel(), this.subPurcharseOrderReportWindow , name, icon);
		} else if (nodeId.equals("452")) {
			loadWindow(this._main.getPurchasingPanel(), this.subSaleOrderReportWindow , name, icon);
		} else init();
	}
}
