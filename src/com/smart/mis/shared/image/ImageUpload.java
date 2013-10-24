package com.smart.mis.shared.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.layout.VLayout;

//Test Class
public class ImageUpload {

	static UserImageServiceAsync userImageService = GWT.create(UserImageService.class);
	static String uploadUrlResult = null;
	
  public static VLayout getUploadForm() {

        VLayout layout = new VLayout();
        layout.setSize("100px", "44px");
        
        userImageService.getBlobstoreUploadUrl( new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error connecting to server..");
			}
			@Override
			public void onSuccess(String result) {
				uploadUrlResult = result;
			}
		
        });
        
        if (uploadUrlResult != null) {

	        //final HTMLPane hidden_frame = new HTMLPane();
	        //hidden_frame.setID("hidden_frame");
	        //hidden_frame.setContents("<IFRAME NAME=\"hidden_frame\" style=\"width:0;height:0;border:0\"></IFRAME>");
	        //hidden_frame.setVisibility(Visibility.VISIBLE);
	        
            final DynamicForm uploadForm = new DynamicForm();        
            uploadForm.setSize("54px", "147px");
            uploadForm.setEncoding(Encoding.MULTIPART);
            UploadItem fileItem = new UploadItem("image");
            
        	uploadForm.setAction(uploadUrlResult);
        	uploadForm.setTarget("fileUploadFrame");
        	
	        IButton uploadButton = new IButton("Attachment");
	        uploadButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler()
	        {
	            @Override
	            public void onClick(
	                    com.smartgwt.client.widgets.events.ClickEvent event) {
	                // TODO Auto-generated method stub
	                uploadForm.submitForm();
	            }
	        });
	        
	        uploadForm.setItems(fileItem);
	        
	        layout.setMembers(uploadForm, uploadButton);
        }
        
        return layout;
    }
}
