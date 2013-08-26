package com.smart.mis.client.function.purchasing;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smartgwt.client.types.Alignment;

public class PurchasingPanel extends FunctionPanel{
	
	public PurchasingPanel(MainPage main){
		super(main, "ระบบการจัดซื้อวัตถุดิบ", 4);
	}

	@Override
	public void init() {
		this._main.getPurchasingPanel().setAlign(Alignment.CENTER);
		this._main.getPurchasingPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		this._main.getPurchasingPanel().setMembers(createFuncWindow(name, icon));
	}
}
