package com.smart.mis.client.function.security;
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.Record;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;  
import com.smartgwt.client.widgets.tab.TabSet;  
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;  
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;  
import com.smartgwt.client.widgets.viewer.DetailViewer;  

public class PermissionDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm, normalForm, reportForm, adminForm;  
    private Label editorLabel;  
    private PermissionListGrid permissionListGrid; 
    private DataSource permissionDataSource;
    private VLayout outlineForm;
    private IButton saveButton;
    
    public PermissionDetailTabPane(DataSource permissionDS , PermissionListGrid permisssionGrid){
    	this.permissionListGrid = permisssionGrid;
    	this.permissionDataSource = permissionDS;
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(permissionDS);  
        itemViewer.setWidth(400);  
        itemViewer.setMargin(5);  
        itemViewer.setEmptyMessage("Select an item to view its details");
        
        editorLabel = new Label();  
        editorLabel.setWidth100();  
        editorLabel.setHeight100();  
        editorLabel.setAlign(Alignment.CENTER);  
        editorLabel.setContents("Select a record to edit its details");  

        outlineForm = new VLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(10);
        editorForm = new DynamicForm();  
        editorForm.setWidth(650);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(6);  
        editorForm.setCellPadding(5);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(permissionDS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลสิทธิการใช้งาน");

        HLayout inlineForm = new HLayout();
        inlineForm.setWidth(650);
        inlineForm.setHeight(50);
        
        normalForm = new DynamicForm(); 
        normalForm.setAutoFocus(false);
        normalForm.setCellPadding(5);
        normalForm.setMargin(5); 
        normalForm.setNumCols(4); 
        normalForm.setWidth(300); 
        normalForm.setDataSource(permissionDS);  
        normalForm.setUseAllDataSourceFields(false); 
        normalForm.setIsGroup(true);
        normalForm.setGroupTitle("สิทธิการใช้งานทั่วไป");
               
        VLayout subInlineForm = new VLayout();
        subInlineForm.setWidth(170);
        //subInlineForm.setHeight(50);
        
        reportForm = new DynamicForm(); 
        reportForm.setAutoFocus(false);
        reportForm.setCellPadding(5); 
        reportForm.setMargin(5); 
        reportForm.setWidth(170);
        reportForm.setDataSource(permissionDS);  
        reportForm.setUseAllDataSourceFields(false); 
        reportForm.setIsGroup(true);
        reportForm.setGroupTitle("สิทธิเจ้าของกิจการ");
        //reportForm.setAlign(Alignment.LEFT);
        
        adminForm = new DynamicForm(); 
        adminForm.setAutoFocus(false);
        adminForm.setMargin(5); 
        adminForm.setWidth(170);
        adminForm.setCellPadding(5);
        adminForm.setDataSource(permissionDS);  
        adminForm.setUseAllDataSourceFields(false); 
        adminForm.setIsGroup(true);
        adminForm.setGroupTitle("สิทธิผู้ดูและระบบ");
        //adminForm.setAlign(Alignment.LEFT);
        
        //editorForm
        StaticTextItem name = new StaticTextItem("name", "ชื่อ");
        SelectItem role = new SelectItem("role", "ขอบเขตการใช้งาน");
        SelectItem status = new SelectItem("status", "สถานะ");
        
        name.setRequired(true);
        role.setRequired(true);
        
        //normalForm
        final CheckboxItem canSale = new CheckboxItem("cSale");
        final CheckboxItem canProduct = new CheckboxItem("cProd");
        final CheckboxItem canInven = new CheckboxItem("cInv");
        final CheckboxItem canPurchase = new CheckboxItem("cPurc");
        final CheckboxItem canFinance = new CheckboxItem("cFin");
        //reportForm
        final CheckboxItem canReport = new CheckboxItem("cRep");
        //adminForm
        final CheckboxItem canAdmin = new CheckboxItem("cAdm");

        role.addChangeHandler(new ChangeHandler() {  
            public void onChange(ChangeEvent event) {  
                String selectedItem = (String) event.getValue();  
                if (selectedItem.equalsIgnoreCase("Staff")){
                	canReport.setValue(false);
                	canReport.setDisabled(true);
                	canAdmin.setValue(false);
                	canAdmin.setDisabled(true);
                } else if (selectedItem.equalsIgnoreCase("Manager")) {
                	canReport.setDisabled(false);
                	canAdmin.setValue(false);
                	canAdmin.setDisabled(true);
                } else if (selectedItem.equalsIgnoreCase("Administrator")) {
                	canReport.setDisabled(false);
                	canAdmin.setDisabled(false);
                }
            }  
        });
        
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขสิทธิการใช้", "ท่านต้องการแก้ไขสิทธิการใช้งาน" + (String) editorForm.getValue("name") + "หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
			            	saveData();
						}
					}
            		
            	});
            }  
        }); 

        saveButton.setDisabled(true);
        
        editorForm.setFields(name, role, status);
        editorForm.setColWidths(50, 150, 200, 80, 100, 50); 
        normalForm.setFields(canSale,canProduct,canInven,canPurchase, canFinance );
        reportForm.setFields(canReport);
        adminForm.setFields(canAdmin);
        
        subInlineForm.addMembers(reportForm, adminForm);
        inlineForm.addMembers(normalForm, subInlineForm, saveButton);
        outlineForm.addMembers(editorForm, inlineForm);
        //
        
        Tab viewTab = new Tab("ข้อมูลสิทธิการใช้งาน");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        viewTab.setPane(itemViewer);  
  
        Tab editTab = new Tab("แก้ไข");  
        editTab.setIcon("icons/16/icon_edit.png");  
        editTab.setWidth(70);  
        //editTab.setPane(editorForm);
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
        } else {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
                updateTab(1, outlineForm);
                editorForm.editNewRecord();  
                normalForm.editNewRecord();
                reportForm.editNewRecord();
                adminForm.editNewRecord();
            } else {  
                updateTab(1, editorLabel);  
            }  
        }  
    }  
  
    public void updateDetails() {  
        Record selectedRecord  = permissionListGrid.getSelectedRecord();  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
        } else {  
            // edit tab : show record editor  
        	if (selectedRecord != null) {
        		saveButton.setDisabled(false);
        		editorForm.editRecord(selectedRecord);  
                normalForm.editRecord(selectedRecord);  
                String role = selectedRecord.getAttribute("role");
                if (role.equalsIgnoreCase("Staff")){
                	reportForm.getField("cRep").setDisabled(true);
                	adminForm.getField("cAdm").setDisabled(true);
                } else if (role.equalsIgnoreCase("Manager")) {
                	reportForm.getField("cRep").setDisabled(false);
                	adminForm.getField("cAdm").setDisabled(true);
                } else {
                	reportForm.getField("cRep").setDisabled(false);
                	adminForm.getField("cAdm").setDisabled(false);
                }
                reportForm.editRecord(selectedRecord);  
                adminForm.editRecord(selectedRecord);  
        	}
        }  
    }  
    
    public void updateDetails(Record selectedRecord) {
    	int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
        } else { 
        	saveButton.setDisabled(false);
    		editorForm.editRecord(selectedRecord);  
            normalForm.editRecord(selectedRecord);  
            String role = selectedRecord.getAttribute("role");
            if (role.equalsIgnoreCase("Staff")){
            	reportForm.getField("cRep").setDisabled(true);
            	adminForm.getField("cAdm").setDisabled(true);
            } else if (role.equalsIgnoreCase("Manager")) {
            	reportForm.getField("cRep").setDisabled(false);
            	adminForm.getField("cAdm").setDisabled(true);
            } else {
            	reportForm.getField("cRep").setDisabled(false);
            	adminForm.getField("cAdm").setDisabled(false);
            }
            reportForm.editRecord(selectedRecord);  
            adminForm.editRecord(selectedRecord); 
        }
    }
    
    public void saveData(){
   	
    	Record updateRecord = PermissionData.createRecord(
    			(String) editorForm.getValue("name"),
    			(String) editorForm.getValue("role"),
    			(String) editorForm.getValue("status"),
    	    	(Boolean) normalForm.getValue("cSale"),
    	    	(Boolean) normalForm.getValue("cProd"),
    	    	(Boolean) normalForm.getValue("cInv"),
    	    	(Boolean) normalForm.getValue("cPurc"),
    	    	(Boolean) normalForm.getValue("cFin"),
    	    	(Boolean) reportForm.getValue("cRep"),
    	    	(Boolean) adminForm.getValue("cAdm")
    			);
    	
    	permissionDataSource.updateData(updateRecord);
    }
}
