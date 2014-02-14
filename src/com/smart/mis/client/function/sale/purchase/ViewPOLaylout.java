package com.smart.mis.client.function.sale.purchase;

import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleViewWindow;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ViewPOLaylout extends VLayout{

	ListGrid saleListGrid;
	DynamicForm searchForm;
	IButton viewButton;
	
	public ViewPOLaylout(final User currentUser) {
		setWidth(800);
		setHeight100();
		
		HLayout search = new HLayout();
		search.setWidth(650);
		search.setHeight(45);
		search.setMargin(5);
		search.setMembersMargin(5);
		
		searchForm = new DynamicForm();
		TextItem searchItem = new TextItem("purchase_id", "ค้นหาเลขที่คำสั่งซื้อ");
        searchItem.setTitleOrientation(TitleOrientation.TOP);
        searchItem.setColSpan(2);
        searchItem.setTitleAlign(Alignment.LEFT);
        searchItem.addKeyPressHandler(new KeyPressHandler() {
            public void onKeyPress(KeyPressEvent event) {
                if("enter".equalsIgnoreCase(event.getKeyName())) {
                    updateDetails();
                }
            }
        });
        final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
        final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
        searchItem.setIcons(findIcon, cancelIcon);
        
        searchItem.addIconClickHandler(new IconClickHandler() {
            public void onIconClick(IconClickEvent event) {
                FormItemIcon icon = event.getIcon();
                if(icon.getSrc().equals(cancelIcon.getSrc())) {
                	searchForm.reset();
                	resetDetails();
                } else {
                	updateDetails();
                }
            }
        });
        
        searchForm.setFields(searchItem);
        search.addMember(searchForm);
        
        VLayout buttonLayout = new VLayout();
        buttonLayout.setAlign(VerticalAlignment.BOTTOM);
        buttonLayout.setHeight(38);
        viewButton = new IButton("ตรวจสอบคำสั่งซื้อ");
        viewButton.setIcon("icons/16/folder_out.png");
        viewButton.setWidth(150);
        viewButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SaleViewWindow sale =  new SaleViewWindow();
            	sale.show(saleListGrid.getSelectedRecord(), false, currentUser, 2);
          }
        });
        
        viewButton.hide();
        buttonLayout.addMember(viewButton);
        
        search.addMember(buttonLayout);
        addMember(search);
        
        addMember(createResult());
	}
	
	private VLayout createResult() {
		
		saleListGrid = new ListGrid();
		 
		saleListGrid.setAutoFetchData(true);  
		saleListGrid.setCanMultiSort(true);
		//saleListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"));
		saleListGrid.setDataSource(SaleOrderDS.getInstance());
		saleListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
                new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		saleListGrid.setUseAllDataSourceFields(false);
		//saleListGrid.setGroupByField("status");
		//saleListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField purchase_id = new ListGridField("purchase_id" , 100);
		ListGridField sale_id = new ListGridField("sale_id" , 100);
		//ListGridField quote_id = new ListGridField("quote_id" , 90);
		ListGridField cus_name = new ListGridField("cus_name", 200);
		ListGridField status = new ListGridField("status", 100);
//		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
//		valueMap.put("3_approved", "approved");
//		status.setValueMap(valueMap);
		ListGridField total_amount = new ListGridField("total_amount", 100);
		total_amount.setCellFormatter(FieldFormatter.getIntegerFormat());
		total_amount.setAlign(Alignment.RIGHT);
		ListGridField netInclusive = new ListGridField("netInclusive", 120);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
//		ListGridField created_date = new ListGridField("created_date", 100);
//		created_date.setType(ListGridFieldType.DATE);
//		created_date.setCellFormatter(new CellFormatter() {
//
//			@Override
//			public String format(Object arg0, ListGridRecord arg1, int arg2, int arg3) {
//			DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy");
//			return fmt.format(arg1.getAttributeAsDate("created_date"));
//			}
//		});
			
//		ListGridField iconField = new ListGridField("viewSaleOrderField", "เรียกดู", 100);
		
		saleListGrid.setFields(purchase_id, sale_id, cus_name,status, total_amount, netInclusive);
		
		saleListGrid.addRecordClickHandler(new RecordClickHandler() {  
			@Override
			public void onRecordClick(RecordClickEvent event) {
				viewButton.show();
			}  
        });
		
		//saleListGrid.hideField("status");
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth(750);
		gridLayout.setHeight(450);
		gridLayout.setMargin(5);
		
		gridLayout.addMember(saleListGrid);
		
		return gridLayout;
	}
	
	private void updateDetails() {
//		for(ListGridRecord record : saleListGrid.getRecords()) {
//			saleListGrid.removeData(record);
//		}
		viewButton.hide();
		saleListGrid.fetchData(searchForm.getValuesAsCriteria());
		saleListGrid.deselectAllRecords();
	}
	
	private void resetDetails() {
//		for(ListGridRecord record : saleListGrid.getRecords()) {
//			saleListGrid.removeData(record);
//		}
		viewButton.hide();
		saleListGrid.fetchData();
		saleListGrid.deselectAllRecords();
	}
}
