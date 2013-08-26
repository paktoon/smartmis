package com.smart.mis.client.function.report;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smartgwt.client.types.Alignment;

public class ReportPanel extends FunctionPanel{
	
	public ReportPanel(MainPage main){
		super(main, "ระบบสารสนเทศเพื่อผู้บริหาร", 6);
	}

	@Override
	public void init() {
		this._main.getReportPanel().setAlign(Alignment.CENTER);
		this._main.getReportPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		this._main.getReportPanel().setMembers(createFuncWindow(name, icon));
	}
}
