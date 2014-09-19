package com.jeromesimmonds.phonebook.web.helper;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

/**
 * @author Jerome Simmonds
 *
 */
public class AWSHelperImpl implements AWSHelper {

	private static final String X_AMZ_ACL = "x-amz-acl";
	private static final String PUBLIC_READ = "public-read";
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Override
	public PutObjectResult upload(String bucketName, String key, InputStream inputStream, ObjectMetadata metadata) {
		metadata.setHeader(X_AMZ_ACL, PUBLIC_READ);
		PutObjectRequest request = new PutObjectRequest(bucketName, key, inputStream, metadata);
		return s3Client.putObject(request);
	}

	@Override
	public void delete(String bucketName, String key) {
		DeleteObjectRequest request = new DeleteObjectRequest(bucketName, key);
		s3Client.deleteObject(request);
	}
	
	@Override
	public S3Object getObject(String bucketName, String key) {
		return s3Client.getObject(bucketName, key);
	}
	
	@Override
	public ObjectListing listObject(String bucketName, String prefix)	{
		ObjectListing oObjectListing = s3Client.listObjects(bucketName, prefix); 
		return oObjectListing;
	}
	
	@Override
	public URL generateTempAccessURL(String bucketName, String key) {
	    Date oneHourExpirationTime = new DateTime().plusHours(1).toDate();
	    return s3Client.generatePresignedUrl(bucketName, key, oneHourExpirationTime, HttpMethod.GET);
	}
}
