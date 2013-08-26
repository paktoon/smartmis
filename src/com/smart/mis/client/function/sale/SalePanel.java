package com.smart.mis.client.function.sale;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smartgwt.client.types.Alignment;

public class SalePanel extends FunctionPanel{
	
	public SalePanel(MainPage main){
		super(main, "ระบบจัดการงานขาย", 1);
	}

	@Override
	public void init() {
		this._main.getSalePanel().setAlign(Alignment.CENTER);
		this._main.getSalePanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		this._main.getSalePanel().setMembers(createFuncWindow(name, icon));
	}
}
