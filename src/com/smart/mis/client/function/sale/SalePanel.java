package com.smart.mis.client.function.sale;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smartgwt.client.types.Alignment;

public class SalePanel extends FunctionPanel{
	
	private final FunctionWindow customerWindow;
	private final FunctionWindow quotationWindow;
	private final FunctionWindow saleOrderWindow;
	private final FunctionWindow invoiceWindow;
	private final FunctionWindow checkPOWindow;
	private final FunctionWindow saleReportWindow;
	private final FunctionWindow SubQuotationReportWindow;
	private final FunctionWindow SubSaleReportWindow;
	private final FunctionWindow SubDeliveryReportWindow;
	private final FunctionWindow SubInvoiceReportWindow;

	public SalePanel(MainPage main){
		super(main, "ระบบจัดการงานขาย", 1);
		customerWindow = createFuncWindow();
		quotationWindow = createFuncWindow();
		saleOrderWindow = createFuncWindow();
		invoiceWindow = createFuncWindow();
		checkPOWindow = createFuncWindow();
		saleReportWindow = createFuncWindow();
		
		SubQuotationReportWindow = createFuncWindow();
		SubSaleReportWindow = createFuncWindow();
		SubDeliveryReportWindow = createFuncWindow();
		SubInvoiceReportWindow = createFuncWindow();
		
//		prepareCustomerWindow();
//		prepareQuotationWindow();
//		prepareSaleOrderWindow();
//		prepareCheckPOWindow();
//		prepareSaleReportWindow();
	}

	@Override
	public void init() {
		this._main.getSalePanel().setAlign(Alignment.CENTER);
		this._main.getSalePanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("11")) {
			loadWindow(this._main.getSalePanel(), this.customerWindow , name, icon);
		} else if (nodeId.equals("12")) {
			loadWindow(this._main.getSalePanel(), this.quotationWindow , name, icon);
		} else if (nodeId.equals("13")) {
			loadWindow(this._main.getSalePanel(), this.saleOrderWindow , name, icon);
		} else if (nodeId.equals("14")) {
			loadWindow(this._main.getSalePanel(), this.checkPOWindow , name, icon);
		} else if (nodeId.equals("16")) {
			loadWindow(this._main.getSalePanel(), this.invoiceWindow , name, icon);
		} else if (nodeId.equals("15")) {
			loadWindow(this._main.getSalePanel(), this.saleReportWindow , name, icon);
		} else if (nodeId.equals("151")) {
			loadWindow(this._main.getSalePanel(), this.SubQuotationReportWindow , name, icon);
		} else if (nodeId.equals("152")) {
			loadWindow(this._main.getSalePanel(), this.SubSaleReportWindow , name, icon);
		} else if (nodeId.equals("153")) {
			loadWindow(this._main.getSalePanel(), this.SubDeliveryReportWindow , name, icon);
		} else if (nodeId.equals("154")) {
			loadWindow(this._main.getSalePanel(), this.SubInvoiceReportWindow , name, icon);
		} else init();
	}
}
