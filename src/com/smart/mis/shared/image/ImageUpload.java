package com.smart.mis.shared.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smart.mis.shared.ImageTabPane;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

public class ImageUpload {

	ImageTabPane main;
	Img target;
	
	UserImageServiceAsync userImageService = GWT.create(UserImageService.class);
	String uploadUrlResult = null;
	final FormPanel uploadForm = new FormPanel();
	Button uploadButton = new Button();
	Button cancelButton = new Button();
	FileUpload uploadField = new FileUpload();
	
	public ImageUpload(ImageTabPane imageTabPane, Img target) {
		this.main = imageTabPane;
		this.target = target;
	}
	
	public Window getUploadWindow(String title) {
		Window popup = new Window();
		
    	popup.setTitle(title);
    	
    	popup.setShowMinimizeButton(false);
    	popup.setIsModal(true);
    	popup.setShowModalMask(true);
    	popup.setCanDragResize(false);
    	popup.setCanDragReposition(false);
    	popup.centerInPage();
    	popup.setWidth(400);
    	popup.setHeight(150);
    	
	  	Canvas imageUpload = new Canvas();
	  	imageUpload.setWidth100();
	  	imageUpload.setHeight(100);
	  	//imageUpload.setAlign(Alignment.CENTER);
	  	imageUpload.setMargin(10);
	  	imageUpload.addChild(getGwtForm(popup));
	  	
    	popup.addItem(imageUpload);
    	
        return popup;
    }
  
  private Widget getGwtForm(final Window popup) {
	  
      //uploadForm.setStyleName(style);
      uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
      uploadForm.setMethod(FormPanel.METHOD_POST);
      uploadForm.setHeight("100px");
      uploadField.setName("image");
      
	Loading();
  	startNewBlobstoreSession();

  	uploadButton.addClickHandler(new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			Upload();
		}});
  	
    uploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
   	 
		@Override
		public void onSubmitComplete(SubmitCompleteEvent event) {
			//uploadForm.reset();
           // This is what gets the result back - the content-type *must* be
           // text-html
           String imageUrl = event.getResults();
           //System.out.println("imageUrl on client : " + imageUrl);
           //startNewBlobstoreSession();
           if (imageUrl != null) { 
        	   imageUrl = convertToComplete(imageUrl);
        	   SC.say("การอัพโหลดภาพเสร็จสมบูรณ์...");
        	   System.out.println(imageUrl);
        	   main.currentEditImgUrl = imageUrl;
        	   target.setSrc(main.currentEditImgUrl);
           }
           popup.hide();
           uploadForm.reset();
           startNewBlobstoreSession();
		}
     });
  	
    cancelButton.addClickHandler(new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			popup.hide();
		}});
    
    VerticalPanel vPanel = new VerticalPanel();
  	HorizontalPanel hPanel = new HorizontalPanel();
  	
  	hPanel.add(uploadButton);
  	hPanel.add(cancelButton);
  	
  	vPanel.add(uploadField);
  	vPanel.add(hPanel);
  	
  	uploadForm.add(vPanel);
  	
  	return uploadForm;
  }
  
  private void Upload() {
	  uploadForm.submit();
	  Loading();
  }
  
  private void Loading() {
      uploadButton.setText("Loading...");
      uploadButton.setEnabled(false);
      cancelButton.setText("Cancel");
      cancelButton.setEnabled(false);
  }
  
  private void startNewBlobstoreSession() {
      userImageService.getBlobstoreUploadUrl(new AsyncCallback() {

          @Override
          public void onFailure(Throwable caught) {
        	  SC.warn("Error connecting to server..");
          }

		@Override
		public void onSuccess(Object result) {
			//System.out.println("startNewBlobstoreSession Result : " + (String) result);
			uploadForm.setAction((String) result);
            uploadButton.setText("Upload");
            uploadButton.setEnabled(true);
            cancelButton.setEnabled(true);
		}
      });
  }
  
  public String convertToComplete(String imageUrl){
	  	imageUrl = imageUrl.replaceAll("http://0.0.0.0:[0-9]*/", GWT.getHostPageBaseURL());
		return imageUrl;
  }
}
