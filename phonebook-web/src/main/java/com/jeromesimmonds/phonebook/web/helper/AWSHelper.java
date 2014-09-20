package com.jeromesimmonds.phonebook.web.helper;

import java.io.InputStream;
import java.net.URL;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

/**
 * @author Jerome Simmonds
 *
 */
public interface AWSHelper {
	
	PutObjectResult upload(String bucketName, String key, InputStream inputStream);
	
	void delete(String bucketName, String key);
	
	URL generateTempAccessURL(String bucketName, String key);
	
	S3Object getObject(String bucketName, String key);
	
	ObjectListing listObject(String bucketName, String prefix);	
}
