package com.smart.mis.client.function.financial;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.financial.disburse.material.DisbursePurchaseLayout;
import com.smart.mis.client.function.financial.disburse.wage.WageLayout;
import com.smart.mis.client.function.financial.receipt.ReceiptLayout;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.VLayout;

//--Function--
public class FinancialPanel extends FunctionPanel{
	
	private final FunctionWindow cashReceiptWindow;
	private final FunctionWindow disburseMaterialWindow;
	private final FunctionWindow disburseWageWindow;
	//private final FunctionWindow reportWindow;
	private final FunctionWindow subReceiptReportWindow;
	private final FunctionWindow subDisbusementReportWindow;
	
	public FinancialPanel(MainPage main){
		super(main, "ระบบบันทึกรายรับรายจ่าย", 5);
		
		cashReceiptWindow = createFuncWindow();
		disburseMaterialWindow = createFuncWindow();
		disburseWageWindow = createFuncWindow();
		
		//reportWindow = createFuncWindow();
		subReceiptReportWindow = createFuncWindow();
		subDisbusementReportWindow = createFuncWindow();
		
		prepareReceiptWindow();
		prepareDisburseMaterialWindow();
		prepareDisburseWageWindow();
	}

	@Override
	public void init() {
		this._main.getFinancialPanel().setAlign(Alignment.CENTER);
		this._main.getFinancialPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("51")) {
			loadWindow(this._main.getFinancialPanel(), this.cashReceiptWindow , name, icon);
		} else if (nodeId.equals("52")) {
			loadWindow(this._main.getFinancialPanel(), this.disburseMaterialWindow , name, icon);
		} else if (nodeId.equals("53")) {
			loadWindow(this._main.getFinancialPanel(), this.disburseWageWindow , name, icon);
		} else if (nodeId.equals("54")) {
			//loadWindow(this._main.getFinancialPanel(), this.reportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("541")) {
			loadWindow(this._main.getFinancialPanel(), this.subReceiptReportWindow , name, icon);
		} else if (nodeId.equals("542")) {
			loadWindow(this._main.getFinancialPanel(), this.subDisbusementReportWindow , name, icon);
		} else init();
	}
	
	private void prepareReceiptWindow(){
		VLayout receiptLayout = new ReceiptLayout(this._main.getCurrentUser());
		this.cashReceiptWindow.addItem(receiptLayout);
	}
	
	private void prepareDisburseMaterialWindow(){
		VLayout disburseLayout = new DisbursePurchaseLayout(this._main.getCurrentUser());
		this.disburseMaterialWindow.addItem(disburseLayout);
	}
	
	private void prepareDisburseWageWindow(){
		VLayout disburseLayout = new WageLayout(this._main.getCurrentUser());
		this.disburseWageWindow.addItem(disburseLayout);
	}
	
}
