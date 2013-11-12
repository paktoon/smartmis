package com.smart.mis.client.function.production.product;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.smart.mis.client.function.production.process.ProcessData;
import com.smart.mis.client.function.production.process.ProcessListDS;
import com.smart.mis.client.function.production.process.ProcessOutline;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ImageTabPane;
import com.smart.mis.shared.image.ImageUpload;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.Record;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.Window;
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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;  
import com.smartgwt.client.widgets.tab.TabSet;  
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;  
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;  
import com.smartgwt.client.widgets.viewer.DetailViewer;  

public class ProductDetailTabPane extends ImageTabPane {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm, sizeForm;
    
    private Label editorLabel;
    private ProductListGrid ListGrid; 
    private ProductDS DataSource;
    //private ProcessListDS psDataSource;
    private ProcessOutline psOutline;
    private HLayout outlineForm;
    private IButton saveButton, cancelButton;
 
    private Record currentRecord;
    
    private SelectItem size;
    private FloatItem width, length, height, diameter, thickness;
    
    public ProductDetailTabPane(ProductDS DS , ProductListGrid Grid){
    	setHeight("70%");
    	this.ListGrid = Grid;
    	this.DataSource = DS;
    	psOutline = new ProcessOutline(ProcessListDS.getInstance(null));
    	viewProductImage = new Img(this.currentViewImgUrl);
    	editProductImage = new Img(this.currentEditImgUrl);
    	currentRecord = new Record();
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(DS);  
        itemViewer.setWidth(500);  
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
        editorForm.setWidth(450);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(DS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลสินค้า");
        
        //editorForm
        StaticTextItem pid = new StaticTextItem("pid", "รหัสสินค้า");
        pid.setRequired(true);
		TextItem name = new TextItem("name", "ชื่อสินค้าภาษาอังกฤษ");
		TextItem name_th = new TextItem("name_th", "ชื่อสินค้าภาษาไทย");
		FloatItem weight = new FloatItem("weight", "น้ำหนัก");
		FloatItem price = new FloatItem("price", "ราคา");
		final SelectItem type = new SelectItem("type", "ประเภท");
		
		type.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String selectType = type.getValueAsString();
				showSizeElement(selectType);
			}
        });
		
		name.setRequired(true);
		name_th.setRequired(true);
		weight.setRequired(true);
		price.setRequired(true);
		type.setRequired(true);
		
		name.setHint("*");
		name_th.setHint("*");
		weight.setHint("กรัม *");
		price.setHint("บาท *");
		type.setHint("*");
		
		sizeForm = new DynamicForm();  
		sizeForm.setWidth(450);  
		sizeForm.setMargin(5);  
		sizeForm.setNumCols(2);  
		sizeForm.setAutoFocus(false);  
		sizeForm.setDataSource(DS);  
		sizeForm.setUseAllDataSourceFields(false); 
		sizeForm.setIsGroup(true);
		sizeForm.setGroupTitle("ลายละเอียดสินค้า");
		
        //editorForm
		size = new SelectItem("size", "ขนาดสินค้า");
		size.setValueMap("5.0","5.5","6.0","6.5","7.0","7.5","8.0","8.5","9.0");
		width = new FloatItem("width", "ความกว้าง");
		length = new FloatItem("length", "ความยาว");
		height = new FloatItem("height", "ความสูง");
		diameter = new FloatItem("diameter", "เส้นผ่าศูนย์กลาง");
		thickness = new FloatItem("thickness", "ความหนา");
		
		size.setHint("[ขนาดมาตรฐาน USA]");
		width.setHint("ซม.");
		length.setHint("ซม.");
		height.setHint("มม.");
		diameter.setHint("มม.");
		thickness.setHint("มม.");
		
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);
        saveButton.setWidth(150);   
        saveButton.setMargin(5); 
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขข้อมูลสินค้า", "ท่านต้องการแก้ไขข้อมูลสินค้า " + (String) editorForm.getValue("pid") + " หรือไม่ ?" , new BooleanCallback() {
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
        cancelButton.setWidth(150);
        cancelButton.setMargin(5); 
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยกเลิกการแก้ไขข้อมูลสินค้า", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูลสินค้า หรือไม่ ?" , new BooleanCallback() {
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
        
        name.setWidth(250);
        name_th.setWidth(250);
        weight.setWidth(250);
        price.setWidth(250);
        type.setWidth(250);
        
        VLayout leftLayout = new VLayout();
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(pid, name, name_th, weight, price, type);
        editorForm.setColWidths(200	, 300);
        sizeForm.setFields(size,width,length,height,diameter,thickness);
        leftLayout.addMembers(editorForm, sizeForm);
        
        VLayout editor_control = new VLayout();
        editor_control.addMembers(saveButton, cancelButton);
        VLayout rightLayout = new VLayout();
        rightLayout.setMembersMargin(5);
        rightLayout.addMembers(getImageWindow(editProductImage, 1), editor_control);
        
        outlineForm.addMembers(leftLayout , rightLayout);
        
        HLayout tempViewer = new HLayout();
        tempViewer.setWidth100();
        tempViewer.setHeight100();
        tempViewer.setMargin(5);
        tempViewer.addMembers(itemViewer, getImageWindow(viewProductImage, 0));
        Tab viewTab = new Tab("ข้อมูลสินค้า");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        viewTab.setPane(tempViewer);
        
        Tab editTab = new Tab("แก้ไข");  
        editTab.setIcon("icons/16/icon_edit.png");  
        editTab.setWidth(70);  
        editTab.setPane(outlineForm);
        
        Tab processTab = new Tab("ขั้นตอนการผลิต");  
        processTab.setIcon("icons/16/application_form.png");  
        processTab.setWidth(70);  
        processTab.setPane(psOutline);
        
        setTabs(viewTab, editTab, processTab);  
        
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
        } else if (selectedTab == 1) {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
                updateTab(1, outlineForm);
                editorForm.editNewRecord();
                sizeForm.editNewRecord();
            } else {  
                updateTab(1, editorLabel);  
            }  
        } else if (selectedTab == 2) {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
            	psOutline = new ProcessOutline(ProcessListDS.getInstance(null));
                updateTab(2, psOutline);
            } else {  
                updateTab(2, psOutline);  
            }  
        }
    }  
  
    public void updateDetails() {  
        Record selectedRecord  = ListGrid.getSelectedRecord();  
        int selectedTab = getSelectedTabNumber();  
        
        if (selectedRecord != null) {
        	this.currentRecord = selectedRecord;
        	String imgUrl = selectedRecord.getAttributeAsString("imgUrl");
        	if (!imgUrl.isEmpty())
        	{
        		this.currentViewImgUrl = imgUrl;
        		this.currentEditImgUrl = imgUrl;
        	} else {
        		resetUrl();
        	}
        	changeViewImg(this.currentViewImgUrl);
            changeEditImg(this.currentEditImgUrl);
        }
        
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});
        } else if (selectedTab == 1) {   
            // edit tab : show record editor  
        	if (selectedRecord != null) {
        		saveButton.setDisabled(false);
        		cancelButton.setDisabled(false);
        		editorForm.editRecord(selectedRecord);
        		showSizeElement(selectedRecord.getAttributeAsString("type"));
        		sizeForm.editRecord(selectedRecord);
        	}
        } else if (selectedTab == 2) {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
                String pid = selectedRecord.getAttributeAsString("pid");
            	psOutline = new ProcessOutline(ProcessListDS.getInstance(pid));
                updateTab(2, psOutline);
            } else {  
                updateTab(2, psOutline); 
            }  
        }
    }  
    
    public void updateDetails(Record selectedRecord) {
    	int selectedTab = getSelectedTabNumber();  
    	
    	if (selectedRecord != null) {
        	this.currentRecord = selectedRecord;
        	String imgUrl = selectedRecord.getAttributeAsString("imgUrl");
        	if (!imgUrl.isEmpty())
        	{
        		this.currentViewImgUrl = imgUrl;
        		this.currentEditImgUrl = imgUrl;
        	} else {
        		resetUrl();
        	}
        	changeViewImg(this.currentViewImgUrl);
            changeEditImg(this.currentEditImgUrl);
        }
    	
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});
        } else if (selectedTab == 1) {   
        		saveButton.setDisabled(false);
        		cancelButton.setDisabled(false);
        		editorForm.editRecord(selectedRecord);
        		showSizeElement(selectedRecord.getAttributeAsString("type"));
        		sizeForm.editRecord(selectedRecord);
        } else if (selectedTab == 2) { 
                String pid = selectedRecord.getAttributeAsString("pid");
            	psOutline = new ProcessOutline(ProcessListDS.getInstance(pid));
                updateTab(2, psOutline);
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate() && sizeForm.validate()) {
    		
    		String pid = editorForm.getValueAsString("pid");
			String name = editorForm.getValueAsString("name");
	    	String name_th = editorForm.getValueAsString("name_th");
	    	Double weight = Double.parseDouble(editorForm.getValueAsString("weight"));
	    	Double price = Double.parseDouble(editorForm.getValueAsString("price"));
	    	String type = editorForm.getValueAsString("type");
	    	
//	    	Integer inStock = currentRecord.getAttributeAsInt("inStock");
//	    	Integer reserved = currentRecord.getAttributeAsInt("reserved");
	    	Double size = null;
	    	Double width = null;
	    	Double length = null;
	    	Double height = null;
	    	Double diameter = null;
	    	Double thickness = null;
	    	
	    	if (type.equalsIgnoreCase("ring") || type.equalsIgnoreCase("toe ring")) {
	    		size = Double.parseDouble(sizeForm.getValueAsString("size"));
	    		thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
	    	} else if (type.equalsIgnoreCase("necklace") || type.equalsIgnoreCase("bangle")) {
	    	    width = Double.parseDouble(sizeForm.getValueAsString("width"));
	    	    length = Double.parseDouble(sizeForm.getValueAsString("length"));
	    	    thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
	    	} else if (type.equalsIgnoreCase("earring") || type.equalsIgnoreCase("pendant") || type.equalsIgnoreCase("anklet") || type.equalsIgnoreCase("bracelet")) {
	    		height = Double.parseDouble(sizeForm.getValueAsString("height"));
	    		diameter = Double.parseDouble(sizeForm.getValueAsString("diameter"));
	    		thickness = Double.parseDouble(sizeForm.getValueAsString("thickness"));
	    	}
	    	
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
//						//System.out.println("*** Update result => " + result);
	    				//System.out.println(this.currentEditImgUrl);
						Record updateRecord = ProductData.createUpdatedRecord(
								editorForm.getValueAsString("pid"),
								editorForm.getValueAsString("name"),
								editorForm.getValueAsString("name_th"),
								Double.parseDouble(editorForm.getValueAsString("weight")),
								Double.parseDouble(editorForm.getValueAsString("price")),
								editorForm.getValueAsString("type"),
								currentEditImgUrl,
								size,
								width,
								length,
								height,
								diameter,
								thickness
				    			);
						DataSource.updateData(updateRecord);
						this.currentViewImgUrl = this.currentEditImgUrl;
						changeEditImg();
						changeViewImg();
						SC.say("แก้ไขข้อมูลสินค้าเรียบร้อยแล้ว");
//					} else {
//						SC.warn("Updating user Fails - please contact administrator");
//					}
//				}
//			});
    	} else {
    		SC.warn("ข้อมูลสินค้าไม่ถูกต้อง");
    	}
    }
    
    public void onRefresh() {
    	DataSource.refreshData();
    	//permissionDataSource.refreshData();
    	ListGrid.invalidateCache();
//    	profile.invalidateDisplayValueCache();
    }
    
    public Window getImageWindow(Img productImage, int type){
        Window imageFrame = new Window();
        if (type == 1) {
        	imageFrame.setTitle("คลิกที่ภาพเพื่อแก้ไข");
        } else imageFrame.setTitle("ภาพสินค้า");
        imageFrame.setCanDrag(false);
        imageFrame.setCanDragResize(false);
        imageFrame.setHeaderControls(HeaderControls.HEADER_LABEL);
        imageFrame.setWidth(200);
        imageFrame.setHeight(200);
        
        productImage.setWidth100();
        productImage.setHeight100();
        productImage.setImageType(ImageStyle.STRETCH);
        
        if (type == 1) {
        	ImageUpload upload = new ImageUpload(this, productImage);
        	final Window popup = upload.getUploadWindow("อัพโหลดภาพสินค้า");
	        productImage.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
	        			popup.show();
				}});
        }
        
        imageFrame.addItem(productImage);
        
        return imageFrame;
    }
    
    public void showSizeElement(String type){
    	if (type.equalsIgnoreCase("ring") || type.equalsIgnoreCase("toe ring")) {
    	    size.show();
    	    width.hide();
    	    length.hide();
    	    height.hide();
    	    diameter.hide();
    	    thickness.show();
    	    
    	    size.setRequired(true);
    	    width.setRequired(false);
    	    length.setRequired(false);
    	    height.setRequired(false);
    	    diameter.setRequired(false);
    	    thickness.setRequired(true);
    	} else if (type.equalsIgnoreCase("necklace") || type.equalsIgnoreCase("bangle")) {
    		size.hide();
    	    width.show();
    	    length.show();
    	    height.hide();
    	    diameter.hide();
    	    thickness.show();
    	    
    	    size.setRequired(false);
    	    width.setRequired(true);
    	    length.setRequired(true);
    	    height.setRequired(false);
    	    diameter.setRequired(false);
    	    thickness.setRequired(true);
    	} else if (type.equalsIgnoreCase("earring") || type.equalsIgnoreCase("pendant") || type.equalsIgnoreCase("anklet") || type.equalsIgnoreCase("bracelet")) {
    		size.hide();
    	    width.hide();
    	    length.hide();
    	    height.show();
    	    diameter.show();
    	    thickness.show();
    	    
    	    size.setRequired(false);
    	    width.setRequired(false);
    	    length.setRequired(false);
    	    height.setRequired(true);
    	    diameter.setRequired(true);
    	    thickness.setRequired(true);
    	}
    }
}
