package com.smart.mis.client.function.production;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smartgwt.client.types.Alignment;

public class ProductionPanel extends FunctionPanel{
	
	public ProductionPanel(MainPage main){
		super(main, "ระบบจัดการงานผลิต", 2);
	}

	@Override
	public void init() {
		this._main.getProductionPanel().setAlign(Alignment.CENTER);
		this._main.getProductionPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		this._main.getProductionPanel().setMembers(createFuncWindow(name, icon));
	}
}
