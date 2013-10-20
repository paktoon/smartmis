package com.smart.mis.client.function.purchasing.supplier;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.material.MaterialDetailTabPane;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class SupplierMaterialChange {

	private SupplierDetailTabPane main;
	public String listId;
	final ListGrid editItemGrid = new ListGrid();
	
	public SupplierMaterialChange(SupplierDetailTabPane main) {
		this.main = main;
	}
	
	public void show(final String mid){
		
		if (mid == null)
		{
			SC.warn("กรูณาเลือกรายการที่ต้องการแก้ไข");
			return;
		}
		
		final Window winModel = new Window();
		
		winModel.setTitle("แก้ไขรายการวัตถุดิบ");
		//winModel.setAutoSize(true);	
		winModel.setWidth(600);
		winModel.setHeight(350);
		winModel.setHeaderIcon("icons/16/comment_edit.png");
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
		
		VLayout outline = new VLayout();
		outline.setWidth100();
		outline.setHeight100();
		outline.setMargin(10);
        
		outline.addMember(getEditItemList());
		
        IButton saveButton = new IButton("ยืนยัน");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	SC.confirm("ยืนยันการแก้ไขรายการ", "ท่านต้องการแก้ไขรายการ  หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value && mid != null) {
							String list = "";
							boolean isStart = true;
							for (ListGridRecord record : editItemGrid.getRecords()) {
								if (isStart) {
									list += record.getAttributeAsString("mid");
									isStart = false;
								} else {
									list += "|" + record.getAttributeAsString("mid");
								}
							}
							main.fetchEditItemList(list);
							winModel.destroy();
						}
					}
            	});
            }  
        }); 
        
        IButton cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setMargin(10);
        cancelButton.setWidth(150);  
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	winModel.destroy();
            }  
        }); 
        
        HLayout temp = new HLayout();
    	temp.addMembers(saveButton, cancelButton);
    	temp.setMargin(3);
    	temp.setAlign(Alignment.CENTER);
        outline.addMember(temp);
        
        winModel.addItem(outline);
        winModel.show();
	}
	private HStack getEditItemList(){
    	//Grid
    	 ListGridField[] defaultField = new ListGridField[] {new ListGridField("mid", 80), new ListGridField("mat_name")};
    	 final ListGrid selectItemGrid = new ListGrid();
    	selectItemGrid.setEmptyMessage("No Item to show.");
    	selectItemGrid.setWidth(240);
    	selectItemGrid.setHeight(224);
    	selectItemGrid.setCanDragRecordsOut(true);
    	selectItemGrid.setAutoFetchData(false);
    	selectItemGrid.setUseAllDataSourceFields(false);
    	selectItemGrid.setDataSource(MaterialDS.getInstance());
    	selectItemGrid.setDragDataAction(DragDataAction.COPY); 
    	selectItemGrid.setDefaultFields(defaultField);
    	//selectItemGrid.hideFields("sup_phone1", "sup_phone2", "fax", "email", "leadtime", "address");
    	
        editItemGrid.setEmptyMessage("No Item to show.");
        editItemGrid.setWidth(240);
        editItemGrid.setHeight(224);
        editItemGrid.setCanAcceptDroppedRecords(true);  
        editItemGrid.setCanRemoveRecords(true);  
        editItemGrid.setAutoFetchData(false);  
        editItemGrid.setPreventDuplicates(true);
        editItemGrid.setUseAllDataSourceFields(false);
        editItemGrid.setDataSource(MaterialDS.getCustomInstance(listId.split("\\|")));
        editItemGrid.setDefaultFields(defaultField);
        //editItemGrid.hideFields("sup_phone1", "sup_phone2", "fax", "email", "leadtime", "address");
        
    	HStack hStack = new HStack(10);  
        hStack.setHeight(160);  
        
        VStack vStack = new VStack(); 
        LayoutSpacer spacer = new LayoutSpacer();  
        spacer.setHeight(30);  
        vStack.addMember(spacer);
        vStack.addMember(selectItemGrid);
        
        hStack.addMember(vStack);
        
        TransferImgButton arrowImg = new TransferImgButton(TransferImgButton.RIGHT);  
        arrowImg.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	editItemGrid.transferSelectedData(selectItemGrid);  
            }  
        });  
        hStack.addMember(arrowImg);
        
        VStack vStack2 = new VStack();
        Label topLabel = new Label("วัตถุดิบที่เลือก");
        topLabel.setHeight(30);
        vStack2.addMember(topLabel);
        vStack2.addMember(editItemGrid);
        
        hStack.addMember(vStack2);
        
        editItemGrid.fetchData();
		selectItemGrid.fetchData();
		
        return hStack;
    }

}
