package com.smart.mis.shared.image;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserImageServiceAsync {
	public void getBlobstoreUploadUrl(AsyncCallback callback);
}