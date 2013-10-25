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
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class ImageUpload {

	UserImageServiceAsync userImageService = GWT.create(UserImageService.class);
	String uploadUrlResult = null;
	final FormPanel uploadForm = new FormPanel();
	Button uploadButton = new Button();
	FileUpload uploadField = new FileUpload();
	String imageUrl = null;
	
  public Canvas getUploadForm() {
	  	
	  	Canvas imageUpload = new Canvas();
	  	imageUpload.setWidth100();
	  	imageUpload.setHeight100();
	  	imageUpload.addChild(getGwtForm());
	  
        return imageUpload;
    }
  
  private Widget getGwtForm() {
      //uploadForm.setStyleName(style);
      uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
      uploadForm.setMethod(FormPanel.METHOD_POST);
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
           imageUrl = event.getResults();
           //System.out.println("imageUrl on client : " + imageUrl);
           startNewBlobstoreSession();
		}
     });
  	
  	HorizontalPanel hPanel = new HorizontalPanel();
  	hPanel.add(uploadField);
  	hPanel.add(uploadButton);
  	
  	uploadForm.add(hPanel);
  	
  	return uploadForm;
  }
  
  private void Upload() {
	  uploadForm.submit();
	  Loading();
  }
  
  private void Loading() {
      uploadButton.setText("Loading...");
      uploadButton.setEnabled(false);
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
		}
      });
  }
  
  public String getImageUrl(){
	  return imageUrl;
  }
}
