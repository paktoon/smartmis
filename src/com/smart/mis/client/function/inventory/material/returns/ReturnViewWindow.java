package com.smart.mis.client.function.inventory.material.returns;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.purchasing.order.material.OrderMaterialDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.delivery.DeliveryData;
import com.smart.mis.client.function.sale.delivery.DeliveryItemDS;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleOrderData;
import com.smart.mis.shared.Country;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.ValidatorFactory;
import com.smart.mis.shared.inventory.ReturnStatus;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.DeliveryStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DoubleItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.ListGridSummaryField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReturnViewWindow extends EditorWindow{

	Window editWindow;
	
	public ReturnViewWindow(){
		
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		editWindow = new Window();
		editWindow.setTitle("ข้อมูลขอคืนวัตถุดิบ");
		editWindow.setWidth(600);  
		editWindow.setHeight(350);
		editWindow.setShowMinimizeButton(false);
		editWindow.setIsModal(true);
		editWindow.setShowModalMask(true);
		editWindow.setCanDragResize(false);
		editWindow.setCanDragReposition(false);
		editWindow.centerInPage();
		
		editWindow.addItem(getViewEditor(record, edit, editWindow, currentUser, page));
		editWindow.show();
	}
	
	private VLayout getViewEditor(final ListGridRecord record, boolean edit, final Window main, final User currentUser, int page) {
		VLayout layout = new VLayout();
		layout.setWidth(570);
		layout.setHeight(320);
		layout.setMargin(10);
		
		final String return_id = record.getAttributeAsString("return_id");
		String job_id = record.getAttributeAsString("job_id");
		String status = record.getAttributeAsString("status");
		
		String smid = record.getAttributeAsString("smid");
		String sm_name = record.getAttributeAsString("sm_name");
		Date return_date = record.getAttributeAsDate("return_date");
		
		final String mid = record.getAttributeAsString("mid");
		String mname = record.getAttributeAsString("mat_name");

		Double return_weight = record.getAttributeAsDouble("total_return_weight");
		Double received_weight = record.getAttributeAsDouble("total_received_weight");
		
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		String received_by = record.getAttributeAsString("received_by");
		Date received_date = record.getAttributeAsDate("received_date");
		
		//Form 1
		DynamicForm returnForm = new DynamicForm();
		returnForm.setWidth100(); 
		returnForm.setHeight(30);
		returnForm.setMargin(5);
		returnForm.setIsGroup(true);
		returnForm.setNumCols(6);
		returnForm.setGroupTitle("ข้อมูลคำขอคืนวัตถุดิบ");

		StaticTextItem reid = new StaticTextItem("return_id", "รหัสคืนวัตถุดิบ");
		StaticTextItem jbid = new StaticTextItem("job_id", "รหัสคำสั่งผลิต");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		reid.setValue(return_id);
		jbid.setValue(job_id);
		sts.setValue(ReturnStatus.getDisplay(status));
		returnForm.setFields(reid, jbid, sts);
		returnForm.setColWidths(100,100,100,100,100,100);
		layout.addMember(returnForm);
		
		//Form 2
		DynamicForm smithForm = new DynamicForm();
		smithForm.setWidth100(); 
		smithForm.setHeight(30);
		smithForm.setMargin(5);
		smithForm.setIsGroup(true);
		smithForm.setNumCols(6);
		smithForm.setGroupTitle("ข้อมูลช่าง");

		StaticTextItem smithid = new StaticTextItem("smid", "รหัสช่าง");
		StaticTextItem smithname = new StaticTextItem("sm_name", "ชื่อช่าง");
		StaticTextItem rdate = new StaticTextItem("return_date", "วันที่ขอคืน");
		smithid.setValue(smid);
		smithname.setValue(sm_name);
		rdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(return_date));
		smithForm.setFields(smithid, smithname, rdate);
		smithForm.setColWidths(100,100,100,100,100,100);
		layout.addMember(smithForm);
		
		//Form 3
		final DynamicForm detailsForm = new DynamicForm();
		detailsForm.setWidth100(); 
		detailsForm.setHeight(30);
		detailsForm.setMargin(5);
		detailsForm.setIsGroup(true);
		detailsForm.setNumCols(4);
		detailsForm.setGroupTitle("ข้อมูลคืนวัตถุดิบ");

		StaticTextItem mat_id = new StaticTextItem("mid", "รหัสวัตถุดิบ");
		StaticTextItem mat_name = new StaticTextItem("mat_name", "ชื่อวัตถุดิบ");
		StaticTextItem ret_weight = new StaticTextItem("total_return_weight", "น้ำหนักที่ขอคืน");
		mat_id.setValue(mid);
		mat_name.setValue(mname);
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		ret_weight.setValue(nf.format(return_weight));
		ret_weight.setHint("กรัม");

		final DoubleItem rec_weight = new DoubleItem("total_received_weight", "น้ำหนักที่รับคืน");
		if (page ==2) {
			rec_weight.setRequired(true);
			rec_weight.setHint("กรัม*");
		} else {
			rec_weight.setValue(nf.format(received_weight));
			rec_weight.setCanEdit(false);
			rec_weight.setHint("กรัม*");
		}
		rec_weight.setValidators(ValidatorFactory.doubleRange(return_weight * 0.98, return_weight * 1.02));
		
		detailsForm.setFields(mat_id, mat_name, ret_weight, rec_weight);
		detailsForm.setColWidths(100,120,100,120);
		layout.addMember(detailsForm);
		
		final DynamicForm endForm = new DynamicForm();
		//endForm.setWidth(380);
		endForm.setHeight(75);
		endForm.setNumCols(4);
		endForm.setMargin(5);
		endForm.setIsGroup(true);
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		endForm.setGroupTitle("ข้อมูลการรับวัตถุดิบ");
		
		StaticTextItem tby = new StaticTextItem("created_by", "ออกคำขอโดย");
		StaticTextItem tdate = new StaticTextItem("created_date", "ออกคำขอเมื่อ");
		tby.setValue(created_by);
		tdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		
		StaticTextItem rcby = new StaticTextItem("received_by", "รับวัตถุดิบโดย");
		StaticTextItem rcdate = new StaticTextItem("received_date", "รับวัตถุดิบเมื่อ");
		if (received_by != null) {
			rcby.setValue(received_by);
		} else {
			rcby.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
		}
		
		if (received_date != null) {
			rcdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(received_date));
		} else {
			rcdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(new Date()));
		}
		
		endForm.setFields(tby, rcby, tdate, rcdate);
		endForm.setColWidths(100,180,120,180);
		//dateForm.editRecord(record);
		layout.addMember(endForm);
		//******************End
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		
		final IButton issueButton = new IButton("บันทึกรับวัตถุดิบ");
		issueButton.setIcon("icons/16/save.png");
		issueButton.setWidth(150);
		issueButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	if (!detailsForm.validate()) {
            		SC.warn("ข้อมูลรับคืนไม่ถูกต้อง กรุณาตรวจสอบอีกครั้ง");
            		return;
            	}
            	
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกรับวัตถุดิบ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//update stock - clear reserved, clear inStock
							//update delivery order status
							//update sale order status
							updateReceived(return_id, record, mid , rec_weight.getValueAsDouble(), currentUser);
						}
					}
            	});
          
          }
        });
		
		final IButton closeButton = new IButton("ปิด");
		closeButton.setIcon("icons/16/close.png");
		closeButton.setWidth(120);
		closeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	main.destroy();
          }
        });
		
		//controls.addMember(printButton);
		if (page == 2 && status.equals("1_return")) controls.addMember(issueButton);
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		return layout;
	}
	
	public void updateReceived(final String order_id , final ListGridRecord record, final String mid, final Double rec_weight, User currentUser){

			final String status = "2_received";
			final String user = currentUser.getFirstName() + " " + currentUser.getLastName();
			
			record.setAttribute("status", status);
			record.setAttribute("total_received_weight", rec_weight);
			record.setAttribute("modified_date", new Date());
			record.setAttribute("modified_by", user);
			record.setAttribute("received_date", new Date());
			record.setAttribute("received_by", user);
			
			//final String wage_id = createWagePayment(record, user);
			
			ReturnDS.getInstance().updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							editWindow.destroy();
						} else { 
							updateStock(mid, rec_weight);
							
							ReturnDS.getInstance().refreshData();
							SC.say("บันทึกรับวัตถุดิบเสร็จสิ้น");
							editWindow.destroy();
						}
				}
			});
	}
	
	void updateStock(String mid, Double rec_weight) {
		
		MaterialDS.getInstance().refreshData();
		Record[] updated_records = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mid", OperatorId.EQUALS, mid));
		Record updated = updated_records[0];
		Double inStock = updated.getAttributeAsDouble("inStock") + rec_weight;
		updated.setAttribute("inStock", inStock);
		Double remain = updated.getAttributeAsDouble("remain") + rec_weight;
		updated.setAttribute("remain", remain);
		MaterialDS.getInstance().updateData(updated);
	}
}
