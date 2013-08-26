package com.smart.mis.client.function.financial;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smartgwt.client.types.Alignment;

//--Function--
public class FinancialPanel extends FunctionPanel{
	
	public FinancialPanel(MainPage main){
		super(main, "ระบบบันทึกรายรับรายจ่าย", 5);
	}

	@Override
	public void init() {
		this._main.getFinancialPanel().setAlign(Alignment.CENTER);
		this._main.getFinancialPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		this._main.getFinancialPanel().setMembers(createFuncWindow(name, icon));
	}
}
