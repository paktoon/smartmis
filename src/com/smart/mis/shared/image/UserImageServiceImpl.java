package com.smart.mis.shared.image;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserImageServiceImpl extends RemoteServiceServlet implements UserImageService {
 
    @Override
    public String getBlobstoreUploadUrl() {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return blobstoreService.createUploadUrl("/upload");
    }
 
}
