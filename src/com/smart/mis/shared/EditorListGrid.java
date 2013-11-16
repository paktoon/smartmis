package com.smart.mis.shared;

import com.smart.mis.shared.security.User;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class EditorListGrid extends ListGrid{

	EditorWindow EditWindow;
	User currentUser;
	
	public EditorListGrid(EditorWindow target, User user) {
		setWidth100();
		setHeight(400);
		setAlternateRecordStyles(true);  
		setShowAllRecords(true);
		setCanResizeFields(false);
		setSelectionType(SelectionStyle.SINGLE);
		setShowRecordComponents(true);          
        setShowRecordComponentsByCell(true);
		this.EditWindow = target;
		this.currentUser = user;
	}
	
//	@Override
//	public Canvas updateRecordComponent(ListGridRecord record, Integer colNum, Canvas component, boolean recordChanged) { 
//		String fieldName = this.getFieldName(colNum);  
//        return getRecordComponent(record, fieldName);  
//	}

	@Override  
    protected Canvas createRecordComponent(ListGridRecord record, Integer colNum) {  
        String fieldName = this.getFieldName(colNum);  
        return getRecordComponent(record, fieldName);
    } 
	
	private Canvas getRecordComponent(final ListGridRecord record, String fieldName) {
		if (fieldName.equalsIgnoreCase("viewAndEditField")) {  
            HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);  
            ImgButton editImg = new ImgButton();  
            editImg.setShowDown(false);  
            editImg.setShowRollOver(false);  
            editImg.setLayoutAlign(Alignment.CENTER);  
            editImg.setSrc("icons/16/comment_edit.png");  
            editImg.setPrompt("แก้ไขรายการ");  
            editImg.setHeight(16);  
            editImg.setWidth(16);  
            editImg.addClickHandler(new ClickHandler() {  
                public void onClick(ClickEvent event) {  
                    //SC.say("Edit Icon Clicked for : " + record.getAttribute("quote_id"));
                    EditWindow.show(record, true, currentUser, 1);
                }  
            });  

            ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER);  
            viewImg.setSrc("icons/16/icon_view.png");  
            viewImg.setPrompt("เรียกดูรายละเอียด");  
            viewImg.setHeight(16);  
            viewImg.setWidth(16);  
            viewImg.addClickHandler(new ClickHandler() {  
                public void onClick(ClickEvent event) {  
                    //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                    EditWindow.show(record, false, currentUser, 1);
                }  
            });  

            if (record.getAttributeAsString("status").equalsIgnoreCase("3_approved")) {
            	recordCanvas.addMember(viewImg); 
            }
            recordCanvas.addMember(editImg);   
            return recordCanvas;  
        } else if (fieldName.equals("approveField")) { 
        	HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);
            
        	ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER); 
            if (record.getAttributeAsString("status").equalsIgnoreCase("2_waiting_for_approved")) {
	            viewImg.setSrc("icons/16/process-warning-icon-16.png");  
	            viewImg.setPrompt("มีคำร้องขออนุมัติ");  
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("3_approved")) {
	            viewImg.setSrc("icons/16/approved.png");  
	            viewImg.setPrompt("เรียกดูรายละเอียด");  
            } else {
            	viewImg.setSrc("icons/16/icon_view.png");  
	            viewImg.setPrompt("เรียกดูรายละเอียด"); 
            }
            viewImg.setHeight(16);  
            viewImg.setWidth(16);  
            viewImg.addClickHandler(new ClickHandler() {  
                public void onClick(ClickEvent event) {  
                    //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                    EditWindow.show(record, false, currentUser, 2);
                }  
            });  

            recordCanvas.addMember(viewImg); 
            return recordCanvas;  
        } else if (fieldName.equalsIgnoreCase("createSaleOrderField")) { 
        	HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);
            
        	ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER); 
	        viewImg.setSrc("icons/16/approved.png");  
	        viewImg.setPrompt("สร้างรายการขาย");
            viewImg.setHeight(16);  
            viewImg.setWidth(16);  
            viewImg.addClickHandler(new ClickHandler() {  
                public void onClick(ClickEvent event) {  
                    //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                    EditWindow.show(record, false, currentUser, 3);
                }  
            });  

            recordCanvas.addMember(viewImg); 
            return recordCanvas;  
        } else if (fieldName.equalsIgnoreCase("viewSaleOrderField")) { 
        	HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);
            
        	ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER); 
            viewImg.setHeight(16);  
            viewImg.setWidth(16);  
            
            if (record.getAttributeAsString("status").equalsIgnoreCase("1_waiting_for_production")) {
            	viewImg.setSrc("icons/16/process-warning-icon-16.png");  
    	        viewImg.setPrompt("จัดการรายการ");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("2_production_in_progress")) {
            	viewImg.setSrc("icons/16/process-info-icon.png");  
    	        viewImg.setPrompt("จัดการรายการ");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("3_production_completed")) {
            	viewImg.setSrc("icons/16/process-accept-icon-16.png");  
    	        viewImg.setPrompt("จัดการรายการ");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("4_on_delivery")) {
            	viewImg.setSrc("icons/16/truck-icon-16.png");  
    	        viewImg.setPrompt("จัดการรายการ");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("5_delivery_completed")) {
            	viewImg.setSrc("icons/16/success-icon-16.png");  
    	        viewImg.setPrompt("จัดการรายการ");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else {
		        viewImg.setSrc("icons/16/faq-icon-16.png");  
		        viewImg.setPrompt("สถานะรายการขายไม่ถูกต้อง");
	            viewImg.addClickHandler(new ClickHandler() {  
	                public void onClick(ClickEvent event) {  
	                    SC.say("สถานะรายการขาย : " + record.getAttributeAsString("status"));
	                }  
	            });  
            }

            recordCanvas.addMember(viewImg); 
            return recordCanvas;  
        } else if (fieldName.equalsIgnoreCase("viewInvoiceField")) { 
        	HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);
            
        	ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER); 
            
            if (record.getAttributeAsString("status").equalsIgnoreCase("3_over_due")) {
            	viewImg.setSrc("icons/16/danger-icon.png");  
    	        viewImg.setPrompt("เรียกดูใบชำระหนี้");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else {
            	viewImg.setSrc("icons/16/print.png");  
    	        viewImg.setPrompt("เรียกดูหรือพิมพ์ใบแจ้งหนี้");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            }
            viewImg.setHeight(16);  
            viewImg.setWidth(16);
            
            recordCanvas.addMember(viewImg); 
            return recordCanvas;
            
        } else if (fieldName.equalsIgnoreCase("viewDeliveryField")) { 
        	HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);
            
        	ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER); 
            
            if (record.getAttributeAsString("status").equalsIgnoreCase("1_on_delivery")) {
            	viewImg.setSrc("icons/16/save.png");  
    	        viewImg.setPrompt("บันทึกการนำส่งสินค้า");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("2_delivery_completed")) {
            	viewImg.setSrc("icons/16/icon_view.png");  
    	        viewImg.setPrompt("เรียกดูรายการนำส่งสินค้า");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else {
		        viewImg.setSrc("icons/16/faq-icon-16.png");  
		        viewImg.setPrompt("สถานะรายการนำส่งสินค้าไม่ถูกต้อง");
	            viewImg.addClickHandler(new ClickHandler() {  
	                public void onClick(ClickEvent event) {  
	                    SC.say("สถานะรายการนำส่งสินค้า : " + record.getAttributeAsString("status"));
	                }  
	            });  
            }
            viewImg.setHeight(16);  
            viewImg.setWidth(16);

            recordCanvas.addMember(viewImg); 
            return recordCanvas;
            
        } else if (fieldName.equalsIgnoreCase("createProductionOrderField")) { 
        	HLayout recordCanvas = new HLayout(3);  
            recordCanvas.setHeight(22);  
            recordCanvas.setAlign(Alignment.CENTER);
            
        	ImgButton viewImg = new ImgButton();  
            viewImg.setShowDown(false);  
            viewImg.setShowRollOver(false);  
            viewImg.setAlign(Alignment.CENTER); 
            
            if (record.getAttributeAsString("status").equalsIgnoreCase("3_approved")) {
            	viewImg.setSrc("icons/16/process-info-icon.png");  
    	        viewImg.setPrompt("ออกคำสั่งผลิตสินค้า");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 3);
                    }  
                });
            } else if (record.getAttributeAsString("status").equalsIgnoreCase("5_created_order")) {
            	viewImg.setSrc("icons/16/setting-icon-16.png");  
    	        viewImg.setPrompt("เรียกดูแผนการผลิต");
                viewImg.addClickHandler(new ClickHandler() {  
                    public void onClick(ClickEvent event) {  
                        //SC.say("View Icon Clicked for : " + record.getAttribute("quote_id"));
                        EditWindow.show(record, false, currentUser, 1);
                    }  
                });
            } else {
		        viewImg.setSrc("icons/16/faq-icon-16.png");  
		        viewImg.setPrompt("สถานะแผนการผลิตไม่ถูกต้อง");
	            viewImg.addClickHandler(new ClickHandler() {  
	                public void onClick(ClickEvent event) {  
	                    SC.say("สถานะแผนการผลิตไม่ถูกต้อง : " + record.getAttributeAsString("status"));
	                }  
	            });  
            }
            viewImg.setHeight(16);  
            viewImg.setWidth(16);

            recordCanvas.addMember(viewImg); 
            return recordCanvas;
//        } else if (fieldName.equals("approveField")) {  
//            IButton button = new IButton();  
//            button.setHeight(18);  
//            button.setWidth(65);                      
//            button.setIcon("flags/16/" + record.getAttribute("countryCode") + ".png");  
//            button.setTitle("Info");  
//            button.addClickHandler(new ClickHandler() {  
//                public void onClick(ClickEvent event) {  
//                    SC.say(record.getAttribute("countryName") + " info button clicked.");  
//                }  
//            });  
//            return button;  
        } else {  
            return null;  
        }  
	}
}
