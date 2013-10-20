package com.smart.mis.client.function.purchasing.material;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierData;
import com.smart.mis.client.function.purchasing.supplier.SupplierListGrid;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.Record;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;  
import com.smartgwt.client.widgets.tab.TabSet;  
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;  
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;  
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;  

public class MaterialDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm; //, normalForm;
    private SupplierListGrid viewItemGrid, editItemGrid;
    private Label editorLabel;  
    private MaterialListGrid materialListGrid; 
    private MaterialDS materialDataSource;
    //private PermissionDS permissionDataSource;
    private HLayout outlineForm;
    private IButton saveButton, cancelButton;
    private String currentMid = null;
    public String currentChangeSidList = null;
    
    private final MaterialSupplierChange changeFunc = new MaterialSupplierChange(this);
//    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
//    private SelectItem profile;
    
    public MaterialDetailTabPane(MaterialDS mDS, MaterialListGrid Grid){
    	this.materialListGrid = Grid;
    	this.materialDataSource = mDS;
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(mDS);  
        itemViewer.setWidth(450);  
        itemViewer.setMargin(5);  
        itemViewer.setEmptyMessage("Select an item to view its details");
        
        editorLabel = new Label();  
        editorLabel.setWidth100();  
        editorLabel.setHeight100();  
        editorLabel.setAlign(Alignment.CENTER);  
        editorLabel.setContents("Select a record to edit its details");  

        outlineForm = new HLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(5);
        
        editorForm = new DynamicForm();  
        editorForm.setWidth(400);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(mDS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลวัตถุดิบ");
        
        //editorForm
        StaticTextItem mid = new StaticTextItem("mid", "รหัสวัตถุดิบ");
        mid.setRequired(true);
		TextItem mat_name = new TextItem("mat_name", "ชื่อวัตถุดิบ");
		TextAreaItem desc = new TextAreaItem("desc", "คำธิบาย");
		desc.setWidth(300);
		desc.setRowSpan(3);
		
		SelectItem type = new SelectItem("type", "ชนิด");
		FloatItem safety = new FloatItem("safety", "จำนวนสำรองขั้นต่ำ");
		FloatItem remain = new FloatItem("remain", "จำนวนคงเหลือ");
		StaticTextItem unit = new StaticTextItem("unit", "หน่วย");
		
		mat_name.setRequired(true);
		type.setRequired(true);
		safety.setRequired(true);
		remain.setRequired(true);
		mat_name.setHint("*");
		type.setHint("*");
		safety.setHint("*");
		remain.setHint("*");
		
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขข้อมูล", "ท่านต้องการแก้ไขข้อมูล " + (String) editorForm.getValue("mid") + " หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
			            	saveData();
						}
					}
            		
            	});
            }  
        }); 
        
        cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setMargin(10);
        cancelButton.setWidth(150);  
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยกเลิกการแก้ไขข้อมูล", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูล หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							updateDetails();
							selectTab(0); // back to detail tab
						}
					}
            		
            	});
            }  
        });

        saveButton.setDisabled(true);
        cancelButton.setDisabled(true);
        
        mat_name.setWidth(250);
        desc.setWidth(250);
        type.setWidth(250);
        safety.setWidth(250);
        remain.setWidth(250);
        editorForm.setFields(mid, mat_name, desc, type, safety, remain, unit);
        editorForm.setColWidths(150	, 250); 
        VLayout editor_control = new VLayout();
        editor_control.addMembers(saveButton, cancelButton);
        outlineForm.addMembers(editorForm, getEditItemList(), editor_control);
        
        Tab viewTab = new Tab("ข้อมูลวัตถุดิบ");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        HLayout tempLayout = new HLayout();
        tempLayout.addMembers(itemViewer, getViewItemList());
        viewTab.setPane(tempLayout);
        
        Tab editTab = new Tab("แก้ไข");  
        editTab.setIcon("icons/16/icon_edit.png");  
        editTab.setWidth(70);  
        editTab.setPane(outlineForm);
        
        setTabs(viewTab, editTab);  
        
        addTabSelectedHandler(new TabSelectedHandler() {  
            public void onTabSelected(TabSelectedEvent event) {  
                updateDetails();  
            }  
        });
    }
    
    public void clearDetails(Record selectedRecord) {  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData((Record[]) null);  
            fetchViewItemList(null);
        } else {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
                updateTab(1, outlineForm);
                editorForm.editNewRecord();
            } else {  
                updateTab(1, editorLabel);  
            }  
        }  
    }  
  
    public void updateDetails() {  
        Record selectedRecord  = materialListGrid.getSelectedRecord();  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});
            if (selectedRecord != null) fetchViewItemList(selectedRecord.getAttributeAsString("sup_list"));
            else fetchViewItemList("NULL");
        } else {  
            // edit tab : show record editor  
        	if (selectedRecord != null) {
        		saveButton.setDisabled(false);
        		cancelButton.setDisabled(false);
//        		profile.invalidateDisplayValueCache();
        		editorForm.editRecord(selectedRecord);
        		fetchEditItemList(selectedRecord.getAttributeAsString("sup_list"));
        		this.currentMid = selectedRecord.getAttributeAsString("mid");
        	} else {
      	      fetchEditItemList("NULL");
        	}
        }  
    }  
    
    public void updateDetails(Record selectedRecord) {
    	int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
            fetchViewItemList(selectedRecord.getAttributeAsString("sup_list"));
            this.currentMid = selectedRecord.getAttributeAsString("mid");
        } else { 
        	saveButton.setDisabled(false);
        	cancelButton.setDisabled(false);
//        	profile.invalidateDisplayValueCache();
    		editorForm.editRecord(selectedRecord);
    		fetchEditItemList(selectedRecord.getAttributeAsString("sup_list"));
    		this.currentMid = selectedRecord.getAttributeAsString("mid");
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate()) {
    		
//    		String mid = (String) editorForm.getValue("mid");
//			String mat_name = (String) editorForm.getValue("mat_name");
//			String desc = (String) editorForm.getValue("desc");
//	    	String type = (String) editorForm.getValue("type");
//	    	Double safety = (Double) editorForm.getValue("safety");
//	    	Double remain = (Double) editorForm.getValue("remain");
//	    	String unit = (String) editorForm.getValue("unit");
	    	
//	    	User updatedUser = new User(uname, pwd, fname, lname, email, position, title, status);
//	    	securityService.updateUserOnServer(updatedUser, pname, this.user, new AsyncCallback<Boolean>() {
//				@Override
//				public void onFailure(Throwable caught) {
//					SC.warn("Updating permission Fails - please contact administrator");
//				}
//				@Override
//				public void onSuccess(Boolean result) {
//					if (result)
//					{
						//System.out.println("*** Update result => " + result);
						Record updateRecord = MaterialData.createRecord(
								editorForm.getValueAsString("mid"),
				    			editorForm.getValueAsString("mat_name"),
				    			editorForm.getValueAsString("desc"),
				    			editorForm.getValueAsString("type"),
				    			Double.parseDouble(editorForm.getValueAsString("safety")),
				    	    	Double.parseDouble(editorForm.getValueAsString("remain")),
				    	    	editorForm.getValueAsString("unit"),
				    	    	currentChangeSidList
				    			);
						materialDataSource.updateData(updateRecord);
//					} else {
//						SC.warn("Updating user Fails - please contact administrator");
//					}
//				}
//			});
    	} else {
    		SC.warn("ข้อมูลไม่ถูกต้อง");
    	}
    }
    
    private SectionStack getViewItemList(){
    	
    	SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth(530);
    	sectionStack.setHeight(200);
        String title = Canvas.imgHTML("icons/16/vcard_edit.png", 15, 15) + " รายการผู้จำหน่าย";
        SectionStackSection section = new SectionStackSection(title);  
        section.setCanCollapse(false);
        section.setExpanded(true);
        
        viewItemGrid = new SupplierListGrid();
        viewItemGrid.setEmptyMessage("No Item to show.");
        viewItemGrid.setUseAllDataSourceFields(false);
        viewItemGrid.hideFields("sup_phone1", "sup_phone2", "fax", "email", "address");
        viewItemGrid.setWidth100();
        viewItemGrid.setHeight100();
        
        section.setItems(viewItemGrid);
        sectionStack.setSections(section);
        return sectionStack;
    }
    
    private SectionStack getEditItemList(){
    	
    	SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth(530);
    	sectionStack.setHeight(200);
        String title = Canvas.imgHTML("icons/16/vcard_edit.png", 15, 15) + " รายการผู้จำหน่าย";
        SectionStackSection section = new SectionStackSection(title);  
        section.setCanCollapse(false);
        section.setExpanded(true);
        
        editItemGrid = new SupplierListGrid();
        editItemGrid.setEmptyMessage("No Item to show.");
        editItemGrid.setUseAllDataSourceFields(false);
        editItemGrid.hideFields("sup_phone1", "sup_phone2", "fax", "email", "address");
        editItemGrid.setWidth100();
        editItemGrid.setHeight100();

	      
	      ToolStripButton changeButton = new ToolStripButton();  
	      changeButton.setHeight(18);  
	      changeButton.setWidth(120);
	      changeButton.setIcon("icons/16/comment_edit.png");  
	      changeButton.setTitle("แก้ไขรายการผู้จำหน่าย");  
	      changeButton.addClickHandler(new ClickHandler() {  
	          public void onClick(ClickEvent event) {  
	        	  changeFunc.show(currentMid);
	          }  
	      });
	      
	      section.setControls(changeButton);
        section.setItems(editItemGrid);
        sectionStack.setSections(section);
        return sectionStack;
    }
    
    public void fetchViewItemList(String sids){
    	if (sids != null){
    		Criterion ci = new Criterion("sid", OperatorId.REGEXP, sids);
	        viewItemGrid.fetchData(ci);
    	}
    }
   
    public void fetchEditItemList(String sids){
    	if (sids != null){
    		Criterion ci = new Criterion("sid", OperatorId.REGEXP, sids);
    		editItemGrid.fetchData(ci);
    		changeFunc.listId = sids;
    		currentChangeSidList = sids;
    	}
    }
    
    public void onRefresh() {
    	materialDataSource.refreshData();
    	//permissionDataSource.refreshData();
    	materialListGrid.invalidateCache();
//    	profile.invalidateDisplayValueCache();
    }
}
