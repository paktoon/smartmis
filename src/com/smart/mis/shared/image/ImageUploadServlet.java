package com.smart.mis.shared.image;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.smart.mis.datastore.Util;

public class ImageUploadServlet extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        BlobKey blobKey = blobs.get("image").get(0);

        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
 
            ImagesService imagesService = ImagesServiceFactory
                    .getImagesService();
 
            // Get the image serving URL
            String imageUrl = imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKey));
 
            // Low-level entities
            Entity uploadedImage = new Entity("UploadedImage");
            uploadedImage.setProperty("blobKey", blobKey);
            uploadedImage.setProperty("created", new Date());
            uploadedImage.setUnindexedProperty("url",
                    imageUrl);
 
            Util.persistEntity(uploadedImage);
            
            res.sendRedirect("/upload?imageUrl=" + imageUrl);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
 
        String imageUrl = req.getParameter("imageUrl");
        resp.setHeader("Content-Type", "text/html");
 
        resp.getWriter().println(imageUrl);
 
    }
}