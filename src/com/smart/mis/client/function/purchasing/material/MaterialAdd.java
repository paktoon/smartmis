package com.smart.mis.client.function.purchasing.material;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.validation.client.constraints.NotNullValidator;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.StringCountValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class MaterialAdd {
	
    private final DataSource dataSource;
    private final MaterialListGrid listGrid;
    private final MaterialDetailTabPane tabPane;
    final ListGrid editItemGrid;
    //private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
    private String user;
    
	public MaterialAdd(DataSource DS, MaterialListGrid ListGrid, MaterialDetailTabPane TabPane, String user){
		this.dataSource = DS;
    	this.listGrid = ListGrid;
    	this.tabPane = TabPane;
    	this.user = user;
    	
    	editItemGrid = new ListGrid();
        editItemGrid.setEmptyMessage("No Item to show.");
        editItemGrid.setWidth(240);
        editItemGrid.setHeight(224);
        editItemGrid.setCanAcceptDroppedRecords(true);  
        editItemGrid.setCanRemoveRecords(true);  
        editItemGrid.setAutoFetchData(false);  
        editItemGrid.setPreventDuplicates(true);
        editItemGrid.setUseAllDataSourceFields(false);
        editItemGrid.setDataSource(SupplierDS.getCustomInstance(null));
        ListGridField[] defaultField = new ListGridField[] {new ListGridField("sid", 80), new ListGridField("sup_name")};
       	editItemGrid.setDefaultFields(defaultField);
	}
	
	public void show(){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มวัตถุดิบ");
		//winModel.setAutoSize(true);	
		winModel.setWidth(570);
		winModel.setHeight(315);
		winModel.setHeaderIcon("[SKIN]actions/add.png");
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
		
		VLayout outlineForm = new VLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(10);
        
        final DynamicForm editorForm = new DynamicForm();
        
        editorForm.setWidth(520);  
        //editorForm.setHeight(300);
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(this.dataSource);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลวัตถุดิบ");
        
      //editorForm
		TextItem mat_name = new TextItem("mat_name", "ชื่อวัตถุดิบ");
		FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("ชื่อวัตถุดิบต้องไม่ซ้ำ");
        mat_name.setIcons(icon);
        mat_name.setRequired(true);
        mat_name.setHint("*");
        mat_name.setDefaultValue("[ชื่อวัตถุดิบ]");
        
		TextAreaItem desc = new TextAreaItem("desc", "คำธิบาย");
		desc.setWidth(300);
		desc.setRowSpan(3);
		
		SelectItem type = new SelectItem("type", "ชนิด");
		//FloatItem safety = new FloatItem("safety", "จำนวนสำรองขั้นต่ำ");
		//FloatItem remain = new FloatItem("remain", "จำนวนคงเหลือ");
		TextItem unit = new TextItem("unit", "หน่วย");
		
		type.setRequired(true);
		//safety.setRequired(true);
		//remain.setRequired(true);
		type.setHint("*");
		//Validate number field
		//safety.setHint("* มากกว่าหรือเท่ากับ 0");
		//remain.setHint("* มากกว่าหรือเท่ากับ 0");
		IntegerRangeValidator integerRangeValidator = new IntegerRangeValidator();  
        integerRangeValidator.setMin(0);
        //safety.setValidators(integerRangeValidator);
        //remain.setValidators(integerRangeValidator);
        
		CustomValidator cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				return !(value == null || ((String) value).length() == 0);
			}
		};
        mat_name.setValidators(cv);
        unit.setValidators(cv);
        
		type.setRequired(true);
		type.setDefaultValue("แร่เงิน");
		
        IButton saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	            	
            	if (!editorForm.validate()) {
            		SC.warn("ข้อมูลไม่ถูกต้อง");
            		return;
            	}
            		
            	SC.confirm("ยืนยันการเพิ่มข้อมูล", "ท่านต้องการเพิ่มวัตถุดิบ " + (String) editorForm.getValue("mat_name") + " หรือไม่ ?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {	
//				    		String cid = (String) editorForm.getValue("cid");
//							String cus_name = (String) editorForm.getValue("cus_name");
//							String cus_phone = (String) editorForm.getValue("cus_phone");
//					    	String contact_name = (String) editorForm.getValue("contact_name");
//					    	String contact_phone = (String) editorForm.getValue("contact_phone");
//					    	String contact_email = (String) editorForm.getValue("contact_email");
//					    	String address = (String) editorForm.getValue("address");
//					    	String type = (String) editorForm.getValue("cus_type");
//					    	
//					    	User createdUser = new User(uname, pwd, fname, lname, email, position, title, status);
//					    	securityService.createUserOnServer(createdUser, pname, user, new AsyncCallback<String>() {
//								@Override
//								public void onFailure(Throwable caught) {
//									SC.warn("Updating permission Fails - please contact administrator");
//								}
//								@Override
//								public void onSuccess(String result) {
//									if (result != null)
//									{
										Record newRecord = MaterialData.createRecord(
												"MA70" + Math.round((Math.random() * 100)),
								    			editorForm.getValueAsString("mat_name"),
								    			editorForm.getValueAsString("desc"),
								    			editorForm.getValueAsString("type"),
								    			0.0,
								    			0.0,
								    			//Double.parseDouble(editorForm.getValueAsString("safety")),
								    	    	//Double.parseDouble(editorForm.getValueAsString("remain")),
								    	    	editorForm.getValueAsString("unit")
								    	    	//,
								    	    	//getAttributeList(editItemGrid, "sid")
								    			);
										
										dataSource.addData(newRecord, new DSCallback() {

											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การเพิ่มข้อมูลวัตถุดิบล้มเหลว มีชื่อนี้อยู่ในระบบแล้ว");
													} else { 
														SC.warn("เพิ่มข้อมูลวัตถุดิบเรียบร้อยแล้ว");
														winModel.destroy();
														listGrid.fetchData();
														listGrid.selectSingleRecord(dsResponse.getData()[0]);
														tabPane.updateDetails(dsResponse.getData()[0]);
													}
											}
//								    	});
//									} else {
//										SC.warn("Updating customer Fails - please contact administrator");
//									}
//								}
							});
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

        mat_name.setWidth(250);
        desc.setWidth(250);
        type.setWidth(250);
        //safety.setWidth(250);
        //remain.setWidth(250);
        //editorForm.setFields(mat_name, desc, type, safety, remain, unit);
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(mat_name, desc, type, unit);
        editorForm.setColWidths(150	, 250);
    	
    	HLayout temp = new HLayout();
    	temp.addMembers(saveButton, cancelButton);
    	temp.setMargin(3);
    	temp.setAlign(Alignment.CENTER);
        //outlineForm.addMembers(editorForm, getEditItemList(), temp);
        outlineForm.addMembers(editorForm, temp);
        winModel.addItem(outlineForm);
        winModel.show();
	}
	
	private String getAttributeList(ListGrid grid, String fieldName){
		String list = "";
		boolean isStart = true;
		for (ListGridRecord record : grid.getRecords()) {
			if (isStart) {
				list += record.getAttributeAsString(fieldName);
				isStart = false;
			} else {
				list += "|" + record.getAttributeAsString(fieldName);
			}
		}
		return list;
	}
	
	private HStack getEditItemList(){
    	//Grid
   	 ListGridField[] defaultField = new ListGridField[] {new ListGridField("sid", 80), new ListGridField("sup_name")};
   	 final ListGrid selectItemGrid = new ListGrid();
   	selectItemGrid.setEmptyMessage("No Item to show.");
   	selectItemGrid.setWidth(240);
   	selectItemGrid.setHeight(224);
   	selectItemGrid.setCanDragRecordsOut(true);
   	selectItemGrid.setAutoFetchData(false);
   	selectItemGrid.setUseAllDataSourceFields(false);
   	selectItemGrid.setDataSource(SupplierDS.getInstance());
   	selectItemGrid.setDragDataAction(DragDataAction.COPY); 
   	selectItemGrid.setDefaultFields(defaultField);
       
   	HStack hStack = new HStack(10);  
       hStack.setHeight(160);  
       
       VStack vStack = new VStack(); 
       Label topLabel = new Label("ผู้จำหน่ายที่มีในระบบ");
       topLabel.setHeight(30);
       vStack.addMember(topLabel);
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
       Label topLabel2 = new Label("ผู้จำหน่ายที่เลือก");
       topLabel2.setHeight(30);
       vStack2.addMember(topLabel2);
       vStack2.addMember(editItemGrid);
       
       hStack.addMember(vStack2);
       
       editItemGrid.fetchData();
		selectItemGrid.fetchData();
		
       return hStack;
	}
}
